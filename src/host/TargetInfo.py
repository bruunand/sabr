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


# Normalizes a box from TensorFlow and returns it as a tuple
def normalize_box(im_width, im_height, box):

class TargetInfo(ITargetInfo):
    """ 
    This class is used for capturing frames of the environment
    and provide target object information to an embedded system
    
    Attributes:
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
    def __init__(self, capture_device=1, sample_size=10, target_width=8.25, target_height=10.5, debug=True):
        self._camera = cv2.VideoCapture(capture_device)
        # self._camera.set(3,1600)
        # self._camera.set(4,1200)
        self._sample_size = sample_size
        self._debug = debug
        self._aspect_ratio = target_width / target_height

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
        sample_data = self.get_sample_data()

        # Get the width of a frame in the sample_data
        frame_width = np.shape(sample_data[0])[1]

        # Process the sample data to a list of bounding boxes (a bounding box / rectangle consists of four integers: x-coordinate, y-coordinate, width and height)
        bounding_boxes = self.image_processing(sample_data)

        # Return the bounding boxes and frame width as a touple
        return (bounding_boxes, frame_width)

    def image_processing(self, sample_data):
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
        bounding_boxes = []

        # Colour ranges for colour and contouring
        lower_hsv_colour = np.array([0, 0, 0])
        upper_hsv_colour = np.array([0, 0, 0])

        with self.detection_graph.as_default():
            with tf.Session(graph=self.detection_graph) as sess:
                for frame in sample_data:
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
                    (boxes, scores, classes, num_detections) = sess.run(
                        [boxes, scores, classes, num_detections],
                        feed_dict={image_tensor: image_np_expanded})
                    vis_util.visualize_boxes_and_labels_on_image_array()
                    percent_difference = 0.40

                    height, width, _ = np.shape(frame)
                    # Squeeze all detected boxes into a list of tuples
                    y_min, x_min, y_max, x_max = np.squeeze(boxes)[0]
                    # Normalize the positions
                    y_min = int(y_min * height)
                    x_min = int(x_min * width)
                    y_max = int(y_max * height)
                    x_max = int(x_max * width)

                    centre_x, centre_y = int((x_max + x_min) / 2), int((y_max + y_min) / 2)
                    # Get the colour value at the center of bounding box
                    colour = frame[centre_y, centre_x]
                    colour = np.uint8([[[int(colour[0]), int(colour[1]), int(colour[2])]]])
                    # Convert the current frame with a BGR color profile to a frame with a HSV color profile
                    hsv_colour = cv2.cvtColor(colour, cv2.COLOR_BGR2HSV)
                    hsv_1dcolor = np.array(hsv_colour[0][0])

                    # Set the colour bounds based on the colour at the center of the
                    ## bounding box.
                    for i in range(3):
                        lower_hsv_colour[i] = int(hsv_1dcolor[i] * (1 - percent_difference))
                        upper_hsv_colour[i] = int(hsv_1dcolor[i] * (1 + percent_difference))

                    hsv_red = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

                    mask_red = cv2.inRange(hsv_red, lower_hsv_colour, upper_hsv_colour)

                    # Create contours for all objects in the defined colorspace
                    _, contours, _ = cv2.findContours(mask_red.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)

                    if len(contours) == 0:
                        continue

                    # Get the largest contour
                    contour = max(contours, key=cv2.contourArea)
                    area = cv2.boundingRect(contour)
                    size = cv2.contourArea(contour)

                    # Continue if no bounding box is found
                    if size == 0:
                        continue
                    bounding_boxes.append(area)
                    print("iaro ist und faglord")

        # Refine bounding box set
        ## OBS !!!! Currently removes too much !!!! OBS
        # bounding_boxes = self.remove_outliers(bounding_boxes)


        # If debugging is enabled draw all bounding boxes on the first frame and show the result
        if self._debug and len(sample_data) > 0:

            base_image = sample_data[0]
            for box in bounding_boxes:
                cv2.rectangle(base_image, (box[0], box[1]), (box[0] + box[2], box[1] + box[3]), (0, 255, 0), 3)

            cv2.imshow('debug', base_image)
            cv2.waitKey(1)

        # Return the coordinate sets
        return bounding_boxes

    def remove_outliers(self, bounding_boxes, max_deviance=20, max_return_size=100):
        """
        remove_outliers(): a simple outlier_detection based on a red cups aspect ratio.

        args:
            bounding_boxes: a list of 4-tuples representing the calculated bounding
                boxes around the red cups.
            max_deviance: integer representing the maximum amount the aspect ration
                of each bounding can deviate.
            max_return_size: integer defining the maximum amount of bounding boxes
                that can be returned by this function.
        return:
            refined_set: a list of bounding boxes where obvoius outliers have been
                removed.
        todo:
            * Mikkel Jarlund comment this function
            * Removes all bounding boxes with the new object detection method
            ** PLZ FIX

        """
        refined_dict = {}
        refined_set = []

        for box in bounding_boxes:
            aspect_ratio = box[2] / box[3]

            ar_deviance = abs(self._aspect_ratio - aspect_ratio) / ((self._aspect_ratio + aspect_ratio) / 2) * 100

            if ar_deviance < max_deviance:
                refined_dict[ar_deviance] = box

        i = 0

        for key in sorted(refined_dict):
            if i == max_return_size:
                break
            refined_set.append(refined_dict[key])
            i = i + 1

        return refined_set

    def get_sample_data(self):
        """
        get_sample_data(): uses the capture device to take a number
            of fames, this number is provided through the class contructor.

        retuns:
            sample_data: a list of arrays each representing a captured frame.

        """
        sample_data = []

        # Add x frames to the sample data collection
        for i in range(0, self._sample_size):
            return_value, frame = self._camera.read()

            # Should exit if the no pictures are returned from _camera.read() function
            if not return_value:
                raise Errors.CaptureDeviceUnavailableError()

            sample_data.append(frame)

        return sample_data
