// Nichole Maldonado
// CS331 - Lab 2, Queen Class

/*
 * This class file contains the Queen class which stores the
 * x and y Position of a Queen on the chess board, whether
 * a Queen is black or white, and the type. It also includes the method
 * validateMove which determines if the Queen can reach the 
 * coordinate.
 */

// changelog
// [1/28/20] [Nichole Maldonado] copied the ChessPiece class from Lab1 and renamed
//                               the class to Queen.
// [1/28/20] [Nichole Maldonado] copied the validateMove function from Lab1 and
//                               changed the parameters to accept a move object
//                               with the new points.
// [1/28/20] [Nichole Maldonado] Added a listPosition method that would print
//                               a string with the pawn's name and position.
// [1/28/20] [Nichole Maldonado] changed Move object parameter in validateMove to
//                               Coordinates.
// [1/28/20] [Nichole Maldonado] created default constructor.
// [1/29/20] [Nichole Maldonado] added the string field for the type to meet the 
//                               lab requirements.
// [1/30/20] [Nichole Maldonado] created a package to store the class.
// [1/30/20] [Nichole Maldonado] added setters for each field to maintain code quality.

package edu.nmaldonado2.chessconfigs;
import edu.nmaldonado2.chessconfigs.Coordinates;

/*
 * Class Queen that stores the x position on the chess board,
 * the current y-position on the chess board, the type, and 
 * whether the chessboard is white or black.
 * Assume that xPosition is a character from
 * 'A' to 'H' and yPosition is an integer from 1 - 8.
 */
public class Queen {
    private char xPosition;
    private int yPosition;
    private boolean isWhite;
    private String type;
    
    /*
     * Default constructor for Queen. Initializes the xPosition and yPosition
     * with the piece's initial values. isWhite defaults to true.
     * @param: None.
     * @return: None.
     */
    public Queen(){
        this.xPosition = 'D';
        this.yPosition = Coordinates.MIN_BOARD_Y_POSITION;
        this.isWhite = true;
        this.type = "Queen";
    }

    /*
     * Constructor for Queen class that initializes the attributes
     * xPosition, yPosition, and whether the pawn is black or white.
     * @param: the name, xPosition, yPosition, and isWhite values that
     *         will be assigned to the fields.
     * @return: None.
     */
    public Queen(char xPosition, int yPosition, boolean isWhite, String type){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isWhite = isWhite;
        this.type = type;
    }
    
    /*
     * Accessor method for the attribute xPosition.
     * @param: None.
     * @return: the attribute xPosition.
     */
    public char getXPosition(){
        return this.xPosition;
    }
    
    /*
     * Accessor method for the attribute yPosition.
     * @param: None.
     * @return: the attribute yPosition.
     */
    public int getYPosition(){
        return this.yPosition;
    }
    
    /*
     * Accessor method for the attribute isWhite.
     * @param: None.
     * @return: the attribute isWhite.
     */
    public boolean getIsWhite(){
        return this.isWhite;
    }
    
    /*
     * Accessor method for the attribute type.
     * @param: None.
     * @return: the attribute type.
     */
    public String getType(){
        return this.type;
    }
    
    /*
     * Setter method for the attribute xPosition.
     * @param: a character representing the xPosition.
     *         Assume xPosition is in the board's range.
     * @return: None.
     */
    public void setXPosition(char xPosition){
        this.xPosition = xPosition;
    }
    
    /*
     * Setter method for the attribute yPosition.
     * @param: a integer representing the yPosition.
     *         Assume xPosition is in the board's range.
     * @return: None.
     */
    public void setYPosition(int yPosition){
        this.yPosition = yPosition;
    }
    
    /*
     * Setter method for the attribute isWhite.
     * @param: a boolean of whether the piece is white.
     * @return: None.
     */
    public void setIsWhite(boolean isWhite){
        this.isWhite = isWhite;
    }
    
    /*
     * Setter method for the attribute type.
     * @param: a string of the piece's type.
     * @return: None.
     */
    public void setType(String type){
        this.type = type;
    }
    
    /*
     * Method that verfies if a queen ChessPiece can move to the new
     * x and y position.
     * @param: The coordinate that will be tested to detmine if the queen
     *         can move to the coordinate.
     * @return: Returns true if the piece can move to the square marked by the
     *         coordinate. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8 for the coordinate.
     */
    public boolean validateMove(Coordinates position) {
        
        // A queen can move any number of spaces vertically, horizontally, or
        // diagonally.
        if (position.canReachVertically(this.xPosition, Coordinates.MIN_BOARD_Y_POSITION, Coordinates.MAX_BOARD_Y_POSITION)) {
            return true;   
        }
        if (position.canReachHorizontally(this.yPosition, Coordinates.MIN_BOARD_X_POSITION, Coordinates.MAX_BOARD_X_POSITION)) {
            return true;
        }
        return position.canReachDiagonally(this.xPosition, this.yPosition, false);
    }
    
    /*
     * Method that returns a string of the piece's name and position.
     * @param: None.
     * @return: a string of the piece's name and position.
     */
    public String listPosition(){
        return this.type + " at " + this.xPosition + ", " + this.yPosition;
    }
    
    // Based on class instructions, only accessors are allowed.
    // Thus no setters are included.
}