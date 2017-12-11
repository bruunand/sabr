from ballthrower.errors import MultipleCandidatesError, FaultyHandshakeError
from ballthrower.interfaces import Connection
from ballthrower.packets import PacketIds, Packet
from ballthrower.type_converter import *
import bluetooth
import time

# Find the nearest Bluetooth device with the given name.
def find_device(target_name):

    # Even though we only need to return one candidate, we
    # need to store them in an array so we can yield a proper
    # error message if several units with the same name exist.
    candidates = []

    # Check available devices - if name matches, add to candidates.
    for address, name in bluetooth.discover_devices(duration=4,lookup_names=True):
        if name == target_name:
            candidates.append(address)

    # No candidates were found.
    if len(candidates) == 0:
        return None

    elif len(candidates) > 1:
        # We can't be sure which one is the NXT - abort. 
        raise (MultipleCandidatesError(len(candidates), target_name))
    else:
        # Return the NXT (hopefully).
        return candidates[0]

# Main class for creating and maintaining a Bluetooth connection
# between the PC and other Bluetooth devices.
class BluetoothConnection(Connection):
    BLUETOOTH_PORT = 1

    # Verifies the established connection by confirming a
    # 'handshake' with the NXT. Mostly a formality here.
    def perform_handshake(self):
        
        # The NXT sends the first handshake.
        packet = self.receive_packet()

        # The first packet we should receive must be a handshake.
        # If it is not, abort.
        if packet.get_id() == PacketIds.HANDSHAKE:
            print("Received handshake with token %d" % packet.get_validation_token())

            # Send the handshake back to the NXT, confirming the handshake.
            self.send_packet(packet)
        else:
            raise FaultyHandshakeError(packet.get_id())

    # Connect to a device with the given name. 
    def connect(self, host_name=None):
        start_delay = 0.05
        delay = start_delay
        start_time = time.time()

        # Search for a candidate. Keep searching until a candidate is found
        while True:
            self.remote_address = find_device(host_name)

            if self.remote_address is not None:
                break
            else:
                if time.time() - start_time < 30:
                    print("Failed to find device, retrying...")
                else:
                    print("Failed to find device within 30 seconds. Now exiting.")
                    exit()

        start_time = time.time()

        # Attempt to connect to host
        while True:
            try:
                # Instantiate a new connection object and try to connect it. 
                new_connection = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
                new_connection.connect((self.remote_address, BluetoothConnection.BLUETOOTH_PORT))

                # If succeeded, perform the handshake. 
                self.remote_connection = new_connection
                self.perform_handshake()
                break

            except bluetooth.btcommon.BluetoothError as error:
                if time.time() - start_time < 30:
                    print("Failed to connect to device, retrying... (Bluetooth error %d)" % error.errno)
                    time.sleep(delay)
                    delay = delay * 1.5
                else:
                    print("Failed to connect to device with in 30 seconds. Now exiting.")
                    exit()


    # Read the data from the input stream and categorize
    # it as a type of packet.
    def receive_packet(self):
        # The first bit (byte because of padding) of the 
        # data stream is the packet ID. See report for 
        # further details.
        packet_id = self.remote_connection.recv(1)[0]

        # Instantiate a new empty packet from the ID.
        packet = Packet.instantiate_from_id(packet_id)

        # Fill the packet with the rest of the information
        # and return it. 
        packet.construct_from_connection(self)
        return packet

    # Close the current connection. 
    def disconnect(self):
        self.remote_connection.close()

    # Send packet data accross the established connection.
    def send_packet(self, packet):

        # First bit (byte because of padding) of a packet is 
        # the ID. 
        self.send_byte(packet.get_id())

        # Packets are responsible for transmitting the rest
        # of their properties themselves. 
        packet.send_to_connection(self)


# -------- Utility functions for sending and receiving data ------- #
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
