from ballthrower.errors import MultipleCandidatesError, FaultyHandshakeError
from ballthrower.interfaces import Connection
from ballthrower.packets import PacketIds, Packet
from ballthrower.type_converter import *
import bluetooth

def find_device(target_name):
    # Even though we only need to return one candidate, we
    # need to store them in an array so we can yield a proper
    # error message if several units with the same name exist.

    candidates = []

    for address, name in bluetooth.discover_devices(lookup_names=True):
        if name == target_name:
            candidates.append(address)

    if len(candidates) == 0:
        return None
    # There should only be one unit in distance with this name, abort
    elif len(candidates) > 1:
        raise (MultipleCandidatesError(len(candidates), target_name))
    else:
        return candidates[0]


class BluetoothConnection(Connection):
    BLUETOOTH_PORT = 1

    def perform_handshake(self):
        # The NXT sends a handshake first, followed by a response from the host
        packet = self.receive_packet()

        if packet.get_id() == PacketIds.HANDSHAKE:
            print("Received handshake with token %d" % packet.get_validation_token())
            self.send_packet(packet)
        else:
            raise FaultyHandshakeError(packet.get_id())

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
        packet = Packet.instantiate_from_id(packet_id)
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
        self.remote_connection.send(short_to_bytes(value))

    def send_float(self, value):
        self.remote_connection.send(float_to_bytes(value))

    def receive_byte(self):
        return self.remote_connection.recv(1)[0]

    def receive_short(self):
        return bytes_to_short(self.remote_connection.recv(2))

    def receive_float(self):
        return bytes_to_float(self.remote_connection.recv(4))
