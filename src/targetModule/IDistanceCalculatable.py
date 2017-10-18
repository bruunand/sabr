
import abc

class IDistanceCalculatable(metaclass = abc.ABCMeta):
	
	@abc.abstractmethod
	def calculate_distance(self):
		return