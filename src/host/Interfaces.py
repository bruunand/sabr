
import abc

class IImageFeedable(metaclass = abc.ABCMeta):

  @abc.abstractmethod
  def capture_frame(self,captureDevice=1):
    """ Reads the current frame from the capture device

      Args:
          company_name : the id of the capture device (opencv perspective)
      Returns:
          the current frame
      """
    return

class ITargetInfo(metaclass = abc.ABCMeta):

  @abc.abstractmethod
  def get_target_info(self):
    """
      Description:
        Calls the get_sample_data() method and passes the return value to the image_processing() method.

      Args:

      Returns:
        A touple consisting of a set of bounding rectangles and the frame width of one of the frames in the sample data
    """


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


  @abc.abstractmethod
  def get_sample_data(self):
    """
      Description:
        Queries the camera to take x amount of pictures.

      Args:

      Returns:
        A set of frames / pictures.
    """

class IDistanceCalculatable(metaclass = abc.ABCMeta):
  
  @abc.abstractmethod
  def calculate_distance(self, box_sample_list):
    """ asd

      Args:
          a frame
      Returns:
        np.array 
      """
    return

class IDirectionCalculatable(metaclass = abc.ABCMeta):

  @abc.abstractmethod
  def calculate_direction(self, bounding_box, frame_shape):
    """ asd

      Args:
          np.array containing the shape of the environment
          np.array containing the bounding box of the target object
      Returns:
        asd
      """
    return