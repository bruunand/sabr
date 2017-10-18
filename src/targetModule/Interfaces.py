
import abc

class IImageFeedable(metaclass = abc.ABCMeta):

	@abc.abstractmethod
	def capture_image(self,captureDevice=1):
		""" Reads the current frame from the capture device

    	Args:
      		company_name : the id of the capture device (opencv perspective)
    	Returns:
      		the current frame
    	"""
		return

class IObjectDetectable(metaclass = abc.ABCMeta):

	@abc.abstractmethod
	def detect_object(self):
		""" asd

    	Args:
      		asd
    	Returns:
    		asd
    	"""
		return

class IDistanceCalculatable(metaclass = abc.ABCMeta):
	
	@abc.abstractmethod
	def calculate_distance(self):
		""" asd

    	Args:
      		asd
    	Returns:
    		asd
    	"""
		return

class IDirectionCalculatable(metaclass = abc.ABCMeta):

	@abc.abstractmethod
	def calculate_direction(self):
		""" asd

    	Args:
      		asd
    	Returns:
    		asd
    	"""
		return