package paquete_principal;

public class BinarySearchTreeWrapper {
    class TreeNode {
        ClientData clientData;
        TreeNode leftChild, rightChild;

        public TreeNode(ClientData clientData) {
            this.clientData = clientData;
            leftChild = rightChild = null;
        }
    }

    TreeNode treeRoot;

    public BinarySearchTreeWrapper() {
        treeRoot = null;
    }

    // Inserta un objeto ClientData en el árbol, ordenado por uniqueId (asumido único)
    public void addClient(ClientData client) {
        treeRoot = insertNodeRecursive(treeRoot, client);
    }

    private TreeNode insertNodeRecursive(TreeNode node, ClientData client) {
        if (node == null) {
            return new TreeNode(client);
        }

        // Si el uniqueId ya existe, no se inserta (se considera un duplicado).
        // Se podría agregar lógica para actualizar si se prefiere.
        if (node.clientData.getUniqueId().equals(client.getUniqueId())) {
            return node;
        }

        int comparisonResult = client.getUniqueId().compareTo(node.clientData.getUniqueId());
        if (comparisonResult < 0) {
            node.leftChild = insertNodeRecursive(node.leftChild, client);
        } else { // comparisonResult > 0 (ir a la derecha)
            node.rightChild = insertNodeRecursive(node.rightChild, client);
        }
        return node;
    }

    // Busca un cliente por nombre o apellido (recorrido completo del árbol)
    // 'searchKey' es el término de búsqueda del usuario (ya en minúsculas)
    public ClientData findClient(String searchKey) {
        return searchNodeRecursive(treeRoot, searchKey);
    }

    private ClientData searchNodeRecursive(TreeNode node, String searchKey) {
        if (node == null) {
            return null;
        }

        // Comprueba si el cliente en el nodo actual coincide con la clave de búsqueda
        // Comparación insensible a mayúsculas/minúsculas y usando .equals() porque 'searchKey' ya está en minúsculas
        if (node.clientData.getGivenNames().toLowerCase().equals(searchKey) ||
            node.clientData.getFamilyNames().toLowerCase().equals(searchKey)) {
            return node.clientData;
        }

        // Debido a que la clave de búsqueda (nombre/apellido) no es la clave de ordenación (uniqueId),
        // se deben buscar en ambos subárboles.
        ClientData foundInLeftSubtree = searchNodeRecursive(node.leftChild, searchKey);
        if (foundInLeftSubtree != null) {
            return foundInLeftSubtree;
        }

        ClientData foundInRightSubtree = searchNodeRecursive(node.rightChild, searchKey);
        return foundInRightSubtree;
    }
}