import TypeConverter
import Errors
import time
import Packets

try:
    import bluetooth
except ImportError:
    print("Bluetooth module is not installed on this PC.")
    # TODO: Raise error

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

# TODO: Error handling everywhere
class BluetoothConnection(object):
    BLUETOOTH_PORT = 1
    NUM_SEARCH_ATTEMPTS = 5
    NUM_CONNECTION_ATTEMPTS = 20
    SLEEP_BETWEEN_RETRIES = 1.0 # Measured in seconds

    def __init__(self, host_name):
        self.host_name = host_name
        self.remote_connection = None

        # Search for a candidate. If no candidate is found, throw an error
        for attempt in range(BluetoothConnection.NUM_SEARCH_ATTEMPTS):
            self.remote_address = find_device(host_name)

            if self.remote_address != None:
                break
            else:
                print("Failed to find device, retrying...")
                time.sleep(BluetoothConnection.SLEEP_BETWEEN_RETRIES)
        else:
            raise(Errors.CandidateNotFoundError(self.host_name))

    def connect(self):
        for attempt in range(BluetoothConnection.NUM_CONNECTION_ATTEMPTS):
            try:
                new_connection = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
                new_connection.connect((self.remote_address, BluetoothConnection.BLUETOOTH_PORT))
                self.remote_connection = new_connection
                break
            except bluetooth.btcommon.BluetoothError as error:
                print("Failed to connect to device, retrying...")
                time.sleep(BluetoothConnection.SLEEP_BETWEEN_RETRIES)
        else:
            raise(Errors.FailedToConnectError(self.host_name))
    
    def receive_packet(self):
        # Todo: Error handling
        packet_id = self.remote_connection.recv(1)[0]

        # Instantiate from id using Packet's factory
        packet = Packets.Packet.factory(packet_id)
        packet.construct_from_connection(self)

        return packet

    def disconnect(self):
        self.remote_connection.close()

    def send_packet(self, packet):
        # Todo: Error handling
        self.send_byte(packet.get_id())
        packet.send_to_connection(self)

    def send_byte(self, content):
        self.remote_connection.send(bytes([content]))

    def send_short(self, content):
        self.remote_connection.send(TypeConverter.short_to_bytes(content))

    def send_float(self, content):
        self.remote_connection.send(TypeConverter.float_to_bytes(content))

    def receive_byte(self):
        return self.remote_connection.recv(1)[0]

    def receive_short(self):
        return TypeConverter.bytes_to_short(self.remote_connection.recv(2))

    def receive_float(self):
        return TypeConverter.bytes_to_float(self.remote_connection.recv(4))