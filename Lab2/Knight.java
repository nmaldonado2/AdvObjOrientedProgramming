// Nichole Maldonado
// CS331 - Lab 2, Knight Class

/*
 * This class file contains the Knight class which stores the
 * x and y Position of a Knight on the chess board, whether
 * a Knight is black or white, and the type. It also includes the method
 * validateMove which determines if the Knight can reach the 
 * coordinate.
 */

// changelog
// [1/28/20] [Nichole Maldonado] copied the ChessPiece class from Lab1 and renamed
//                               the class to Knight.
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
 * Class Knight that stores the x position on the chess board,
 * the current y-position on the chess board, the type, and a boolean
 * of whether the chessboard is white or black.
 * Assume that xPosition is a character from
 * 'A' to 'H' and yPosition is an integer from 1 - 8.
 */
public class Knight {
    private char xPosition;
    private int yPosition;
    private boolean isWhite;
    private String type;
    
    /*
     * Default constructor for Knight. Initializes the xPosition and yPosition
     * with the board's minimum values. isWhite defaults to true.
     * @param: None.
     * @return: None.
     */
    public Knight(){
        this.xPosition = 'B';
        this.yPosition = Coordinates.MIN_BOARD_Y_POSITION;
        this.isWhite = true;
        this.type = "Knight";
    }
    
    /*
     * Constructor for Knight class that initializes the attributes
     * xPosition, yPosition, whether the piece is white, and type.
     * @param: xPosition, yPosition, isWhite, and type values that
     *         will be assigned to the fields.
     * @return: None.
     */
    public Knight(char xPosition, int yPosition, boolean isWhite, String type){
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
     * @return: the attribute color.
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
     * Method that verfies if a Knight can move to the new coordinate.
     * @param: The coordinate that will be tested to detmine if the knight
     *         can move to the coordinate.
     * @return: Returns true if the piece can move to the square marked by the
     *         coordinate. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8 for the coordinate.
     */
    public boolean validateMove(Coordinates position) {
        return position.canReachLShape(this.xPosition, this.yPosition);
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