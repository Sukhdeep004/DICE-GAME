import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main Dice Game class that handles the gameplay and GUI
 * Fixed version with proper single player mode and accurate dice display
 */
public class DiceGameMain extends JFrame implements ActionListener {
    
    // Game configuration
    private boolean isTwoPlayerMode;
    private int maxRounds;
    
    // Game state
    private Player player1, player2;
    private boolean isPlayer1Turn = true;
    private int currentRound = 1;
    private boolean gameEnded = false;
    
    // Game components
    private Dice dice1, dice2;
    
    // GUI components
    private JPanel mainPanel, dicePanel, controlPanel, statusPanel, scorePanel;
    private JButton rollButton, newGameButton, exitButton;
    private JLabel titleLabel, roundLabel, turnLabel, resultLabel;
    private JLabel player1ScoreLabel, player2ScoreLabel;
    private JProgressBar roundProgressBar;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    
    /**
     * Constructor to initialize the game with configuration
     */
    public DiceGameMain(boolean isTwoPlayerMode, int maxRounds) {
        this.isTwoPlayerMode = isTwoPlayerMode;
        this.maxRounds = maxRounds;
        
        initializeGame();
        setupGUI();
        setupEventListeners();
        updateDisplay();
    }
    
    /**
     * Initialize game components and players
     */
    private void initializeGame() {
        dice1 = new Dice();
        dice2 = new Dice();
        
        player1 = new Player("Player 1");
        if (isTwoPlayerMode) {
            player2 = new Player("Player 2");
        } else {
            player2 = new Player("Computer");
        }
    }
    
    /**
     * Setup the main GUI
     */
    private void setupGUI() {
        setTitle("🎲 Dice Game - " + (isTwoPlayerMode ? "Two Player" : "Single Player"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with gradient background
        mainPanel = new JPanel(new BorderLayout(15, 15)) {
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        setupTitlePanel();
        setupDicePanel();
        setupControlPanel();
        setupStatusPanel();
        setupScorePanel();
        
        add(mainPanel);
    }
    
    /**
     * Setup the title panel
     */
    private void setupTitlePanel() {
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setOpaque(false);
        
        String modeText = isTwoPlayerMode ? "Two Player Mode" : "Single Player Mode";
        titleLabel = new JLabel(" DICE GAME - " + modeText + " ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titlePanel.add(titleLabel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }
    
    /**
     * Setup the dice display panel
     */
    private void setupDicePanel() {
        dicePanel = new JPanel(new GridLayout(1, 2, 40, 0));
        dicePanel.setOpaque(false);
        dicePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2), 
            "Dice Roll Result", 0, 0, new Font("Arial", Font.BOLD, 16), PRIMARY_COLOR));
        
        dicePanel.add(dice1.getDiceLabel());
        dicePanel.add(dice2.getDiceLabel());
        
        mainPanel.add(dicePanel, BorderLayout.CENTER);
    }
    
    /**
     * Setup the control buttons panel
     */
    private void setupControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        controlPanel.setOpaque(false);
        
        rollButton = createStyledButton("ROLL DICE", SUCCESS_COLOR);
        rollButton.setPreferredSize(new Dimension(180, 60));
        rollButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        newGameButton = createStyledButton("NEW GAME", PRIMARY_COLOR);
        newGameButton.setPreferredSize(new Dimension(150, 60));
        
        exitButton = createStyledButton("EXIT", DANGER_COLOR);
        exitButton.setPreferredSize(new Dimension(120, 60));
        
        controlPanel.add(rollButton);
        controlPanel.add(newGameButton);
        controlPanel.add(exitButton);
        
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup the status information panel
     */
    private void setupStatusPanel() {
        statusPanel = new JPanel(new GridLayout(5, 1, 5, 10));
        statusPanel.setOpaque(false);
        statusPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2), 
            "Game Status", 0, 0, new Font("Arial", Font.BOLD, 14), PRIMARY_COLOR));
        
        roundLabel = new JLabel("Round: 1/" + maxRounds, JLabel.CENTER);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        turnLabel = new JLabel("Turn: " + player1.getName(), JLabel.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setForeground(SUCCESS_COLOR);
        
        resultLabel = new JLabel("Click 'Roll Dice' to start!", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        
        roundProgressBar = new JProgressBar(0, maxRounds);
        roundProgressBar.setValue(1);
        roundProgressBar.setStringPainted(true);
        roundProgressBar.setString("Round Progress");
        
        JLabel instructionLabel = new JLabel("<html><center>Roll both dice<br>Doubles = 2x points!</center></html>", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        instructionLabel.setForeground(Color.GRAY);
        
        statusPanel.add(roundLabel);
        statusPanel.add(turnLabel);
        statusPanel.add(resultLabel);
        statusPanel.add(roundProgressBar);
        statusPanel.add(instructionLabel);
        
        mainPanel.add(statusPanel, BorderLayout.WEST);
    }
    
    /**
     * Setup the score display panel
     */
    private void setupScorePanel() {
        scorePanel = new JPanel(new GridLayout(2, 1, 5, 20));
        scorePanel.setOpaque(false);
        scorePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2), 
            "Scores", 0, 0, new Font("Arial", Font.BOLD, 14), PRIMARY_COLOR));
        
        player1ScoreLabel = new JLabel(player1.getName() + ": 0", JLabel.CENTER);
        player1ScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        player1ScoreLabel.setForeground(SUCCESS_COLOR);
        
        player2ScoreLabel = new JLabel(player2.getName() + ": 0", JLabel.CENTER);
        player2ScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        player2ScoreLabel.setForeground(DANGER_COLOR);
        
        scorePanel.add(player1ScoreLabel);
        scorePanel.add(player2ScoreLabel);
        
        mainPanel.add(scorePanel, BorderLayout.EAST);
    }
    
    /**
     * Create a styled button
     */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(color.brighter());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    /**
     * Setup event listeners
     */
    private void setupEventListeners() {
        rollButton.addActionListener(this);
        newGameButton.addActionListener(this);
        exitButton.addActionListener(this);
    }
    
    /**
     * Handle button click events
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rollButton && !gameEnded) {
            rollDice();
        } else if (e.getSource() == newGameButton) {
            startNewGame();
        } else if (e.getSource() == exitButton) {
            exitToSetup();
        }
    }
    
    /**
     * Main dice rolling logic - FIXED for single player mode
     */
    private void rollDice() {
        if (gameEnded) return;
        
        // Disable roll button during turn
        rollButton.setEnabled(false);
        
        // Roll the dice
        dice1.roll();
        dice2.roll();
        
        // Calculate score (dice values are now accurate)
        int die1Value = dice1.getValue();
        int die2Value = dice2.getValue();
        int rollScore = die1Value + die2Value;
        
        // Check for doubles bonus
        boolean isDouble = (die1Value == die2Value);
        if (isDouble) {
            rollScore *= 2; // Double the score for matching dice
        }
        
        // Add score to current player
        Player currentPlayer = isPlayer1Turn ? player1 : player2;
        currentPlayer.addScore(rollScore);
        
        // Update display
        updateScoreDisplay();
        updateResultDisplay(die1Value, die2Value, rollScore, isDouble);
        
        // Handle turn progression
        if (isTwoPlayerMode) {
            // Two player mode: switch turns after each roll
            switchTurn();
            if (isPlayer1Turn) {
                // Both players have played, advance round
                nextRound();
            }
        } else {
            // Single player mode: FIXED - computer takes turn automatically
            if (isPlayer1Turn) {
                // Player just rolled, now computer's turn
                switchTurn();
                // Schedule computer's turn after a delay
                Timer computerTimer = new Timer(2000, e -> {
                    if (!gameEnded) {
                        computerRoll();
                    }
                });
                computerTimer.setRepeats(false);
                computerTimer.start();
            } else {
                // Computer just rolled, advance round and switch back to player
                nextRound();
                switchTurn();
                rollButton.setEnabled(true);
            }
        }
        
        // Check for game end
        if (currentRound > maxRounds) {
            endGame();
        } else if (isTwoPlayerMode) {
            rollButton.setEnabled(true);
        }
    }
    
    /**
     * Handle computer's automatic roll in single player mode
     */
    private void computerRoll() {
        if (gameEnded) return;
        
        // Computer rolls dice
        dice1.roll();
        dice2.roll();
        
        int die1Value = dice1.getValue();
        int die2Value = dice2.getValue();
        int rollScore = die1Value + die2Value;
        
        boolean isDouble = (die1Value == die2Value);
        if (isDouble) {
            rollScore *= 2;
        }
        
        // Add score to computer
        player2.addScore(rollScore);
        
        // Update display
        updateScoreDisplay();
        updateResultDisplay(die1Value, die2Value, rollScore, isDouble);
        
        // Advance round and switch back to player
        nextRound();
        switchTurn();
        
        // Check for game end
        if (currentRound > maxRounds) {
            endGame();
        } else {
            rollButton.setEnabled(true);
        }
    }
    
    /**
     * Switch to the next player's turn
     */
    private void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        updateTurnDisplay();
    }
    
    /**
     * Move to the next round
     */
    private void nextRound() {
        currentRound++;
        updateRoundDisplay();
    }
    
    /**
     * Update all display elements
     */
    private void updateDisplay() {
        updateScoreDisplay();
        updateRoundDisplay();
        updateTurnDisplay();
    }
    
    /**
     * Update score display
     */
    private void updateScoreDisplay() {
        player1ScoreLabel.setText(player1.getName() + ": " + player1.getScore());
        player2ScoreLabel.setText(player2.getName() + ": " + player2.getScore());
    }
    
    /**
     * Update round display
     */
    private void updateRoundDisplay() {
        roundLabel.setText("Round: " + currentRound + "/" + maxRounds);
        roundProgressBar.setValue(currentRound - 1);
    }
    
    /**
     * Update turn display
     */
    private void updateTurnDisplay() {
        String currentPlayerName = isPlayer1Turn ? player1.getName() : player2.getName();
        turnLabel.setText("Turn: " + currentPlayerName);
        turnLabel.setForeground(isPlayer1Turn ? SUCCESS_COLOR : DANGER_COLOR);
    }
    
    /**
     * Update result display with accurate information
     */
    private void updateResultDisplay(int die1, int die2, int score, boolean isDouble) {
        String currentPlayerName = isPlayer1Turn ? player1.getName() : player2.getName();
        String message = currentPlayerName + " rolled: " + die1 + " + " + die2;
        
        if (isDouble) {
            message += " (DOUBLE! x2 bonus)";
        }
        
        message += " = " + score + " points";
        resultLabel.setText(message);
    }
    
    /**
     * End the game and show results
     */
    private void endGame() {
        gameEnded = true;
        rollButton.setEnabled(false);
        
        String winner;
        Color winnerColor;
        
        if (player1.getScore() > player2.getScore()) {
            winner = player1.getName() + " WINS! 🎉";
            winnerColor = SUCCESS_COLOR;
        } else if (player2.getScore() > player1.getScore()) {
            winner = player2.getName() + " WINS! 🎉";
            winnerColor = DANGER_COLOR;
        } else {
            winner = "IT'S A TIE! 🤝";
            winnerColor = PRIMARY_COLOR;
        }
        
        resultLabel.setText(winner);
        resultLabel.setForeground(winnerColor);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Show detailed results
        showGameResults(winner);
    }
    
    /**
     * Show detailed game results dialog
     */
    private void showGameResults(String winner) {
        String message = String.format(
            "🎮 GAME OVER 🎮\n\n" +
            "%s\n\n" +
            "Final Scores:\n" +
            "%s: %d points\n" +
            "%s: %d points\n\n" +
            "Rounds Played: %d\n\n" +
            "Would you like to play again?",
            winner,
            player1.getName(), player1.getScore(),
            player2.getName(), player2.getScore(),
            maxRounds
        );
        
        int choice = JOptionPane.showConfirmDialog(
            this, message, "Game Results", 
            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        }
    }
    
    /**
     * Start a new game with same settings
     */
    private void startNewGame() {
        // Reset game state
        player1.resetScore();
        player2.resetScore();
        currentRound = 1;
        isPlayer1Turn = true;
        gameEnded = false;
        
        // Reset dice display
        dice1.reset();
        dice2.reset();
        
        // Reset UI
        rollButton.setEnabled(true);
        resultLabel.setText("Click 'Roll Dice' to start!");
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        
        updateDisplay();
    }
    
    /**
     * Exit to setup screen
     */
    private void exitToSetup() {
        int choice = JOptionPane.showConfirmDialog(
            this, "Return to game setup?", 
            "Exit Game", JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            this.dispose();
            new GameSetup().setVisible(true);
        }
    }
}
