from flask import Flask, request, jsonify, render_template
import json
import numpy as np
import pandas as pd
import pickle
import os
import logging

app = Flask(__name__)
model = pickle.load(open('model_20220729.pkl', 'rb'))

@app.route('/kdc/api/v1/predict', methods=['POST'])
def predict():
    data = request.get_json(force=True)
    app.logger.info(data)
    x_test = pd.DataFrame([data], columns=data.keys())
    
    app.logger.info(x_test)
    prediction = model.predict(x_test)

    app.logger.info(type(prediction[0]))
    return str(prediction[0])
    
if __name__ == '__main__':
    app.run(host="0.0.0.0", port=os.environ['PORT'], debug=os.environ['DEBUG'])