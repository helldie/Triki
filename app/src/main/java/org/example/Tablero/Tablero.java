package org.example.Tablero;

import org.example.Interfaces.ITablero;

public class Tablero implements ITablero {

    private int[][] tablero = new int[3][3];

    public boolean marcarCasilla(int fila, int columna, int jugador) {
        if (tablero[fila][columna] == 0) {
            tablero[fila][columna] = jugador;
            return true;
        }
        return false;
    }

    public int verificarGanador() {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] != 0 && tablero[i][0] == tablero[i][1] && tablero[i][0] == tablero[i][2]) {
                return tablero[i][0];
            }
        }
        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (tablero[0][j] != 0 && tablero[0][j] == tablero[1][j] && tablero[0][j] == tablero[2][j]) {
                return tablero[0][j];
            }
        }
        // Verificar diagonales
        if (tablero[0][0] != 0 && tablero[0][0] == tablero[1][1] && tablero[0][0] == tablero[2][2]) {
            return tablero[0][0];
        }
        if (tablero[0][2] != 0 && tablero[0][2] == tablero[1][1] && tablero[0][2] == tablero[2][0]) {
            return tablero[0][2];
        }
        return 0;
    }

    public boolean hayEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void reiniciar() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = 0;
            }
        }
    }

    public int[][] getTablero() {
        return tablero;
    }
}
