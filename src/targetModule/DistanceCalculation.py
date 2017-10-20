import abc
import os
from Interfaces import IDistanceCalculatable

import cv2
import pandas as pd
import pickle
import numpy as np
from sklearn import linear_model
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import PolynomialFeatures

class DistanceCalculationPolynomialRegression(IDistanceCalculatable):

    # Model name format (Tuple): Name, polynomial degree.
    def get_model(self, model_name = ('Ploynomial',5), data_file = 'red_cup_data.txt', retrain = False):
        if retrain is False and os.path.isfile("{}-degree-{}.pickle".format(model_name[0],model_name[1])):

            # Load model if already existing
            pickle_in = open("{}-degree-{}.pickle".format(model_name[0],model_name[1]), 'rb')
            model = pickle.load(pickle_in)
            return model

        df = pd.read_csv(data_file)
        df.drop_duplicates(subset='distance_cm',keep='first',inplace=True)
        features = np.array(df.drop(['distance_cm'],1))
        labels = np.array(df['distance_cm'])
        model = make_pipeline(PolynomialFeatures(model_name[1]), linear_model.Ridge())
        model.fit(features, labels)

        # Save the trained model
        with open("{}-degree-{}.pickle".format(model_name[0],model_name[1]), 'wb') as f:
            pickle.dump(model,f)
        return model
    
    def get_means_of_samples(self,box_sample_list):
        # Calculate the mean width and height of the box samples
        if len(box_sample_list) == 0:
            print("No samples detected.")
            return (-1, -1)
        width_list = []
        height_list = []
        for bounding_box in box_sample_list:
            # TODO: Test this calculation
            width = math.abs(bounding_box[0][1] - bounding_box[0][0])
            height = math.abs(bounding_box[1][1] - bounding_box[1][0])

            width_list.append(width)
            height_list.append(height)

        # TODO: handle outliers

        width_mean = np.mean(width_list)
        height_mean = np.mean(height_list)
        return (mean_height,mean_width)
    
    def calculate_distance(self, box_sample_list):
        model = self.get_model()
        mean_height,mean_width = self.get_means_of_samples(box_sample_list)
        distance_approximation = model.predict([[mean_height,mean_width]])
        return distance_approximation