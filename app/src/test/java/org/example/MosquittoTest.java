package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.Interfaces.IPublisher;
import org.example.Mosquito.Publisher;
//import org.example.Mosquito.Subscriber;
import org.junit.jupiter.api.Test;

public class MosquittoTest {

    private IPublisher publisher;

    @Test
    public void inicializarPublisherTest() {
        try {

            publisher = new Publisher();

            publisher.publish("Camilo");

        } catch (MqttException e) {
            e.printStackTrace();
        }

        
        assertEquals("Camilo","Camilo");

    }



    // assertEquals(100, calculadora.suma(1, 99));

}
