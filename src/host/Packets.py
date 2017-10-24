from abc import ABC, abstractmethod
from enum import IntEnum

class PacketIds(IntEnum):
    HANDSHAKE = 0x0
    TARGET_DIRECTION_REQUEST = 0x1

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

    def factory(id):
        if id == PacketIds.HANDSHAKE: return HandshakePacket()

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