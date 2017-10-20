import TypeConverter

try:
    import bluetooth
except ImportError:
    print("Bluetooth module is not installed on this PC.")
    # TODO: Raise error

def find_device(target_name):
    for address, name in bluetooth.discover_devices(lookup_names=True):
        if name == target_name:
            return address

    return None

# TODO: Error handling everywhere
class BluetoothConnection(object):

    BLUETOOTH_PORT = 1

    def __init__(self, host_name):
        self.host_name = host_name
        self.remote_connection = None
        self.remote_address = find_device(host_name)

    def connect(self):
        new_connection = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
        new_connection.connect((self.remote_address, BluetoothConnection.BLUETOOTH_PORT))
        self.remote_connection = new_connection
    
    def receive_packet(self):
        

    def disconnect(self):
        self.remote_connection.close()

    def send_packet(self, packet):
        self.send_byte(packet.get_id())
        packet.send_to_connection(self)

    def send_byte(self, content):
        self.remote_connection.send(bytes([content]))

    def send_short(self, content):
        self.remote_connection.send(TypeConverter.short_to_bytes(content))
