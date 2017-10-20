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

class EnginePowerPacket(Packet):

    def __init__(self, engine_power):
        self.engine_power = engine_power

    def send_to_connection(self, connection):
        connection.send_short(self.engine_power)

    def construct_from_connection(self, connection):
        pass

    def get_id(self):
        return 0x00

class RequestTargetPacket(Packet):

    def __init__(self):
        pass

    def send_to_connection(self, connection):
        pass

    def construct_from_connection(self, connection):
        pass

    def get_id(self):
        return 0x01

class RotationRequestPacket(Packet):

    def __init__(self, degrees_to_rotate):
        self.degrees_to_rotate = degrees_to_rotate

    def send_to_connection(self, connection):
        connection.send_float(self.degrees_to_rotate)

    def construct_from_connection(self):
        pass

    def get_id(self):
        return 0x02