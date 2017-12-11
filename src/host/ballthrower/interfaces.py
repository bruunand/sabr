import abc
# ------ Interfaces are implemented as abstract classes. ------ #

# Interface used for Bluetooth connections. 
# Implements basic required methods for opening
# and closing connections, and sending data 
# of different formats and sizes.
class Connection(metaclass=abc.ABCMeta):  
    @abc.abstractmethod
    def connect(self, host_name=None):
        """
            Description:
                Connect to remote  Return when handshake has been performed.
        """
        pass

    def disconnect(self):
        """
            Description:
                Disconnect from remote.
        """
        pass

    @abc.abstractmethod
    def receive_packet(self):
        """
            Description: 
                Classify packet and deserialize data
                appropriately.
        """
        pass

    @abc.abstractmethod
    def send_packet(self, packet):
        """
            Description: 
                Send packet data accross connection.
        """
        pass

    @abc.abstractmethod
    def send_byte(self, value):
        pass

    @abc.abstractmethod
    def send_short(self, value):
        pass

    @abc.abstractmethod
    def send_float(self, value):
        pass

    @abc.abstractmethod
    def receive_byte(self):
        pass

    @abc.abstractmethod
    def receive_short(self):
        pass

    @abc.abstractmethod
    def receive_float(self):
        pass


# Interface used for implementing basic methods
# for requesting and receiving target data from
# the image recognition module.
class ITargetInfo(metaclass=abc.ABCMeta):
    @abc.abstractmethod
    def get_targets(self):
        """
            Description:
                Calls the get_sample_data() method and passes the return value to the image_processing() method.

            Args:

            Returns:
                A touple consisting of a set of bounding rectangles and the frame width of one of the frames in the sample data
        """
        pass

    @abc.abstractmethod
    def get_bounding_boxes(self, farme):
        """
            Description:
                Processes a frame from the passed sample data to construct bounding rectangles around the largets countours.

            Args:
                frame - a frame to process

            Returns:
                A set of BoundingBox instances.
        """
        pass

    @abc.abstractmethod
    def get_frame(self):
        """
            Description:
                Get a frame to be used for further processing.

            Args:

            Returns:
                A frame.
        """
        pass
