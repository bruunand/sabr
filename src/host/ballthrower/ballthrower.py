from ballthrower.packets import PacketIds, Packet
from ballthrower.bluetooth_connection import BluetoothConnection
from ballthrower.errors import NoPacketHandlerError
from ballthrower.target_info import TargetInfo


class BallThrower(object):
    def __init__(self, host_name):
        self.host_name = host_name
        self.target_info = None

    def handle_target_request(self, packet):
        print("Information requested")

        # Request target information from vision module
        bounding_boxes, frame_width = self.target_info.get_targets()

        # Construct and send packet
        packet = Packet.instantiate_from_id(PacketIds.TARGET_INFO_REQUEST)
        packet.set_frame_width(int(frame_width))
        for box in bounding_boxes:
            packet.append_box(box.x_min, box.width, box.height)

        self.connection.send_packet(packet)

    # Mapping from ids to handlers
    # Handshake has no handler as it is handled by connector
    id_handler_map = {PacketIds.TARGET_INFO_REQUEST: handle_target_request}

    # Query the id_handler_map for the appropriate method to run.
    def handle_packet(self, packet):
        if not BallThrower.id_handler_map.__contains__(packet.get_id()):
            raise NoPacketHandlerError(packet.get_id())
        else:
            BallThrower.id_handler_map[packet.get_id()](self, packet)

    def connect(self):
        self.connection = BluetoothConnection()
        self.connection.connect(self.host_name)

    def handle_packets(self):
        # When connected, initialize targetinfo
        self.target_info = TargetInfo(capture_device=1, debug=True)

        # Receive packets in a loop
        while True:
            packet = self.connection.receive_packet()

            self.handle_packet(packet)
