import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Game Setup Screen - Initial screen for configuring game options
 * Allows players to choose game mode and number of rounds
 */
public class GameSetup extends JFrame implements ActionListener {
    
    // GUI Components
    private JPanel mainPanel, setupPanel, buttonPanel;
    private JLabel titleLabel, modeLabel, roundsLabel;
    private JRadioButton singlePlayerRadio, twoPlayerRadio;
    private ButtonGroup modeGroup;
    private JComboBox<String> roundsComboBox;
    private JButton startButton, exitButton;
    
    // Game Configuration
    private boolean isTwoPlayerMode = false;
    private int numberOfRounds = 5;
    
    // Colors and styling
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    
    /**
     * Constructor to initialize the setup screen
     */
    public GameSetup() {
        setupWindow();
        createComponents();
        layoutComponents();
        setupEventListeners();
    }
    
    /**
     * Setup the main window properties
     */
    private void setupWindow() {
        setTitle(" Dice Game - Setup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Create all GUI components
     */
    private void createComponents() {
        // Main panel with gradient background
        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_COLOR, 
                                                         0, getHeight(), Color.WHITE);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Title
        titleLabel = new JLabel(" DICE GAME SETUP ", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        // Setup panel
        setupPanel = new JPanel(new GridBagLayout());
        setupPanel.setOpaque(false);
        setupPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        // Game mode selection
        modeLabel = new JLabel("Choose Game Mode:");
        modeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        modeLabel.setForeground(PRIMARY_COLOR);
        
        singlePlayerRadio = new JRadioButton(" Single Player (vs Computer)");
        singlePlayerRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        singlePlayerRadio.setOpaque(false);
        singlePlayerRadio.setSelected(true);
        
        twoPlayerRadio = new JRadioButton(" Two Player (Human vs Human)");
        twoPlayerRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        twoPlayerRadio.setOpaque(false);
        
        modeGroup = new ButtonGroup();
        modeGroup.add(singlePlayerRadio);
        modeGroup.add(twoPlayerRadio);
        
        // Rounds selection
        roundsLabel = new JLabel("Number of Rounds:");
        roundsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roundsLabel.setForeground(PRIMARY_COLOR);
        
        String[] roundOptions = new String[20];
        for (int i = 1; i <= 20; i++) {
            roundOptions[i-1] = String.valueOf(i);
        }
        roundsComboBox = new JComboBox<>(roundOptions);
        roundsComboBox.setSelectedIndex(4); // Default to 5 rounds
        roundsComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        roundsComboBox.setPreferredSize(new Dimension(100, 30));
        
        // Buttons
        startButton = createStyledButton(" START GAME", SUCCESS_COLOR);
        startButton.setPreferredSize(new Dimension(200, 50));
        
        exitButton = createStyledButton(" EXIT", DANGER_COLOR);
        exitButton.setPreferredSize(new Dimension(120, 50));
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
    }
    
    /**
     * Layout all components using GridBagLayout
     */
    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Game mode section
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        setupPanel.add(modeLabel, gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 30, 5, 10);
        setupPanel.add(singlePlayerRadio, gbc);
        
        gbc.gridy = 2;
        setupPanel.add(twoPlayerRadio, gbc);
        
        // Rounds section
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        setupPanel.add(roundsLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        setupPanel.add(roundsComboBox, gbc);
        
        // Add components to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(setupPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Create a styled button with custom appearance
     */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    /**
     * Setup event listeners for buttons and radio buttons
     */
    private void setupEventListeners() {
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        singlePlayerRadio.addActionListener(e -> isTwoPlayerMode = false);
        twoPlayerRadio.addActionListener(e -> isTwoPlayerMode = true);
        
        roundsComboBox.addActionListener(e -> 
            numberOfRounds = Integer.parseInt((String) roundsComboBox.getSelectedItem()));
    }
    
    /**
     * Handle button click events
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startGame();
        } else if (e.getSource() == exitButton) {
            exitApplication();
        }
    }
    
    /**
     * Start the game with selected configuration
     */
    private void startGame() {
        // Get final configuration
        isTwoPlayerMode = twoPlayerRadio.isSelected();
        numberOfRounds = Integer.parseInt((String) roundsComboBox.getSelectedItem());
        
        // Show loading message
        startButton.setText("Loading...");
        startButton.setEnabled(false);
        
        // Create and show the main game window
        SwingUtilities.invokeLater(() -> {
            DiceGameMain gameWindow = new DiceGameMain(isTwoPlayerMode, numberOfRounds);
            gameWindow.setVisible(true);
            
            // Close the setup window
            this.dispose();
        });
    }
    
    /**
     * Exit the application with confirmation
     */
    private void exitApplication() {
        int choice = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to exit?", 
            "Exit Application", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
