from flask import Flask, request, jsonify, render_template
import json
import numpy as np
import pandas as pd
import pickle
import os

app = Flask(__name__)
model = pickle.load(open('model.pkl', 'rb'))

@app.route('/kdc/api/v1/predict', methods=['POST'])
def predict():
    data = request.get_json(force=True)
    print(data)
    x_test = pd.DataFrame([data], columns=data.keys())
    print(x_test)
    prediction = model.predict(x_test)

    print(type(prediction[0]))
    return str(prediction[0])
    
if __name__ == '__main__':
    app.run(host="0.0.0.0", port=os.environ['PORT'], debug=os.environ['DEBUG'])