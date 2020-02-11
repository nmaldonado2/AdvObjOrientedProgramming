// Nichole Maldonado
// CS331 - Lab 3

/*
 * The main purpose of this lab was to read a file
 * with chess piece attributes, create the objects
 * using derived classes and inheritance, store the
 * objects in an array, collect a new x and y position
 * from the user, and identify if each piece could move
 * to the new position.
 */

// changelog
// [2/07/20] [Nichole Maldonado] added retrieveYPosition and retrieveXPosition methods
//                               from Lab2 to collect the new positions from the user.
// [2/07/20] [Nichole Maldonado] added verifyNewPosition method which would identify
//                               if each piece could move to the new positions.
// [2/07/20] [Nichole Maldonado] removed bug in verifyNewPosition method that exceluded the
//                               piece's current position in the print statment.
// [2/07/20] [Nichole Maldonado] fixed bug with scanners by creating only one scanner and
//                               passing it to the appropriate methods as needed.
// [2/07/20] [Nichole Maldonado] fixed bug that was allowing the user to input the
//                               x and y positions even if no pieces had been created
//                               by adding a check in the main method.

import edu.nmaldonado2.fileutil.FileReader;
import edu.nmaldonado2.chesspieces.ChessPiece;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * The Maldonado_Nichole_Lab3 is the main class
 * that initiates a file to be read with chess pieces,
 * the chess pieces to be created and store in an
 * array, collect a new x and y position from the user,
 * and determine if each piece can move to the new
 * x and y positions.
 */
public class Maldonado_Nichole_Lab3 {
    
    /*
     * Method that intiates the verification of the ChessPieces' move to the new
     * positions and prints the results.
     * @param: An array list with ChessPiece objects whose current positions will be compared 
     *         with xPosition and yPosition to determine if the move is valid.
     * @return: None.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8.
     */
    private static void verifyNewPosition(ArrayList<ChessPiece> chessPieceArr, char xPosition, int yPosition) {
        boolean foundPosition = false;
        for (int i = 0; i < chessPieceArr.size(); i++) {         
            if (chessPieceArr.get(i).validMove(xPosition, yPosition)) {
                foundPosition = true;
                System.out.print(chessPieceArr.get(i).getName() + " at " + chessPieceArr.get(i).getXPosition());
                System.out.print(", " + chessPieceArr.get(i).getYPosition());
                System.out.println(" can move to " + xPosition + ", " + yPosition + ".");
            }             
        }
        
        if(!foundPosition){
            System.out.println("No valid chess piece moves to the position were found.");
        }
    }
    
    /*
     * Method that returns a valid xPosition from 'A' to 'H'
     * based on the user's input. 0 is returned otherwise.
     * @param: the scanner to retrieve the input from the user.
     * @return: a character denoting the xPosition.
     */ 
    private static char retrieveXPosition(Scanner input) {
        System.out.print("Enter the x - position (A - H): ");
        String xPositionStr = input.nextLine().replaceAll("\\s+", "");
        
        if (xPositionStr.length() != 1) {
            System.out.print("Invalid x-position. The x-position can only be one character.");
            System.out.println(" Program terminating.");
            return 0;
        }
        
        // Converts position to character.
        char xPosition = xPositionStr.toUpperCase().charAt(0);

        // xPosition must be in the chess board's range.
        if (xPosition < ChessPiece.MIN_X_POSITION || 
        xPosition > ChessPiece.MAX_X_POSITION) {
            System.out.print("Invalid x-position. The x-position must be in the range A to H.");
            System.out.println(" Program terminating.");
            return 0;
        }
        
        return xPosition;
    }
    
    /*
     * Method that returns a valid yPosition from 1 to 8.
     * based on the user's input. -1 is returned otherwise.
     * @param: the scanner to retrieve the input from the user.
     * @return: an integer denoting the yPosition.
     */
    private static int retrieveYPosition(Scanner input) throws InputMismatchException {
        System.out.print("Enter the y - position (1 - 8): ");
   
        int yPosition = input.nextInt();
        input.nextLine();
        System.out.println();

        // yPosition must be in the chess board's range.
        if (yPosition < ChessPiece.MIN_Y_POSITION || 
                yPosition > ChessPiece.MAX_Y_POSITION) {
            
            System.out.print("Invalid y-position. The y-position must in the range ");
            System.out.println("of 1 to 8. Program terminating.");
            return -1;
        }
    
        return yPosition;
    }
    
    /*
     * Main method that initiates the reading of
     * a file of chess pieces, storing of the chess pieces,
     * collecting a new position from the user, and validating
     * each move.
     * @param: None.
     * @return: None.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        
        FileReader fileReader = new FileReader();
        fileReader.collectFilePath(input);
        
        // Read file and collect array of ChessPieces.
        fileReader.retrieveAndAllocatePieces();
        
        // Retrieves new x and y positions.
        if (fileReader.getValidPieces().size() > 0) {
            char xPosition = retrieveXPosition(input);
            if (xPosition != 0) {

                try {
                    int yPosition = retrieveYPosition(input);
                    if(yPosition != -1){
                        
                        // Verifies the new position for each ChessPiece.
                        verifyNewPosition(fileReader.getValidPieces(), xPosition, yPosition);
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input. Program terminating.");
                }
            }
        }
        else {
            System.out.println("No valid chess pieces were created. Program terminating.");
        }
    } 
}