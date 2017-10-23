from BluetoothConnection import BluetoothConnection
import TypeConverter
import Packets
import random

def handle_engine_power(packet):
	print("Engine power")

def handle_target_requested(packet):
	connection.send_packet(Packets.RotationRequestPacket(random.uniform(25, 1000)))

def handle_rotation_request(packet):
	print("Rotation request")

# Mapping from ids to handlers
id_handler_map = {0x0 : handle_engine_power, 0x1 : handle_target_requested, 0x2 : handle_rotation_request}

def handle_packet(packet):
	id_handler_map[packet.get_id()](packet)

connection = BluetoothConnection("YAYER")
connection.connect()

# Receive packets in a loop
while True:
	packet = connection.receive_packet()

	handle_packet(packet)