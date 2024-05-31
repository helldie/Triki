package org.example.Mosquito;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.example.Interfaces.IPublisher;
import org.example.Interfaces.ISubscriber;
import org.example.Servicios.GameService;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Subscriber implements ISubscriber {

    private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

    private GameService gameService;
    private IPublisher publisher;  // Guardar referencia al Publisher
    private MqttClient client;

    // Constructor modificado para aceptar Publisher y TrikiController
    public Subscriber(GameService gameService, IPublisher publisher) {
        this.gameService = gameService;
        this.publisher = publisher;
    }

    public void iniciarSubscriber() {
        String broker = "tcp://test.mosquitto.org";
        String clientIdSub = UUID.randomUUID().toString();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            client = new MqttClient(broker, clientIdSub, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    logger.error("Conexion perdida! ", cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String fullMessage = new String(message.getPayload());
                    String[] parts = fullMessage.split(":", 2);
                    if (parts.length > 1 && !parts[0].equals(publisher.getClientIdPub())) {
                        String payload = parts[1];
                        Platform.runLater(() -> {
                            gameService.procesarMovimientoRecibido(payload);
                        });
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // No operation for subscribers
                }
            });
            client.subscribe("LenguajeAvanzado/trikis");
            logger.info("Subscribed to topic: LenguajeAvanzado/trikis");
        } catch (MqttException me) {
            logger.error("Error de MQTT", me);
        }
    }
}
