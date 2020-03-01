// Nichole Maldonado
// CS331 - Lab 3, Knight Class

/*
 * This class file contains the Knight class which is a derived class
 * of the ChessPiece class and thus inherits all
 * the fields and methods. The class also overrides the
 * validMove function but still calls the super's method
 * to continue the parameter checks.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made Knight class extend the ChessPiece class.
// [2/07/20] [Nichole Maldonado] created validInitialPosition method to
//                               check the x and y positions before creating the
//                               pawn.
// [2/07/20] [Nichole Maldonado] added createKnight method to take care of Knight
//                               creation and verify the initial positions.
// [2/07/20] [Nichole Maldonado] overrode the super's validMove method by
//                               checking if the knight can move in an L shape
//                               but still calls the super's to make sure the
//                               x and y position are correct positions.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.
// [2/08/20] [Nichole Maldonado] removed validInitialPosition to allow for the chess
//                               piece to initially start anywhere.

package utep.cs3331.lab4.chess.chesspieces;
import utep.cs3331.lab4.chess.chesspieces.ChessPiece;

/*
 * The Knight Class inherits all the methods and fields
 * from the ChessPiece class which include name, 
 * xPosition, yPosition, and isWhite. The class
 * paritially overrides the validMove method.
 */
public class Knight extends ChessPiece {
    
    /*
     * Constructor for Knight that uses the super's constructor
     * to initialize the object's fields.
     * @param: a string denoted as "Knight", the x position, y position, and
     *         a boolean that is true if the piece is white. 
     * @return: None.
     * Assume the xPosition and yPosition are valid positions within the board.
     */
    public Knight(String name, char xPosition, int yPosition, boolean isWhite) {
        super(name, xPosition, yPosition, isWhite);
    }
    
    /*
     * Default constructor for Knight that uses the super's 
     * constructor to intialize the piece's fields.
     * @param: a string denoted as "Knight", column b,
     *         the board's minimum y position, and true, 
     * .       denoting a white piece.
     * @return: None.
     */
    public Knight(char xPosition, boolean isWhite) {
        super("Knight", xPosition, ChessPiece.MIN_Y_POSITION, isWhite);
        
        if (!isWhite) {
            this.setYPosition(ChessPiece.MAX_Y_POSITION);
            this.setName("Knight" + xPosition + ChessPiece.MAX_Y_POSITION);
        }
    }
    
    /*
     * Method that determines if the Knight can move to the new positions.
     * @param: the new x and y positions that the piece could potentially
     *         move to.
     * @return: true if the Knight can move to the new position, false otherwise.
     */
    @Override
    public boolean validMove(char xPosition, int yPosition) {
        int xDifference = this.xDifference(xPosition);
        int yDifference = this.yDifference(yPosition);
        
        return super.validMove(xPosition, yPosition) && ((xDifference == 2 && yDifference == 1) || 
                (xDifference == 1 && yDifference == 2));
    }
    
    @Override
    public String pieceInitial() {
        
        //Remove whitespace for alignment when printing.
        return super.pieceInitial().substring(0,2) + this.getName().substring(1,2);
    }
}