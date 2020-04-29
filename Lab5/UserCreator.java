// Nichole Maldonado
// CS331 - Lab 5, UserCreator Class

/*
 * The UserCreator class is a class without
 * any fields and purely static behaviours used
 * to help ParserReader collect user input for the
 * ChessPlayer object to be created. It includes methods to collect the
 * user name, color, and expertise level.
 */

// changelog
// [2/28/20] [Nichole Maldonado] added helper methods to colloect user
//                               name, color, and expertise level.
// [2/29/20] [Nichole Maldonado] added catch statements for a possible
//                               null pointer exception while
//                               retrieving existing user information.
// [2/29/20] [Nichole Maldonado] added createPlayer method to create a player
//                               after collecting necessary attributes.
// [3/01/20] [Nichole Maldonado] Reorganized import statements
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.files;

import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Deque;
import java.util.Scanner;
import java.util.List;

import utep.cs3331.lab5.players.ChessPlayer;
import utep.cs3331.lab5.players.ExpertiseLevel;

import org.jdom2.Document;
import org.jdom2.Element;

/*
 * The UserCreator class is a class without
 * any fields and purely static behaviours used
 * to help ParserReader collect user input for the
 * ChessPlayer object to be created.
 */
public class UserCreator {
    
    /*
     * Allows for no class instantiation. However, the 
     * class is not abstract, since ParserWriter does not inherit
     * from UserCreator. UserCreator is used as more of a helper class.
     */
    private UserCreator(){}
    
    /*
     * Method that asks the user if they are a new or existing user.
     * @param: a scanner for the user input.
     * @return: returns false for a new user and true for an existing user.
     */
    protected static boolean determineUserExistence(Scanner input){
        int numTries = 5;
        int userSelection = -1;
        
        while (numTries > 0) {
            System.out.println("1. New User");
            System.out.println("2. Existing User");
            System.out.print("Select 1 or 2: ");
            try {
                userSelection = input.nextInt();
                input.nextLine();
            }
            catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Invalid user selection. Please Try again");
                userSelection = -1;
            }
            System.out.println();
            if (userSelection == 1) {
                return false;
            }
            else if (userSelection == 2) {
                return true;
            }
            else {
                numTries--;
                System.out.printf("Number of tries left: %d\n\n", numTries);
                userSelection = -1;
            }
        }

        System.out.println("Exceeded the maximum number of tries. You will be redirected to the new user page.");
        return false;
    }
    
    /*
     * Method that prompts user of whether they would
     * like to re-enter a username or quit the program.
     * @param: a scanner for user input.
     * @return: true if the user wants to re-enter a username.
     *         false if the user wants to quit.
     */
    protected static boolean reEnterUserName(Scanner input) {
        int numTries = 5;
        int userSelection = -1;
        
        // Re-enter user name until user quits or finds a u
        while (numTries > 0) {
            System.out.println("\nWould you like to:");
            System.out.println("1. Re-enter username");
            System.out.println("2. Quit");
            System.out.print("Select 1 or 2: ");
            try {
                userSelection = input.nextInt();
                input.nextLine();
            }
            catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Invalid user selection. Please Try again");
                userSelection = -1;
            }
            System.out.println();
            if (userSelection == 1) {
                return true;
            }
            else if (userSelection == 2) {
                return false;
            }
            else {
                numTries--;
                System.out.printf("Number of tries left: %d\n\n", numTries);
                userSelection = -1;
            }
        }
        System.out.println("Exceeded the maximum number of tries. Program terminating");
        return false;
    }
    
    /*
     * Method that returns an user name based on the user's selection.
     * @param: a scanner for input.
     * @return: a non-white space user name with at least one character.
     */
    protected static String getUserName(Scanner input) {
        
        String name = "";
        
        while (name.length() < 1) {
            System.out.println("\nEnter your name");
            System.out.print("Note: Usernames are case sensitive and any");
            System.out.println(" whitespaces will automatically be removed.\n");
            System.out.print("Username: ");
            name = input.nextLine().replaceAll("\\s+", "");
            System.out.println();
            if (name.length() < 1) {
                System.out.println("\nUser names must have at least one non-whitespace character.");
                System.out.println("Please try again\n");
            }
        }
        return name;
    }
    
    /*
     * Method that returns an ExpertiseLevel based on the user's selection
     * @param: a scanner for input.
     * @return: an ExpertiseLevel enum value.
     */
    protected static ExpertiseLevel getExpertiseLevel(Scanner input) {
        System.out.println("Select your expertise level");
        System.out.println("1. Novice");
        System.out.println("2. Medium");
        System.out.println("3. Advanced");
        System.out.println("4. Master");
        
        // Get user input.
        System.out.print("Select 1 - 4: ");
        int level = 1;
        try{
            level = input.nextInt();
            
            if (level < 1 || level > 4) {
                throw new InputMismatchException();
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Novice will be selected.");
            level = 1;
        }
        finally {
            System.out.println();
            input.nextLine();
        }
        
        // Return expertise level based on input.
        if (level == 1) {
            return ExpertiseLevel.NOVICE;
        }
        if (level == 2) {
            return ExpertiseLevel.MEDIUM;
        }
        if (level == 3) {
            return ExpertiseLevel.ADVANCED;
        }
        return ExpertiseLevel.MASTER;
    }
    
    /*
     * Method that returns a color based on the user's selection
     * @param: a scanner for input.
     * @return: a Color enum value.
     */
    protected static boolean getUserColor(Scanner input) {
        int colorNum = 1;
        System.out.println("Select a color");
        System.out.println("1. White");
        System.out.println("2. Black");
        
        System.out.print("Select 1 or 2: ");
        colorNum = 0;
        try{
            colorNum = input.nextInt();
            
            if (colorNum < 1 || colorNum > 2) {
                System.out.println("Invalid input. White will be selected.");
                colorNum = 1;
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. White will be selected.");
            colorNum = 1;
        }
        finally {
            input.nextLine();
            System.out.println();
        }
        
        return (colorNum == 1) ? true : false;
    }
    
    /*
     * Method that continues to prompt a user for a unique user name
     * until one is found or until the user decides to quit the program.
     * @param: a hash table with all the user and a scanner to retrieve
     *        the user input.
     * @return: A unique username or null if the user wants to quit.
     */
    protected static String resolveMatchingUserNames(HashMap<String, String> users, Scanner input) {
        System.out.println("Your user name has already been taken. Please try again.");
        int menuNum = 1;
        String userName = null;
        while (menuNum == 1 && userName == null) {
            
            // Get new user name.
            userName = getUserName(input);
            
            // If the user name is already used, tell user.
            if (users.containsKey(userName)) {
                System.out.println("This user name has already been taken.");
                System.out.println("Would you like to :");
                System.out.println("1. Try again");
                System.out.println("2. Quit");
                System.out.print("Select 1 or 2: ");
                
                try{
                    menuNum = input.nextInt();
                    input.nextLine();
                    
                    if (menuNum != 1 && menuNum != 2) {
                        System.out.println("Invalid selection. Please try again.");
                        menuNum = 1;
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid selection. Please try again.");
                    menuNum = 1;
                }
                
                // Used since unique user name was not found.
                userName = null;
            }
            else {
                System.out.printf("Unique username found as %s!\n\n", userName);
            }
        }
        return userName;
    }
    
    /*
     * Method that converts the ids stored in the list elements to 
     * a deque.
     * @param: The list of elements.
     * @return: Non.
     */
    private static Deque<String> elementsToIds(List<Element> userKeys) {
        System.out.println(userKeys);
        Deque<String> keys = new ArrayDeque<String>();
        if (userKeys != null) {
            for (int i = 0; i < userKeys.size(); i++) {
                if (userKeys.get(i).getText().length() >= 16) {
                    keys.add(userKeys.get(i).getText());
                }
                
            }
        }
        return keys;
    }
    
    /*
     * Method that creates an existing user from the elements of user.
     * Input: The new userName of the player and the user to be base off.
     * Output: a new ChessPlayer object.
     * @param: the userName, user element, list of the user keys, the config file, inputfile,
     *         parserWriter, and scanner for input.
     * @return: a ChessPlayer with the attributes from the user element
     *          and if missing, filled in by the user input.
     * NOTE: If for some reason, the chess player attributes below are missing,
     * the user will be asked for new ones but the changes will not be saved,
     * since it is apparent that these attributes were purposely deleted
     * and thus the program assumes that they user would like to have their
     * expertise level and color change per game.
     */
    protected static ChessPlayer createExistingPlayer(String userName, Scanner input, Element user, 
            List<Element> userKeys, Document configFile, File inputFile, ParserWriter parserWriter) {
        
        boolean usesWhitePieces;
        ExpertiseLevel expertiseLevel;
        boolean addedInfo = false;
        
        Deque<String> keys = elementsToIds(userKeys);
        
        // Get expertise level if it exists, if not prompt user for new expertise level.
        try {
            String level = user.getChild("expertise_level").getValue();
            expertiseLevel = ExpertiseLevel.valueOf(level.toUpperCase());    
        }
        catch (NullPointerException e) {
            System.out.println("An error occurred while accessing your expertise level. Please re-enter a new one.");
            expertiseLevel = getExpertiseLevel(input);
            user.addContent(new Element("expertise_level"));
            user.getChild("expertise_level").setText(expertiseLevel.formatName());
            addedInfo = true;
        }
        
        // Get color if it exists, if not ask user for new color.
        try {
            String selectedColor = user.getChild("user_color").getText().toLowerCase();
            if (selectedColor.equals("white")) {
                usesWhitePieces = true;
            }
            else if (selectedColor.equals("black")) {
                usesWhitePieces = false;
            }
            else {
                System.out.println("An error occurred while accessing your piece color. Please re-enter a new one.");
                usesWhitePieces = getUserColor(input);
                user.addContent(new Element("user_color"));
                user.getChild("user_color").setText((usesWhitePieces) ? "white" : "black");
            }
        }
        catch (NullPointerException e) {
            System.out.println("An error occurred while accessing your piece color. Please re-enter a new one.");
            usesWhitePieces = getUserColor(input);
            user.addContent(new Element("user_color"));
            user.getChild("user_color").setText((usesWhitePieces) ? "white" : "black");
            addedInfo = true;
        }
        
        // If changes were made, update the file.
        if (addedInfo) {
            System.out.println("added info");
            parserWriter.sendToFile(configFile, inputFile, input);
        }
        
        return new ChessPlayer(userName, expertiseLevel, usesWhitePieces, keys);
    }
    
    /*
     * Method that creates a new player with the userName.
     * @param: the userName of the player and a scanner for input.
     * @return: None.
     */
    protected static ChessPlayer createPlayer(String userName, Scanner input) {
        return new ChessPlayer(userName, getExpertiseLevel(input), getUserColor(input), new ArrayDeque<String>());
    }
}