from ballthrower.packets import PacketIds, Packet
from ballthrower.bluetooth_connection import BluetoothConnection
from ballthrower.errors import NoPacketHandlerError
from ballthrower.target_info import TargetInfo


# Class used for making communication and target identification
# work together. Similar in responsibility to the Robot class on
# the NXT.
from ballthrower.tcp.client import Client


class BallThrower(object):
    def __init__(self, host_name):
        self.host_name = host_name
        self.target_info = None
        self.connection = None

    # When a TARGET_INFO_REQUEST packet is received, fetch
    # data from the targeting module, package it, and send
    # accross the same connection.
    def handle_target_request(self, packet):
        # Request target information from vision module
        bounding_boxes, frame_width = self.target_info.get_targets()

        # Instantiate packet
        packet = Packet.instantiate_from_id(PacketIds.TARGET_INFO_REQUEST)

        # Insert data into packet
        packet.set_frame_width(int(frame_width))
        for box in bounding_boxes:
            packet.append_box(box.x_min, box.width, box.height)

        # Send packet
        self.connection.send_packet(packet)

    # Prints a debug string sent from the NXT
    def handle_debug(self, packet):
        print("Debug/NXT: {}".format(packet.message))

    # Mapping from Packet IDs to handler-methods
    # Handshake has no handler as it is handled by
    # 'BluetoothConnection.perform_handshake()'.
    id_handler_map = {PacketIds.TARGET_INFO_REQUEST: handle_target_request, PacketIds.DEBUG: handle_debug}

    # Query the id_handler_map for the appropriate method to run.
    def handle_packet(self, packet):
        if not BallThrower.id_handler_map.__contains__(packet.get_id()):
            raise NoPacketHandlerError(packet.get_id())
        else:
            BallThrower.id_handler_map[packet.get_id()](self, packet)

    # Establish a Bluetooth connection
    def connect(self):
        self.connection = BluetoothConnection()
        self.connection.connect(self.host_name)

    # Continuously check if packets are being received through
    # the Bluetooth connection. If they are, handle the packet.
    def handle_packets(self):
        # When connected, initialize target information
        self.target_info = TargetInfo(capture_device=1, debug=True, passthrough_client=Client("74.82.29.43", 9000))

        # Receive packets in a loop
        while True:
            packet = self.connection.receive_packet()

            self.handle_packet(packet)
