package org.example.Mosquito;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.example.Interfaces.IPublisher;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Getter
public class Publisher implements IPublisher {
    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);
    private MqttClient client;
    private final String topic = "LenguajeAvanzado/trikis";
    private final String clientIdPub = UUID.randomUUID().toString();

    public Publisher() throws MqttException {
        String broker = "tcp://test.mosquitto.org";
        MemoryPersistence persistence = new MemoryPersistence();
        client = new MqttClient(broker, clientIdPub, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        logger.info("Conectando al broker: {}", broker);
        client.connect(connOpts);
        logger.info("Conectado");
    }

    public void publish(String messageText) throws MqttException {
        String fullMessage = clientIdPub + ":" + messageText; // Concatena el clientId con el mensaje
        MqttMessage message = new MqttMessage(fullMessage.getBytes());

        logger.info("Publicando mensaje: {}", messageText);
        message.setQos(2);
        client.publish(topic, message);
        logger.info("Mensaje publicado");
    }

    public void disconnect() throws MqttException {
        client.disconnect();
        logger.info("Desconectado");
    }

    public String getClientIdPub() {
        return clientIdPub;
    }
}
