import os
import sys
import getch
import numpy as np
import tensorflow as tf
import cv2
from PIL import Image
from utils import label_map_util

# Unused imports
#from io import StringIO
#from collections import defaultdict
#import csv
#import zipfile
#import tarfile
#import six.moves.urllib as urllib
#from matplotlib import pyplot as plt
#from utils import visualization_utils as vis_util



#sys.path.append('/your/dir/to/tensorflow/models')
#sys.path.append('/your/dir/to/tensorflow/models/slim')


PATH_TO_TEST_IMAGES_DIR = 'Images'
TEST_IMAGE_PATHS = [ os.path.join(PATH_TO_TEST_IMAGES_DIR, 'image{}.jpg'.format(i)) for i in range(0, 15) ]

#cap = cv2.VideoCapture("20_1.jpg")

#cap.set(3,1600)

#cap.set(4,1200)
# This is needed since the notebook is stored in the object_detection folder.
sys.path.append("..")


# ## Object detection imports
# Here are the imports from the object detection module.

# In[3]:

# USED FOR LENGTH CALC
KNOWN_HEIGHT = 11.5
focallength = 120 * 110.2 / 11.5
focallength_no_enhance_old = 66 * 234 / 11.5
focallength_no_enhance = 118 * 120 / 11.5
focallength_cc = 53*269/11.5
focallength_nn = 53*301/11.5

max_angle = 26.725

#degrees_per_pixel = max_angle /
# END LENGTH CALC

# # Model preparation 

# ## Variables
# 
# Any model exported using the `export_inference_graph.py` tool can be loaded here simply by changing `PATH_TO_CKPT` to point to a new .pb file.  
# 
# By default we use an "SSD with Mobilenet" model here. See the 
#  [detection model zoo](https://github.com/tensorflow/models/blob/master/object_detection/g3doc/detection_model_zoo.md) 
#  for a list of other models that can be run out-of-the-box with varying speeds and accuracies.

# In[4]:

#correct_distance = sys.argv[1]

# What model to download.
MODEL_NAME = 'redcup_model'

# Path to frozen detection graph. This is the actual model that is used for the object detection.
PATH_TO_CKPT = MODEL_NAME + '/frozen_inference_graph.pb'

# List of the strings that is used to add correct label for each box.
PATH_TO_LABELS = os.path.join(MODEL_NAME, 'label_map.pbtxt')

NUM_CLASSES = 1


detection_graph = tf.Graph()
with detection_graph.as_default():
  od_graph_def = tf.GraphDef()
  with tf.gfile.GFile(PATH_TO_CKPT, 'rb') as fid:
    serialized_graph = fid.read()
    od_graph_def.ParseFromString(serialized_graph)
    tf.import_graph_def(od_graph_def, name='')


# ## Loading label map
# Label maps map indices to category names, so that when our convolution network predicts `5`, we know that this corresponds to `airplane`.  
# Here we use internal utility functions, but anything that returns a dictionary mapping integers to appropriate string labels would be fine

# In[7]:

label_map = label_map_util.load_labelmap(PATH_TO_LABELS)
categories = label_map_util.convert_label_map_to_categories(label_map, max_num_classes=NUM_CLASSES, use_display_name=True)
category_index = label_map_util.create_category_index(categories)


# ## Helper code

# In[8]:

def load_image_into_numpy_array(image):
  (im_width, im_height) = image.size
  return np.array(image.getdata()).reshape(
      (im_height, im_width, 3)).astype(np.uint8)


# In[10]:

img_path = "asd.jpg"
method = "Contouring"
enhance_image = False
# Image name format: enhanced or not, distance and image format.
image_name = "{}_{}_110cm.png".format(method,"enhanced" if enhance_image else "not_enhanced")
all_boxes = []
length_to_cup = 0.0
area = []
itt = 0
with detection_graph.as_default():
  with tf.Session(graph=detection_graph) as sess:
    for path in TEST_IMAGE_PATHS:
      print(path)
      cap = cv2.VideoCapture(path)
      ret, image_np = cap.read()
      #image_np = cv2.imread(img_path)
      # Expand dimensions since the model expects images to have shape: [1, None, None, 3]
      image_np_expanded = np.expand_dims(image_np, axis=0)
      image_tensor = detection_graph.get_tensor_by_name('image_tensor:0')
      # Each box represents a part of the image where a particular object was detected.
      boxes = detection_graph.get_tensor_by_name('detection_boxes:0')
      # Each score represent how level of confidence for each of the objects.
      # Score is shown on the result image, together with the class label.
      scores = detection_graph.get_tensor_by_name('detection_scores:0')
      classes = detection_graph.get_tensor_by_name('detection_classes:0')
      num_detections = detection_graph.get_tensor_by_name('num_detections:0')
      feed_dict = {image_tensor: image_np_expanded}
      # Actual detection.
      (boxes, scores, classes, num_detections) = sess.run(
          [boxes, scores, classes, num_detections],
          feed_dict={image_tensor: image_np_expanded})
      # Visualization of the results of a detection.
      """vis_util.visualize_boxes_and_labels_on_image_array(
          image_np,
          np.squeeze(boxes),
          np.squeeze(classes).astype(np.int32),
          np.squeeze(scores),
          category_index,
          use_normalized_coordinates=True,
          line_thickness=1)"""
      test_img = image_np
      degrees_per_pixel = max_angle / (np.shape(test_img)[1] / 2)
      if enhance_image:
        image_rgb = cv2.cvtColor(image_np,cv2.COLOR_BGR2RGB)
        enhaced = Image.fromarray(image_rgb).resize((800,600), Image.ANTIALIAS)
        enhaced = np.array(enhaced)
        enhaced = cv2.cvtColor(enhaced,cv2.COLOR_RGB2BGR)
        test_img = cv2.medianBlur(enhaced, 3)
      else:
        test_img = image_np
      lower_color = np.array([169, 100, 100])
      upper_color = np.array([189, 255, 255])
      per_diff = 0.40
      height,width,_ = np.shape(test_img)
      y_min, x_min, y_max,x_max = np.squeeze(boxes)[0]
      y_min = int(y_min * height)
      x_min = int(x_min * width)
      y_max = int(y_max * height)
      x_max = int(x_max * width)
      centre_x,centre_y = int((x_max+x_min)/2),int((y_max+y_min)/2)
      c = test_img[centre_y,centre_x]
      c = np.uint8([[[int(c[0]),int(c[1]),int(c[2])]]])
      hsv_c = cv2.cvtColor(c,cv2.COLOR_BGR2HSV)
      hsv_1dcolor = np.array(hsv_c[0][0])
      for i in range(3):
        lower_color[i] = int(hsv_1dcolor[i]*(1-per_diff))
        upper_color[i] = int(hsv_1dcolor[i]*(1+per_diff))
      hsv_blue = cv2.cvtColor(test_img, cv2.COLOR_BGR2HSV)

      mask_blue = cv2.inRange(hsv_blue, lower_color, upper_color)

      _, contours, hierarchy = cv2.findContours(mask_blue.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)

      if len(contours) == 0:
          exit()

      contour = max(contours, key=cv2.contourArea)
      area = cv2.boundingRect(contour)
      size = cv2.contourArea(contour)
      if size == 0:
          exit()

      #height_nn = y_max-y_min
      #height_cc = area[3]
      # DRAWING BB AND LENGTH
      #box = np.int0(cv2.boxPoints(area))
      #cv2.drawContours(test_img, [box], -1, (255, 0, 0), 2)
      height = area[3]
      width = area[2]
      frame_width, frame_height = np.shape(test_img)[1],np.shape(test_img)[0]
      cv2.rectangle(test_img,(area[0],area[1]),(area[0]+area[2],area[1]+area[3]),(0,255,0),2)
      length_to_cup = (focallength_no_enhance*KNOWN_HEIGHT) / height
      angle_to_cup = (centre_x-frame_width/2)*degrees_per_pixel
      #length_to_cup_cc = (focallength_cc * KNOWN_HEIGHT) / height_cc
      #length_to_cup_nn = (focallength_nn * KNOWN_HEIGHT) / height_nn 
      print("Length to cup using new focal length: ",length_to_cup)
      print("Angle to cup ",angle_to_cup)
      print("distance to center line ", abs(centre_x-frame_width/2))
      print("degrees per pixel ", degrees_per_pixel)
      print("frame width ",frame_width)
      cv2.line(test_img,(int(frame_width/2),0),(int(frame_width/2),int(frame_height)),(255,0,0),2)
      # END DRAWING AND LENGTH
      #print("cc",height_cc)
      #print("nn",height_nn)
      #print("cc",length_to_cup_cc)
      #print("nn",length_to_cup_nn)
      cv2.imshow('object detection', test_img)
      print(frame_height,frame_width)
      #cv2.imwrite(image_name,test_img)
      #cv2.imshow('object_detection', image_np)
      print("iaro ist und faglord")
      """if(itt > 6):
        print("final length", length_to_cup_cc)
        with open('test_cc_data_distance.csv', 'a', newline='\n') as csvfile:
          vals = [correct_distance,length_to_cup_cc,area[0],area[1],area[2],area[3]]
          writer = csv.writer(csvfile, delimiter=',',quoting=csv.QUOTE_MINIMAL)
          writer.writerow(vals)
      if(itt > 6):
        itt=0
        print("final length", length_to_cup_nn)
        with open('test_nn_data_distance.csv', 'a', newline='\n') as csvfile:
          vals = [correct_distance,length_to_cup_nn,x_min,y_min,x_max-x_min,height_nn]
          writer = csv.writer(csvfile, delimiter=',',quoting=csv.QUOTE_MINIMAL)
          writer.writerow(vals)
        correct_distance = input("Enter new distance ")
      itt += 1"""
      #getch.getch()
      if cv2.waitKey(1) & 0xFF == ord('q'):
        cv2.destroyAllWindows()
        exit()