package org.example.Controlador;
import org.example.Enum.Modo;
import org.example.Interfaces.ITablero;
import org.example.Servicios.GameService;
import org.example.Tablero.Tablero;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TrikiController {
    @FXML private Button btn00, btn01, btn02, btn10, btn11, btn12, btn20, btn21, btn22, reiniciar, vscpu, vsjugador, btnOnline;
    @FXML private Label mensaje, puntosx, puntoso;

    private GameService gameController;

    @FXML
    public void initialize() {
        Button[][] botones = {
            {btn00, btn01, btn02},
            {btn10, btn11, btn12},
            {btn20, btn21, btn22}
        };

        setupButtonActions();

        ITablero tablero = new Tablero();

        gameController = new GameService(tablero, mensaje, puntosx, puntoso, botones);

        gameController.inciarMusica();
    }

    public void onClicReiniciar() {
        gameController.reiniciarJuego();
        gameController.reiniciarLabel();
    }

    public void onClicJugador() {
        gameController.cambiarModo(Modo.JUGADOR);
    }

    public void onClicCpu() {
        gameController.cambiarModo(Modo.CPU);
    }

    public void onClicOnline() {
        gameController.cambiarModo(Modo.ONLINE);
        gameController.inicializarPublisher();
        gameController.initializeSubscriber();
    }

    private void setupButtonActions() {
        btn00.setOnAction(e -> gameController.marcarCasilla(btn00, 0, 0));
        btn01.setOnAction(e -> gameController.marcarCasilla(btn01, 0, 1));
        btn02.setOnAction(e -> gameController.marcarCasilla(btn02, 0, 2));
        btn10.setOnAction(e -> gameController.marcarCasilla(btn10, 1, 0));
        btn11.setOnAction(e -> gameController.marcarCasilla(btn11, 1, 1));
        btn12.setOnAction(e -> gameController.marcarCasilla(btn12, 1, 2));
        btn20.setOnAction(e -> gameController.marcarCasilla(btn20, 2, 0));
        btn21.setOnAction(e -> gameController.marcarCasilla(btn21, 2, 1));
        btn22.setOnAction(e -> gameController.marcarCasilla(btn22, 2, 2));
        reiniciar.setOnAction(e -> gameController.reiniciarJuego());
        vscpu.setOnAction(e -> gameController.cambiarModo(Modo.CPU));
        vsjugador.setOnAction(e -> gameController.cambiarModo(Modo.JUGADOR));
        btnOnline.setOnAction(e -> gameController.cambiarModo(Modo.ONLINE));
    }

}
