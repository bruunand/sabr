import abc
import cv2
from Interfaces import IDirectionCalculatable
from math import sqrt
import numpy as np
import math

class DirectionCalculationVecLength(IDirectionCalculatable):
	
	# Found ratio between degress and pixels (see. Physical design: Rotating the platform)
	degrees_per_pixel = 0.1017

	def get_vec2line(self,frame_mid_x, bounding_box_centre_x):
		return (frame_mid-bounding_box_centre_x, 0)
	
	def get_vec_len(self,vec2line):
		return (sqrt((vec2line[0]**2+vec2line[1]**2)))
	
	def calculate_direction(self, bounding_box, frame_shape):
		bounding_box_centre_x = np.averge(bounding_box[:,0])
		bounding_box_centre_y = np.averge(bounding_box[:,1])
		frame_mid_x = int(frame_shape[1]/2)
		vec2line = self.get_vec2line(frame_mid_x,bounding_box_centre_x)
		length2line = self.get_vec_len(vec2line)
		angle = length2line*self.degrees_per_pixel
		return angle