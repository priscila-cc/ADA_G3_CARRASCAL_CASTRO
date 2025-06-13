package paquete_principal;

public class SequentialProbeTable implements HashStructure {
    private ClientData[] dataArray;
    private int storageCapacity;
    private int currentSize; // Cantidad actual de elementos
    private long latestOperationDuration;
    private final ClientData DELETED_MARKER = new ClientData("ERASED_ID", "ERASED", "ENTRY", null, null, null, null);

    public SequentialProbeTable(int capacity) {
        this.storageCapacity = capacity;
        this.dataArray = new ClientData[capacity];
        this.currentSize = 0; // Inicializar tamaño
    }

    // Función de dispersión: Convierte una 'clave' en un índice
    private int computeHash(String key) {
        if (key == null || key.isEmpty()) {
            return 0; // Manejo básico para claves nulas o vacías
        }
        return Math.abs(key.hashCode() % storageCapacity);
    }

    @Override
    public void addEntry(String lookupKey, ClientData dataValue) {
        long startedTime = System.nanoTime();
        int initialIndex = computeHash(lookupKey); // Usa la clave completa (nombres + apellidos) para el hash

        int startingPoint = initialIndex;
        int trialCount = 0;
        do {
            if (dataArray[initialIndex] == null || dataArray[initialIndex] == DELETED_MARKER) {
                // Posición vacía o marcada como eliminada, insertamos aquí
                dataArray[initialIndex] = dataValue;
                currentSize++;
                latestOperationDuration = System.nanoTime() - startedTime;
                return;
            }
            // Si el cliente ya existe (ej. mismo ID), no se reinserta.
            if (dataArray[initialIndex].getUniqueId().equals(dataValue.getUniqueId())) {
                latestOperationDuration = System.nanoTime() - startedTime;
                return;
            }

            initialIndex = (initialIndex + 1) % storageCapacity; 
            trialCount++;
            // Previene bucles infinitos si la tabla está completamente llena o ya se revisaron todas las posiciones.
            if (trialCount >= storageCapacity) {
                System.err.println("Advertencia: Tabla de sondeo lineal llena o muy densa, no se pudo agregar: " + dataValue.getGivenNames());
                latestOperationDuration = System.nanoTime() - startedTime;
                return;
            }
        } while (initialIndex != startingPoint || trialCount == 0);

        // Si el bucle termina sin encontrar espacio, la tabla está llena.
        System.err.println("Error: Tabla de sondeo lineal llena, no se pudo agregar: " + dataValue.getGivenNames());
        latestOperationDuration = System.nanoTime() - startedTime;
    }

    @Override
    public ClientData findEntry(String searchKey) { // 'searchKey' es el término de búsqueda (ej. "juan", en minúsculas)
        long startedTime = System.nanoTime();
        
        for (int i = 0; i < storageCapacity; i++) {
            if (dataArray[i] == null) {
                continue; 
            }

            // Ignorar celdas marcadas como DELETED_MARKER (si se implementa la eliminación)
            if (dataArray[i] != DELETED_MARKER) {
                if (dataArray[i].getGivenNames().toLowerCase().equals(searchKey) ||
                    dataArray[i].getFamilyNames().toLowerCase().equals(searchKey)) {
                    latestOperationDuration = System.nanoTime() - startedTime;
                    return dataArray[i];
                }
            }
        }
        latestOperationDuration = System.nanoTime() - startedTime;
        return null; 
    }

    @Override
    public long getLastOpDuration() {
        return latestOperationDuration;
    }

    public int getCurrentSize() {
        return currentSize;
    }
}