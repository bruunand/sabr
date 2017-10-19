import abc
import cv2
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
        cv2.imshow('img', sample_data[0])
        cv2.waitKey(1)


        bounding_boxes = []
        #for frame in sample_data:
            # Find bounding box for frame
            # bounding_box = 
            # bounding_boxes.append(bounding_box)

        return bounding_boxes

    def get_sample_data(self):
        webcam = ImageFeedWebcamera()
        sample_data = []

        for i in range(0,10):
            frame = webcam.capture_frame()  
            sample_data.append(frame)
        
        return sample_data


    def get_distance_info(self):
        self.image_processing(self.get_sample_data())

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