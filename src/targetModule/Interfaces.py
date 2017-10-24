
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
  def image_processing(self, sample_data):
    """
      Description:
        Processes a set of frames in the sample_data to construct bounding rectangles around the largets countours.

      Args:
        sample_data - a set of frames to be processed.

      Returns:
        A set of bounding rectangles.
    """
    return

  @abc.abstractmethod
  def get_box_data(self):
    """ Calculates the pixel distance from the centre target object
      to the centre line of the frame. 
      Responabilities: Image processing
      object detection and calling appropirate calculation algorithms.

      Args:
          
      Returns:
        a floating point pixel length from the target to the centre line
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