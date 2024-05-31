package org.example.Interfaces;

import org.example.Servicios.GameService;

public interface IMosquito {
    void inicializarSubscriber(GameService gameService, IPublisher publisher);
    void inicializarPublisher(IPublisher publisher);
}
