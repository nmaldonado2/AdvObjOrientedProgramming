// Nichole Maldonado
// CS331 - Lab 4, Maldonado_Nichole_Lab4

/*
 * Class that drives the program and initiates the reading of
 * user and chess xml files as well as starting the game
 * controller which will start the game.
 *
 * The program first starts by retrieving the user xml file which
 * houses user data and the chess xml file which houses the chess pieces,
 * board, and game. Users are then asked if they are an existing user or
 * new user. Then the program will allow the user to load
 * a game or create a new one. Afterwards, the user can move the pieces
 * and leave the game when ready. The users are given the option to save
 * their game at the end if autosave is disabled.
 */

// changelog
// [2/28/20] [Nichole Maldonado] created main program class and created
//                               a FileReader and FileWriter to allow for
//                               the correspondence between the game dispalyed and
//                               the one stored in the xml file.
// [3/1/20] [Nichole Maldonado] moved code to retrieve game attributes to the game class.  
// [3/1/20] [Nichole Maldonado] refactored code in main to more logical code blocks.

// Compile: javac -cp "/Users/nichole_maldonado/Desktop/Lab4/jdom-2.0.6.jar" Maldonado_Nichole_Lab4.java
// Run: java -cp "/Users/nichole_maldonado/Desktop/Lab4/jdom-2.0.6.jar:." Maldonado_Nichole_Lab4
// export CLASSPATH=$CLASSPATH:/Users/nichole_maldonado/Desktop/Lab4/jdom-2.0.6.jar

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import utep.cs3331.lab4.chess.GameController;
import utep.cs3331.lab4.files.exceptions.ExceptionHandler;
import utep.cs3331.lab4.files.FileReader;
import utep.cs3331.lab4.files.FileWriter;
import utep.cs3331.lab4.files.UserCreator;

/*
 * Class that drives the program and initiates the reading of
 * user and chess xml files as well as starting the game
 * controller which will start the game.
 */
public class Maldonado_Nichole_Lab4 implements ExceptionHandler {
    
    /*
     * Method that determines if the user wants to start a new
     * game or load a game.
     * @param: a scanner for input.
     * @return: false to load a game and true to start a new game.
     */
    public static boolean gameChoice(Scanner input) {
        System.out.println("Would you like to");
        System.out.println("1. Start a new game");
        System.out.println("2. Load the previous game");
        System.out.print("Select 1 or 2: ");
        int selection = 0;
        int numTries = 5;
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
        
        if (selection == 2) {
            System.out.println();
            return false;
        }
        if (selection == 1) {
            System.out.println("\nA new game will be created in place of your prexisting game.\n");
            return true;
        }

        System.out.println("\nExceeded tries. A new game will be created in place of your prexisting game.\n");
        return true;
    }
    
    /*
     * Method that determines whether ChessPlayer should be
     * created from existing player or create a new player.
     * @param: a scanner for input and a fileReader.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    private static boolean startPlayerCreation(FileReader fileReader, Scanner input) {
        
        // User signaled that they are an existing user.
        if (UserCreator.determineUserExistence(input)){

            // If the user was never found, leave program.
             return fileReader.findExistingUser(input);     
        }

        // User signaled that they are a new user.
        return fileReader.createPlayerFromXML(input);
    }
    
    /*
     * Method that signals the creation a new game to write to xml.
     * @param: a scanner for input, fileWriter, and the game controller.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    private static boolean signalNewGameCreation(FileWriter fileWriter, 
                GameController controller, Scanner input) {
        
        System.out.println("Creating a new game\n");
        
        // Write new game.
        return fileWriter.writeNewGame(controller, input);
    }
    
    /*
     * Method that signals the change of a game id if a new game is
     * going to be created instead.
     * @param: a scanner for input, fileWriter, and the game controller.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    private static boolean changeGameId(FileWriter fileWriter, 
                GameController controller, Scanner input) {
        
        // New game needs a new id.
        String oldId = controller.getGame().getId();
        controller.getGame().setId(FileWriter.createId());

        // Change user to have new id.
        return fileWriter.updateUserGameKey(controller.getGame().getId(), oldId, input);
    }
    
    /*
     * Method that signals either loads an existing game or
     * cretes a new game for an existing user.
     * @param: a scanner for input, fileWriter, fileReader, and the game controller.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    private static boolean existingLoadGameOrNewGame(FileWriter fileWriter, FileReader fileReader, 
                GameController controller, Scanner input) {
        
        // Existing user wants to start new game.
        if (gameChoice(input)) {
            return fileWriter.removeOldGame(controller.getGame().getId(), input) &&
                    changeGameId(fileWriter, controller, input) && 
                    signalNewGameCreation(fileWriter, controller, input);
        }
        
        // Creates the board from the file if the user
        // chooses to load a game.
        return fileReader.fileToBoard(controller, input, fileWriter);
    }
    
    /*
     * Method that determines whether a new game needs to be created,
     * or determines if an existing user still needs to decide.
     * @param: a scanner for input, fileWriter, fileReader, and the game controller.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    private static boolean signalGameCreation(boolean createNewGame, FileWriter fileWriter, 
                FileReader fileReader, GameController controller, Scanner input) {
        
        // Creates new game.
        if (createNewGame) {
            return signalNewGameCreation(fileWriter, controller, input);
        }

        // Or determines whether to create or load a new game from an
        // existing user.
        return existingLoadGameOrNewGame(fileWriter, fileReader, controller, input);
    }
    
    /*
     * Method that runs the entire program and starts the file
     * reading writing and game start.
     * @param: the FileReader and scanner for input.
     * @return: None.
     */
    public static void runChessInterface(FileReader fileReader, Scanner input) {
        boolean restart = true;
        do {
            try {
                fileReader.getFilePaths().collectFilePath(input, "user");
                fileReader.getFilePaths().collectFilePath(input, "chess");

                if (fileReader.getFilePaths().getFilePathUsers() != null && 
                        fileReader.getFilePaths().getFilePathChess() != null) {
                    
                    // User was never found or user decided to quit game.
                    if (!startPlayerCreation(fileReader, input)){
                        return;
                    }

                    // Now that player is created, move on to game construction.
                    // Player could be null if a player was never found.
                    if (fileReader.getPlayer() != null) {

                        FileWriter fileWriter = new FileWriter(fileReader.getFilePaths());
                        boolean createNewGame = false;

                        // If the user does not have an existing game, create a new game key
                        // and write the new user with the game key to the user xml file.
                        if (fileReader.getUserGameKey() == null) {
                            createNewGame = true;
                            fileReader.setUserGameKey(fileWriter.storePlayer(fileReader.getPlayer(), input));
                            
                            // The user decided to quit program before,
                            // finding key.
                            if (fileReader.getUserGameKey() == null) {
                                return;
                            }
                        }
                        
                        // Create game
                        GameController controller = new GameController(fileReader.getPlayer(), fileReader.getUserGameKey());
                        if (!signalGameCreation(createNewGame, fileWriter, fileReader, controller, input)){
                            return;
                        }
                        
                        // Start game.
                        controller.startGame(input);
                        if (controller.getGame().getUseAutoSave() || controller.saveGame(input)) {
                            fileWriter.updateGame(controller, input);
                        }
                        restart = false;
                    }
                }
            }
            catch (FileNotFoundException e) {
                restart = ExceptionHandler.handleException("trying to read the xml files.", "Restart program", input);
            }
        } while (restart);
    }
    
    /*
     * Main method that initiates the start of the program.
     * @param: None.
     * @return: None.
     */
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        FileReader file = new FileReader();
        runChessInterface(file, input);
    }
}
