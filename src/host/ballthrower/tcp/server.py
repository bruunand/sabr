import socket
import threading
import numpy as np
import cv2

from ballthrower.target_info import TargetInfo
from ballthrower.connection_helper import *


target_info = TargetInfo(capture_device=-1)


def receive_image(client_socket):
    # Read image length
    image_length = receive_uint(client_socket)

    # Read image bytes
    image_bytes = np.frombuffer(receive_bytes(client_socket, image_length), dtype="byte")

    # Decode bytes to image
    img_np = cv2.imdecode(image_bytes, cv2.IMREAD_COLOR)

    return img_np


def client_handler(client_socket):
    while True:
        boxes, width = target_info.get_targets(receive_image(client_socket))

        send_short(client_socket, width)
        send_short(client_socket, len(boxes))

        for box in boxes:
            send_short(client_socket, box.x_min)
            send_short(client_socket, box.y_min)
            send_short(client_socket, box.width)
            send_short(client_socket, box.height)


class Server:
    def __init__(self, port):
        self.port = port
        self.server_socket = None

    def listen(self):
        self.server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # Bind to all interfaces on specified port
        self.server_socket.bind(('', self.port))
        self.server_socket.listen(16)

        # Accept incoming connections
        while True:
            (client, address) = self.server_socket.accept()

            client_thread = threading.Thread(target=client_handler, args=[client])
            client_thread.start()
