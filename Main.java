// Main.java
public class Main {
    /**
     * Main method to start the application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Set system look and feel for better appearance
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getLookAndFeel());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }
        
        // Launch the game setup screen
        javax.swing.SwingUtilities.invokeLater(() -> {
            new GameSetup().setVisible(true);
        });
    }
}
