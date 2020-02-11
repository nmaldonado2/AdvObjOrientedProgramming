// Nichole Maldonado
// CS331 - Lab 3, King Class

/*
 * This class file contains the King class which is a derived class
 * of the Queen class, which inherits from the ChessPiece class
 * and thus inherits all the fields and methods. The class also overrides
 * the validMove function but still calls the super's method
 * to continue the parameter checks.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made King class extend the Queen class.
// [2/07/20] [Nichole Maldonado] created validInitialPosition method to
//                               check the x and y positions before creating the
//                               pawn.
// [2/07/20] [Nichole Maldonado] added createKing method to take care of King
//                               creation and verify the initial positions.
// [2/07/20] [Nichole Maldonado] overrode the super's validMove method by
//                               checking if the King moves by one and calls the
//                               super's method to see if the King can move
//                               vertically, horizontally, or diagonally.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.
// [2/08/20] [Nichole Maldonado] removed validInitialPosition to allow for the chess
//                               piece to initially start anywhere.

package edu.nmaldonado2.chesspieces;
import edu.nmaldonado2.chesspieces.Queen;

/*
 * The King Class inherits all the methods and fields
 * from the Queen clas, which inherits from the
 * ChessPiece class, which includes name, 
 * xPosition, yPosition, and isWhite. The class
 * paritially overrides the validMove method.
 */
public class King extends Queen {
    
    /*
     * Constructor for King that uses the super's constructor
     * to initialize the object's fields.
     * @param: a string denoted as "King", the x position, y position, and
     *         a boolean that is true if the piece is white. 
     * @return: None.
     * Assume the xPosition and yPosition are valid positions within the board.
     */
    public King(String name, char xPosition, int yPosition, boolean isWhite) {
        super(name, xPosition, yPosition, isWhite);
    }
    
    /*
     * Default constructor for King that uses the super's 
     * constructor to intialize the piece's fields.
     * @param: a string denoted as "King", column e,
     *         the board's minimum y position, and true, 
     * .       denoting a white piece.
     * @return: None.
     */
    public King() {
        super("King", 'E', ChessPiece.MIN_Y_POSITION, true);
    }
    
    /*
     * Method that determines if the King can move to the new positions.
     * @param: the new x and y positions that the piece could potentially
     *         move to.
     * @return: true if the King can move to the new position, false otherwise.
     */
    @Override
    public boolean validMove(char xPosition, int yPosition) {
        return this.xDifference(xPosition) <= 1 && 
                this.yDifference(yPosition) <= 1 &&
                super.validMove(xPosition, yPosition);
    }
}