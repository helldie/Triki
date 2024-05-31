package org.example.CPU;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.example.Interfaces.ITablero;
import org.example.Servicios.GameService;

public class CPU {

    private ITablero tablero;
    private GameService controller;

    public CPU(ITablero tablero, GameService controller) {
        this.tablero = tablero;
        this.controller = controller;
    }

    public void realizarMovimiento() {
        if (buscarMovimientoGanadorOBloqueo(2)) {
            return;
        }
        if (buscarMovimientoGanadorOBloqueo(1)) {
            return;
        }
        List<int[]> casillasLibres = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero.getTablero()[i][j] == 0) {
                    casillasLibres.add(new int[]{i, j});
                }
            }
        }

        if (!casillasLibres.isEmpty()) {
            Random random = new Random();
            int[] movimiento = casillasLibres.get(random.nextInt(casillasLibres.size()));
            int fila = movimiento[0];
            int columna = movimiento[1];
            Button btn = controller.obtenerBoton(fila, columna);
            controller.marcarCasilla(btn, fila, columna);
        }
    }

    private boolean buscarMovimientoGanadorOBloqueo(int jugador) {
        int[][] tablero = this.tablero.getTablero();
        // Buscar en filas y columnas
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == jugador && tablero[i][1] == jugador && tablero[i][2] == 0) {
                controller.marcarCasilla(controller.obtenerBoton(i, 2), i, 2);
                return true;
            }
            if (tablero[i][0] == jugador && tablero[i][2] == jugador && tablero[i][1] == 0) {
                controller.marcarCasilla(controller.obtenerBoton(i, 1), i, 1);
                return true;
            }
            if (tablero[i][1] == jugador && tablero[i][2] == jugador && tablero[i][0] == 0) {
                controller.marcarCasilla(controller.obtenerBoton(i, 0), i, 0);
                return true;
            }
            if (tablero[0][i] == jugador && tablero[1][i] == jugador && tablero[2][i] == 0) {
                controller.marcarCasilla(controller.obtenerBoton(2, i), 2, i);
                return true;
            }
            if (tablero[0][i] == jugador && tablero[2][i] == jugador && tablero[1][i] == 0) {
                controller.marcarCasilla(controller.obtenerBoton(1, i), 1, i);
                return true;
            }
            if (tablero[1][i] == jugador && tablero[2][i] == jugador && tablero[0][i] == 0) {
                controller.marcarCasilla(controller.obtenerBoton(0, i), 0, i);
                return true;
            }
        }
        // Buscar en diagonales
        if (tablero[0][0] == jugador && tablero[1][1] == jugador && tablero[2][2] == 0) {
            controller.marcarCasilla(controller.obtenerBoton(2, 2), 2, 2);
            return true;
        }
        if (tablero[0][0] == jugador && tablero[2][2] == jugador && tablero[1][1] == 0) {
            controller.marcarCasilla(controller.obtenerBoton(1, 1), 1, 1);
            return true;
        }
        if (tablero[1][1] == jugador && tablero[2][2] == jugador && tablero[0][0] == 0) {
            controller.marcarCasilla(controller.obtenerBoton(0, 0), 0, 0);
            return true;
        }
        if (tablero[0][2] == jugador && tablero[1][1] == jugador && tablero[2][0] == 0) {
            controller.marcarCasilla(controller.obtenerBoton(2, 0), 2, 0);
            return true;
        }
        if (tablero[0][2] == jugador && tablero[2][0] == jugador && tablero[1][1] == 0) {
            controller.marcarCasilla(controller.obtenerBoton(1, 1), 1, 1);
            return true;
        }
        if (tablero[1][1] == jugador && tablero[2][0] == jugador && tablero[0][2] == 0) {
            controller.marcarCasilla(controller.obtenerBoton(0, 2), 0, 2);
            return true;
        }
        return false;
    }
}

