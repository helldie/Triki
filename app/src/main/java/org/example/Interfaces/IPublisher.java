package org.example.Interfaces;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface IPublisher {
    void publish(String message) throws MqttException;
    String getClientIdPub();
}
