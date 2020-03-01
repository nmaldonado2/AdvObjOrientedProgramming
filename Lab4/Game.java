// Nichole Maldonado
// CS331 - Lab 4, Game Class

/*
 * Game class that represents the current
 * attributes of the game including whether
 * the game has chat, an id, and uses auto save.
 * Game class that contains setters and getters
 * to modify its fields and thus the state of the game.
 */

// changelog
// changelog
// [2/29/20] [Nichole Maldonado] moved max time, id, chat, and autosave to
//                               class from GameController since all the attributes
//                               describe the current game.
// [2/29/20] [Nichole Maldonado] added getters and setters for 
//                               max time, id, chat, and auto save to change the state of the game.
// [3/30/20] [Nichole Maldonado] moved max time, save method, and chat method from main
//                               program and added to class to set fields directly.

package utep.cs3331.lab4.chess;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Game class that represents the current
 * attributes of the game including whether
 * the game has chat, an id, and uses auto save.
 * Game class that contains setters and getters
 * to modify its fields and thus the state of the game.
 */
public class Game {
    private String id;
    private int maxTime;
    private boolean useAutoSave;
    private boolean hasChat;
    
    /*
     * Constructor that creates a game and initializes
     * the id with the id provided.
     * @param: a string id.
     * @return: none.
     */
    public Game(String id) {
        this.id = id;
        
        // Default time to 1.
        this.maxTime = 1;
    }
    
    /*
     * Getter method for the field id.
     * @param: None.
     * @return: the id field.
     */
    public String getId() {
        return this.id;
    }
    
    /*
     * Getter method for the field maxTime.
     * @param: None.
     * @return: the maxTime field.
     */
    public int getMaxTime() {
        return this.maxTime;
    }
    
    /*
     * Getter method for the field useAutoSave.
     * @param: None.
     * @return: the useAutoSave field.
     */
    public boolean getUseAutoSave() {
        return this.useAutoSave;
    }
    
    /*
     * Getter method for the field hasChat.
     * @param: None.
     * @return: the hasChat field.
     */
    public boolean getHasChat() {
        return this.hasChat;
    }
    
    /*
     * Setter method for the field id.
     * @param: the id to set the corresponding field to.
     * @return: None.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /*
     * Setter method for the field maxTime.
     * @param: the maxTime to set the corresponding field to.
     * @return: None.
     */
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
    
    /*
     * Setter method for the field useAutoSave.
     * @param: the useAutoSave to set the corresponding field to.
     * @return: None.
     */
    public void setUseAutoSave(boolean useAutoSave) {
        this.useAutoSave = useAutoSave;
    }
    
    /*
     * Setter method for the field hasChat.
     * @param: the hasChat to set the corresponding field to.
     * @return: None.
     */
    public void setHasChat(boolean hasChat) {
        this.hasChat = hasChat;
    }
    
    /*
     * Method that retrieves save capability from user and assigns
     * result ot useAutoSave.
     * @param: the scanner for input.
     * @return: None.
     */  
    public void retrieveSaveMethod(Scanner input) {
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
        
        this.useAutoSave = (selectionNum == 1) ? true : false;
    }
    
    /*
     * Method that retrieves max time limit selection from the user and assigns
     * result ot maxTime.
     * @param: the scanner for input.
     * @return: None.
     */  
    public void retrieveTimeLimit(Scanner input) {
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
        this.maxTime = gameTime;
    }
    
    /*
     * Method that retrieves chat capability from user and assigns
     * result ot hasChat.
     * @param: the scanner for input.
     * @return: None.
     */  
    public void retrieveHasChat(Scanner input) {
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
        this.hasChat = (selection == 1) ? true : false;
    }
    
    /*
     * Method that intiates the retrieval of the chat, max time
     * limit, and save method from the user.
     * @param: the scanner for input.
     * @return: None.
     */  
    public void setTimeChatSave(Scanner input) {
        this.retrieveHasChat(input);
        this.retrieveTimeLimit(input);
        this.retrieveSaveMethod(input);
    }
}