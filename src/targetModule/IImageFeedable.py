
import abc

class IImageFeedable(metaclass = abc.ABCMeta):

	@abc.abstractmethod
	def capture_image(self,captureDevice=1):
		return
