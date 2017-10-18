
import abc

class IObjectDetectable(metaclass = abc.ABCMeta):

	@abc.abstractmethod
	def detect_object(self):
		return