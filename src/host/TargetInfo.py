import os
import cv2
import numpy as np
import tensorflow as tf
import Errors
from utils import label_map_util
from Interfaces import ITargetInfo
from PIL import Image
from utils import label_map_util
from utils import visualization_utils as vis_util
from math import floor


class BoundingBox():
    def __init__(self, x_min, x_max, y_min, y_max, width, height):
        self.x_min = x_min
        self.y_min = y_min
        self.x_max = x_max
        self.y_max = y_max
        self.width = width
        self.height = height

    def crop(self, from_image):
        return from_image[self.y_min:self.y_max, self.x_min:self.x_max]

    def get_centre(self):
        return (int(self.width / 2), int(self.height / 2))

    def __str__(self):
        return "x: {}-{}, y: {}-{}, width: {}, height: {}".format(self.x_min, self.y_min, self.y_min, self.y_max, self.width, self.height)

    def draw_rectangle(self, source_image):
        cv2.rectangle(source_image, (self.x_min, self.y_min), (self.x_max, self.y_max), (255, 0, 0), 3)

    def fromTensorFlowBox(source_width, source_height, box_array):
        y_min = floor(box_array[0] * source_height)
        x_min = floor(box_array[1] * source_width)
        y_max = floor(box_array[2] * source_height)
        x_max = floor(box_array[3] * source_width)

        return BoundingBox(x_min, x_max, y_min, y_max, x_max - x_min, y_max - y_min)

    def fromNormalized(x_min, y_min, width, height):
        return BoundingBox(x_min, x_min + width, y_min, y_min + height, width, height)


class TargetInfo(ITargetInfo):
    """
    This class is used for capturing frames of the environment
    and provide target object information to an embedded system

    Attributes:
        HSV_MAX_DEVIATION (float): Maximum deviation used in determining
            which HSV lower and upper bounds to be used.
        MODEL_NAME (string): Path to folder where the neural network object
            detection model resides.
        PATH_TO_CKPT (string): Path to frozen detection graph.
            This is the actual model that is used for the object detection.
        PATH_TO_LABELS (string): Path to the list labels used to classify
            detected objects.
        NUM_CLASSES (integer): number of categories for classification.
        detection_graph (object): a computation graph.
        label_map (list): list of labels.
        categories (list): list of dictionaries representing all possible categories.
        category_index (dictionary): a dictionary of the same entries as categories but
            the key value is a category id.

    Todo:
        *
    """
    HSV_MAX_DEVIATION = 0.4

    MODEL_NAME = 'redcuprcnn'

    PATH_TO_CKPT = MODEL_NAME + '/frozen_inference_graph.pb'

    PATH_TO_LABELS = os.path.join(MODEL_NAME, 'label_map.pbtxt')

    NUM_CLASSES = 1

    detection_graph = tf.Graph()

    label_map = label_map_util.load_labelmap(PATH_TO_LABELS)
    categories = label_map_util.convert_label_map_to_categories(label_map, max_num_classes=NUM_CLASSES,
                                                                use_display_name=True)
    category_index = label_map_util.create_category_index(categories)

    # Initialize TargetInfo with default capture device set to 1 and sample size set to 10
    def __init__(self, capture_device=3, debug=True):
        self._camera = cv2.VideoCapture(capture_device)
        self.debug = debug

        # Load frozen graph
        with self.detection_graph.as_default():
            od_graph_def = tf.GraphDef()
            with tf.gfile.GFile(self.PATH_TO_CKPT, 'rb') as fid:
                serialized_graph = fid.read()
                od_graph_def.ParseFromString(serialized_graph)
                tf.import_graph_def(od_graph_def, name='')

    def get_targets(self):
        """
        get_targets() gathers the necessary data need by the
        embedded system to calculate the direction or distance.

        args:

        returns:
            A tuple contaning a list of bounding boxes and
            an integer representing the frame width

        todo:
            *
        """

        # Retrieve a list of sample data to be processed
        frame = [cv2.imread('cup.jpg')]  # self.get_sample_data()

        # Get the width of a frame in the sample_data
        frame_width = frame.shape(frame[0])[1]

        # Process the sample data to a list of bounding boxes (a bounding box / rectangle consists of four integers: x-coordinate, y-coordinate, width and height)
        bounding_boxes = self.get_bounding_boxes(frame)

        return bounding_boxes, frame_width

    def get_bounding_boxes(self, frame):
        """
        image_processing() processes a collection of frames.
        It uses the neural network object detection model
        to detect red cups and uses these results to dynamically calculate the
        colour ranges for colour and contouring which sets the final bounding box
        around the red cups.

        args:
            sample data: an integer representing the number of frames to process.
        return:
            bounding_boxes: a list of 4-tuples each
                having the following form [top_x_pos,top_y_pos,width,height].

        todo:
            * (maybe) split this function into smaller functions.

        """
        all_bounding_boxes = []

        # Colour ranges for colour and contouring
        lower_hsv_colour = np.array([0, 0, 0])
        upper_hsv_colour = np.array([0, 0, 0])

        with self.detection_graph.as_default():
            with tf.Session(graph=self.detection_graph) as sess:
                # Expand dimensions since the model expects images to have shape: [1, None, None, 3]
                image_np_expanded = np.expand_dims(frame, axis=0)
                image_tensor = self.detection_graph.get_tensor_by_name('image_tensor:0')

                # Each box represents a part of the image where a particular object was detected.
                boxes = self.detection_graph.get_tensor_by_name('detection_boxes:0')

                # Each score represent how level of confidence for each of the objects.
                # Score is shown on the result image, together with the class label.
                scores = self.detection_graph.get_tensor_by_name('detection_scores:0')
                classes = self.detection_graph.get_tensor_by_name('detection_classes:0')
                num_detections = self.detection_graph.get_tensor_by_name('num_detections:0')

                # Actual detection.
                (boxes, scores, classes, num_detections) = sess.run([boxes, scores, classes, num_detections],
                                                                    feed_dict={image_tensor: image_np_expanded})

                # Squeeze score and box arrays as they are both single-dimensional arrays of arrays
                scores = np.squeeze(scores)
                boxes = np.squeeze(boxes)

                # Iterate detections and filter based on score
                # There is a mapping between the indices of scores and boxes
                # Meaning that the score of index 0 is associated with the box that has index 0
                filtered_boxes = []
                for index, score in enumerate(scores):
                    if score >= 0.5:
                        filtered_boxes.append(boxes[index])

                # If no boxes were found, return empty list
                if len(filtered_boxes) == 0:
                    return all_bounding_boxes

                # Normalize box sizes by converting them to BoundingBox classes
                height, width, _, = np.shape(frame)
                filtered_boxes = [BoundingBox.fromTensorFlowBox(width, height, box) for box in filtered_boxes]

                # Crop all cups out of the image
                for index, box in enumerate(filtered_boxes):
                    # Crop the subset of the image corresponding to the bounding box
                    cropped = box.crop(frame)
                    cropped_hsv = cv2.cvtColor(cropped, cv2.COLOR_BGR2HSV)

                    # Get the colour value at the center of the bounding box
                    crop_centre = box.get_centre()
                    centre_colour_hsv = cropped_hsv[crop_centre[1], crop_centre[0]]

                    # Get lower and upper bounds based on centre colour
                    for i in range(3):
                        lower_hsv_colour[i] = int(centre_colour_hsv[i] * (1 - TargetInfo.HSV_MAX_DEVIATION))
                        upper_hsv_colour[i] = int(centre_colour_hsv[i] * (1 + TargetInfo.HSV_MAX_DEVIATION))

                    # Mask colour with dynamically retrieved range
                    crop_masked = cv2.inRange(cropped_hsv, lower_hsv_colour, upper_hsv_colour)

                    # Create contours for all objects in the defined colorspace
                    _, contours, _ = cv2.findContours(crop_masked.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)

                    # If no contours are found, we cann
                    if len(contours) == 0:
                        all_bounding_boxes.append(box)
                        continue

                    # Get the largest contour
                    contour = max(contours, key=cv2.contourArea)
                    area = cv2.boundingRect(contour)

                    # Define a narrow bounding box and add it to the list of all boxes
                    narrow_box = BoundingBox.fromNormalized(box.x_min + area[0], box.y_min + area[1], area[2],
                                                            area[3])
                    all_bounding_boxes.append(narrow_box)

        # If debugging is enabled draw all bounding boxes on the first frame and show the result
        if self.debug:
            for box in all_bounding_boxes:
                box.draw_rectangle(frame)

            cv2.imwrite('target_debug.png', frame)

        # Return the coordinate sets
        return all_bounding_boxes

    def capture_frame(self):
        """
        capture_frame(): uses the capture device to capture a frame from the
            capture device.

        retuns:
            frame: an image frame retrieved from the capture device.

        """
        return_value, frame = self._camera.read()

        # Exits if no frame is returned from _camera.read() function
        if not return_value:
            raise Errors.CaptureDeviceUnavailableError()

        return frame


t = TargetInfo()
t.get_targets()
