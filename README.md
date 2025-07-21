# DICE-GAME

# 🎲 Java Swing Dice Game

A fully-featured dice game built with Java Swing, featuring modern GUI design, sound effects, and comprehensive game mechanics.

## 🎮 Game Features

### Core Gameplay
- **Single Player Mode**: Play against an intelligent computer opponent
- **Two Player Mode**: Play with a friend on the same computer
- **Round Tournament**: Select the number of rounds
- **Scoring System**: 
  - Normal roll: Sum of both dice
  - Double roll (same numbers): Double points bonus
- **Real-time Score Tracking**: Live score updates and round progress

### Visual Features
- **Modern GUI Design**: Clean, colorful interface with gradient backgrounds
- **Animated Dice**: Unicode dice faces with rolling animation
- **Responsive Layout**: Professional layout using Java Swing components
- **Visual Feedback**: Button hover effects and color-coded player turns
- **Progress Tracking**: Round progress bar and detailed status information



## 🎯 Game Rules

### Objective
Score the highest total points after 5 rounds to win the game.

### Gameplay
1. **Roll Dice**: Click "🎲 ROLL DICE" to roll both dice
2. **Scoring**:
   - Regular roll: Points = Die 1 + Die 2
   - Double roll (same numbers): Points = (Die 1 + Die 2) × 2
3. **Turns**: 
   - Single Player: You vs Computer (alternating turns)
   - Two Player: Player 1 vs Player 2 (alternating turns)
4. **Rounds**: Game lasts exactly 5 rounds
5. **Winner**: Player with highest total score wins

### Controls
- **🎲 ROLL DICE**: Roll both dice (main game action)
- **🔄 RESET**: Start a new game
- **👥 TWO PLAYER / 🤖 SINGLE PLAYER**: Toggle game mode
- **❌ EXIT**: Quit the game



### **Key Classes**

#### `Main.java`
- Application entry point
- Sets system look and feel
- Launches GameSetup screen

#### `GameSetup.java`
- Initial configuration screen
- Game mode and rounds selection
- Launches main game with selected settings

#### `DiceGameMain.java`
- Main game controller
- Handles all gameplay logic
- Manages GUI and user interactions
- Fixed single player mode with proper computer turns

#### `Dice.java`
- Individual dice representation
- **FIXED**: Dice display now matches actual rolled values
- Unicode dice faces with animations

#### `Player.java`
- Player data management
- Score tracking and statistics
- Comprehensive game statistics

## 🎨 Visual Features

### **Color Scheme**
- **Primary Blue**: #2980b9 (Headers, borders)
- **Success Green**: #27ae60 (Player 1, positive actions)
- **Danger Red**: #e74c3c (Player 2/Computer, exit)
- **Background**: Gradient from #ecf0f1 to white

### **Typography**
- **Headers**: Arial Bold, various sizes
- **Dice**: Segoe UI Symbol, 100px
- **Buttons**: Arial Bold with hover effects


**Enjoy playing the Dice Game! 🎲🎉**

### <i class="fa-regular fa-camera"></i> Snapshots

<img width="602" height="487" alt="Image" src="https://github.com/user-attachments/assets/8a3de3dd-f7d2-4153-8626-62ce19ebd4ae" />


<img width="602" height="487" alt="Image" src="https://github.com/user-attachments/assets/f649e796-f57c-4105-9d01-5ad36df062a3" />

