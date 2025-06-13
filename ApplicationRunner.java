package paquete_principal;

import javax.swing.SwingUtilities;

public class ApplicationRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserInterface userInterface = new UserInterface();
                userInterface.setVisible(true);
                userInterface.displayAsymptoticComparison(); 
            }
        });
    }
}