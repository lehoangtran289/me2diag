FROM python:3.8-slim-buster

WORKDIR /app

ENV PORT 5555
ENV DEBUG True

COPY . .

RUN pip3 install -r requirements.txt

CMD [ "python3", "main.py"]