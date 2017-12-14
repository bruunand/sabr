from ballthrower.type_converter import *


def send_byte(socket, value):
    socket.send(bytes([value]))


def send_short(socket, value):
    socket.send(short_to_bytes(value))


def send_float(socket, value):
    socket.send(float_to_bytes(value))


def send_uint(socket, value):
    socket.send(uint_to_bytes(value))


def send_string(socket, string):
    encoded_string = string.encode("utf-8")
    send_short(socket, len(encoded_string))
    socket.send(encoded_string)


def receive_bytes(socket, length):
    buffer = bytearray()

    while len(buffer) < length:
        buffer.extend(socket.recv(length - len(buffer)))

    return buffer


def receive_byte(socket):
    return receive_bytes(socket, 1)[0]


def receive_short(socket):
    return bytes_to_short(receive_bytes(socket, 2))


def receive_float(socket):
    return bytes_to_float(receive_bytes(socket, 4))


def receive_uint(socket):
    return bytes_to_uint(receive_bytes(socket, 4))


def receive_string(socket):
    string_length = receive_short(socket)

    return receive_bytes(socket, string_length).decode("utf-8")

