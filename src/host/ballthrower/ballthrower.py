from ballthrower.packets import PacketIds, Packet
from ballthrower.bluetooth_connection import BluetoothConnection
from ballthrower.errors import NoPacketHandlerError
from ballthrower.target_info import TargetInfo


# Class used for making communication and target identification
# work together. Similar in responsibility to the Robot class on 
# the NXT. 
class BallThrower(object):
    def __init__(self, host_name):
        self.host_name = host_name


    # When a TARGET_INFO_REQUEST packet is received, fetch
    # data from the targeting module, package it, and send 
    # accross the same connection. 
    def handle_target_request(self, packet):
        print("Information requested")

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


    # Mapping from Packet IDs to handler-methods
    # Handshake has no handler as it is handled by connector. 
    id_handler_map = {PacketIds.TARGET_INFO_REQUEST: handle_target_request}

    # When receiving a packet, verify the Packet ID, and
    # run the appropriate method. 
    def handle_packet(packet):
        if not BallThrower.id_handler_map.__contains__(packet.get_id()):
            raise NoPacketHandlerError(packet.get_id())
        else:
            BallThrower.id_handler_map[packet.get_id()](packet)

    # Establish a bluetooth connection
    def connect(self):
        self.connection = BluetoothConnection()
        self.connection.connect(self.host_name)

    # Continuously check if packets are being received through
    # the Bluetooth connection. If they are, handle the packet.
    def handle_packets(self):
        # When connected, initialize targetinfo
        target_info = TargetInfo(capture_device=1, debug=True)

        # Receive packets in a loop
        while True:
            packet = self.connection.receive_packet()

            self.handle_packet(packet)
