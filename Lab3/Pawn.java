// Nichole Maldonado
// CS331 - Lab 3, Pawn Class

/*
 * This class file contains the Pawn class which is a derived class
 * of the ChessPiece class and thus inherits all
 * the fields and methods. The class has a static 
 * createPawn method which creates a pawn with a correct
 * initial position. The class also overrides the
 * validMove function but still calls the super's method
 * to continue the parameter checks.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made Pawn class extend the ChessPiece class.
// [2/07/20] [Nichole Maldonado] created validInitialPosition method to
//                               check the x and y positions before creating the
//                               pawn.
// [2/07/20] [Nichole Maldonado] added createPawn method to take care of Pawn
//                               creation and verify the initial positions.
// [2/07/20] [Nichole Maldonado] overrode the super's validMove method by
//                               checking if the pawn can move vertically
//                               but still calls the super's to make sure the
//                               x and y position are correct.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.

package edu.nmaldonado2.chesspieces;
import edu.nmaldonado2.chesspieces.ChessPiece;

/*
 * The Pawn Class inherits all the methods and fields
 * from the ChessPiece class which include name, 
 * xPosition, yPosition, and isWhite. The class
 * paritially overrides the validMove method
 * and includes other functions like
 * validInitialPosition and createPawn.
 */
public class Pawn extends ChessPiece{
    
    /*
     * Constructor for Pawn that uses the super's constructor
     * to initialize the object's fields.
     * @param: a string denoted as "Pawn", the x position, y position, and
     *         a boolean that is true if the piece is white. 
     * @return: None.
     * Assume the xPosition and yPosition are valid positions within the board.
     */
    public Pawn(String name, char xPosition, int yPosition, boolean isWhite){
        super(name, xPosition, yPosition, isWhite);
    }
    
    /*
     * Default constructor for Pawn that uses the super's 
     * constructor to intialize the piece's fields.
     * @param: a string denoted as "Pawn", the board's minimum x position,
     *         row 2, and true, denoting a white piece.
     * @return: None.
     */
    public Pawn(){
        super("Pawn", ChessPiece.MIN_X_POSITION, 2, true);
    }
    
    /*
     * Method that verifies if a Pawn at xPosition and yPosition
     * could exist based on the chess board rules.
     * @param: the xPosition, yPosition, and whether the piece is white
     *         which will be used to determine if the piece can initially
     *         start at the positions.
     * @return: True if a pawn could start at the x and y positions, false
     *          otherwise.
     */
    private static boolean validInitialPosition(char xPosition, int yPosition, boolean isWhite){
        return ((isWhite && yPosition == 2) || (!isWhite && yPosition == 7)) && xPosition >= 'A' && xPosition <= 'H';
    }
    
    /*
     * Creates a Pawn object if x position and y position are
     * valid initial positions.
     * @param: the name of the pawn, x and y positions, and a boolean
     *         denoting whether the piece will be black or white.
     * @return: a Pawn object if successful, or null otherwise.
     */
    public static Pawn createPawn(String name, char xPosition, int yPosition, boolean isWhite){
        if (validInitialPosition(xPosition, yPosition, isWhite)) {
            return new Pawn(name, xPosition, yPosition, isWhite);
        }
        return null;
    }
    
    /*
     * Method that determines if the Pawn can move to the new positions.
     * @param: the new x and y positions that the piece could potentially
     *         move to.
     * @return: true if the Pawn can move to the new position, false otherwise.
     */
    public boolean validMove(char xPosition, int yPosition){
        if(super.validMove(xPosition, yPosition) && (xPosition != this.xPosition)){
            return false;
        }
        return (isWhite && this.yPosition + 1 == yPosition) || (!isWhite && this.yPosition -1 == yPosition);
    }
}