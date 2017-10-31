from BluetoothConnection import BluetoothConnection
import TypeConverter
import Packets
import random
from TargetInfo import TargetInfo

def handle_target_request(packet):
	print("Target information requested")	

	# Request target information from vision module
	bounding_boxes, frame_width = target_info.get_target_info()

	# Construct and send packet
	packet = Packets.Packet.factory(Packets.PacketIds.TARGET_INFO_REQUEST)

	packet.set_frame_width(int(frame_width))
	for box in bounding_boxes:
		packet.append_box(int(box[0]), box[2], box[3])

	connection.send_packet(packet)

	print("Done")

# Mapping from ids to handlers
# Handshake has no handler as it is handled by connector
id_handler_map = {Packets.PacketIds.TARGET_INFO_REQUEST : handle_target_request}

def handle_packet(packet):
	# Todo: Check if handler exists
	id_handler_map[packet.get_id()](packet)

connection = BluetoothConnection("YAYER")
connection.connect()

# When connected, initialize targetinfo
target_info = TargetInfo(sample_size=5,capture_device = 0)

# Receive packets in a loop
while True:
	packet = connection.receive_packet()

	handle_packet(packet)
