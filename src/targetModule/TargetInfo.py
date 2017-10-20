import abc
import cv2
import numpy as np
from Interfaces import ITargetInfo
from ImageFeed import ImageFeedWebcamera
from DistanceCalculation import DistanceCalculationPolynomialRegression
from DirectionCalculation import DirectionCalculationVecLength

class TargetInfo(ITargetInfo):
    def get_direction_info(self):
        return

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
            mask = cv2.erode(mask,element, iterations=2)
            mask = cv2.dilate(mask,element,iterations=2)
            mask = cv2.erode(mask,element)

            # Create Contours for all objects in the defined colorspace
            _, contours, hierarchy = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

            if len(contours) == 0:
                continue

            # Search through the Countours for the largest object
            best_contour = max(contours, key = cv2.contourArea)

            # Create a bounding box around the largest object
            if best_contour is not None:

                # Draw the smallest rectangle possible around the object
                rect = cv2.minAreaRect(best_contour)

                # Get the four corners of rectangle x,y,w,h and contain the details in a box element
                box = cv2.boxPoints(rect)
                box = np.int0(box)

                # Append to the list of coordinate sets for the obtained bounding boxes
                bounding_boxes.append(box)

        # Return the coordinate sets
        return bounding_boxes

    def get_sample_data(self, sample_size):
        sample_data = []

        for i in range(0,sample_size):
            ret, frame = cam.read() 
            sample_data.append(frame)
        
        return sample_data

    def get_distance_info(self):
        bounding_boxes = []
        while not bounding_boxes:
            sample_data = self.get_sample_data(10)
            bounding_boxes = self.image_processing(sample_data)
        for (box, frame) in zip(bounding_boxes,sample_data):
            cv2.drawContours(frame, [box], -1, (0, 255, 0), 2)
        cv2.imshow('TEST', frame)
        cv2.waitKey(1)
        return -1

    webcam = ImageFeedWebcamera()

capture_device = 0
cam = cv2.VideoCapture(capture_device)

while True:
    asd = TargetInfo()
    asd.get_distance_info()