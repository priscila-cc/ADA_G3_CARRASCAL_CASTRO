import java.util.Stack;

public class AckermannAnalyzer {

    static class OperationCounter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int get() {
            return count;
        }
    }

    public static void printFunctionDefinition() {
        System.out.println("=== Función de Ackermann-Péter ===");
        System.out.println("A(m, n) =");
        System.out.println("   n + 1                   si m = 0");
        System.out.println("   A(m - 1, 1)             si m > 0 y n = 0");
        System.out.println("   A(m - 1, A(m, n - 1))   si m > 0 y n > 0");
        System.out.println("==================================\n");
    }

    public static int ackermann(int m, int n, OperationCounter counter) {
        Stack<Integer> stack = new Stack<>();
        stack.push(m);

        while (!stack.isEmpty()) {
            counter.increment();

            m = stack.pop();

            if (m == 0) {
                n++;
            } else if (n == 0) {
                stack.push(m - 1);
                n = 1;
            } else {
                stack.push(m - 1);
                stack.push(m);
                n--;
            }
        }

        return n;
    }

    public static void runAnalysis(int m, int n) {
        System.out.println("Parámetros de entrada:");
        System.out.printf("m = %d%n", m);
        System.out.printf("n = %d%n\n", n);

        OperationCounter counter = new OperationCounter();

        long startTime = System.nanoTime();
        int result = ackermann(m, n, counter);
        long elapsedTime = System.nanoTime() - startTime;

        System.out.println("Resultado de la función:");
        System.out.printf("Ackermann(%d, %d) = %d%n", m, n, result);
        System.out.printf("T(%d, %d) = %d operaciones%n", m, n, counter.get());
        System.out.printf("Tiempo de ejecución: %.3f ms%n", elapsedTime / 1_000_000.0);
    }

    public static void main(String[] args) {
        printFunctionDefinition();

        int m = 3;
        int n = 2;

        runAnalysis(m, n);
    }
}
