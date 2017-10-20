import type_converter

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
        print(self.remote_connection)

    def send(self, content):
        self.remote_connection.send(type_converter.short_to_bytes(content))