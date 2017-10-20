from BluetoothConnection import BluetoothConnection

connection = BluetoothConnection("YAYER")
connection.connect()

# Receive packets in a loop
while True:
	packet = connection.receive_packet()