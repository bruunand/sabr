
import abc

class IDirectionCalculatable(metaclass = abc.ABCMeta):

	@abc.abstactmethod
	def calculate_direction(self):
		return