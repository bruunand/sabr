import abc
from Interfaces import IImageFeedable
import cv2

class ImageFeedWebcamera(IImageFeedable):
	self.cam = 0
	
	def capture_frame(self, captureDevice=1):
		cam = cv2.VideoCapture(captureDevice)
		ret, frame = cam.read()
		return frame