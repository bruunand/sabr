from abc import ABC, abstractmethod
from enum import IntEnum


class PacketIds(IntEnum):
    HANDSHAKE = 0x0
    TARGET_INFO_REQUEST = 0x1


# Packet class - abstract, as only concrete packets can be sent
class Packet(ABC):
    @abstractmethod
    def send_to_connection(self, connection):
        pass

    @abstractmethod
    def construct_from_connection(self, connection):
        pass

    @abstractmethod
    def get_id(self):
        pass

    @staticmethod
    def instantiate_from_id(packet_id):
        if packet_id == PacketIds.HANDSHAKE: return HandshakePacket()
        if packet_id == PacketIds.TARGET_INFO_REQUEST: return TargetInfoRequestPacket()

        return None


class HandshakePacket(Packet):
    def __init__(self):
        pass

    def send_to_connection(self, connection):
        connection.send_short(self.validation_token)

    def construct_from_connection(self, connection):
        self.validation_token = connection.receive_short()

    def get_validation_token(self):
        return self.validation_token

    def get_id(self):
        return PacketIds.HANDSHAKE


class TargetInfoRequestPacket(Packet):
    def __init__(self):
        self.x_values = []
        self.width_values = []
        self.height_values = []
        self.frame_width = -1

    def set_frame_width(self, width):
        self.frame_width = width

    def append_box(self, x, width, height):
        self.x_values.append(x)
        self.width_values.append(width)
        self.height_values.append(height)

    def send_to_connection(self, connection):
        assert self.frame_width != -1

        # Write frame width first
        connection.send_short(self.frame_width)

        # Write length of box instances
        connection.send_byte(len(self.x_values))

        # Write box instances
        for i in range(len(self.x_values)):
            connection.send_short(self.x_values[i])

            connection.send_short(self.width_values[i])
            connection.send_short(self.height_values[i])

    def construct_from_connection(self, connection):
        pass

    def get_id(self):
        return PacketIds.TARGET_INFO_REQUEST
