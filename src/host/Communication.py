from BluetoothConnection import BluetoothConnection
import TypeConverter
import Packets
import random

# Mapping from ids to handlers
# Handshake has no handler as it is handled by connector
id_handler_map = {}

def handle_packet(packet):
	id_handler_map[packet.get_id()](packet)

connection = BluetoothConnection("YAYER")
connection.connect()

# Receive packets in a loop
while True:
	packet = connection.receive_packet()

	handle_packet(packet)