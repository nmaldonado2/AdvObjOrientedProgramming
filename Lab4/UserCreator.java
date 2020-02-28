package utep.cs3331.lab4.files;

import org.jdom2.Element;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

import utep.cs3331.lab4.players.ChessPlayer;
import utep.cs3331.lab4.players.Color;
import utep.cs3331.lab4.players.ExpertiseLevel;

public class UserCreator{
    private ChessPlayer user;
    
    // True if existing user, false for new user.
    public static boolean determineUserExistence(Scanner input){
        int numTries = 5;
        int userSelection = -1;
        
        while (numTries > 0) {
            System.out.println("1. New User");
            System.out.println("2. Existing User");
            System.out.println("Select 1 or 2: ");
            try {
                userSelection = input.nextInt();
                input.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid user selection. Please Try again");
                input.nextLine();
            }
            if (userSelection == 1) {
                return false;
            }
            else if (userSelection == 2) {
                return true;
            }
            else {
                numTries--;
                System.out.printf("Number of tries left: %d\n", numTries);
                userSelection = -1;
            }
        }
        
       
        System.out.println("Exceeded the maximum number of tries. You will be redirected to the new user page.");
        return false;
    }
    
    public static String getUserName(Scanner input) {
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        return name;
    }
    
    public static ExpertiseLevel getExpertiseLevel(Scanner input) {
        System.out.println("1. Novice");
        System.out.println("2. Medium");
        System.out.println("3. Advanced");
        System.out.println("4. Master");
        
        System.out.println("Select 1 - 4: ");
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
        
        // Prints all color options
        for (Color colorChoice : Color.values()) { 
            System.out.printf("%d. %s\n", colorNum, colorChoice); 
            colorNum++;
        } 
        
        System.out.printf("Select 1 - %d: ", colorNum - 1);
        colorNum = -1;
        try{
            colorNum = input.nextInt();
            
            // 9 is num colors
            if (colorNum < 1 || colorNum > 9) {
                throw new InputMismatchException();
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Blue will be selected.");
            colorNum = 1;
        }
        finally {
            input.nextLine();
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
    
    public static String resolveMatchingUserNames(Hashtable<String, Element> users, Scanner input) {
        System.out.println("Your user name has already been taken. Please try again.");
        int menu_num = 1;
        String userName = null;
        while (menu_num == 1) {
         userName = getUserName(input);
            if (users.containsKey(userName)) {
                System.out.println("This user name has already been taken.");
                System.out.println("Would you like to :");
                System.out.println("1. Try again");
                System.out.println("2. Quit");
                
                try{
                    menu_num = input.nextInt();
                    input.nextLine();
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid selection. Your username will not be saved.");
                    input.nextLine();
                    return null;
                }
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
            expertiseLevel = ExpertiseLevel.valueOf(level);    
            
        }
        catch (IllegalArgumentException e) {
            System.out.println("An error occurred while accessing your expertise level. Please re-enter a new one.");
            expertiseLevel = getExpertiseLevel(input);
        }
        
        try {
            color = Color.valueOf(selectedColor);
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