package paquete_principal;

public interface HashStructure {
    void addEntry(String lookupKey, ClientData dataValue);
    ClientData findEntry(String searchKey);
    long getLastOpDuration(); // Para registrar la duración de la última operación
}