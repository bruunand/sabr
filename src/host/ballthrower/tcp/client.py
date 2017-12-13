import socket
import cv2

from ballthrower.connection_helper import *
from ballthrower.target_info import BoundingBox


class Client:
    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.client_socket = None

    def connect(self):
        self.client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client_socket.connect((self.host, self.port))

    def send_image(self, frame):
        encoded = cv2.imencode('.jpg', frame)[1].tobytes()

        self.client_socket.send(uint_to_bytes(len(encoded)))
        self.client_socket.send(encoded)

    def handle_frame(self, frame=None):
        self.send_image(cv2.imread('test.jpg'))

        # Receive target information
        frame_width = receive_short(self.client_socket)
        target_count = receive_short(self.client_socket)
        boxes = []

        for i in range(target_count):
            x_min = receive_short(self.client_socket)
            y_min = receive_short(self.client_socket)
            width = receive_short(self.client_socket)
            height = receive_short(self.client_socket)

            boxes.append(BoundingBox.from_normalized(x_min, y_min, width, height))

        return boxes, frame_width
