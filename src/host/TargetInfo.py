import os
import cv2
import numpy as np
import tensorflow as tf
import Errors
from utils import label_map_util
from Interfaces import ITargetInfo
from PIL import Image



class TargetInfo(ITargetInfo):
    #Used in height calculation.
    KNOWN_HEIGHT = 11.5
    focallength = 127.5 * 60 / 11.5
    focallength_no_enhance = 66 * 234 / 11.5

    #The model used to find the redcup
    MODEL_NAME = 'redcuprcnn'

    # Path to frozen detection graph. This is the actual model that is used for the object detection.
    PATH_TO_CKPT = MODEL_NAME + '/frozen_inference_graph.pb'

    # List of the strings that is used to add correct label for each box.
    PATH_TO_LABELS = os.path.join(MODEL_NAME, 'label_map.pbtxt')

    #Redcup class number in tensorflow
    NUM_CLASSES = 1

    #Tensorflow graph
    detection_graph = tf.Graph()

    label_map = label_map_util.load_labelmap(PATH_TO_LABELS)
    categories = label_map_util.convert_label_map_to_categories(label_map, max_num_classes=NUM_CLASSES, use_display_name=True)
    category_index = label_map_util.create_category_index(categories)

    # Initialize TargetInfo with default capture device set to 0 and sample size set to 10
    def __init__(self, capture_device=1, sample_size=10, target_width=8.25, target_height=10.5, debug=True):
        self._camera = cv2.VideoCapture(capture_device)
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
        # Retrieve a list of sample data to be processed
        sample_data = self.get_sample_data()

        # Get the width of a frame in the sample_data
        frame_width = np.shape(sample_data[0])[1]

        # Process the sample data to a list of bounding boxes (a bounding box / rectangle consists of four integers: x-coordinate, y-coordinate, width and height)
        bounding_boxes = self.image_processing(sample_data)

        # Return the bounding boxes and frame width as a touple
        return (bounding_boxes, frame_width)

    def image_processing(self, sample_data):
        bounding_boxes = []

        # For help on colorspaces: https://docs.opencv.org/3.2.0/df/d9d/tutorial_py_colorspaces.html
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

                    #Set frame size to 800x600 px. using antialiasing 
                    frame = Image.fromarray(frame).resize((800, 600), Image.ANTIALIAS)
                    frame = np.array(frame)

                    #Removes big colour changes with median blur
                    frame = cv2.medianBlur(frame, 3)

                    percent_difference = 0.40
                    height, width, _ = np.shape(frame)
                    y_min, x_min, y_max, x_max = np.squeeze(boxes)[0]
                    y_min = int(y_min * height)
                    x_min = int(x_min * width)
                    y_max = int(y_max * height)
                    x_max = int(x_max * width)
                    centre_x,centre_y = int((x_max+x_min)/2),int((y_max+y_min)/2)

                    colour = frame[centre_y,centre_x]
                    colour = np.uint8([[[int(colour[0]),int(colour[1]),int(colour[2])]]])

                    # Convert the current frame with a BGR color profile to a frame with a HSV color profile
                    hsv_c = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
                    hsv_1dcolor = np.array(hsv_c[0][0])

                    for i in range(3):
                        lower_hsv_colour[i] = int(hsv_1dcolor[i]*(1-percent_difference))
                        upper_hsv_colour[i] = int(hsv_1dcolor[i]*(1+percent_difference))
                      
                    hsv_blue = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

                    mask_blue = cv2.inRange(hsv_blue, lower_hsv_colour, upper_hsv_colour)

                    # Create contours for all objects in the defined colorspace (See: http://opencv-python-tutroals.readthedocs.io/en/latest/py_tutorials/py_imgproc/py_contours/py_contours_hierarchy/py_contours_hierarchy.html)
                    _, contours, _ = cv2.findContours(mask_blue.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)

                    '''# Create a mask of the image (everything within the threshold appears white and everything else is black)
                    mask = cv2.inRange(hsv_c, lower_hsv_colour, upper_hsv_colour)

                    # Get rid of background noise using erosion
                    element = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
                    mask = cv2.erode(mask, element, iterations=2)
                    mask = cv2.dilate(mask, element, iterations=2)
                    mask = cv2.erode(mask, element)

                    # Create contours for all objects in the defined colorspace (See: http://opencv-python-tutroals.readthedocs.io/en/latest/py_tutorials/py_imgproc/py_contours/py_contours_hierarchy/py_contours_hierarchy.html)
                    _, contours, hierarchy = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)'''

                    if len(contours) == 0:
                        continue

                    contour = max(contours, key=cv2.contourArea)
                    area = cv2.boundingRect(contour)
                    size = cv2.contourArea(contour)
                    
                    # Continue if no bounding box is found
                    if size == 0:
                        continue

                    # Search through the countours for the largest object
                    #best_contour = max(contours, key=cv2.contourArea)

                    # Create a bounding box around the contour
                    #bounding_boxes.append(cv2.boundingRect(best_contour))

                    # DRAWING BB AND LENGTH
                    height = area[3]
                    cv2.rectangle(frame,(area[0],area[1]),(area[0]+area[2],area[1]+area[3]),(0,255,0),2)
                    length_to_cup = (self.focallength_no_enhance*self.KNOWN_HEIGHT) / height
                    print(length_to_cup)
                    # END DRAWING AND LENGTH

                    #cv2.imshow('object detection', frame)
                    #cv2.imwrite(self.image_name,frame)
                    print("iaro ist und faglord")
                    if cv2.waitKey(1) & 0xFF == ord('q'):
                        break

        # Refine bounding box set
        bounding_boxes = self.remove_outliers(bounding_boxes)


        # If debugging is enabled draw all bounding boxes on the first frame and show the result
        if self._debug and len(sample_data) > 0:
            
            base_image = sample_data[0]

            for box in bounding_boxes:
                cv2.rectangle(base_image, (box[0], box[1]), (box[0] + box[2], box[1] + box[3]), (0, 255, 0), 3)
                print(box)

            cv2.imshow('debug', base_image)
            cv2.waitKey(1)

        # Return the coordinate sets
        return bounding_boxes

    def remove_outliers(self, bounding_boxes, max_deviance = 20, max_return_size = 100):
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
        sample_data = []

        # Add x frames to the sample data collection
        for i in range(0, self._sample_size):
            return_value, frame = self._camera.read()

            # Should exit if the no pictures are returned from _camera.read() function
            if not return_value:
                raise Errors.CaptureDeviceUnavailableError()

            sample_data.append(frame)

        return sample_data
