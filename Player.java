/**
 * Player class to manage player information and scoring
 * Handles player name, score tracking, and game statistics
 */
public class Player {
    private String name;
    private int score;
    private int rollCount;
    private int doublesCount;
    private int highestSingleRoll;
    private int totalPointsFromDoubles;

    public Player(String name) {
        this.name = name;
        resetScore();
    }

    public void addScore(int points) {
        score += points;
        rollCount++;

        if (points > highestSingleRoll) {
            highestSingleRoll = points;
        }

        if (points > 12) {
            doublesCount++;
            totalPointsFromDoubles += points;
        }
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void resetScore() {
        score = 0;
        rollCount = 0;
        doublesCount = 0;
        highestSingleRoll = 0;
        totalPointsFromDoubles = 0;
    }

    public int getRollCount() {
        return rollCount;
    }

    public int getDoublesCount() {
        return doublesCount;
    }

    public int getHighestSingleRoll() {
        return highestSingleRoll;
    }

    public int getTotalPointsFromDoubles() {
        return totalPointsFromDoubles;
    }

    public double getAverageScore() {
        if (rollCount == 0) return 0.0;
        return (double) score / rollCount;
    }

    public double getDoublesPercentage() {
        if (rollCount == 0) return 0.0;
        return (double) doublesCount / rollCount * 100;
    }

    public String getDetailedStatistics() {
        return String.format(
            "=== %s Statistics ===\n" +
            "Total Score: %d points\n" +
            "Rolls Made: %d\n" +
            "Doubles Rolled: %d (%.1f%%)\n" +
            "Highest Single Roll: %d points\n" +
            "Points from Doubles: %d\n" +
            "Average Score per Roll: %.2f points",
            name, score, rollCount, doublesCount, getDoublesPercentage(),
            highestSingleRoll, totalPointsFromDoubles, getAverageScore()
        );
    }

    public int compareScore(Player other) {
        return Integer.compare(this.score, other.score);
    }

    public boolean hasWonAgainst(Player other) {
        return this.score > other.score;
    }

    public boolean isTiedWith(Player other) {
        return this.score == other.score;
    }

    public String getSummary() {
        return String.format("%s: %d points (%d rolls)", name, score, rollCount);
    }

    @Override
    public String toString() {
        return String.format("Player{name='%s', score=%d, rolls=%d}", name, score, rollCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
