
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
	def get_direction_info(self):
		""" Calculates the pixel distance from the centre target object
			to the centre line of the frame. 
			Responabilities: Image processing
			object detection and calling appropirate calculation algorithms.

    	Args:
      		
    	Returns:
    		a floating point pixel length from the target to the centre line
    	"""
		return
	def get_distance_info(self):
		""" Calculates the distance in cm from the camera to the target object
			Responabilities: Image processing
			object detection and calling appropirate calculation algorithms.

    	Args:
      		
    	Returns:
    		a floating point distance to the target object
    	"""
		return

class IDistanceCalculatable(metaclass = abc.ABCMeta):
	
	@abc.abstractmethod
	def calculate_distance(self, mean_height, mean_width):
		""" asd

    	Args:
      		a frame
    	Returns:
    		np.array 
    	"""
		return

class IDirectionCalculatable(metaclass = abc.ABCMeta):

	@abc.abstractmethod
	def calculate_direction(self, frame_shape):
		""" asd

    	Args:
      		np.array containg the shape of the environment
    	Returns:
    		asd
    	"""
		return