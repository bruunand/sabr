import os
import cv2
import numpy as np
from math import floor
from ballthrower.errors import CaptureDeviceUnavailableError
from ballthrower.interfaces import ITargetInfo

# TensorFlow imports
from utils import label_map_util
import tensorflow as tf

# Class used for storing bounding box information.
# A bounding box defines the bounds of an identified
# target.
class BoundingBox():
    # Receive values describing the coordinates for each
    # corner of the bounding box.
    def __init__(self, x_min, x_max, y_min, y_max, width, height):
        self.x_min = x_min
        self.y_min = y_min
        self.x_max = x_max
        self.y_max = y_max
        self.width = width
        self.height = height

    # Crop an image to the pixels contained by the bounding box.
    def crop(self, from_image):
        return from_image[self.y_min:self.y_max, self.x_min:self.x_max]

    # Get the centre of the bounding box.
    def get_centre(self):
        return (int(self.width / 2), int(self.height / 2))

    # Pretty print
    def __str__(self):
        return "x: {}-{}, y: {}-{}, width: {}, height: {}".format(self.x_min, self.y_min, self.y_min, self.y_max,
                                                                  self.width, self.height)

    # Visualizes the bounding box. Used for live testing
    # debugging.
    def draw_rectangle(self, source_image, color = (255, 0, 0)):
        cv2.rectangle(source_image, (self.x_min, self.y_min), (self.x_max, self.y_max), color, 1)

    # TensorFlow describes bounding boxes with values between 0 and 1.
    # Construct and return a bounding box with these values scaled
    # to the dimensions of the image.
    def fromTensorFlowBox(source_width, source_height, box_array):
        y_min = floor(box_array[0] * source_height)
        x_min = floor(box_array[1] * source_width)
        y_max = floor(box_array[2] * source_height)
        x_max = floor(box_array[3] * source_width)

        return BoundingBox(x_min, x_max, y_min, y_max, x_max - x_min, y_max - y_min)

    # If bounding box data has already been scaled to the image,
    # construct a bounding box and return it.
    def fromNormalized(x_min, y_min, width, height):
        return BoundingBox(x_min, x_min + width, y_min, y_min + height, width, height)

# This class is used for capturing frames of the environment
# and provide target object information to an embedded system
class TargetInfo(ITargetInfo):

    # Maximum deviation used in determining
    # which RGB lower and upper bounds to be used.
    RGB_CONSTANT_DEVIATION = 30

    # Path to folder where the neural network object
    # detection model resides.
    MODEL_NAME = 'redcup_model'

    # Path to frozen detection graph.
    # This is the actual model that is used for the object detection.
    PATH_TO_CKPT = os.path.join(os.path.join('res', MODEL_NAME), 'frozen_inference_graph.pb')

    # Path to the list labels used to classify detected objects.
    PATH_TO_LABELS = os.path.join(os.path.join('res', MODEL_NAME), 'label_map.pbtxt')

    # Number of categories for classification.
    NUM_CLASSES = 1

    # A computation graph.
    detection_graph = tf.Graph()

    # List of labels
    label_map = label_map_util.load_labelmap(PATH_TO_LABELS)

    # List of dictionaries representing all possible categories.
    categories = label_map_util.convert_label_map_to_categories(label_map, max_num_classes=NUM_CLASSES,
                                                                           use_display_name=True)
    # A dictionary of the same entries as categories but the
    # key value is a category ID.
    category_index = label_map_util.create_category_index(categories)


    # Initialize TargetInfo with default capture device set to 1.
    def __init__(self, capture_device=1, debug=True):
        self.capture_device=capture_device
        self.debug = debug

        # Load frozen graph
        with self.detection_graph.as_default():
            od_graph_def = tf.GraphDef()
            with tf.gfile.GFile(self.PATH_TO_CKPT, 'rb') as fid:
                serialized_graph = fid.read()
                od_graph_def.ParseFromString(serialized_graph)
                tf.import_graph_def(od_graph_def, name='')

    # Gather the necessary data needed by the NXT to calculate
    # the direction and/or distance. Returns a list of bounding
    # boxes and an integer represeting the frame width.
    def get_targets(self):

        # Retrieve a list of sample data to be processed.
        frame = self.get_frame()

        # Get the width of a frame in the sample_data.
        frame_width = np.shape(frame)[1]

        # Process the sample data to a list of bounding boxes.
        bounding_boxes = self.get_bounding_boxes(frame)

        return bounding_boxes, frame_width

    #
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
        lower_rgb_colour = np.array([0, 0, 0])
        upper_rgb_colour = np.array([0, 0, 0])

        with self.detection_graph.as_default():

            # Start a new Tensorflow session with the initialized detection graph.
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
                    cropped_rgb = cv2.cvtColor(cropped, cv2.COLOR_BGR2RGB)

                    # Get the colour value at the center of the bounding box
                    crop_centre = box.get_centre()
                    centre_colour_rgb = cropped_rgb[crop_centre[1], crop_centre[0]]

                    # Get lower and upper bounds based on centre colour
                    for i in range(3):
                        lower_rgb_colour[i] = int(centre_colour_rgb[i] - TargetInfo.RGB_CONSTANT_DEVIATION)
                        upper_rgb_colour[i] = int(centre_colour_rgb[i] + TargetInfo.RGB_CONSTANT_DEVIATION)

                    # Mask colour with dynamically retrieved range
                    crop_masked = cv2.inRange(cropped_rgb, lower_rgb_colour, upper_rgb_colour)

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

                # Draw all rectangles for bounding boxes produced by NN

                [box.draw_rectangle(frame, (0, 255, 0)) for box in filtered_boxes]

        # If debugging is enabled draw all bounding boxes on the frame and save the result
        if self.debug:
            # Print amount of bounding boxes
            print("{} boxes produced by neural network, {} boxes after colour/contouring".format(len(filtered_boxes), len(all_bounding_boxes)))

            # Draw all rectangles for bounding boxes produced by NN
            [box.draw_rectangle(frame, (0, 255, 0)) for box in filtered_boxes]

            # Draw all boxes that are produced after colour/contouring
            for box in all_bounding_boxes:
                print(box)
                box.draw_rectangle(frame)

            cv2.imwrite('target_debug.png', frame)

        # Return the coordinate sets
        return all_bounding_boxes

    # Use the capture device to capture a frame/image.
    def get_frame(self):

        camera = cv2.VideoCapture(self.capture_device)
        camera.set(3,1600)
        camera.set(4,1200)
        return_value, frame = camera.read()
        camera.release()

        # Exits if no frame is returned from camera.read() function.
        if not return_value:
            raise CaptureDeviceUnavailableError()

        return frame
