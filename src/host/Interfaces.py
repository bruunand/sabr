
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
  def image_processing(self):
    """
      Description:
        Processes a set of frames from the sample data collected to construct bounding rectangles around the largets countours.

      Args:

      Returns:
        A set of bounding rectangles.
    """
    return


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