import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The DiceGame class is main class of the program.
 * It reads an input file from path, and performs the operations and writes to the output file from path.
 */
public class DiceGame {

    /**
     * The main method of the program.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {

        ArrayList<Players> playersList = new ArrayList<>();    //An array list to hold the objects we will create from the players class.

        /**
         * A try catch block.
         * It reads an input file from path, and performs the operations and writes to the output file from path.
         * Gets the number of players, player names, and player moves from the txt file.
         * Completes the task by ensuring that the game is played in accordance with the rules.
         */
        try (Scanner scanner = new Scanner(new FileReader(args[0]));    // The input file from path.
             FileWriter writerToTxt = new FileWriter(args[1])) {    // The output file from path.

            int numberOfPlayers = scanner.nextInt();    // Players numbers.
            scanner.nextLine();   // To move the cursor to the next line.
            String[] namesOfPlayers = scanner.nextLine().split(",");   // It parses the string containing player names into player names and stores them in a string array.

            for (String name : namesOfPlayers) {    // We create new objects from the players class with the player names in the created string array.
                playersList.add(new Players(name));
            }

            int counter = 0;    // We create a counter so that the game progresses in the correct order.
            while (scanner.hasNextLine()) {    // A while loop that will continue until the file to be read is finished.

                if (numberOfPlayers == counter) {
                    counter = 0;
                }

                String playerName = playersList.get(counter).getPlayerName();    // Name of the player whose turn it is to play.
                int playersScore = playersList.get(counter).getPlayerScore();    // Score of the player whose turn it is to play.

                String move = scanner.nextLine();   // Reads the player's move.
                String[] moves = move.split("-");   // It parses the moves in the move.
                int firstDice = Integer.parseInt(moves[0]);    // First dice move.
                int secondDice = Integer.parseInt(moves[1]);   // Second dice move.

                if (firstDice == 1 && secondDice == 1) {    // If the player moves 1-1, the game is over for him or her.
                    writerToTxt.write(playerName + " threw " + move + ". Game over " + playerName + "!\n");
                    playersList.remove(counter);    // Removes the player from the list.
                    numberOfPlayers--;    // Reduces the number of players by one, so that the game proceeds in the correct order.
                } else if (firstDice == 1 || secondDice == 1) {    // Nothing happens if the player scores a point other than one and one.
                    writerToTxt.write(playerName + " threw " + move + " and " + playerName + "’s score is " + playersScore + ".\n");
                    counter++;
                } else if (firstDice == 0 && secondDice == 0) {    // If a player moves 0-0, he or she skips his or her turn
                    writerToTxt.write(playerName + " skipped the turn and " + playerName + "’s score is " + playersScore + ".\n");
                    counter++;
                } else {    // If the player makes a move other than these, the player is awarded a point and the game continues.
                    playersList.get(counter).setPlayerScore(playersScore + firstDice + secondDice);
                    writerToTxt.write(playerName + " threw " + move + " and " + playerName + "’s score is " + playersList.get(counter).getPlayerScore() + ".\n");
                    counter++;
                }

                if (numberOfPlayers == 1) {    // If one player remains in the game, the game is over and the last remaining player wins the game.
                    writerToTxt.write(playersList.get(0).getPlayerName() + " is the winner of the game with the score of " + playersList.get(0).getPlayerScore() + ". Congratulations "+ playersList.get(0).getPlayerName() + "!");
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Error.");
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }
}
