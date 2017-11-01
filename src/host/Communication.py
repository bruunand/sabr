import Errors
from BluetoothConnection import BluetoothConnection
import TypeConverter
import Packets
import random
from TargetInfo import TargetInfo


def handle_target_request(packet):
    # Request target information from vision module
    bounding_boxes, frame_width = target_info.get_target_info()

    # Construct and send packet
    packet = Packets.Packet.instantiate_from_id(Packets.PacketIds.TARGET_INFO_REQUEST)
    packet.set_frame_width(int(frame_width))
    for box in bounding_boxes:
        packet.append_box(int(box[0]), box[2], box[3])

    connection.send_packet(packet)


# Mapping from ids to handlers
# Handshake has no handler as it is handled by connector
id_handler_map = {Packets.PacketIds.TARGET_INFO_REQUEST: handle_target_request}


def handle_packet(packet):
    if not id_handler_map.__contains__(packet.get_id()):
        raise Errors.NoPacketHandlerError(packet.get_id())
    else:
        id_handler_map[packet.get_id()](packet)


connection = BluetoothConnection()
connection.connect("YAYER")

# When connected, initialize targetinfo
target_info = TargetInfo(sample_size=10, capture_device=0, debug=True)

# Receive packets in a loop
while True:
    packet = connection.receive_packet()

    handle_packet(packet)
