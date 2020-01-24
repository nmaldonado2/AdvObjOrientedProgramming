// Nichole Maldonado
// CS331 - Lab1, Validating a Move for a Chess Piece

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
class ChessPiece {
    String name;
    char xPosition;
    int yPosition;
    boolean isWhite;
}

/*
 * Main class that houses the main method. The file is read and an array
 * of chess pieces is populated. The pieces are then checked to determine
 * if they could move to the new user-designated position.
 */
public class Maldonado_Nichole_Lab1 {
    
    // Constants for the board dimensions.
    static final char MAX_BOARD_X_POSITION = 'H';
    static final char MIN_BOARD_X_POSITION = 'A';
    static final int MAX_BOARD_Y_POSITION = 8;
    static final int MIN_BOARD_Y_POSITION = 1;
    
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
    public static boolean canMoveVertically(char currXPosition, char newXPosition, 
        int newYPosition, int minYPosition, int maxYPosition) {
        
        if (currXPosition != newXPosition) {
            return false;
        }
        return newYPosition >= minYPosition && newYPosition <= maxYPosition;
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
    public static boolean canMoveHorizontally(int currYPosition, char newXPosition, 
        int newYPosition, char minXPosition, char maxXPosition){
        
        if (currYPosition != newYPosition) {
            return false;
        }
        return newXPosition >= minXPosition && newXPosition <= maxXPosition;
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
    public static boolean canMoveDiagonally(ChessPiece piece, char xPosition, int yPosition, boolean moveOneOnly) {
        
        // The differences of the x and y positions must both be equals to
        // 1.
        if (moveOneOnly) {
            return Math.abs(piece.xPosition - xPosition) == 1 && Math.abs(piece.yPosition - yPosition) == 1;
        }
        
        // Otherwise, only the differences must be the same.
        return Math.abs(piece.xPosition - xPosition) == Math.abs(piece.yPosition - yPosition);
    }
    
    /*
     * Method that verfies if a pawn ChessPiece can move to the new
     * x and y position.
     * @param: The chess piece and the x and y positions that will be verified.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8. Assume the piece's name is "Pawn".
     */
    public static boolean validatePawnMove(ChessPiece piece, char xPosition, int yPosition) {
        
        // Since white pieces move up, 1 and 2 are added to the current
        // y position for the min and max y position values.
        if (piece.isWhite) {
            return canMoveVertically(piece.xPosition, xPosition, yPosition, 
                piece.yPosition + 1, piece.yPosition + 2);
        }
        
        // Since black pieces move down, 1 and 2 are subtracted from the
        // current y position.
        return canMoveVertically(piece.xPosition, xPosition, yPosition, 
            piece.yPosition - 2, piece.yPosition - 1);
    }
    
    /*
     * Method that verfies if a rook ChessPiece can move to the new
     * x and y position.
     * @param: The chess piece and the x and y positions that will be verified.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8. Assume the piece's name is "Rook".
     */
    public static boolean validateRookMove(ChessPiece piece, char xPosition, int yPosition) {
        
        // Rooks can move vertically or horizontally.
        return canMoveVertically(piece.xPosition, xPosition, yPosition, MIN_BOARD_Y_POSITION, 
            MAX_BOARD_Y_POSITION) || canMoveHorizontally(piece.yPosition, xPosition, yPosition, 
            MIN_BOARD_X_POSITION, MAX_BOARD_X_POSITION);
    }
    
    /*
     * Method that verfies if a knight ChessPiece can move to the new
     * x and y position.
     * @param: The chess piece and the x and y positions that will be verified.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8. Assume the piece's name is "Knight".
     */
    public static boolean validateKnightMove(ChessPiece piece, char xPosition, int yPosition) {
        
        // Valid move if the new position is 2 spaces horizontally and 1 space
        // vertically.
        if (Math.abs(piece.xPosition - xPosition) == 2) {
            if (Math.abs(piece.yPosition - yPosition) == 1) {
                return true;
            }
        }
        
        // Valid move if the new position is 2 spaces vertically and 1 space
        // horizontally.
        else if (Math.abs(piece.yPosition - yPosition) == 2) {
            if (Math.abs(piece.xPosition - xPosition) == 1) {
                return true;
            }
        }
        return false;
    }
    
    /*
     * Method that verfies if a bishop ChessPiece can move to the new
     * x and y position.
     * @param: The chess piece and the x and y positions that will be verified.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8. Assume the piece's name is "Bishop".
     */
    public static boolean validateBishopMove (ChessPiece piece, char xPosition, int yPosition) {
        
        // Bishop can move diagonally by any number of spaces.
        return canMoveDiagonally(piece, xPosition, yPosition, false);
    }
    
    /*
     * Method that verfies if a queen ChessPiece can move to the new
     * x and y position.
     * @param: The chess piece and the x and y positions that will be verified.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8. Assume the piece's name is "Queen".
     */
    public static boolean validateQueenMove(ChessPiece piece, char xPosition, int yPosition) {
        
        // A queen can move any number of spaces vertically, horizontally, or
        // diagonally.
        if (canMoveVertically(piece.xPosition, xPosition, yPosition, 
            MIN_BOARD_Y_POSITION, MAX_BOARD_Y_POSITION)) {
            
            return true;   
        }
        if (canMoveHorizontally(piece.yPosition, xPosition, yPosition, 
            MIN_BOARD_X_POSITION, MAX_BOARD_X_POSITION)) {
            
            return true;
        }
        return canMoveDiagonally(piece, xPosition, yPosition, false);
    }
    
    /*
     * Method that verfies if a king ChessPiece can move to the new
     * x and y position.
     * @param: The chess piece and the x and y positions that will be verified.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8. Assume the piece's name is "King".
     */
    public static boolean validateKingMove(ChessPiece piece, char xPosition, int yPosition) {
        
        // A king can move vertically, horizontally, or diagonally by only
        // 1 space.
        if (canMoveVertically(piece.xPosition, xPosition, yPosition, piece.yPosition - 1, piece.yPosition + 1)) {
            return true;   
        }
        if (canMoveHorizontally(piece.yPosition, xPosition, yPosition, (char)(piece.xPosition - 1), 
            (char)(piece.xPosition + 1))) {
            
            return true;
        }
        return canMoveDiagonally(piece, xPosition, yPosition, true);
    }
    
    /*
     * Method that verfies if a ChessPiece can move to the new
     * x and y position by calling the appropriate validate method
     * based on the chess piece's name.
     * @param: The chess piece and the x and y positions that will be verified.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8. Assume the piece's name is one of the
     * six chess piece types.
     */
    public static boolean validateMove(ChessPiece piece, char xPosition, int yPosition) {
        
        if (piece.name.equals("Pawn")) {
            return validatePawnMove(piece, xPosition, yPosition);
        }
        if (piece.name.equals("Rook")) {
            return validateRookMove(piece, xPosition, yPosition);
        }
        if (piece.name.equals("Knight")) {
            return validateKnightMove(piece, xPosition, yPosition);
        }
        if (piece.name.equals("Bishop")) {
            return validateBishopMove(piece, xPosition, yPosition);
        }
        if (piece.name.equals("Queen")) {
            return validateQueenMove(piece, xPosition, yPosition);
        }
        
        // To avoid code redudancy, checks have already been made in prior methods 
        // so the last piece type is knight.
        return validateKingMove(piece, xPosition, yPosition);
    }
    
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
    public static ChessPiece createChessPiece(boolean isWhite, char xPosition, 
        int yPosition, String pieceType) {    
        
        // Only creates a ChessPiece if it is one of the six chess piece types.
        if (pieceType.equals("pawn") || pieceType.equals("rook") || pieceType.equals("knight") || 
           pieceType.equals("bishop") || pieceType.equals("queen") || pieceType.equals("king")) {
            
            ChessPiece piece = new ChessPiece();
            
            // Capitalize first letter only to keep all names uniform and prepare for eventual printing.
            piece.name = pieceType.substring(0, 1).toUpperCase() + pieceType.substring(1);
            piece.isWhite = isWhite;
            piece.xPosition = xPosition;
            piece.yPosition = yPosition;
            return piece;
        }
        return null;
    }
    
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
    public static ChessPiece prepareChessPiece(String[] pieceAttributes) {
        
        boolean isWhite;
        char xPosition;
        int yPosition;
        
        try {
            if (pieceAttributes[1].toLowerCase().equals("white")) {
                isWhite = true;   
            }
            else if (pieceAttributes[1].toLowerCase().equals("black")) {
                isWhite = false;
            }
            
            // The piece color can only be white or black.
            else {
                return null;
            }
            
            // The x-position of the piece must fall in range of the chess board.
            if (pieceAttributes[2].length() == 1) {
                xPosition = pieceAttributes[2].toUpperCase().charAt(0);
                if (xPosition < MIN_BOARD_X_POSITION || xPosition > MAX_BOARD_X_POSITION) {
                    return null;
                }
            }
            else {
                return null;
            }
            
            // The y-position must fall within the range of the chess board.
            yPosition = Integer.parseInt(pieceAttributes[3]);
            if (yPosition < MIN_BOARD_Y_POSITION || yPosition > MAX_BOARD_Y_POSITION) {
                return null;
            }
        } 
        
        // Catches exception in the event that xPosition or yPosition datatypes
        // do not match their assignement.
        catch (NumberFormatException e) {
            return null;
        }
        
        // Creates the chess piece with the checked attributes.
        return createChessPiece(isWhite, xPosition, yPosition, pieceAttributes[0].toLowerCase());
    }
    
    /*
     * Method that prints the line numbers of pieces not evaluated from the file.
     * @param: An array list of Integers that contains the line numbers of pieces
     *        not evaluated.
     * @return: None.
     */
    public static void printUnevaluatedPieces(ArrayList<Integer> piecesNotEvaluated) {
        
        System.out.print("\nThe pieces on the following lines were not added since");
        System.out.print(" one or more of the attributes did not match those expected"); 
        System.out.println(" or the 4 necessary attributes were not included.");
        
        //Prints line numbers.
        System.out.print("Line(s): ");
        for (int i = 0; i < piecesNotEvaluated.size(); i++) {
            System.out.print(piecesNotEvaluated.get(i));
            if (i < piecesNotEvaluated.size() - 1) {
                System.out.print(", ");
            }
            else {
                System.out.println("\n");
            }
        }
    }
    
    /*
     * Method that converts an ArrayList with ChessPiece objects into an Array with
     * ChessPiece objects.
     * @param: An array list of ChessPiece objects.
     * @return: an array of ChessPiece objects.
     */
    public static ChessPiece[] convertChessListToArray(ArrayList<ChessPiece> chessPieceList) {
        
        ChessPiece[] chessPieceArr = new ChessPiece[chessPieceList.size()];
        for (int i = 0; i < chessPieceList.size(); i++) {
            chessPieceArr[i] = chessPieceList.get(i);
        }
        return chessPieceArr;
    }
    
    /*
     * Method that reads the file found and populates an array with 
     * valid ChessPiece objects.
     * @param: The file path for the file to be read with the chess pieces.
     * @return: An array with ChessPiece objects.
     */
    public static ChessPiece[] retrieveAndAllocatePieces(String filePath) throws IOException {
        
        ArrayList<ChessPiece> chessPieceList = new ArrayList<ChessPiece>();
        ArrayList<Integer> piecesNotEvaluated = new ArrayList<Integer>();
        int lineNum = 0;
        
        File chessFile = new File(filePath);
        Scanner fileReader = new Scanner(chessFile);
        
        // Iterates through file lines.
        while (fileReader.hasNextLine()) {
            lineNum++;
            
            // Removes all white spaces from the line.
            String chessPieceDescription = fileReader.nextLine().replaceAll("\\s+", "");
            
            //Evaluates the line as long as the description is not empty.
            if (!chessPieceDescription.isEmpty()) {
                String[] chessPieceAttributes = chessPieceDescription.split(",");
                if (chessPieceAttributes.length == 4) {
                    ChessPiece piece = prepareChessPiece(chessPieceAttributes);
                    
                    // Adds the piece to the list if it was successfully created.
                    if (piece != null) {
                        chessPieceList.add(piece);
                    }
                    else {
                        piecesNotEvaluated.add(lineNum);
                    }
                }
                else {
                    piecesNotEvaluated.add(lineNum);
                } 
                
            }
        }
        
        //Prints the unevaluated pieces' line numbers.
        if (piecesNotEvaluated.size() > 0) {
            printUnevaluatedPieces(piecesNotEvaluated);
        }
        if (chessPieceList.size() < 1) {
            return null;
        }
        
        // Close file scanner.
        fileReader.close();
        
        //returns array of ChessPiece objects.
        return  convertChessListToArray(chessPieceList);
    }
    
    /*
     * Method that intiates the verification of the ChessPieces' move to the new
     * positions and prints the results.
     * @param: An array with ChessPiece objects whose current positions will be compared with
     *         xPosition and yPosition to determine if the move is valid.
     * @return: None.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8.
     */
    public static void verifyNewPosition(ChessPiece[] chessPieceArr, char xPosition, int yPosition) {
        
        for (int i = 0; i < chessPieceArr.length; i++) {
            String verificationNotifier = "";
            String samePositionNotifier = "";
            
            // Does not verify position if the new poistion matches the current
            // position.
            if (chessPieceArr[i].xPosition == xPosition && chessPieceArr[i].yPosition == yPosition) {
                samePositionNotifier = " (The piece is already located in the new position) ";
                verificationNotifier = "NOT ";
            }
            else {
                if (!validateMove(chessPieceArr[i], xPosition, yPosition)) {
                    verificationNotifier = "NOT ";
                }
            }
            
            System.out.print(chessPieceArr[i].name + " at " + chessPieceArr[i].xPosition);
            System.out.print(", " + chessPieceArr[i].yPosition + " can " + verificationNotifier);
            System.out.println("move to " + xPosition + ", " + yPosition + samePositionNotifier);
        }
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
    public static void retrieveNewPosition(ChessPiece[] chessPieceArr, Scanner input) {
        
        System.out.print("Enter the new x - position: ");
        String xPositionStr = input.nextLine();
        
        if (xPositionStr.length() != 1) {
            System.out.print("Invalid x-position. The x-position can only be one character.");
            System.out.println(" Program terminating.");
        }
        else {
            try {
                char xPosition = xPositionStr.toUpperCase().charAt(0);
                
                // xPosition must be in the chess board's range.
                if (xPosition < MIN_BOARD_X_POSITION || xPosition > MAX_BOARD_X_POSITION) {
                    System.out.print("Invalid x-position. The x-position must be in the range A to H.");
                    Sytem.out.println(" Program terminating.");
                }
                else {
                    System.out.print("Enter the new y - position: ");
                    int yPosition = input.nextInt();
                    System.out.println();
                    
                    // yPosition must be in the chess board's range.
                    if(yPosition < MIN_BOARD_Y_POSITION || yPosition > MAX_BOARD_Y_POSITION){
                        System.out.print("Invalid y-position. The x-position must in the range ");
                        System.out.println("of 0 to 9. Program terminating.");
                    }
                    else{
                        verifyNewPosition(chessPieceArr, xPosition, yPosition); 
                    }  
                }
            }
            
            // Catches exception in the event that user types a 
            // position in the wrong data type.
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Program terminating.");
            }
            
        }
    }
    
    /* Ensures that file_path includes a .txt file at the end.
     * @param: a string of the file path that will be evaluated.
     * @return: Returns false if file_path does not end in .txt and returns
     *        true otherwise.  
     */              
    public static boolean isTxtFile(String filePath) {
                
        // Returns false if the file_path does not have at least 5 letters since a
        // valid .txt file name can have a minimum of 5 letters.
        if (filePath.length() < 5) {
            System.out.print("\nInvalid file. The file must be a .txt file with at ");
            System.out.println("least a one character name. Program terminating");
            return false;
        }
        
        // If the length of file_path is greater than or equal to 5, then true is
        // returned only if file_path ends in ".txt".
        if (filePath.substring(filePath.length() - 4).equals(".txt")) {
            return true;
        }
                
        System.out.println("\nInvalid file. The file must be a .txt file. Program terminating.");
        return false;
    }
    
    /* Main method that retrieves the file path from the user and intializes
     * the ChessPiece creation, storage, and verfication of the new position.
     * @param: None.
     * @return: None.  
     */  
    public static void main(String[] args){
        
        // Gets file path.
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the path for the file that contains the chess pieces: ");
        String filePath = input.nextLine();
        
        if (isTxtFile(filePath)) {
            try {
                
                // Populates array with ChessPiece objects.
                ChessPiece[] chessPieceArr = retrieveAndAllocatePieces(filePath);
                if (chessPieceArr != null) {
                    
                    //Retrieves and verfies new position.
                    retrieveNewPosition(chessPieceArr, input);
                }
                else {
                    System.out.print("\nNo valid chess pieces exist in the file so the new position ");
                    System.out.println("will not be evaluated. Program terminating.");
                }
            } 
            
            // Catches exceptions that may occur during file reading.
            catch (FileNotFoundException e) {
                System.out.println("\nThe file could not be found. Program terminating.");
            }
            catch (IOException e) {
                System.out.print("\nAn error occurred with an input/output operation.");
                System.out.println(" Program terminating.");
            }
        }

    }
}

