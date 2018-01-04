# -*- coding: utf-8 -*-
"""Pub/Sub pull example on Google Kubernetes Engine.

This program pulls messages from a Cloud Pub/Sub topic and
prints to standard output.
"""

import datetime

from google.cloud import pubsub

PUBSUB_TOPIC = 'eco'
PUBSUB_SUBSCRIPTION = 'echo-read'


def main():
    """Continuously pull messages from subsciption"""
    client = pubsub.Client()
    subscription = client.topic(PUBSUB_TOPIC).subscription(PUBSUB_SUBSCRIPTION)

    print('Pulling messages from Pub/Sub subscription...')
    while True:
        with pubsub.subscription.AutoAck(subscription, max_messages=10) as ack:
            for _, message in list(ack.items()):
                print("[{0}] ID={1} Data={2}".format(datetime.datetime.now(),
                                                     message.message_id,
                                                     message.data))

if __name__ == '__main__':
    main()
