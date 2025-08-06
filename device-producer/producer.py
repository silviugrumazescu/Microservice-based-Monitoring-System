import pika
import json
from datetime import datetime
import requests
import time

r = requests.get("http://localhost:8082/getDeviceUUIDs")
uuids = r.json()

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()

channel.queue_declare(queue='queue')
file = open("sensor.csv", 'r')

while True:
    for uuid in uuids:

        first_entry = float(file.readline())
        second_entry = float(file.readline())
        consumption = second_entry - first_entry
        print(consumption)
        message = {'timestamp' : str(datetime.now()), 'device_id' : uuid, 'measurement_value':consumption}
        channel.basic_publish(exchange='exchange',
                            routing_key='consumptionqueue',
                            body=json.dumps(message))
        print("Send object")
    time.sleep(5)
    
connection.close()

