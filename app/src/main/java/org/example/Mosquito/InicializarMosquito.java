package org.example.Mosquito;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.Interfaces.IMosquito;
import org.example.Interfaces.IPublisher;
import org.example.Servicios.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InicializarMosquito implements IMosquito {

    private static final Logger logger = LoggerFactory.getLogger(InicializarMosquito.class);

    public void inicializarSubscriber(GameService gameService, IPublisher publisher) {
        Subscriber subscriber = new Subscriber(gameService, publisher);
        subscriber.iniciarSubscriber();
        inicializarPublisher(publisher);
    }

    public void inicializarPublisher(IPublisher publisher) {
        try {
            if (publisher == null) {
                publisher = new Publisher();
                logger.info("Publisher inicializado correctamente.");
            }
        } catch (MqttException e) {
            logger.error("No se pudo inicializar el Publisher: " + e.getMessage(), e);
        }
    }
}
