// Nichole Maldonado
// CS331 - Lab 5, GameSelector Class

/*
 * GameSelector class allows existing users select if they want
 * to load an existing game or start a new game. The class also
 * keeps track of the number of games the user currently has and
 * calls GameDeleter, if the user wants to create a new game but
 * already reached a maximum capacity of 7 games.
 */

// changelog
// [4/26/20] [Nichole Maldonado] added class to control the amount of games the user can
//                               have and provide an interface to load a list of games.
// [4/26/20] [Nichole Maldonado] create timestampToDate to make human readable ids.

package utep.cs3331.lab5.chess;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.InputMismatchException;
import java.util.Scanner;

import utep.cs3331.lab5.files.FilePaths;
import utep.cs3331.lab5.chess.GameDeleter;

import org.jdom2.JDOMException;

/*
 * GameSelector class allows existing users select if they want
 * to load an existing game or start a new game. The class also
 * keeps track of the number of games the user currently has and
 * calls GameDeleter, if the user wants to create a new game but
 * already reached a maximum capacity of 7 games.
 */
public class GameSelector{
    private Deque<String> idQueue;
    private FilePaths filePaths;
    private String userName;
    
    /*
     * Constructor that sets the idQueue, filePaths, and
     * userName of the current player.
     * @param: the ids, filepaths, and user's name.
     * @return: None.
     */
    public GameSelector(Deque<String> idQueue, FilePaths filePaths, String userName) {
        this.idQueue = idQueue;
        this.filePaths = filePaths;
        this.userName = userName;
    }
    
    /*
     * Getter for the filePaths.
     * @param: None.
     * @return: the FilePaths.
     */
    public FilePaths getFilePaths(){
        return this.filePaths;
    }
    
    /*
     * Getter for the filePaths.
     * @param: None.
     * @return: the FilePaths.
     */
    public Deque<String> getIdQueue(){
        return this.idQueue;
    }
    
    /*
     * Getter for the userName.
     * @param: None.
     * @return: the userName.
     */
    public String getUserName(){
        return this.userName;
    }
    
    /*
     * Method that determines if the user wants to start a new
     * game or load a game and sets the field isNewGame to the
     * result.
     * @param: a scanner for input.
     * @return: True for new game, false if load game.
     */
    private boolean gameChoice(Scanner input) {
        System.out.println("Would you like to");
        System.out.println("1. Start a new game");
        System.out.println("2. Load game");
        System.out.print("Select 1 or 2: ");
        int selection = 0;
        int numTries = 5;
        
        // Determines whether user wants to start a new game or load the last game.
        while (numTries > 0 && selection != 1 && selection != 2) {
            try {
                selection = input.nextInt();
                input.nextLine();
                if (selection != 1 && selection != 2) {
                    System.out.println("\nInvalid input.");
                    System.out.printf("Number of tries left: %d\n\n", numTries);
                    numTries--;
                }
            }
            catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("\nInvalid input.");
                System.out.printf("Number of tries left: %d\n\n", numTries);
                numTries--;
            }
        }
        if (selection == 1) {
            System.out.println();
            return true;
        }
        else if (selection == 2) {
            System.out.println();
            return false;
        }
        else {
            System.out.println("\nExceeded tries. A new game will be created.\n");
            return true;
        }
    }
  
    /*
     * Method that determines if user wants to load an existing game or
     * create a new gamer.
     * @param: a scanner for input.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    public boolean selectGameType(Scanner input) throws IOException, JDOMException, NullPointerException  {
        boolean newGame = gameChoice(input);
            
        // Existing user wants to start new game, but has at least 7 current games.
        if (newGame && this.idQueue.size() >= 7) {
            boolean deleteGames = this.deleteMaxGameCapacity(input);
            
            // If user wants to delete some games, delete them.
            if (deleteGames) {
                GameDeleter gameDeleter = new GameDeleter();
                gameDeleter.deleteGames(this, input);
                if (this.idQueue.size() == 0) {
                    System.out.println("You have deleted all your games. A new game will be made");
                    newGame = true;
                }
            }
            else {
                newGame = false;
            }
        }
        
        // Otherwise, load game.
        if (!newGame) {
            pickGameToLoad(input);
        }
        return newGame;
    }
    
    /*
     * Method that determines the loaded game that the user want to load.
     * @param: Scanner for input.
     * @return: None.
     */
    private void pickGameToLoad(Scanner input) {
      
        System.out.println("\nPick a recent game to load");
        
        // Important to keep an array of size 3, if exception is thrown, we still
        // want to map user input with the array.
        ArrayList<String> gamesToLoad = new ArrayList<String>(3);
        for(int i = 0; i < 3 && this.idQueue.size() > 0; i++) {
            try {
                System.out.printf("%d. %s\n", (i + 1), this.timestampToDay(this.idQueue.peekLast()).substring(0,16));
                gamesToLoad.add(i, this.idQueue.removeLast());
            }
            catch(ParseException e) {
                System.out.println("One of your game ids was corrupted. The game id will be removed.");
                
                // Invalid id, remove it. When the game is over, the ids will be updated.
                this.idQueue.removeLast();
            }
        }
        System.out.print("Select 1 - " + (gamesToLoad.size()) + ": ");
        int selection = 0;
        try {
            selection = input.nextInt();
            input.nextLine();
        }
        catch (InputMismatchException e) {
            input.nextLine();
            selection = 0;
            
        }
        if (selection < 1 || selection > 3) {
            System.out.println("Invalid input, the most recent game will be selected");
            selection = 1;
        }
        
        // Match array indices.
        selection -= 1;
        
        // Push elements back to queue in from least recent to most recent.
        for (int i = 0; i < gamesToLoad.size(); i++) {
            if (i != selection && gamesToLoad.get(i).length() >= 16) {
                this.idQueue.addLast(gamesToLoad.get(i));
            }
        }
        
        // Put selected game at end.
        this.idQueue.addLast(gamesToLoad.get(selection));
    }
    
    /*
     * Method that determines if users want to delete or load an existing game.
     * @param: Scanner for input.
     * @return: false if the user want to load a new game, or true if they want to delete a new game.
     */
    private boolean deleteMaxGameCapacity(Scanner input) {
        System.out.println("Your maximum game capacity has been reached.");
        System.out.println("Would you like to: ");
        System.out.println("1. Load a recent game");
        System.out.println("2. Delete past games");
        System.out.print("Select 1 or 2: ");
        int selection = 0;
        int numTries = 5;
        
        // Determines whether user wants to start a new game or load the last game.
        while (numTries > 0 && selection != 1 && selection != 2) {
            try {
                selection = input.nextInt();
                input.nextLine();
                if (selection != 1 && selection != 2) {
                    System.out.println("\nInvalid input.");
                    System.out.printf("Number of tries left: %d\n\n", numTries);
                    numTries--;
                }
            }
            catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("\nInvalid input.");
                System.out.printf("Number of tries left: %d\n\n", numTries);
                numTries--;
            }
            if (numTries > 0 && selection != 1 && selection != 2) {
                System.out.println("Would you like to: ");
                System.out.println("1. Load a recent game");
                System.out.println("2. Delete past games");
                System.out.print("Select 1 or 2: ");
            }
        }
        if (selection == 1) {
            System.out.println();
            return false;
        }
        if (selection == 2) {
            System.out.println();
            return true;
        }
        System.out.println("\nExceeded tries. A new game will be created.\n");
        return false;        
    }
    
    /*
     * Method that returns the timestamp to the format of yyyy-mm-dd hh:mm:ss:SS
     * @param: The timestamp string to be converted.
     * @return: A readable string of the timestamp.
     */
    public String timestampToDay(String timestamp) throws ParseException {        
        Date date = new SimpleDateFormat("yyyyMMddHHmmssSS").parse(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        return dateFormat.format(date);
    }
}