
import abc
from Interfaces import IImageFeedable

class ImageFeed(IImageFeedable):
	def capture_frame(self,captureDevice=1):
		return 1



