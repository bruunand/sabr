import abc
import cv2
import numpy as np
from Interfaces import ITargetInfo
from ImageFeed import ImageFeedWebcamera

class TargetInfo(ITargetInfo):

    # Initialize TargetInfo with default capture device set to 0 and sample size set to 10
    def __init__(self, capture_device = 0, sample_size = 10, debug = False):
        self._camera = cv2.VideoCapture(capture_device)
        self._sample_size = sample_size
        self._debug = debug

    def get_target_info(self):
    	
    	# Retrieve a list of sample data to be processed
    	sample_data = self.get_sample_data()

    	# Get the frame width of each frame in the sample list
    	frame_width = np.shape(sample_data[0])[1]

    	# Process the sample data to a list of bounding boxes (a bounding box / rectangle consists of four integers: x-coordinate, y-coordinate, width and height)
    	bounding_boxes = self.image_processing(sample_data)

    	for item in bounding_boxes:
    		print(item)

    	# Return the bounding boxes and frame width as a touple
    	return (bounding_boxes, frame_width)


    def image_processing(self, sample_data):

        bounding_boxes = []

        # For help on colorspaces: https://docs.opencv.org/3.2.0/df/d9d/tutorial_py_colorspaces.html
        lower_hsv_colour = np.array([169,100,100])
        upper_hsv_colour = np.array([189,255,255])

        for frame in sample_data:
            # Convert the current frame to HSV
            hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

            # Create a mask of the image (everything within the threshold appears white and everything else is black)
            mask = cv2.inRange(hsv, lower_hsv_colour, upper_hsv_colour)

            # Get rid of background noise using erosion
            element = cv2.getStructuringElement(cv2.MORPH_RECT,(3,3))
            mask = cv2.erode(mask, element, iterations=2)
            mask = cv2.dilate(mask, element, iterations=2)
            mask = cv2.erode(mask, element)

            # Create Contours for all objects in the defined colorspace
            _, contours, hierarchy = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

            if len(contours) == 0:
                continue

            # Search through the Countours for the largest object
            best_contour = max(contours, key = cv2.contourArea)

            # Create a bounding box around the largest object
            if best_contour is not None:
                bounding_boxes.append(cv2.boundingRect(best_contour))

        if len(sample_data) > 0:
            base_image = sample_data[0]

            for box in bounding_boxes:
                cv2.rectangle(base_image, (box[0], box[1]), (box[0] + box[2], box[1] + box[3]), (0, 255, 0), 3)

            if self._debug:
                cv2.imshow('debug', base_image)
                cv2.waitKey(1)

        # Return the coordinate sets
        return bounding_boxes

    def get_sample_data(self):
        sample_data = []

        for i in range(0, self._sample_size):
            ret, frame = self._camera.read() 
            sample_data.append(frame)
        
        return sample_data