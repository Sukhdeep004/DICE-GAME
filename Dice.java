import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Dice class that handles individual dice functionality
 * Manages dice value, display, and rolling animation
 */
public class Dice {
    private int value;
    private JLabel diceLabel;
    private Random random;
    
    // Dice face Unicode characters
    private final String[] DICE_FACES = {
        "⚀", "⚁", "⚂", "⚃", "⚄", "⚅"
    };
    
    // Colors for dice faces
    private final Color DICE_COLOR = new Color(52, 73, 94);
    private final Color DICE_BACKGROUND = Color.WHITE;
    private final Color DICE_BORDER = new Color(41, 128, 185);
    
    /**
     * Constructor to initialize the dice
     */
    public Dice() {
        random = new Random();
        value = 1; 
        setupDiceLabel();
        updateDisplay(); 
    }
    
    /**
     * Setup the visual representation of the dice
     */
    private void setupDiceLabel() {
        diceLabel = new JLabel("", JLabel.CENTER);
        diceLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 100));
        diceLabel.setForeground(DICE_COLOR);
        diceLabel.setOpaque(true);
        diceLabel.setBackground(DICE_BACKGROUND);
        diceLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DICE_BORDER, 3),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        diceLabel.setPreferredSize(new Dimension(180, 180));
    }
    
    /**
     * Roll the dice and update display
     */
    public void roll() {
        value = random.nextInt(6) + 1;
        updateDisplay();
        addRollAnimation();
    }
    
    /**
     * Update the visual display of the dice
     */
    private void updateDisplay() {
        diceLabel.setText(DICE_FACES[value - 1]); // value-1 because array is 0-indexed
        diceLabel.setToolTipText("Value: " + value);
    }
    
    /**
     * Add visual animation when dice is rolled
     */
    private void addRollAnimation() {
        diceLabel.setBackground(new Color(241, 196, 15));
        Timer colorTimer = new Timer(300, e -> {
            diceLabel.setBackground(DICE_BACKGROUND);
        });
        colorTimer.setRepeats(false);
        colorTimer.start();
    }
    
    /**
     * Get the current value of the dice
     * @return current dice value (1-6)
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Get the JLabel component for display
     * @return JLabel representing the dice
     */
    public JLabel getDiceLabel() {
        return diceLabel;
    }
    
    /**
     * Reset dice to initial state
     */
    public void reset() {
        value = 1;
        updateDisplay();
        diceLabel.setBackground(DICE_BACKGROUND);
    }
    
    /**
     * Set a specific value for the dice (for testing)
     * @param newValue value to set (1-6)
     */
    public void setValue(int newValue) {
        if (newValue >= 1 && newValue <= 6) {
            value = newValue;
            updateDisplay();
        } else {
            throw new IllegalArgumentException("Dice value must be between 1 and 6");
        }
    }
    
    /**
     * Get string representation of the dice
     * @return string showing current value
     */
    @Override
    public String toString() {
        return "Dice{value=" + value + ", face='" + DICE_FACES[value - 1] + "'}";
    }
}
