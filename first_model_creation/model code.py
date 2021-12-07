# -*- coding: utf-8 -*-
"""
Created on Tue Jul  2 23:53:16 2019

@author: hamza
"""

import tensorflow as tf
import numpy as np
from tensorflow import keras
from tensorflow import lite

#y = 2x-1

x = np.array([-1.0,0.0,1.0,2.0,3.0,4.0],dtype = float)
y = np.array([-3.0,-1.0,1.0,3.0,5.0,7.0],dtype = float)

model = keras.Sequential([keras.layers.Dense(units=1,input_shape=[1])])
model.compile(optimizer='sgd',loss='mean_squared_error')



model.fit(x,y,epochs= 500)
print(model.predict([10]))

kearas_file = "linear.h5"
tf.keras.models.save_model(model,kearas_file)
converter = tf.lite.TFLiteConverter.from_keras_model(model)
tfmodel = converter.convert()
open("linear.tflite","wb").write(tfmodel)


