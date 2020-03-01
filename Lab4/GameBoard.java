// Nichole Maldonado
// CS331 - Lab 4, GameController Class

/*
 * The GameBoard class contains the chess board
 * on a 2D array and provides the functionality
 * to move the chess pieces as well as create
 * a game board with pieces in their initial positions.
 */

// changelog
// [2/28/20] [Nichole Maldonado] added getters and setters for 
//                               attributes that include board,
//                               chessBoard and pieceConfigurations.
// [2/29/20] [Nichole Maldonado] created function to move the chess pieces
// [2/29/20] [Nichole Maldonado] in createNewGameBoard, added the ability to
//                               simultanelously write the new pieces to the file
//                               resulting in more efficient code.
// [2/29/20] [Nichole Maldonado] removed pieceConfiguration class and added
//                               numRows and numColumns attribute.
// [3/01/20] [Nichole Maldonado] Reorganized import statements

package utep.cs3331.lab4.chess;

import utep.cs3331.lab4.chess.BoardDimensions;
import utep.cs3331.lab4.chess.chesspieces.ChessPiece;
import utep.cs3331.lab4.chess.chesspieces.ChessPieceTypes;
import utep.cs3331.lab4.chess.chesspieces.Bishop;
import utep.cs3331.lab4.chess.chesspieces.King;
import utep.cs3331.lab4.chess.chesspieces.Knight;
import utep.cs3331.lab4.chess.chesspieces.Pawn;
import utep.cs3331.lab4.chess.chesspieces.Queen;
import utep.cs3331.lab4.chess.chesspieces.Rook;
import utep.cs3331.lab4.files.FileWriter;

import org.jdom2.Element;

/*
 * The GameBoard class contains the chess board
 * on a 2D array and provides the functionality
 * to move the chess pieces as well as create
 * a game board with pieces in their initial positions.
 */
public class GameBoard implements BoardDimensions{
    private ChessPiece[][] chessBoard;
    private int numRows;
    private int numColumns;
    
    /*
     * Default constructor for the Game board which
     * creates a new game board based on the number of 
     * rows and columns.
     * Note: This is the standard game board size and 
     * all other xml files will be checked to ensure game board
     * attribute matches.
     * @param: None
     * @return: The gameBoard attribute.
     */
    public GameBoard() {
        this.numRows = this.numColumns = MAX_Y_POSITION;
        this.chessBoard = new ChessPiece[this.numRows][this.numColumns];
    }
    
    /*
     * Getter for the chessBoard attribute.
     * @param: None
     * @return: The chessBoard attribute.
     */
    public ChessPiece[][] getChessBoard() {
        return this.chessBoard;
    }
    
    /*
     * Getter for the numRows attribute.
     * @param: None
     * @return: The numRows attribute.
     */
    public int getNumRows() {
        return this.numRows;
    }
    
    /*
     * Getter for the numColumns attribute.
     * @param: None
     * @return: The numColumns attribute.
     */
    public int getNumColumns() {
        return this.numColumns;
    }
    
    /*
     * Method that draws the game board.
     * @param: None.
     * Output: None.
     */
    public void drawGameBoard() {
        String col = "|";
        String row = "-----------------------------------------------------";
        System.out.println("Current Chess Game\n");
        
        System.out.println(row);
        for (int i = chessBoard.length - 1; i >= 0; i--) {
            System.out.print(" " + (i + 1) + " |");
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] != null) {
                    System.out.print("| " + chessBoard[i][j].pieceInitial() + " ");
                }
                else {
                   System.out.print("|     "); 
                }
            }
            System.out.println("|\n" + row);
        }
        System.out.print(row + "\n   |");
        for (char i = MIN_X_POSITION; i <= MAX_X_POSITION; i++) {
            System.out.print("|  " + i + "  ");
        }
        System.out.println("|\n" + row);
    }
    
    /*
     * Method that intializes the chess board with the
     * pieces in their initial positions while 
     * simultaneously writing these pieces to the xml file.
     * @param: The element pieces that will house the 
     *        xml pieces.
     * @return: None.
     */
    public void createNewGameBoard(Element pieces) {
        
        // Goes column by column and places each figure
        // in their appropriate row.
        for (char i = MIN_X_POSITION; i < MIN_X_POSITION + (chessBoard.length / 2); i++) {
            char pieceOffset = (char)(MAX_X_POSITION - i + MIN_X_POSITION);
            char[] possiblePositions = {i, pieceOffset};

            // O(1), runs two times only for each part of the board (black side and white side).
            for (char position : possiblePositions) {
                
                boolean [] isWhite = {true, false};
                
                // Indeces start at 0.
                int[] pawnPossibilities = {Pawn.WHITE_INITIAL_START - 1, Pawn.BLACK_INITIAL_START - 1};
                int[] otherPiecePossibilites = {MIN_Y_POSITION - 1, MAX_Y_POSITION - 1};
                
                // O(1), runs twice for each piece color.
                for (int j = 0; j < isWhite.length; j++) {
                    
                    // Map letter to array index.
                    int positionVal = Integer.parseInt(String.valueOf(position - 'A'));
                    this.chessBoard[pawnPossibilities[j]][positionVal] = new Pawn(position, isWhite[j]);
                    
                    // Adds piece to xml file.
                    pieces.addContent(FileWriter.createPiece(
                        this.chessBoard[pawnPossibilities[j]][positionVal].getName(), isWhite[j]));
                    
                    switch (position) {
                        case 'A':
                        case 'H':
                            
                            //Create rook.
                            this.chessBoard[otherPiecePossibilites[j]][positionVal] = new Rook(position, isWhite[j]);
                            break;
                        case 'B':
                        case 'G':
                            
                            // Create Knight.
                            this.chessBoard[otherPiecePossibilites[j]][positionVal] = new Knight(position, isWhite[j]);
                            break;
                        case 'F':
                        case 'C':
                            
                            // Create Bishop
                            this.chessBoard[otherPiecePossibilites[j]][positionVal] = new Bishop(position, isWhite[j]);
                            break;

                        case 'E':
                            
                            // Create Queen.
                            this.chessBoard[otherPiecePossibilites[j]][positionVal] = new Queen(position, isWhite[j]);
                            break;
                        default:
                            
                            // CreateKing.
                            this.chessBoard[otherPiecePossibilites[j]][positionVal] = new King(position, isWhite[j]);
                            break;
                    }
                    
                    // Adds piece to xml.
                    pieces.addContent(FileWriter.createPiece(
                        this.chessBoard[otherPiecePossibilites[j]][positionVal].getName(), isWhite[j]));
                }
            }
        }
    }
    
    /*
     * Method that add a chess piece to the board.
     * @param: the type of chess piece, x position, y position, and whether
     *        the chess piece is white or black.
     * @return: None.
     */
    public void addChessPiece(ChessPieceTypes pieceType, char xPosition, int yPosition, boolean isWhite) {
        ChessPiece piece;
        
        // Creates a specific ChessPiece based on the pieceType.
        switch (pieceType) {
            case PAWN:
                piece = new Pawn(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            case ROOK:
                piece =  new Rook(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            case KNIGHT:
                piece = new Knight(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            case QUEEN:
                piece = new Queen(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            case BISHOP:
                piece = new Bishop(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            default:
                piece =new King(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
        }
        
        // Board starts at 0,0.
        this.chessBoard[yPosition - 1][xPosition - MIN_X_POSITION] = piece;
    }
    
    /*
     * Method that moves the current chess piece at the x -y position to the
     * new position.
     * @param: the current position of the piece and the new position of the
     *        piece.
     * @return: None.
     */
    public void movePieces(int xPosition, int yPosition, int newXPosition, int newYPosition){
        this.chessBoard[newYPosition][newXPosition] = this.chessBoard[yPosition][xPosition];
        this.chessBoard[yPosition][xPosition] = null;
    }
}