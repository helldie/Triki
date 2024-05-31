package org.example.Servicios;

import javafx.scene.control.Button;

public class EnableButtons {


public void deshabilitarBotones(Button[][] botones) {
        for (Button[] fila : botones) {
            for (Button boton : fila) {
                boton.setDisable(true);
            }
        }
    }

    public void habilitarBotones(Button[][] botones) {
        for (Button[] fila : botones) {
            for (Button boton : fila) {
                boton.setDisable(false);
            }
        }
    }
    
}