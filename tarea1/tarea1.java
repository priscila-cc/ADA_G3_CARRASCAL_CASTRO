package tarea1;

import java.util.Scanner;

public class tarea1 {

    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    static int n = 4, m = 4;
    static char[][] tablero = {
        {'E', 'S', 'T', 'O'},
        {'S', 'T', 'T', 'M'},
        {'E', 'A', 'S', 'A'},
        {'P', 'R', 'N', 'E'}
    };

    static boolean[][] resaltado = new boolean[n][m];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido a la Sopa de Letras");

        // Mostrar el tablero al inicio
        imprimirTablero();

        while (true) {
            System.out.print("Ingresa una palabra a buscar (o 'S' para salir): ");
            String palabra = scanner.nextLine().toUpperCase();

            if (palabra.equals("S")) {
                System.out.println("¡Gracias por jugar!");
                break;
            }

            limpiarResaltado();

            boolean encontrada = buscarPalabra(palabra);

            if (encontrada) {
                System.out.println("\nPalabra encontrada:" + palabra);
                imprimirTablero();
            } else {
                System.out.println("Palabra no encontrada. Intenta otra.");
            }
        }

        scanner.close();
    }

    static boolean buscarPalabra(String palabra) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (tablero[i][j] == palabra.charAt(0)) {
                    for (int dir = 0; dir < 8; dir++) {
                        int x = i, y = j;
                        int k;

                        for (k = 1; k < palabra.length(); k++) {
                            x += dx[dir];
                            y += dy[dir];

                            if (x < 0 || y < 0 || x >= n || y >= m || tablero[x][y] != palabra.charAt(k)) {
                                break;
                            }
                        }

                        if (k == palabra.length()) {
                            // Resaltar las letras de la palabra encontrada
                            x = i;
                            y = j;
                            resaltado[x][y] = true;
                            for (int l = 1; l < palabra.length(); l++) {
                                x += dx[dir];
                                y += dy[dir];
                                resaltado[x][y] = true;
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    static void limpiarResaltado() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                resaltado[i][j] = false;
            }
        }
    }

    static void imprimirTablero() {
        System.out.println("Tablero:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (resaltado[i][j]) {
                    System.out.print("[" + tablero[i][j] + "] ");
                } else {
                    System.out.print(" " + tablero[i][j] + "  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
