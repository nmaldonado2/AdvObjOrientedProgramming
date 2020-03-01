package utep.cs3331.lab4.files;

import org.jdom2.Element;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

import utep.cs3331.lab4.players.ChessPlayer;
import utep.cs3331.lab4.players.Color;
import utep.cs3331.lab4.players.ExpertiseLevel;

public class UserCreator{
    
    // True if existing user, false for new user.
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
    
    public static boolean reEnterUserName(Scanner input) {
        int numTries = 5;
        int userSelection = -1;
        
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
    
    public static String getUserName(Scanner input) {
        
        String name = "";
        
        while (name.length() < 1) {
            System.out.println("\nEnter your name");
            System.out.println("Note: Usernames are case sensitive.\n");
            System.out.print("Username: ");
            name = input.nextLine();
            System.out.println();
            if (name.length() < 1) {
                System.out.println("\nUser names must have at least one character.");
                System.out.println("Please try again\n");
            }
        }
        return name;
    }
    
    public static ExpertiseLevel getExpertiseLevel(Scanner input) {
        System.out.println("Select your expertise level");
        System.out.println("1. Novice");
        System.out.println("2. Medium");
        System.out.println("3. Advanced");
        System.out.println("4. Master");
        
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
            
            // 9 is num colors
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
    
    public static ChessPlayer createExistingPlayer(String userName, Scanner input, Element user) {
        String level = user.getChild("expertise_level").getValue();
        ExpertiseLevel expertiseLevel;
        
        String selectedColor = user.getChild("user_color").getValue();
        Color color;
        try {
            expertiseLevel = ExpertiseLevel.valueOf(level.toUpperCase());    
            
        }
        catch (IllegalArgumentException e) {
            System.out.println("An error occurred while accessing your expertise level. Please re-enter a new one.");
            expertiseLevel = getExpertiseLevel(input);
        }
        
        try {
            color = Color.valueOf(selectedColor.toUpperCase());
        }
        catch (IllegalArgumentException e){
            System.out.println("An error occurred while accessing your color. Please re-enter a new one.");
            color = getUserColor(input);
        }
        
        return new ChessPlayer(userName, expertiseLevel, color);
    }
    
    public static ChessPlayer createPlayer(String userName, Scanner input) {
        Color color = getUserColor(input);
        ExpertiseLevel expertiseLevel = getExpertiseLevel(input);
        return new ChessPlayer(userName, expertiseLevel, color);
    }
}