import Errors
from BluetoothConnection import BluetoothConnection
import TypeConverter
import Packets
import random
from TargetInfo import TargetInfo


def handle_target_request(packet):
    print("Information requested")

    # Request target information from vision module
    bounding_boxes, frame_width = target_info.get_targets()

    # Construct and send packet
    packet = Packets.Packet.instantiate_from_id(Packets.PacketIds.TARGET_INFO_REQUEST)
    packet.set_frame_width(int(frame_width))
    for box in bounding_boxes:
        packet.append_box(box.x_min, box.width, box.height)

    connection.send_packet(packet)


# Mapping from ids to handlers
# Handshake has no handler as it is handled by connector
id_handler_map = {Packets.PacketIds.TARGET_INFO_REQUEST: handle_target_request}

# Query the id_handler_map for the appropriate method to run.
def handle_packet(packet):
    if not id_handler_map.__contains__(packet.get_id()):
        raise Errors.NoPacketHandlerError(packet.get_id())
    else:
        id_handler_map[packet.get_id()](packet)


connection = BluetoothConnection()
connection.connect("YAYER")

# When connected, initialize targetinfo
target_info = TargetInfo(capture_device=1, debug=True)

# Receive packets in a loop
while True:
    packet = connection.receive_packet()

    handle_packet(packet)
