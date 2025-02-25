/**
 * The Players class represents a player in the dice game system.
 * It contains information about the player's name and score.
 */
public class Players {
    private String playerName;
    private int playerScore;

    /**
     * Constructor for name of player.
     *
     * @param playerName
     */
    public Players(String playerName) {
        this.playerName = playerName;
    }


    /**
     * Getters and setters.
     */
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}
