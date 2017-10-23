from abc import ABC, abstractmethod

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
        if id == 0x0: return HandshakePacket()

        return None

class HandshakePacket(Packet):

    def __init__(self):
        pass

    def send_to_connection(self, connection):
        connection.send_short(self.validation_token)

    def construct_from_connection(self, connection):
        self.validation_token = connection.receive_short()

    def get_id(self):
        return 0x0