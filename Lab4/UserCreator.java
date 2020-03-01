// Nichole Maldonado
// CS331 - Lab 4, UserCreator Class

/*
 * The UserCreator class is a class without
 * any fields and purely static behaviours used
 * to help FileReader collect user input for the
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

package utep.cs3331.lab4.files;

import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

import utep.cs3331.lab4.players.ChessPlayer;
import utep.cs3331.lab4.players.Color;
import utep.cs3331.lab4.players.ExpertiseLevel;

import org.jdom2.Element;

/*
 * The UserCreator class is a class without
 * any fields and purely static behaviours used
 * to help FileReader collect user input for the
 * ChessPlayer object to be created.
 */
public class UserCreator {
    
    /*
     * Allows for no class instantiation. However, the 
     * class is not abstract, since FileWriter does not inherit
     * from UserCreator. UserCreator is used as more of a helper class.
     */
    private UserCreator(){}
    
    /*
     * Method that asks the user if they are a new or existing user.
     * Input: a scanner for the user input.
     * Output: returns false for a new user and true for an existing user.
     */
    public static boolean determineUserExistence(Scanner input){
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
     * Input: a scanner for user input.
     * Output: true if the user wants to re-enter a username.
     *         false if the user wants to quit.
     */
    public static boolean reEnterUserName(Scanner input) {
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
     * Input: a scanner for input.
     * Output: a non-white space user name with at least one character.
     */
    public static String getUserName(Scanner input) {
        
        String name = "";
        
        while (name.length() < 1) {
            System.out.println("\nEnter your name");
            System.out.println("Note: Usernames are case sensitive and any whitespaces will automatically be removed.\n");
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
     * Input: a scanner for input.
     * Output: an ExpertiseLevel enum value.
     */
    public static ExpertiseLevel getExpertiseLevel(Scanner input) {
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
     * Input: a scanner for input.
     * Output: a Color enum value.
     */
    public static Color getUserColor(Scanner input) {
        int colorNum = 1;
        System.out.println("Select a color");
        
        // Prints all color options
        for (Color colorChoice : Color.values()) { 
            System.out.printf("%d. %s\n", colorNum, colorChoice.formatName()); 
            colorNum++;
        } 
        
        System.out.printf("Select 1 - %d: ", colorNum - 1);
        colorNum = -1;
        try{
            colorNum = input.nextInt();
            
            // 9 is the number of colors.
            if (colorNum < 1 || colorNum > 9) {
                System.out.println("Invalid input. Blue will be selected.");
                colorNum = 1;
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Blue will be selected.");
        }
        finally {
            input.nextLine();
            System.out.println();
        }
        
        if (colorNum == 1) {
            return Color.BLUE;
        }
        if (colorNum == 2) {
            return Color.GREEN;
        }
        if (colorNum == 3) {
            return Color.YELLOW;
        }
        if (colorNum == 4) {
            return Color.RED;
        }
        if (colorNum == 5) {
            return Color.BLACK;
        }
        if (colorNum == 6) {
            return Color.ORANGE;
        }
        if (colorNum == 7) {
            return Color.PURPLE;
        }
        if (colorNum == 8) {
            return Color.PINK;
        }
        return Color.WHITE;
    }
    
    /*
     * Method that continues to prompt a user for a unique user name
     * until one is found or until the user decides to quit the program.
     * Input: a hash table with all the user and a scanner to retrieve
     *        the user input.
     * Output: A unique username or null if the user wants to quit.
     */
    public static String resolveMatchingUserNames(Hashtable<String, String> users, Scanner input) {
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
                        System.out.println("Invalid selection. Program terminating");
                        return null;
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid selection. Program terminating");
                    return null;
                }
                
                // Used since unique user name was not found.
                userName = null;
            }
            else {
                System.out.printf("Unique username found as %s!\n", userName);
            }
        }
        return userName;
    }
    
    /*
     * Method that creates an existing user from the elements of user.
     * Input: The new userName of the player and the user to be base off.
     * Output: a new ChessPlayer object.*
     * NOTE: If for some reason, the chess player attributes below are missing,
     * the user will be asked for new ones but the changes will not be saved,
     * since it is apparent that these attributes were purposely deleted
     * and thus the program assumes that they user would like to have their
     * expertise level and color change per game.
     */
    public static ChessPlayer createExistingPlayer(String userName, Scanner input, Element user) {
        Color color;
        ExpertiseLevel expertiseLevel;
        
        
        // Get expertise level if it exists, if not prompt user for new expertise level.
        try {
            String level = user.getChild("expertise_level").getValue();
            expertiseLevel = ExpertiseLevel.valueOf(level.toUpperCase());    
        }
        catch (IllegalArgumentException e) {
            System.out.println("An error occurred while accessing your expertise level. Please re-enter a new one.");
            expertiseLevel = getExpertiseLevel(input);
        }
        catch (NullPointerException e) {
            System.out.println("An error occurred while accessing your expertise level. Please re-enter a new one.");
            expertiseLevel = getExpertiseLevel(input);
        }
        
        // Get color if it exists, if not ask user for new color.
        try {
            String selectedColor = user.getChild("user_color").getValue();
            color = Color.valueOf(selectedColor.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            System.out.println("An error occurred while accessing your color. Please re-enter a new one.");
            color = getUserColor(input);
        }
        catch (NullPointerException e) {
            System.out.println("An error occurred while accessing your color. Please re-enter a new one.");
            color = getUserColor(input);
        }
        
        return new ChessPlayer(userName, expertiseLevel, color);
    }
    
    /*
     * Method that creates a new player with the userName.
     * Input: the userName of the player and a scanner for input.
     * Output: None.
     */
    public static ChessPlayer createPlayer(String userName, Scanner input) {
        Color color = getUserColor(input);
        ExpertiseLevel expertiseLevel = getExpertiseLevel(input);
        return new ChessPlayer(userName, expertiseLevel, color);
    }
}