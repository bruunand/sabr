from BluetoothConnection import BluetoothConnection
import TypeConverter
import Packets
import random

def handle_target_request(packet):
	print("Sending target information")	
	packet = Packets.Packet.factory(Packets.PacketIds.TARGET_INFO_REQUEST)
	packet.set_frame_width(1000)
	packet.append_box(500, 100.2, 133.7)
	connection.send_packet(packet)

# Mapping from ids to handlers
# Handshake has no handler as it is handled by connector
id_handler_map = {Packets.PacketIds.TARGET_INFO_REQUEST : handle_target_request}

def handle_packet(packet):
	# Todo: Check if handler exists
	id_handler_map[packet.get_id()](packet)

connection = BluetoothConnection("YAYER")
connection.connect()

# Receive packets in a loop
while True:
	packet = connection.receive_packet()

	handle_packet(packet)