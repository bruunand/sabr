
import abc
from Interfaces import IImageFeedable

class ImageFeed(IImageFeedable):
	def capture_image(self,captureDevice=1):
		return 1



