package paquete_principal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserInterface extends JFrame {
    private JTextField idField, givenNamesField, familyNamesField, phoneField, emailField, addressField, zipCodeField, searchInputField;
    private JButton addClientButton, searchSequentialButton, searchChainedButton;
    private JTextArea resultsArea, timingArea;

    private SequentialProbeTable sequentialProbeTable;
    private ChainedHashSystem chainedHashSystem;
    private List<ClientData> allClientRecords; 

    public UserInterface() {
        setTitle("Análisis de Estructuras Hash");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); 

        // Inicializar tablas y lista de clientes
        int tableSize = 100; 
        sequentialProbeTable = new SequentialProbeTable(tableSize);
        chainedHashSystem = new ChainedHashSystem(tableSize);
        allClientRecords = new ArrayList<>();

        // Componentes de la Interfaz de Usuario
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 80, 25);
        add(lblId);
        idField = new JTextField();
        idField.setBounds(100, 20, 150, 25);
        add(idField);

        JLabel lblGivenNames = new JLabel("Nombres:");
        lblGivenNames.setBounds(20, 50, 80, 25);
        add(lblGivenNames);
        givenNamesField = new JTextField();
        givenNamesField.setBounds(100, 50, 150, 25);
        add(givenNamesField);

        JLabel lblFamilyNames = new JLabel("Apellidos:");
        lblFamilyNames.setBounds(20, 80, 80, 25);
        add(lblFamilyNames);
        familyNamesField = new JTextField();
        familyNamesField.setBounds(100, 80, 150, 25);
        add(familyNamesField);

        JLabel lblPhone = new JLabel("Teléfono:");
        lblPhone.setBounds(20, 110, 80, 25);
        add(lblPhone);
        phoneField = new JTextField();
        phoneField.setBounds(100, 110, 150, 25);
        add(phoneField);

        JLabel lblEmail = new JLabel("Correo:");
        lblEmail.setBounds(20, 140, 80, 25);
        add(lblEmail);
        emailField = new JTextField();
        emailField.setBounds(100, 140, 150, 25);
        add(emailField);

        JLabel lblAddress = new JLabel("Dirección:");
        lblAddress.setBounds(20, 170, 80, 25);
        add(lblAddress);
        addressField = new JTextField();
        addressField.setBounds(100, 170, 150, 25);
        add(addressField);

        JLabel lblZipCode = new JLabel("C. Postal:");
        lblZipCode.setBounds(20, 200, 80, 25);
        add(lblZipCode);
        zipCodeField = new JTextField();
        zipCodeField.setBounds(100, 200, 150, 25);
        add(zipCodeField);

        addClientButton = new JButton("Añadir Cliente");
        addClientButton.setBounds(20, 240, 150, 25);
        add(addClientButton);

        JLabel lblSearchPrompt = new JLabel("Buscar por Nombres/Apellidos:");
        lblSearchPrompt.setBounds(20, 280, 200, 25);
        add(lblSearchPrompt);
        searchInputField = new JTextField();
        searchInputField.setBounds(220, 280, 150, 25);
        add(searchInputField);

        searchSequentialButton = new JButton("Buscar (Sondeo Secuencial)");
        searchSequentialButton.setBounds(20, 310, 200, 25); // Ajuste de tamaño
        add(searchSequentialButton);

        searchChainedButton = new JButton("Buscar (Hash Encadenado)");
        searchChainedButton.setBounds(230, 310, 200, 25); // Ajuste de tamaño y posición
        add(searchChainedButton);

        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollResults = new JScrollPane(resultsArea);
        scrollResults.setBounds(20, 350, 750, 150);
        add(scrollResults);

        timingArea = new JTextArea();
        timingArea.setEditable(false);
        JScrollPane scrollTimings = new JScrollPane(timingArea);
        scrollTimings.setBounds(20, 510, 750, 100);
        add(scrollTimings);


        // Acciones de los botones
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String names = givenNamesField.getText();
                String surnames = familyNamesField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                String zip = zipCodeField.getText();

                // Validación básica de campos
                if (id.isEmpty() || names.isEmpty() || surnames.isEmpty()) {
                    JOptionPane.showMessageDialog(UserInterface.this, "ID, Nombres y Apellidos son campos obligatorios.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ClientData newClient = new ClientData(id, names, surnames, phone, email, address, zip);
                allClientRecords.add(newClient);

                String hashingKey = (names + surnames).toLowerCase();

                sequentialProbeTable.addEntry(hashingKey, newClient);
                chainedHashSystem.addEntry(hashingKey, newClient);

                resultsArea.append("Cliente añadido: " + newClient.toString() + "\n");
                clearInputFields();
            }
        });

        searchSequentialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String termToSearch = searchInputField.getText().trim();
                if (termToSearch.isEmpty()) {
                    resultsArea.setText("Por favor, ingrese un nombre o apellido para buscar (Sondeo Secuencial).");
                    timingArea.append("Búsqueda Sondeo Secuencial abortada: campo vacío.\n");
                    return;
                }
                
                String actualSearchKey = termToSearch.toLowerCase();

                long startMeasure = System.nanoTime();
                ClientData foundClient = sequentialProbeTable.findEntry(actualSearchKey); // Pasa la clave de búsqueda convertida
                long endMeasure = System.nanoTime();
                long duration = endMeasure - startMeasure;

                resultsArea.setText("Búsqueda por Sondeo Secuencial para '" + termToSearch + "':\n");
                if (foundClient != null) {
                    resultsArea.append("Hallado: " + foundClient.toString() + "\n");
                } else {
                    resultsArea.append("No hallado.\n");
                }
                timingArea.append("Sondeo Secuencial - Tiempo de búsqueda: " + duration + " nanosegundos\n");
            }
        });

        searchChainedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String termToSearch = searchInputField.getText().trim();
                if (termToSearch.isEmpty()) {
                    resultsArea.setText("Por favor, ingrese un nombre o apellido para buscar (Hash Encadenado).");
                    timingArea.append("Búsqueda Encadenamiento abortada: campo vacío.\n");
                    return;
                }

                String actualSearchKey = termToSearch.toLowerCase();

                long startMeasure = System.nanoTime();
                ClientData foundClient = chainedHashSystem.findEntry(actualSearchKey); // Pasa la clave de búsqueda convertida
                long endMeasure = System.nanoTime();
                long duration = endMeasure - startMeasure;

                resultsArea.setText("Búsqueda por Hash Encadenado para '" + termToSearch + "':\n");
                if (foundClient != null) {
                    resultsArea.append("Hallado: " + foundClient.toString() + "\n");
                } else {
                    resultsArea.append("No hallado.\n");
                }
                timingArea.append("Hash Encadenado - Tiempo de búsqueda: " + duration + " nanosegundos\n");
            }
        });
    }

    private void clearInputFields() {
        idField.setText("");
        givenNamesField.setText("");
        familyNamesField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        zipCodeField.setText("");
    }

    public void displayAsymptoticComparison() {
        timingArea.append("\n--- Análisis de Complejidad Asintótica ---\n");
        timingArea.append("Operaciones de Inserción y Búsqueda (Rendimiento Promedio):\n");
        timingArea.append("  - Sondeo Secuencial: $O(1)$ ideal, pero $O(N)$ en este diseño de búsqueda.\n"); // Ajustado
        timingArea.append("  - Hash Encadenado (con BST): $O(\\log k)$ ideal, pero $O(N \\cdot \\log k)$ en este diseño de búsqueda.\n"); // Ajustado
        timingArea.append("Operaciones de Inserción y Búsqueda (Peor Caso):\n");
        timingArea.append("  - Sondeo Secuencial: $O(N)$ (cuando la tabla está casi llena o hay muchas colisiones consecutivas)\n");
        timingArea.append("  - Hash Encadenado (con BST): $O(N \\cdot k)$ (si el BST degenera a una lista enlazada y se busca en todos los buckets)\n");
        timingArea.append("Nota: '$N$' es el número total de elementos, '$k$' es el número de elementos en el BST de una posición.\n");
        timingArea.append("Debido a cómo se realiza la búsqueda por nombre/apellido (que no es la clave directa de hash), el rendimiento en la práctica puede degradarse a $O(N)$.\n"); // Añadido
    }
}