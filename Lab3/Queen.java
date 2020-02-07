// Nichole Maldonado
// CS331 - Lab 3, Queen Class

/*
 * This class file contains the Queen class which is a derived class
 * of the Rook class, which inherits from the ChessPiece class
 * and thus inherits all the fields and methods. The class has a static 
 * createKing method which creates a Queen with a correct
 * initial position. The class also overrides the
 * validMove function but still calls the super's method
 * to continue the parameter checks.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made Queen class extend the Rook class.
// [2/07/20] [Nichole Maldonado] created validInitialPosition method to
//                               check the x and y positions before creating the
//                               pawn.
// [2/07/20] [Nichole Maldonado] added createQueen method to take care of Queen
//                               creation and verify the initial positions.
// [2/07/20] [Nichole Maldonado] overrode the super's validMove method by
//                               checking if the Queen can move diagonally but still calls
//                               the super's method to see if the x and y positions are
//                               valid.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.

package edu.nmaldonado2.chesspieces;
import edu.nmaldonado2.chesspieces.Rook;

/*
 * The Queen Class inherits all the methods and fields
 * from the Rook class, which is derived from the
 * the ChessPiece class which includes name, 
 * xPosition, yPosition, and isWhite. The class
 * paritially ovverrides the validMove method
 * and includes other functions like
 * validInitialPosition and createQueen.
 */
public class Queen extends Rook{
    
    /*
     * Constructor for Queen that uses the super's constructor
     * to initialize the object's fields.
     * @param: a string denoted of the piece's type, the x position, y position, and
     *         a boolean that is true if the piece is white. 
     * @return: None.
     * Assume the xPosition and yPosition are valid positions within the board.
     */
    public Queen(String name, char xPosition, int yPosition, boolean isWhite){
        super(name, xPosition, yPosition, isWhite);
    }
    
    /*
     * Default constructor for Queen that uses the super's 
     * constructor to intialize the piece's fields.
     * @param: a string denoted as "Queen", column b,
     *         the board's minimum y position, and true, 
     * .       denoting a white piece.
     * @return: None.
     */
    public Queen(){
        super("Queen", 'B', ChessPiece.MIN_Y_POSITION, true);
    }
    
    /*
     * Method that verifies if a Queen at xPosition and yPosition
     * could exist based on the chess board rules.
     * @param: the xPosition, yPosition, and whether the piece is white
     *         which will be used to determine if the piece can initially
     *         start at the positions.
     * @return: True if a Queen could start at the x and y positions, false
     *          otherwise.
     */
    private static boolean validInitialPosition(char xPosition, int yPosition, boolean isWhite){
        return correctRowBasedOnColor(yPosition, isWhite) && xPosition == 'D';
    }
    
    /*
     * Creates a Queen object if x position and y position are
     * valid initial positions.
     * @param: the name of the Queen, x and y positions, and a boolean
     *         denoting whether the piece will be black or white.
     * @return: a Queen object if successful, or null otherwise.
     */
    public static Queen createQueen(String name, char xPosition, int yPosition, boolean isWhite){
        if (validInitialPosition(xPosition, yPosition, isWhite)) {
            return new Queen(name, xPosition, yPosition, isWhite);
        }
        return null;
    }
    
    /*
     * Method that determines if the Queen can move to the new positions.
     * @param: the new x and y positions that the piece could potentially
     *         move to.
     * @return: true if the Queen can move to the new position, false otherwise.
     */
    public boolean validMove(char xPosition, int yPosition){
        return super.validMove(xPosition, yPosition) || this.moveDiagonally(xPosition, yPosition);
    }
}