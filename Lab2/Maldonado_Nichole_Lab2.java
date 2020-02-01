// Nichole Maldonado
// CS331 - Lab 2

/*
 * This program retrieves a chess piece attributes based on
 * the user's request and creates the corresponding chess
 * piece. The program then determines if the specific chess
 * piece can reach the coordinates retrieved from the user.
 * 
 * The user is prompted for the type, position, and color of the
 * chess piece (the chess piece can only be white or black).
 * The user is then able to select a new position to verify if
 * their piece can move to that coordinate.
 *
 * The ultimate output of the program is whether or not the chess
 * piece can move to the new location provided by the user.
 */

// changelog
// [1/28/20] [Nichole Maldonado] created retrieveType, retrieveColor, retrieveXPosition, and
//                               retrieveYPosition methods to build the chess piece.
// [1/28/20] [Nichole Maldonado] implemented the method process chess piece to
//                               create a chess piece based off the user's request.
// [1/28/20] [Nichole Maldonado] implemented the methods prepareChessPiece and 
//                               createChessPiece to check the piece's properties and
//                               create a ChessPiece object.
// [1/28/20] [Nichole Maldonado] created Move class which would hold coordinates 
//                               and have the behavior to determine if the current
//                               coordinates could be reached from a specific move.
// [1/28/20] [Nichole Maldonado] renamed Move class to Coordinate class.
// [1/28/20] [Nichole Maldonado] created two print methods that would print whether
//                               the chess piece could move or whether the piece stayed
//                               in the same position.
// [1/29/20] [Nichole Maldonado] removed bug in method getType that resulted in the next
//                               line not being consumed by the scanner.
// [1/29/20] [Nichole Maldonado] moved code from main and created method 
//                               interpretUserInput since the code focused on extracting
//                               the new position.
// [1/29/20] [Nichole Maldonado] fixed bug in validateMovePawn that would not allow a
//                               black piece to move forward.
// [1/29/20] [Nichole Maldonado] added the board constants to the Coordinate class since the
//                               coordinates rely on the board's dimensions.

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;
import java.io.IOException;
import java.util.InputMismatchException;
import edu.nmaldonado2.chessconfigs.Coordinates;
import edu.nmaldonado2.chessconfigs.King;
import edu.nmaldonado2.chessconfigs.Knight;
import edu.nmaldonado2.chessconfigs.Pawn;
import edu.nmaldonado2.chessconfigs.Rook;
import edu.nmaldonado2.chessconfigs.Bishop;
import edu.nmaldonado2.chessconfigs.Queen;
import edu.nmaldonado2.chessconfigs.ChessPieceTypes;

/*
 * Main class that houses the main method. The file is read and an array
 * of chess pieces is populated. The pieces are then checked to determine
 * if they could move to the new user-designated position.
 */
public class Maldonado_Nichole_Lab2 {
    
    /*
     * Methods that prints if a piece is in the same position.
     * @param: the string denoting the piece type and the
     *         position of the piece.
     * @return: None.
     */
    public static void printResultStayedInSameSquare(String pieceType, char xPosition, int yPosition) {
        System.out.printf("%s at %c, %d cannot move to %c, %d ", pieceType, 
                xPosition, yPosition, xPosition, yPosition);
        System.out.println("since the piece did not move!");
    }
    
    /*
     * Methods that prints if a piece can or cannot move to a square.
     * @param: the string denoting the piece type and the
     *         position of the piece, a boolean determining if the piece
     *         can move to the coordinates, and the new coordinates.
     * @return: None.
     */
    public static void printResult(String currentPosition, boolean canMove, Coordinates position) {
        String canMoveNotifier = "";
        if (!canMove) {
            canMoveNotifier = "NOT ";
        }
        System.out.printf("%s can %smove to %c, %d\n", currentPosition, 
                canMoveNotifier, position.getXCoordinate(), position.getYCoordinate());
    }
    
    /*
     * Method that creates a chess piece and initiates the validation
     * of the new move.
     * @param: a string containing the piece type, the xPosition and 
     *         yPosition of the piece, a boolean denoting whether the
     *         piece is black or white, and a scanner.
     * @return: None.
     */
    public static void processChessPiece(ChessPieceTypes pieceType, char xPosition, 
        int yPosition, boolean isWhite, Scanner input) {
        
        // Gets the new x and y position from the user.
        char newXPosition = retrieveXPosition(input);
        
        if (newXPosition != 0) {
            
            int newYPosition = retrieveYPosition(input);
            if (newYPosition != -1) {
                
                // Does not create object since the new positions are not valid.
                if(newXPosition == xPosition && newYPosition == yPosition){
                    printResultStayedInSameSquare(pieceType.formatName(), xPosition, yPosition);
                    return;
                }
                
                // Otherwise crates the object, initiates the test and the printing of
                // the results.
                Coordinates position = new Coordinates(newXPosition, newYPosition);
                
                switch (pieceType) {
                    case PAWN:
                        Pawn pawnPiece = new Pawn(xPosition, yPosition, isWhite, pieceType.formatName());
                        printResult(pawnPiece.listPosition(), pawnPiece.validateMove(position), position);
                        break;
                    case ROOK:
                        Rook rookPiece = new Rook(xPosition, yPosition, isWhite, pieceType.formatName());
                        printResult(rookPiece.listPosition(), rookPiece.validateMove(position), position);
                        break;
                    case KNIGHT:
                        Knight knightPiece = new Knight(xPosition, yPosition, isWhite, pieceType.formatName());
                        printResult(knightPiece.listPosition(), knightPiece.validateMove(position), position);
                        break;
                    case QUEEN:
                        Queen queenPiece = new Queen(xPosition, yPosition, isWhite, pieceType.formatName());
                        printResult(queenPiece.listPosition(), queenPiece.validateMove(position), position);
                        break;
                    case BISHOP:
                        Bishop bishopPiece = new Bishop(xPosition, yPosition, isWhite, pieceType.formatName());
                        printResult(bishopPiece.listPosition(), bishopPiece.validateMove(position), position);
                        break;
                    case KING:
                        King kingPiece = new King(xPosition, yPosition, isWhite, pieceType.formatName());
                        printResult(kingPiece.listPosition(), kingPiece.validateMove(position), position);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    /*
     * Method that returns a valid xPosition from 'A' to 'H'
     * based on the user's input.
     * @param: the scanner to retrieve the input from the user.
     * @return: a character denoting the xPosition.
     */ 
    public static char retrieveXPosition(Scanner input) {
        System.out.print("Enter the x - position (A - H): ");
        String xPositionStr = input.nextLine();
        
        if (xPositionStr.length() != 1) {
            System.out.print("Invalid x-position. The x-position can only be one character.");
            System.out.println(" Program terminating.");
            return 0;
        }
        
        // Converts position to character.
        char xPosition = xPositionStr.toUpperCase().charAt(0);

        // xPosition must be in the chess board's range.
        if (xPosition < Coordinates.MIN_BOARD_X_POSITION || xPosition > Coordinates.MAX_BOARD_X_POSITION) {
            System.out.print("Invalid x-position. The x-position must be in the range A to H.");
            System.out.println(" Program terminating.");
            return 0;
        }
        
        return xPosition;
    }
    
    /*
     * Method that returns a valid yPosition from 1 to 9.
     * based on the user's input.
     * @param: the scanner to retrieve the input from the user.
     * @return: a character denoting the xPosition.
     */
    public static int retrieveYPosition(Scanner input) throws InputMismatchException {
        System.out.print("Enter the y - position (1 - 8): ");
   
        int yPosition = input.nextInt();
        input.nextLine();
        System.out.println();

        // yPosition must be in the chess board's range.
        if (yPosition < Coordinates.MIN_BOARD_Y_POSITION || yPosition > Coordinates.MAX_BOARD_Y_POSITION) {
            System.out.print("Invalid y-position. The y-position must in the range ");
            System.out.println("of 1 to 8. Program terminating.");
            return -1;
        }
    
        return yPosition;
    }

    /*
     * Method that retrieves a string of the piece type
     * based on the user's selection.
     * @param: A scanner to retrieve the user's input.
     * @return: a string denoting the piece type. If the piece is not a 
     *          pawn, rook, knight, bishop, king, or queen, null is returned.
     */
    public static ChessPieceTypes retrieveType(Scanner input) throws InputMismatchException {
        System.out.println("Choose a piece type");
        System.out.println("1. Pawn");
        System.out.println("2. Rook");
        System.out.println("3. Knight");
        System.out.println("4. Bishop");
        System.out.println("5. King");
        System.out.println("6. Queen");
        System.out.print("Select 1 - 6: ");
        int pieceNum = input.nextInt();
        input.nextLine();
        System.out.println();
        
        if (pieceNum == 1) {
            return ChessPieceTypes.PAWN;
        }
        if (pieceNum == 2) {
            return ChessPieceTypes.ROOK;
        }
        if (pieceNum == 3) {
            return ChessPieceTypes.KNIGHT;
        }
        if (pieceNum == 4) {
            return ChessPieceTypes.BISHOP;
        }
        if (pieceNum == 5) {
            return ChessPieceTypes.KING;
        }
        if (pieceNum == 6) {
            return ChessPieceTypes.QUEEN;
        }
  
        System.out.println("Invalid piece type. Program terminating.");
        return null;
    }
    
    /*
     * Method that returns an integer representing the piece's color.
     * @param: a scanner to retrieve the user's input.
     * @return: 1 is returned if white is selected, 0 is returned if black
     *          is selected, -1 is returned otherwise.
     */
    public static int retrieveColor(Scanner input) throws InputMismatchException {
        System.out.println("Select a color:");
        System.out.println("1. Black");
        System.out.println("2. White");
        System.out.print("Select 1 or 2: ");
        int colorSelection = input.nextInt();
        input.nextLine();
        System.out.println();
        
        if(colorSelection >= 1 && colorSelection <= 2){
            
            // Returns 0 if black and 1 if white.
            return colorSelection - 1;
        }
        
        System.out.println("Invalid color type. Program terminating.");
        return -1;
    }
    
    /* Main method that initiates the retrieving of piece attributes from the
     * and the validation of whether the chess piece can move to the new
     * coordinate.
     * @param: None.
     * @return: None.  
     */  
    public static void main(String[] args) {
        System.out.println("Welcome. To create your chess piece follow the instructions below.");
        
        // Retrieves chess piece's attributes.
        Scanner input = new Scanner(System.in);
        
        try{
            ChessPieceTypes pieceType = retrieveType(input);
            if(pieceType != null){

                // Default to false since we only change if colorNum is 1.
                boolean isWhite = false;
                int colorNum = retrieveColor(input);

                if (colorNum == 1) {
                    isWhite = true;
                }
                if(colorNum != -1) {  
                    
                    // Retrieves the current x and y position of the chess piece.
                    char xPosition = retrieveXPosition(input);
                    if(xPosition != 0){
                        int yPosition = retrieveYPosition(input);

                        // Pawn piece can only move forward. Thus, a white pawn can not
                        // exist at row 1 and a black pawn cannot exist at row 8.
                        if (pieceType == ChessPieceTypes.PAWN && isWhite && 
                            yPosition == Coordinates.MIN_BOARD_Y_POSITION) {

                            System.out.println("Invalid pawn position. Program terminating");
                        }
                        else if (pieceType == ChessPieceTypes.PAWN && !isWhite && yPosition == Coordinates.MAX_BOARD_Y_POSITION) {
                            System.out.println("Invalid pawn position. Program terminating");
                        }

                        // If the correct attributes are selected, then the piece is processed
                        // and the verification test ensues.
                        else if (yPosition != -1) {
                            System.out.println("\nWhere would you like to move your piece?");
                            processChessPiece(pieceType, xPosition, yPosition, isWhite, input);
                        }
                    }
                }
            }
        }
        catch(InputMismatchException e) {
                System.out.println("\nInvalid input. Program terminating.");
                System.out.println("Error: " + e);
        }
 
    }
}