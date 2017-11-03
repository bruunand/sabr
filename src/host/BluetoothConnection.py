import TypeConverter
import Errors
import time
import Packets
from Interfaces import Connection
import bluetooth

def find_device(target_name):
    candidates = []

    for address, name in bluetooth.discover_devices(lookup_names=True):
        if name == target_name:
            candidates.append(address)

    if len(candidates) == 0:
        return None
    elif len(candidates) > 1:
        raise(Errors.MultipleCandidatesError(len(candidates), target_name))
    else:
        return candidates[0]

class BluetoothConnection(Connection):
    BLUETOOTH_PORT = 1

    def perform_handshake(self):
        # The NXT sends a handshake first, followed by a response from the host
        packet = self.receive_packet()

        if packet.get_id() == Packets.PacketIds.HANDSHAKE:
            print("Received handshake with token %d" % packet.get_validation_token())
            self.send_packet(packet)
        else:
            raise(Errors.FaultyHandshakeError(packet.get_id()))

    def connect(self, host_name=None):
        # Search for a candidate. Keep searching until a candidate is found
        while True:
            self.remote_address = find_device(host_name)

            if self.remote_address is not None:
                break
            else:
                print("Failed to find device, retrying...")

        # Attempt to connect to host
        while True:
            try:
                new_connection = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
                new_connection.connect((self.remote_address, BluetoothConnection.BLUETOOTH_PORT))
                self.remote_connection = new_connection
                self.perform_handshake()
                break
            except bluetooth.btcommon.BluetoothError as error:
                print("Failed to connect to device, retrying... (Bluetooth error %d)" % error.errno)

    def receive_packet(self):
        packet_id = self.remote_connection.recv(1)[0]

        # Instantiate from id using Packet's factory function
        packet = Packets.Packet.instantiate_from_id(packet_id)
        packet.construct_from_connection(self)

        return packet

    def disconnect(self):
        self.remote_connection.close()

    def send_packet(self, packet):
        # Each packet is initiated with its associated identifier
        self.send_byte(packet.get_id())

        # Packets are responsible for transmitting the rest of their properties themselves
        packet.send_to_connection(self)

    def send_byte(self, value):
        self.remote_connection.send(bytes([value]))

    def send_short(self, value):
        self.remote_connection.send(TypeConverter.short_to_bytes(value))

    def send_float(self, value):
        self.remote_connection.send(TypeConverter.float_to_bytes(value))

    def receive_byte(self):
        return self.remote_connection.recv(1)[0]

    def receive_short(self):
        return TypeConverter.bytes_to_short(self.remote_connection.recv(2))

    def receive_float(self):
        return TypeConverter.bytes_to_float(self.remote_connection.recv(4))