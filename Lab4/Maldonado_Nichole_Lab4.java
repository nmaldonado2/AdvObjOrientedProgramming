// Compile: javac -cp "/Users/nichole_maldonado/Desktop/Lab4/jdom-2.0.6.jar" Maldonado_Nichole_Lab4.java

// Run: java -cp "/Users/nichole_maldonado/Desktop/Lab4/jdom-2.0.6.jar:." Maldonado_Nichole_Lab4

// export CLASSPATH=$CLASSPATH:/Users/nichole_maldonado/Desktop/Lab4/jdom-2.0.6.jar

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

import org.jdom2.input.SAXBuilder;

import utep.cs3331.lab4.files.DomEditor;
import utep.cs3331.lab4.files.FileReader;
import utep.cs3331.lab4.files.FileWriter;
import utep.cs3331.lab4.files.UserCreator;
import utep.cs3331.lab4.chess.GameController;
import utep.cs3331.lab4.files.exceptions.ExceptionHandler;

public class Maldonado_Nichole_Lab4 implements ExceptionHandler {
    
      // returns true for new game.
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
    
    public static boolean retrieveSaveMethod(Scanner input) {
        System.out.println("Would you like your game to ");
        System.out.println("1. Auto-save after the game");
        System.out.println("2. Be prompted to save at the end of the game");
        System.out.print("Select 1 or 2: ");
        int selectionNum = 0;
        try {
            selectionNum = input.nextInt();
            input.nextLine();
            
            if (selectionNum != 1 && selectionNum != 2) {
                System.out.println("\nInvalid menu number. Autosave disabled.");
                selectionNum = 2;
            }
        } 
        catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("\nInvalid input. Auto-save will be disabled");
            selectionNum = 2;
        }
        
        System.out.println();
        
        return (selectionNum == 1) ? true : false;
    }
    
    public static int retrieveTimeLimit(Scanner input) {
        System.out.println("Enter the time limit for your game in minutes from [1, 100]");
        System.out.println("Example: Game time: 20\n");
        System.out.print("Game time: ");
        int gameTime = 0;
        try {
            gameTime = input.nextInt();
            input.nextLine();
            
            if (gameTime < 1 && gameTime > 100) {
                System.out.println("\nInvalid input. The game time will be set to 30 minutes");
                gameTime = 30;
            }
        } 
        catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("\nInvalid input. The game time will be set to 30 minutes");
            gameTime = 30;
        }
        System.out.println();
        return gameTime;
    }
    
    public static boolean retrieveHasChat(Scanner input) {
        System.out.println("Do you want to allow chat?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Select 1 or 2: ");
        int selection = 0;
        try {
            selection = input.nextInt();
            input.nextLine();
            
            if (selection < 1 && selection > 2) {
                System.out.println("\nInvalid input. The chat will be disabled.");
                selection = 2;
            }
        } 
        catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("\nInvalid input. The chat will be disabled.");
            selection = 2;
        }
        System.out.println();
        return (selection == 1) ? true : false;
    }
    
    public static void runChessInterface(FileReader file, Scanner input) {
        boolean restart = true;
        do {
            try {
                file.getFilePaths().collectFilePath(input, "user");
                file.getFilePaths().collectFilePath(input, "chess");

                if (file.getFilePaths().getFilePathUsers() != null && file.getFilePaths().getFilePathChess() != null) {
                    
                    // User signaled that they are an existing user.
                    if (UserCreator.determineUserExistence(input)){

                        // If the user was never found, leave program.
                         if (!file.findExistingUser(input)) {
                             return;
                         }
                    }
                    
                    // User signaled that they are a new user.
                    else {

                        // If user decided to leave the program, leave program.
                        if (!file.createPlayerFromXML(input)) {
                            return;
                        }
                    }

                    // Now that player is created, move on to game construction.
                    if (file.getPlayer() != null) {

                        FileWriter fw = new FileWriter(file.getFilePaths());
                        boolean createNewGame = false;

                        // If the user does not have an existing game, create a new game key
                        // and write the new user with the game key to the user xml file.
                        if (file.getUserGameKey() == null) {
                            createNewGame = true;
                            file.setUserGameKey(fw.storePlayer(file.getPlayer(), input));
                            
                            // The user decided to quit program before,
                            // finding key.
                            if (file.getUserGameKey() == null) {
                                return;
                            }
                        }

                        GameController controller = new GameController(file.getPlayer(), file.getUserGameKey());
                        
                        // Creates new game.
                        if (createNewGame) {
                            System.out.println("Creating a new game");
                            controller.getGame().setMaxTime(retrieveTimeLimit(input));
                            controller.getGame().setUseAutoSave(retrieveSaveMethod(input));
                            controller.getGame().setHasChat(retrieveHasChat(input));
                            
                            if (!fw.writeNewGame(controller, input)) {
                                return;
                            }
                        }
                        else {
                            
                            // Existing user wants to start new game.
                            if (gameChoice(input)) {
                                if (!fw.removeOldGame(controller.getGame().getId(), input)) {
                                        return;
                                }
                                
                                System.out.println("Creating a new game");
                                controller.getGame().setMaxTime(retrieveTimeLimit(input));
                                controller.getGame().setUseAutoSave(retrieveSaveMethod(input));
                                controller.getGame().setHasChat(retrieveHasChat(input));
                                
                                String oldId = controller.getGame().getId();

                                // New game needs a new id.
                                controller.getGame().setId(FileWriter.createId());
                                
                                // Change user to have new id.
                                if(fw.updateUserGameKey(controller.getGame().getId(), oldId, input)){
                                    return;
                                }
                                
                                // Create new game if successful.
                                if (!fw.writeNewGame(controller, input)) {
                                    return;
                                }
                            }
                            
                            // Creates the board from the file if the user
                            // chooses to load a game..
                            else {
                                if (!file.fileToBoard(controller, input, fw)) {
                                    return;
                                }
                            }
                        }
                        controller.startGame(input);
                        if (controller.getGame().getUseAutoSave() || controller.saveGame(input)) {
                            fw.updateGame(controller, input);
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
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        
        FileReader file = new FileReader();
        
        runChessInterface(file, input);
    }
}
