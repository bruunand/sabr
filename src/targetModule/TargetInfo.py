import abc
import cv2
import numpy as np
from Interfaces import ITargetInfo
from ImageFeed import ImageFeedWebcamera

class TargetInfo(ITargetInfo):
    def get_direction_info(self):
        """ Calculates the pixel distance from the centre target object
            to the centre line of the frame. 
            Responabilities: Image processing
            object detection and calling appropirate calculation algorithms.

        Args:
            
        Returns:
            a floating point pixel length from the target to the centre line
        """
        return

    def image_processing(self, sample_data):

        bounding_boxes = []

        # For help on colorspaces: https://docs.opencv.org/3.2.0/df/d9d/tutorial_py_colorspaces.html
        lower_hsv_colour = np.array([0,100,100])
        upper_hsv_colour = np.array([10,255,255])

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
        webcam = ImageFeedWebcamera()
        sample_data = []

        for i in range(0,sample_size):
            frame = webcam.capture_frame(0)  
            sample_data.append(frame)
        
        return sample_data


    def get_distance_info(self):
        self.image_processing(self.get_sample_data(10))

        """ Calculates the distance in cm from the camera to the target object
            Responabilities: Image processing
            object detection and calling appropirate calculation algorithms.

        Args:
            
        Returns:
            a floating point distance to the target object
        """
        return






target = TargetInfo()
target.get_distance_info()