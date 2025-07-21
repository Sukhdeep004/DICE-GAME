import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.*;

/**
 * Main Dice Game class that handles the GUI and game logic
 * Features a modern interface with sound effects and scoring system
 */
public class DiceGame extends JFrame implements ActionListener {
    // Game components
    private Dice dice1, dice2;
    private Player player1, player2;
    private boolean isPlayer1Turn = true;
    private boolean isTwoPlayerMode = false;
    private int currentRound = 1;
    private final int MAX_ROUNDS = 5;
    
    // GUI components
    private JPanel mainPanel, dicePanel, controlPanel, statusPanel, scorePanel;
    private JButton rollButton, resetButton, exitButton, modeButton;
    private JLabel titleLabel, roundLabel, turnLabel, resultLabel;
    private JLabel player1ScoreLabel, player2ScoreLabel;
    private JProgressBar roundProgressBar;
    
    // Colors and styling
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    
    /**
     * Constructor to initialize the game
     */
    public DiceGame() {
        initializeGame();
        setupGUI();
        setupEventListeners();
    }
    
    /**
     * Initialize game components and players
     */
    private void initializeGame() {
        dice1 = new Dice();
        dice2 = new Dice();
        player1 = new Player("Player 1");
        player2 = new Player("Computer");
    }
    
    /**
     * Setup the main GUI components
     */
    private void setupGUI() {
        setTitle(" Dice Game - Roll to Win!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with gradient background
        mainPanel = new JPanel(new BorderLayout(10, 10)) {
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
        
        titleLabel = new JLabel(" DICE GAME ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(PRIMARY_COLOR);
        titlePanel.add(titleLabel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }
    
    /**
     * Setup the dice display panel
     */
    private void setupDicePanel() {
        dicePanel = new JPanel(new GridLayout(1, 2, 30, 0));
        dicePanel.setOpaque(false);
        dicePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2), 
            "Dice", 0, 0, new Font("Arial", Font.BOLD, 16), PRIMARY_COLOR));
        
        dicePanel.add(dice1.getDiceLabel());
        dicePanel.add(dice2.getDiceLabel());
        
        mainPanel.add(dicePanel, BorderLayout.CENTER);
    }
    
    /**
     * Setup the control buttons panel
     */
    private void setupControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setOpaque(false);
        
        // Roll button
        rollButton = createStyledButton("ROLL DICE", SUCCESS_COLOR);
        rollButton.setPreferredSize(new Dimension(150, 50));
        
        // Reset button
        resetButton = createStyledButton("RESET", SECONDARY_COLOR);
        resetButton.setPreferredSize(new Dimension(120, 50));
        
        // Mode toggle button
        modeButton = createStyledButton("TWO PLAYER", PRIMARY_COLOR);
        modeButton.setPreferredSize(new Dimension(150, 50));
        
        // Exit button
        exitButton = createStyledButton("EXIT", DANGER_COLOR);
        exitButton.setPreferredSize(new Dimension(100, 50));
        
        controlPanel.add(rollButton);
        controlPanel.add(resetButton);
        controlPanel.add(modeButton);
        controlPanel.add(exitButton);
        
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup the status information panel
     */
    private void setupStatusPanel() {
        statusPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        statusPanel.setOpaque(false);
        statusPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2), 
            "Game Status", 0, 0, new Font("Arial", Font.BOLD, 14), PRIMARY_COLOR));
        
        roundLabel = new JLabel("Round: 1/" + MAX_ROUNDS, JLabel.CENTER);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        turnLabel = new JLabel("Turn: " + player1.getName(), JLabel.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setForeground(SUCCESS_COLOR);
        
        resultLabel = new JLabel("Roll the dice to start!", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        
        roundProgressBar = new JProgressBar(0, MAX_ROUNDS);
        roundProgressBar.setValue(1);
        roundProgressBar.setStringPainted(true);
        roundProgressBar.setString("Round Progress");
        
        statusPanel.add(roundLabel);
        statusPanel.add(turnLabel);
        statusPanel.add(resultLabel);
        statusPanel.add(roundProgressBar);
        
        mainPanel.add(statusPanel, BorderLayout.WEST);
    }
    
    /**
     * Setup the score display panel
     */
    private void setupScorePanel() {
        scorePanel = new JPanel(new GridLayout(2, 1, 5, 10));
        scorePanel.setOpaque(false);
        scorePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2), 
            "Scores", 0, 0, new Font("Arial", Font.BOLD, 14), PRIMARY_COLOR));
        
        player1ScoreLabel = new JLabel(player1.getName() + ": 0", JLabel.CENTER);
        player1ScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        player1ScoreLabel.setForeground(SUCCESS_COLOR);
        
        player2ScoreLabel = new JLabel(player2.getName() + ": 0", JLabel.CENTER);
        player2ScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        player2ScoreLabel.setForeground(DANGER_COLOR);
        
        scorePanel.add(player1ScoreLabel);
        scorePanel.add(player2ScoreLabel);
        
        mainPanel.add(scorePanel, BorderLayout.EAST);
    }
    
    /**
     * Create a styled button with custom appearance
     */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
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
     * Setup event listeners for buttons
     */
    private void setupEventListeners() {
        rollButton.addActionListener(this);
        resetButton.addActionListener(this);
        exitButton.addActionListener(this);
        modeButton.addActionListener(this);
    }
    
    /**
     * Handle button click events
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rollButton) {
            rollDice();
        } else if (e.getSource() == resetButton) {
            resetGame();
        } else if (e.getSource() == exitButton) {
            exitGame();
        } else if (e.getSource() == modeButton) {
            toggleGameMode();
        }
    }
    
    /**
     * Main dice rolling logic
     */
    private void rollDice() {
        playSound("dice_roll.wav");
        
        // Animate dice rolling
        animateDiceRoll();
        
        // Roll the dice
        dice1.roll();
        dice2.roll();
        
        // Calculate score
        int rollScore = calculateRollScore();
        
        // Update current player's score
        Player currentPlayer = isPlayer1Turn ? player1 : player2;
        currentPlayer.addScore(rollScore);
        
        // Update display
        updateScoreDisplay();
        updateResultDisplay(rollScore);
        
        // Check for round end
        if (!isTwoPlayerMode || !isPlayer1Turn) {
            nextRound();
        } else {
            switchTurn();
        }
        
        // Check for game end
        if (currentRound > MAX_ROUNDS) {
            endGame();
        }
    }
    
    /**
     * Animate dice rolling effect
     */
    private void animateDiceRoll() {
        Timer animationTimer = new Timer(100, null);
        final int[] animationCount = {0};
        
        animationTimer.addActionListener(e -> {
            dice1.roll();
            dice2.roll();
            animationCount[0]++;
            
            if (animationCount[0] >= 5) {
                animationTimer.stop();
            }
        });
        
        animationTimer.start();
        
        // Disable roll button during animation
        rollButton.setEnabled(false);
        Timer enableTimer = new Timer(500, e -> rollButton.setEnabled(true));
        enableTimer.setRepeats(false);
        enableTimer.start();
    }
    
    /**
     * Calculate score based on dice values
     */
    private int calculateRollScore() {
        int die1Value = dice1.getValue();
        int die2Value = dice2.getValue();
        int baseScore = die1Value + die2Value;
        
        // Bonus for doubles
        if (die1Value == die2Value) {
            return baseScore * 2; // Double score for matching dice
        }
        
        return baseScore;
    }
    
    /**
     * Update score display labels
     */
    private void updateScoreDisplay() {
        player1ScoreLabel.setText(player1.getName() + ": " + player1.getScore());
        player2ScoreLabel.setText(player2.getName() + ": " + player2.getScore());
    }
    
    /**
     * Update result display with roll information
     */
    private void updateResultDisplay(int rollScore) {
        String currentPlayerName = isPlayer1Turn ? player1.getName() : player2.getName();
        String message = currentPlayerName + " rolled " + dice1.getValue() + " + " + dice2.getValue();
        
        if (dice1.getValue() == dice2.getValue()) {
            message += " (DOUBLE! ×2 bonus)";
        }
        
        message += " = " + rollScore + " points";
        resultLabel.setText(message);
    }
    
    /**
     * Switch to the next player's turn
     */
    private void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        String currentPlayerName = isPlayer1Turn ? player1.getName() : player2.getName();
        turnLabel.setText("Turn: " + currentPlayerName);
        turnLabel.setForeground(isPlayer1Turn ? SUCCESS_COLOR : DANGER_COLOR);
        
        // Auto-play for computer in single player mode
        if (!isTwoPlayerMode && !isPlayer1Turn) {
            Timer computerTurnTimer = new Timer(1500, e -> rollDice());
            computerTurnTimer.setRepeats(false);
            computerTurnTimer.start();
        }
    }
    
    /**
     * Move to the next round
     */
    private void nextRound() {
        if (isTwoPlayerMode) {
            switchTurn();
            if (isPlayer1Turn) { // Both players have played
                currentRound++;
                updateRoundDisplay();
            }
        } else {
            currentRound++;
            updateRoundDisplay();
            isPlayer1Turn = true;
            turnLabel.setText("Turn: " + player1.getName());
            turnLabel.setForeground(SUCCESS_COLOR);
        }
    }
    
    /**
     * Update round display information
     */
    private void updateRoundDisplay() {
        roundLabel.setText("Round: " + currentRound + "/" + MAX_ROUNDS);
        roundProgressBar.setValue(currentRound - 1);
    }
    
    /**
     * End the game and show results
     */
    private void endGame() {
        rollButton.setEnabled(false);
        
        String winner;
        Color winnerColor;
        
        if (player1.getScore() > player2.getScore()) {
            winner = player1.getName() + " WINS! 🎉";
            winnerColor = SUCCESS_COLOR;
            playSound("win.wav");
        } else if (player2.getScore() > player1.getScore()) {
            winner = player2.getName() + " WINS! 🎉";
            winnerColor = DANGER_COLOR;
            playSound("lose.wav");
        } else {
            winner = "IT'S A TIE! 🤝";
            winnerColor = PRIMARY_COLOR;
            playSound("tie.wav");
        }
        
        resultLabel.setText(winner);
        resultLabel.setForeground(winnerColor);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Show detailed results dialog
        showGameResults(winner);
    }
    
    /**
     * Show detailed game results in a dialog
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
            MAX_ROUNDS
        );
        
        int choice = JOptionPane.showConfirmDialog(
            this, message, "Game Results", 
            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        }
    }
    
    /**
     * Reset the game to initial state
     */
    private void resetGame() {
        player1.resetScore();
        player2.resetScore();
        currentRound = 1;
        isPlayer1Turn = true;
        
        dice1.reset();
        dice2.reset();
        
        rollButton.setEnabled(true);
        updateScoreDisplay();
        updateRoundDisplay();
        
        turnLabel.setText("Turn: " + player1.getName());
        turnLabel.setForeground(SUCCESS_COLOR);
        resultLabel.setText("Roll the dice to start!");
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        
        roundProgressBar.setValue(1);
        
        playSound("reset.wav");
    }
    
    /**
     * Toggle between single player and two player modes
     */
    private void toggleGameMode() {
        isTwoPlayerMode = !isTwoPlayerMode;
        
        if (isTwoPlayerMode) {
            player2 = new Player("Player 2");
            modeButton.setText(" SINGLE PLAYER");
        } else {
            player2 = new Player("Computer");
            modeButton.setText(" TWO PLAYER");
        }
        
        resetGame();
        playSound("mode_change.wav");
    }
    
    /**
     * Exit the game with confirmation
     */
    private void exitGame() {
        int choice = JOptionPane.showConfirmDialog(
            this, "Are you sure you want to exit the game?", 
            "Exit Game", JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    /**
     * Play sound effect (placeholder - would need actual sound files)
     */
    private void playSound(String soundFile) {
        try {
            // This is a placeholder for sound functionality
            // In a real implementation, you would load and play actual sound files
            System.out.println("Playing sound: " + soundFile);
        } catch (Exception e) {
            System.err.println("Could not play sound: " + e.getMessage());
        }
    }
}
