package paquete_principal;

public class ChainedHashSystem implements HashStructure {
    private BinarySearchTreeWrapper[] internalTable;
    private int tableCapacity;
    private int totalEntries;
    private long lastOperationDuration;

    public ChainedHashSystem(int capacity) {
        this.tableCapacity = capacity;
        this.internalTable = new BinarySearchTreeWrapper[capacity];
        for (int i = 0; i < capacity; i++) {
            internalTable[i] = new BinarySearchTreeWrapper(); // Inicializa cada posición con un BST vacío
        }
        this.totalEntries = 0;
    }

    // Función de dispersión: Toma la clave que será usada para distribuir el elemento
    private int calculateBucketIndex(String key) {
        if (key == null || key.isEmpty()) {
            return 0;
        }
        return Math.abs(key.hashCode() % tableCapacity);
    }

    @Override
    public void addEntry(String lookupKey, ClientData dataValue) {
        long startedTime = System.nanoTime();
        int bucketIndex = calculateBucketIndex(lookupKey); // Usa la clave generada para el hashing
        internalTable[bucketIndex].addClient(dataValue); // Delega la inserción al BST
        totalEntries++;
        lastOperationDuration = System.nanoTime() - startedTime;
    }

    @Override
    public ClientData findEntry(String searchKey) { // 'searchKey' es el término de búsqueda (ej. "Juan", en minúsculas)
        long startedTime = System.nanoTime();
        
        // Similar al sondeo lineal, si el calculateBucketIndex(searchKey) no conduce al bucket correcto
        // (porque se usó una clave diferente en la inserción), es necesario buscar en todos los buckets.
        // Esto degrada el rendimiento de la búsqueda a O(N * k) en el peor caso, donde k es el tamaño del BST en el bucket.
        for (int i = 0; i < tableCapacity; i++) {
            ClientData foundData = internalTable[i].findClient(searchKey);
            if (foundData != null) {
                lastOperationDuration = System.nanoTime() - startedTime;
                return foundData;
            }
        }
        
        lastOperationDuration = System.nanoTime() - startedTime;
        return null; 
    }

    @Override
    public long getLastOpDuration() {
        return lastOperationDuration;
    }

    public int getTotalEntries() {
        return totalEntries;
    }
}