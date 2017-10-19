import abc
import cv2
import pandas as pd
from interfaces import IDistanceCalculatable

class Distance_Calculation_Machine_Learning(IDistanceCalculatable):
    def train_model():
        df = pd.read_csv('red_cup_data.txt')
        df.drop_duplicates(subset='distance_cm',keep='first',inplace=True)
        X = np.array(df.drop(['distance_cm'],1))
        y = np.array(df['distance_cm'])
        # Continue traning the model here....
        return
    def get_means_of_samples():
        # Calculate the mean width and height of the box samples
        return (height,width)
    def calculate_distance(self, box_sample_list):
        model = train_model()
        distance_approximation = model.predict([mean_height,mean_width])
        return distance_approximation