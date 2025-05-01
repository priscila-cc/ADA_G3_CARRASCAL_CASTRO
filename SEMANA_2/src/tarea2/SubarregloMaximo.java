package tarea2;

public class SubarregloMaximo {

    public static void main(String[] args) {
        int[] arreglo = {-2, 11, -4, 13, -5, 9, -3, 2, -8, 4};

        // Mostrar valores del arreglo
        System.out.print("Valores:    ");
        for (int n : arreglo) {
            System.out.printf("%4d", n);
        }
        System.out.println();

        // Mostrar índices del arreglo
        System.out.print("Posiciones: ");
        for (int i = 0; i < arreglo.length; i++) {
            System.out.printf("%4d", i + 1);
        }
        System.out.println("\n");

        // Procedimiento paso a paso
        Resultado resultado = calcularMaxSubarray(arreglo);

        System.out.println("\nMáxima suma encontrada: " + resultado.sumaMaxima);
        System.out.println("Desde el índice " + (resultado.inicio + 1) + " hasta el índice " + (resultado.fin + 1));
    }

    public static Resultado calcularMaxSubarray(int[] datos) {
        int sumaActual = 0;
        int sumaMayor = 0;
        int indiceInicio = 0;
        int indiceTemporal = 0;
        int indiceFin = -1;

        System.out.println("Procedimiento paso a paso:");
        for (int i = 0; i < datos.length; i++) {
            sumaActual += datos[i];
            System.out.printf("i=%2d | dato=%3d | sumaActual=%3d", i + 1, datos[i], sumaActual);

            if (sumaActual < 0) {
                sumaActual = 0;
                indiceTemporal = i + 1;
                System.out.print(" => sumaActual < 0, reinicia suma y mueve inicio temporal");
            }

            if (sumaActual > sumaMayor) {
                sumaMayor = sumaActual;
                indiceInicio = indiceTemporal;
                indiceFin = i;
                System.out.print(" => NUEVA SUMA MÁXIMA");
            }

            System.out.println();
        }

        return new Resultado(sumaMayor, indiceInicio, indiceFin);
    }

    static class Resultado {
        int sumaMaxima;
        int inicio;
        int fin;

        public Resultado(int suma, int ini, int fin) {
            this.sumaMaxima = suma;
            this.inicio = ini;
            this.fin = fin;
        }
    }
}
