import socket
import cv2

from ballthrower.connection_utilities import *
from ballthrower.target_info import BoundingBox


class Client:
    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.client_socket = None

    def connect(self):
        self.client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client_socket.connect((self.host, self.port))

        print(f"Connected to {self.host}:{self.port}")

    def send_frame(self, frame):
        # Encode as a JPEG image
        encoded = cv2.imencode('.jpg', frame)[1].tobytes()

        # Send the length of the frame and the frame buffer
        send_uint(self.client_socket, len(encoded))
        self.client_socket.sendall(encoded)

    def get_targets(self, frame):
        # Send frame to host
        self.send_frame(frame)

        # Receive meta information
        frame_width = receive_short(self.client_socket)
        target_count = receive_short(self.client_socket)

        # Construct targets
        targets = []
        for i in range(target_count):
            x_min = receive_short(self.client_socket)
            y_min = receive_short(self.client_socket)
            width = receive_short(self.client_socket)
            height = receive_short(self.client_socket)

            targets.append(BoundingBox.from_normalized(x_min, y_min, width, height))

        return targets, frame_width
