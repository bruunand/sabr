import abc


class Connection(metaclass=abc.ABCMeta): # Todo: Descriptions for this interface
    @abc.abstractmethod
    def connect(self, host_name=None):
        """
            Description:
                Connect to remote host. Return when handshake has been performed.
        """
        pass

    def disconnect(self):
        """
            Description:
                Disconnect from remote host.
        """
        pass

    @abc.abstractmethod
    def receive_packet(self):
        pass

    @abc.abstractmethod
    def send_packet(self, packet):
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


class ITargetInfo(metaclass=abc.ABCMeta):
    @abc.abstractmethod
    def get_target_info(self):
        """
            Description:
                Calls the get_sample_data() method and passes the return value to the image_processing() method.

            Args:

            Returns:
                A touple consisting of a set of bounding rectangles and the frame width of one of the frames in the sample data
        """
        pass

    @abc.abstractmethod
    def image_processing(self, sample_data):
        """
            Description:
                Processes a set of frames from the passed sample data to construct bounding rectangles around the largets countours.

            Args:
                sample_data - a list of frames

            Returns:
                A set of bounding rectangles.
        """
        pass

    @abc.abstractmethod
    def get_sample_data(self):
        """
            Description:
                Queries the camera to take x amount of pictures.

            Args:

            Returns:
                A set of frames / pictures.
        """
        pass
