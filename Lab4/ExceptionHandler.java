package utep.cs3331.lab4.files.exceptions;

import java.util.InputMismatchException;
import java.util.Scanner;

public interface ExceptionHandler {
    
    // returns true if user wants to follow the proposed action.
    public static boolean handleException(String error, String proposedAction, Scanner input) {
        System.out.printf("An error occured when %s", error);
        System.out.println("Would you like to");
        System.out.printf("1. %s\n", proposedAction);
        System.out.println("2. Quit");
        System.out.print("Select 1 or 2: ");
        int menuNum = 2;
        try {
            menuNum = input.nextInt();
            if (menuNum != 1 && menuNum != 2) {
                System.out.println("Invalid selection. Program terminating");
                menuNum = 2;
            }
        }
        catch(InputMismatchException o){
            System.out.println("Invalid selection. Program terminating.");
        }
        finally {
            input.nextLine();
        }
        return menuNum == 1;
    }
}