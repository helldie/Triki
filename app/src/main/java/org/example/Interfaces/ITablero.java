package org.example.Interfaces;

public interface ITablero {
    boolean marcarCasilla(int fila, int columna, int jugador);
    int verificarGanador();
    boolean hayEmpate();
    void reiniciar();
    int[][] getTablero();
}
