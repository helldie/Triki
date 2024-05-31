package org.example.Servicios;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.CPU.CPU;
import org.example.Enum.Modo;
import org.example.Interfaces.IPublisher;
import org.example.Interfaces.ITablero;
import org.example.Mosquito.Publisher;
import org.example.Mosquito.Subscriber;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameService {
    private ITablero tablero;
    private IPublisher publisher;
    private CPU cpu;
    private Label mensaje;
    private Label puntosx;
    private Label puntoso;
    private boolean turnoX = true;
    private boolean esMiTurno = true;
    boolean procesar = false;
    private Modo modoJuego = Modo.JUGADOR;
    private MediaPlayer mediaPlayer;

    private Button[][] botones;

    int contadorx = 0;
    int contadorO = 0;

    public GameService(ITablero tablero, Label mensaje, Label puntosx, Label puntoso, Button[][] botones) {
        this.tablero = tablero;
        this.mensaje = mensaje;
        this.puntosx = puntosx;
        this.puntoso = puntoso;
        this.botones = botones;
        this.cpu = new CPU(tablero, this);
    }

    public void inciarMusica() {
        String audioFile = getClass().getResource("/org/example/Disturbed-Decadence.mp3").toExternalForm();
        Media media = new Media(audioFile);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Para que la música se repita
        mediaPlayer.play();
    }

    public void cambiarModo(Modo nuevoModo) {
        this.modoJuego = nuevoModo;
        reiniciarJuego(); // Reinicia el juego cada vez que se cambia el modo
        reiniciarLabel();
        switch (modoJuego) {
            case CPU:
                esMiTurno = true; // Jugador siempre comienza
                mensaje.setText("Modo Jugador vs CPU");
                break;
            case JUGADOR:
                esMiTurno = true; // Jugador siempre comienza
                mensaje.setText("Modo Jugador vs Jugador");
                break;
            case ONLINE:
                // Iniciar configuración para juegos en línea
                esMiTurno = true; // Esperar movimiento del oponente
                mensaje.setText("Modo Online. Esperando al oponente...");
                break;
        }
    }

    public void procesarMovimientoRecibido(String movimiento) {
        procesar = true;
        esMiTurno = true;

        // Procesar el string
        String[] partes = movimiento.split(",");
        int fila = Integer.parseInt(partes[0]);
        int columna = Integer.parseInt(partes[1]);
        Button btn = obtenerBoton(fila, columna);
        marcarCasilla(btn, fila, columna);
        procesar = false;  // Resetear procesar después de usar
        verificarGanador();
    }

    public void marcarCasilla(Button btn, int fila, int columna) {
        if (modoJuego == Modo.ONLINE && !esMiTurno) {
            mensaje.setText("No es tu turno!");
            return;
        }

        if (tablero.marcarCasilla(fila, columna, turnoX ? 1 : 2)) {
            btn.setText(turnoX ? "X" : "O");
            mensaje.setText(turnoX ? "Es turno de O" : "Es turno de X");
            turnoX = !turnoX; // Cambiar el turno al siguiente jugador

            if (modoJuego == Modo.ONLINE && !procesar) { // Solo en modo online
                try {
                    esMiTurno = false;
                    publisher.publish(fila + "," + columna);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } else if (modoJuego == Modo.CPU && !turnoX) {
                cpu.realizarMovimiento();
            }
            verificarGanador();
        }
    }
    
    private void verificarGanador() {
        EnableButtons enableButtons = new EnableButtons();
        int ganador = tablero.verificarGanador();
        if (ganador != 0) {
             // Deshabilitar los botones
             enableButtons.deshabilitarBotones(botones);
            mostrarMensaje(ganador == 1 ? "Jugador X ha ganado!" : "Jugador O ha ganado!");
            // Añadir tiempo de espera antes de reiniciar el juego
            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // Espera de 3 segundos
            pause.setOnFinished(event -> {
                reiniciarJuego();
                // Habilitar los botones después de reiniciar
                enableButtons.habilitarBotones(botones);

            });
            pause.play();
        } else if (tablero.hayEmpate()) {
           // Deshabilitar los botones
           enableButtons.deshabilitarBotones(botones);

            mostrarMensaje("¡Empate!");
            // Añadir tiempo de espera antes de reiniciar el juego
            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // Espera de 3 segundos
            pause.setOnFinished(event -> {
                reiniciarJuego();
               // Habilitar los botones después de reiniciar
               enableButtons.habilitarBotones(botones);

            });
            pause.play();
        }
    }

    private void mostrarMensaje(String ganador) {
        if (ganador.contains("X")) {
            contadorx++;
            puntosx.setText(String.valueOf(contadorx));
        } else if (ganador.contains("Jugador O")) {
            contadorO++;
            puntoso.setText(String.valueOf(contadorO));
        }
        mensaje.setText(ganador);
    }

    

    public void reiniciarLabel() {
        puntoso.setText("0");
        puntosx.setText("0");
        mensaje.setText("Clic en alguna casilla para iniciar");
        contadorx = 0;
        contadorO = 0;
    }

    public void reiniciarJuego() {
        for (Button[] fila : botones) {
            for (Button boton : fila) {
                boton.setText("");
            }
        }
        tablero.reiniciar();
        turnoX = true;
    }

    public Button obtenerBoton(int fila, int columna) {
        return botones[fila][columna];
    }

    public void initializeSubscriber() {
        Subscriber subscriber = new Subscriber(this, publisher);
        subscriber.iniciarSubscriber();
        inicializarPublisher();
        reiniciarJuego();
    }

    public void inicializarPublisher() {
        try {
            if (publisher == null) {
                publisher = new Publisher();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
