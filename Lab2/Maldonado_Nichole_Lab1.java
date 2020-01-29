// Nichole Maldonado
// CS331 - Lab 1, Programming Skills Assessment

/*
 * This program reads a file containing chess pieces, creates a 
 * ChessPiece object for each piece, populates an array with the
 * objects, prompts the user for a new position, and then verifies
 * if each piece can move to the new position.
 * 
 * The user is initially prompted to input the directory where the
 * file is located. In order for a piece to be turned into an 
 * object, the piece's name, x-position, y-position, and color
 * (either black or white) must be provided.
 *
 * The ultimate output of the program is whether or not each chess
 * piece can move to the new location provided by the user.
 */

// changelog
// [1/21/20] [Nichole Maldonado] create a ChessPiece class and 6 classes for
//                               each chess piece type, making them derived classes.
// [1/21/20] [Nichole Maldonado] implemented the method recieveAndAllocatePieces
//                               to read through the file.
// [1/21/20] [Nichole Maldonado] implemented the methods prepareChessPiece and 
//                               createChessPiece to check the piece's properties and
//                               create a ChessPiece object.
// [1/21/20] [Nichole Maldonado] implemented verifyNewPosition to iterate through the
//                               objects and print the verification results.
// [1/21/20] [Nichole Maldonado] implemented the method validateMove in the ChessPiece
//                               class and overrode the methods in the 6 derived 
//                               classes.
// [1/21/20] [Nichole Maldonado] implemented the methods canMoveVertically,
//                               canMoveHorizontally, and canMoveDiagonally to be
//                               called by the chess piece validate move methods.
// [1/22/20] [Nichole Maldonado] added isTxtFile method to ensure user includes a .txt
//                               file in the path.
// [1/22/20] [Nichole Maldonado] moved code from main and created method 
//                               interpretUserInput since the code focused on extracting
//                               the new position.
// [1/22/20] [Nichole Maldonado] fixed bug in validateMovePawn that would not allow a
//                               black piece to move forward.
// [1/22/20] [Nichole Maldonado] removed redundant code in the move method and instead
//                               ensured that the new position did not match the current
//                               position in verifyNewPosition.
// [1/23/20] [Nichole Maldonado] removed all 6 chess piece classes and removed all 
//                               class methods from ChessPiece.
// [1/23/20] [Nichole Maldonado] removed the overriden validateMove methods for the
//                               pieces by creating static validateChessPieceNameMove
//                               methods instead.
// [1/23/20] [Nichole Maldonado] copied objects from array list to array in order to 
//                               fulfill the requirement of storing objects in an array.
//                               An arraylist is necessary since the file size is unknown.
// [1/23/20] [Nichole Maldonado] added arraylist to store unevaluated pieces in the
//                               method recieveAndAllocatePieces.
// [1/23/20] [Nichole Maldonado] fixed bug in canMoveDiagonally that allowed the king to
//                               move more than one space diagonally.
// [1/23/20] [Nichole Maldonado] fixed spacing for program terminating outputs.
// [1/24/20] [Nichole Maldonado] changed pawn's move to only move by one at a time
// [1/24/20] [Nichole Maldonado] added extra check to pawn's position in checkChessPiece 
//                               function to ensure pawn would not be placed in a backwards
//                               position.
// [1/26/20] [Nichole Maldonado] made the attributes for the ChessPiece class private and
//                               added accessors.

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;
import java.io.IOException;
import java.util.InputMismatchException;

/*
 * Class ChessPiece that stores the type of chess piece (name),
 * the x-position on the chess board, the current y-position on
 * the chess board, and a boolean of whether the chessboard is white
 * or black.
 * Assume that the chess piece names can either be Pawn, Rook, Bishop,
 * King, Queen, or Knight. Also assume that xPosition is a character from
 * 'A' to 'H' and yPosition is an integer from 1 - 8.
 */
class Move {
    private char xPosition;
    private int yPosition;
    
    // Constants for the board dimensions.
    static final char MAX_BOARD_X_POSITION = 'H';
    static final char MIN_BOARD_X_POSITION = 'A';
    static final int MAX_BOARD_Y_POSITION = 8;
    static final int MIN_BOARD_Y_POSITION = 1;
    
    public Move(char xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    
    public char getXPosition(){
        return this.xPosition;
    }
    
    public int getYPosition(){
        return this.yPosition;
    }
    
    /*
     * Method that verfies if a vertical move can be made to newXPosition
     * and newYPosition that fit within the maximum and minimum y-position
     * bounds.
     * @param: The current x-position and the new x and y positions to be 
     *        verified. minYPosition and maxYPosition represent a range
     *        of values that the new y-position should be located in.
     * @return: Returns true if, based on the new piece coordinates, the
     *         object will only move vertically. False is otherwise returned.
     * Assume that the x-positions are within the range of A-H and the y-position
     * is within the range of 1-8.
     */
    public boolean moveVertically(char currXPosition, int minYPosition, int maxYPosition) {
        
        if (currXPosition != this.xPosition) {
            return false;
        }
        return this.yPosition >= minYPosition && this.yPosition <= maxYPosition;
    }
    
    /*
     * Method that verfies if a horizontal move can be made to newXPosition
     * and newYPosition that fit within the maximum and minimum x-position
     * bounds.
     * @param: The current y-position and the new x and y positions to be 
     *        verified. minXPosition and maxXPosition represent a range
     *        of values that the new x-position should be located in.
     * @return: Returns true if, based on the new piece coordinates, the
     *         piece will only move horizontally. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-positions
     * are within the range of 1-8.
     */
    public boolean moveHorizontally(int currYPosition, char minXPosition, char maxXPosition) {
        
        if (currYPosition != this.yPosition) {
            return false;
        }
        return this.xPosition >= minXPosition && this.xPosition <= maxXPosition;
    }
    
    /*
     * Method that verfies if a diagonal move can be made to newXPosition
     * and newYPosition.
     * @param: The chess piece whose x and y values will be compared with the
     *        new x and y values. The boolean moveOneOnly denotes that the
     *        piece can only move diagonally by one square.
     * @return: Returns true if, based on the new piece coordinates, the
     *         piece will only move diagonally. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8.
     */
    public boolean moveDiagonally(char currXPosition, int currYPosition, boolean moveOneOnly) {
        
        // The differences of the x and y positions must both be equal to
        // 1 to move one space.
        if (moveOneOnly) {
            return Math.abs(currXPosition - this.xPosition) == 1 && Math.abs(currYPosition - this.yPosition) == 1;
        }
        
        // Otherwise, only the differences must be the same.
        return Math.abs(currXPosition - this.xPosition) == Math.abs(currYPosition - this.yPosition);
    }
    
    public boolean moveLShape(char currXPosition, int currYPosition){
        
        // Valid move if the new position is 2 spaces horizontally and 1 space
        // vertically.
        if (Math.abs(this.xPosition - currXPosition) == 2) {
            if (this.yPosition - currYPosition == 1) {
                return true;
            }
        }
        
        // Valid move if the new position is 2 spaces vertically and 1 space
        // horizontally.
        else if (Math.abs(this.yPosition - currYPosition) == 2) {
            if (this.xPosition - currXPosition == 1) {
                return true;
            }
        }
        return false;
    }
    
}

/*
 * Main class that houses the main method. The file is read and an array
 * of chess pieces is populated. The pieces are then checked to determine
 * if they could move to the new user-designated position.
 */
public class Maldonado_Nichole_Lab1 {
    
    
//    /*
//     * Method that verfies if a ChessPiece can move to the new
//     * x and y position by calling the appropriate validate method
//     * based on the chess piece's name.
//     * @param: The chess piece and the x and y positions that will be verified.
//     * @return: Returns true if the piece can move to the square marked by the
//     *         x and y positions. False is otherwise returned.
//     * Assume that the x-position is within the range of A-H and the y-position
//     * is within the range of 1-8. Assume the piece's name is one of the
//     * six chess piece types.
//     */
//    public static boolean validateMove(ChessPiece piece, char xPosition, int yPosition) {
//        
//        if (piece.getName().equals("Pawn")) {
//            return validatePawnMove(piece, xPosition, yPosition);
//        }
//        if (piece.getName().equals("Rook")) {
//            return validateRookMove(piece, xPosition, yPosition);
//        }
//        if (piece.getName().equals("Knight")) {
//            return validateKnightMove(piece, xPosition, yPosition);
//        }
//        if (piece.getName().equals("Bishop")) {
//            return validateBishopMove(piece, xPosition, yPosition);
//        }
//        if (piece.getName().equals("Queen")) {
//            return validateQueenMove(piece, xPosition, yPosition);
//        }
//        
//        // To avoid code redudancy, checks have already been made in prior methods 
//        // so the last piece type is knight.
//        return validateKingMove(piece, xPosition, yPosition);
//    }
    
    
    /*
     * Method that creates a ChessPiece object and initializes the attributes
     * with isWhite, xPosition, yPosition, and pieceType.
     * @param: The boolean isWhite that is true if the piece is white, false if
     *        the piece is black. The char xPosition falls within the range
     *        'A' to 'H' and the yPosition falls within the range of 1 to 8.
     *        The pieceType is a string representing the type of the chess piece.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     */
//    public static Object createChessPiece(boolean isWhite, char xPosition, 
//        int yPosition, String pieceType) {
//
//        // Pawn piece can only move forward. Thus, a white pawn can not
//        // exist at row 1 and a black pawn cannot exist at row 8.
//        if (pieceType.equals("pawn") && isWhite && yPosition == MIN_BOARD_Y_POSITION) {
//            return null;
//        }
//        if(pieceType.equals("pawn") && !isWhite && yPosition == MAX_BOARD_Y_POSITION){
//            return null;
////        }
////        
//        if (pieceType.equals("pawn")) {
//            return (xPosition == yPosition) ? new Pawn(yPosition, isWhite) : new Pawn(xPosition, yPosition, isWhite);
//        }
//        if (pieceType.equals("rook")) {
//            return (xPosition == yPosition) ? new Rook(yPosition, isWhite) : new Rook(xPosition, yPosition, isWhite);
//        }
//        if(pieceType.equals("knight")){
//            return (xPosition == yPosition) ? new Knight(yPosition, isWhite) : new Knight(xPosition, yPosition, isWhite);
//        }
//        if(pieceType.equals("queen")){
//            return (xPosition == yPosition) ? new Queen(yPosition, isWhite) : new Queen(xPosition, yPosition, isWhite);
//        }
//        if(pieceType.equals("king")){
//            return (xPosition == yPosition) ? new King(yPosition, isWhite) : new King(xPosition, yPosition, isWhite);
//        }
//        return null;
//    }
    
    /*
     * Method that checks the attributes of the piece before signially the
     * creation of the ChessPiece object.
     * @param: A string array with 4 elements that should be in the form of
     *        the chess piece [type, color, x-position, y-position]. Note
     *        that these attributes will be checked int the method.
     *        The pieceType is a string representing the type of the chess piece.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     */
//    public static Object prepareChessPiece(String[] pieceAttributes) {
//        
//        boolean isWhite;
//        char xPosition;
//        int yPosition;
//        
//        try {
//            if (pieceAttributes[1].toLowerCase().equals("white")) {
//                isWhite = true;   
//            }
//            else if (pieceAttributes[1].toLowerCase().equals("black")) {
//                isWhite = false;
//            }
//            
//            // The piece color can only be white or black.
//            else {
//                return null;
//            }
//            
//            // The x-position of the piece must fall in range of the chess board.
//            if (pieceAttributes[2].length() == 1) {
//                xPosition = pieceAttributes[2].toUpperCase().charAt(0);
//                if (xPosition < MIN_BOARD_X_POSITION || xPosition > MAX_BOARD_X_POSITION) {
//                    return null;
//                }
//            }
//            else {
//                return null;
//            }
//            
//            // The y-position must fall within the range of the chess board.
//            yPosition = Integer.parseInt(pieceAttributes[3]);
//            if (yPosition < MIN_BOARD_Y_POSITION || yPosition > MAX_BOARD_Y_POSITION) {
//                return null;
//            }
//        } 
//        
//        // Catches exception in the event that xPosition or yPosition datatypes
//        // do not match their assignement.
//        catch (NumberFormatException e) {
//            return null;
//        }
//        
//        // Creates the chess piece with the checked attributes.
//        return createChessPiece(isWhite, xPosition, yPosition, pieceAttributes[0].toLowerCase());
//    }
    
    /*
     * Method that intiates the verification of the ChessPieces' move to the new
     * positions and prints the results.
     * @param: An array with ChessPiece objects whose current positions will be compared with
     *         xPosition and yPosition to determine if the move is valid.
     * @return: None.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8.
     */
//    public static void verifyNewPosition(Object[] chessPieceArr, char xPosition, int yPosition) {
//        
//        for (int i = 0; i < chessPieceArr.length; i++) {
//            String verificationNotifier = "";
//            String samePositionNotifier = "";
//            
//            // Does not verify position if the new poistion matches the current
//            // position.
//            if (chessPieceArr[i].getXPosition() == xPosition && chessPieceArr[i].getYPosition() == yPosition) {
//                samePositionNotifier = " (The piece is already located in the new position) ";
//                verificationNotifier = "NOT ";
//            }
//            else {
//                if (!chessPieceArr[i].validateMove(xPosition, yPosition)) {
//                    verificationNotifier = "NOT ";
//                }
//            }
//            
//            System.out.print(chessPieceArr[i].getName() + " at " + chessPieceArr[i].getXPosition());
//            System.out.print(", " + chessPieceArr[i].getYPosition() + " can " + verificationNotifier);
//            System.out.println("move to " + xPosition + ", " + yPosition + samePositionNotifier);
//        }
//    }
    public static void printResultStayedInSameSquare(String pieceType, char xPosition, int yPosition) {
        System.out.printf("%s at %c, %d cannot move to %c, %d.\n", pieceType, xPosition, yPosition, xPosition, yPosition);
        System.out.println("The piece did not move!");
    }
    
    public static void printResult(String currentPosition, boolean canMove, Move position){
        String canMoveNotifier = "";
        if(!canMove){
            canMoveNotifier = "NOT ";
        }
        System.out.printf("%s can %smove to %c, %d\n", currentPosition, canMoveNotifier, position.getXPosition(), position.getYPosition());
    }
    
// Assume the pieceType is valid.
    public static void processChessPiece(String pieceType, char xPosition, int yPosition, boolean isWhite, Scanner input){
        char newXPosition = getXPosition(input);
        
        if(newXPosition != 0){
            
            int newYPosition = getYPosition(input);
            if(newYPosition != -1){
                
                // Does not create object since the new positions are not valid.
                if(newXPosition == xPosition && newYPosition == yPosition){
                    printResultStayedInSameSquare(pieceType, xPosition, yPosition);
                    return;
                }
                
                Move position = new Move(newXPosition, newYPosition);
                
                if (pieceType.equals("pawn")) {
                    Pawn piece = new Pawn(xPosition, yPosition, isWhite);
                    printResult(piece.listPosition(), piece.validateMove(position), position);
                }
                else if (pieceType.equals("rook")) {
                    Rook piece = new Rook(xPosition, yPosition, isWhite);
                    printResult(piece.listPosition(), piece.validateMove(position), position);
                }
                else if(pieceType.equals("knight")){
                    Knight piece = new Knight(xPosition, yPosition, isWhite);
                    printResult(piece.listPosition(), piece.validateMove(position), position);
                }
                else if(pieceType.equals("queen")){
                    Queen piece = new Queen(xPosition, yPosition, isWhite);
                    printResult(piece.listPosition(), piece.validateMove(position), position);
                }
                else if(pieceType.equals("bishop")){
                    Bishop piece = new Bishop(xPosition, yPosition, isWhite);
                    printResult(piece.listPosition(), piece.validateMove(position), position);
                }
                else {
                    King piece = new King(xPosition, yPosition, isWhite);
                    printResult(piece.listPosition(), piece.validateMove(position), position);
                }
            }
        }

    }

    public static char getXPosition(Scanner input){
        System.out.print("Enter the x - position: ");
        String xPositionStr = input.nextLine();
        
        if (xPositionStr.length() != 1) {
            System.out.print("Invalid x-position. The x-position can only be one character.");
            System.out.println(" Program terminating.");
        }
        

        char xPosition = xPositionStr.toUpperCase().charAt(0);

        // xPosition must be in the chess board's range.
        if (xPosition < Move.MIN_BOARD_X_POSITION || xPosition > Move.MAX_BOARD_X_POSITION) {
            System.out.print("Invalid x-position. The x-position must be in the range A to H.");
            System.out.println(" Program terminating.");
            return 0;
        }
        
        return xPosition;
    }

    public static int getYPosition(Scanner input) throws InputMismatchException {
        System.out.print("Enter the y - position: ");
   
        int yPosition = input.nextInt();
        input.nextLine();
        System.out.println();

        // yPosition must be in the chess board's range.
        if(yPosition < Move.MIN_BOARD_Y_POSITION || yPosition > Move.MAX_BOARD_Y_POSITION){
            System.out.print("Invalid y-position. The y-position must in the range ");
            System.out.println("of 0 to 9. Program terminating.");
            return -1;
        }
    
        return yPosition;
    }

    /*
     * Method that retrieves the new x and y position from the user
     * and intiates the verification of the position.
     * @param: An array with ChessPiece objects and the scanner
     *        used to collect the user's input.
     * @return: None.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8.
     */

    
    public static String getType(Scanner input) throws InputMismatchException{
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
        
        if(pieceNum == 1){
            return "pawn";
        }
        if(pieceNum == 2){
            return "rook";
        }
        if(pieceNum == 3){
            return "knight";
        }
        if(pieceNum == 4){
            return "bishop";
        }
        if(pieceNum == 5){
            return "king";
        }
        if(pieceNum == 6){
            return "queen";
        }
        
        return null;
    }

    public static int getColor(Scanner input) throws InputMismatchException{
        System.out.println("Select a color:");
        System.out.println("1. White");
        System.out.println("2. Black");
        System.out.print("Select 1 or 2: ");
        int isWhite = input.nextInt();
        input.nextLine();
        System.out.println();
        
        if(isWhite == 1){
            return 1;
        }
        if(isWhite == 2){
            return 0;
        }
        
        return -1;
    }
    
    /* Main method that retrieves the file path from the user and intializes
     * the ChessPiece creation, storage, and verfication of the new position.
     * @param: None.
     * @return: None.  
     */  
    public static void main(String[] args){
        System.out.println("Welcome. To create your chess piece follow the instructions below.");
        // Gets file path.
        Scanner input = new Scanner(System.in);
        String pieceType = getType(input);
        if(pieceType != null){
            try {
                
                int color = getColor(input);
                //need to initialize for the pawn check.
                boolean isWhite = false;
                if(color == 1){
                    isWhite = true;
                }
                else if(color == 0){
                    isWhite = false;
                }
                else{
                    System.out.println("Invalid color type. Program terminating.");
                }
                if(color == 1 || color == 0){
                    char xPosition = getXPosition(input);
                    if(xPosition != 0){
                        int yPosition = getYPosition(input);
                        
                        // Pawn piece can only move forward. Thus, a white pawn can not
                        // exist at row 1 and a black pawn cannot exist at row 8.
                        if (pieceType.equals("pawn") && isWhite && yPosition == Move.MIN_BOARD_Y_POSITION) {
                            System.out.println("Invalid pawn position. Program terminating");
                        }
                        else if(pieceType.equals("pawn") && !isWhite && yPosition == Move.MAX_BOARD_Y_POSITION){
                            System.out.println("Invalid pawn position. Program terminating");
                        }
                        else if(yPosition != -1){
                            System.out.println("\nWhere would you like to move your piece?");
                            processChessPiece(pieceType, xPosition, yPosition, isWhite, input);
                        }
                    }
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Invalid input. Program terminating.");
            }
        }
        else{
            System.out.println("Invalid piece type. Program terminating.");   
        }
    }
}