// Nichole Maldonado
// CS331 - Lab 4, ChessPiece Class

/*
 * This class file contains the ChessPiece class
 * which defines the fields of name, xPosition,
 * yPosition, and isWhite. The class also defines the 
 * max y and x positions that can be reached in the chess board.
 * The class has a validMove method which verifies if the
 * x and y position are in the board's realm. It also includes
 * other helper methods, such as moveDiagonally, which is
 * used by inheriting classes.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made ChessPiece class abstract since
//                               ChessPiece objects are not needed.
// [2/07/20] [Nichole Maldonado] added the constants for the positions
//                               which will be used by various classes.
// [2/07/20] [Nichole Maldonado] added a moveDiagonally method for inheriting
//                               classes to use.
// [2/07/20] [Nichole Maldonado] add xDifference and yDifference methods to reduce
//                               code redundancy among inheriting classes.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.
// [2/09/20] [Nichole Maldonado] made all the fields private.
// [2/28/20] [Nichole Maldonado] added pieceInitial to create a string of the piece's intial
//                               to be used when drawing the board.
// [2/28/20] [Nichole Maldonado] created move function to update the x and y position.

package utep.cs3331.lab4.chess.chesspieces;

import utep.cs3331.lab4.chess.BoardDimensions;
/*
 * ChessPiece class which contains the fileds name,
 * xPosition, yPosition, and isWhite. Notable
 * behavious include validMove, xDifference, and
 * yDifference.
 */
public abstract class ChessPiece implements BoardDimensions{
    private String name;
    private char xPosition;
    private int yPosition;
    private boolean isWhite;
        
    /*
     * Constructor for the chess piece class that
     * initializes  the necessary fields.
     * @param: the name of the piece (can only be one of the 6 chess piece types),
     *         the x and y positions, and a boolean that is true if the piece
     *         is white.
     * @return: None.
     * Assume the x and y positions are within the board's range.
     */
    public ChessPiece(String name, char xPosition, int yPosition, boolean isWhite) {
        
        //Creates a unique name with the x and y position appended.
        this.name = name + xPosition + yPosition;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isWhite =isWhite;
    }
    
    /*
     * Accessor method for the attribute xPosition.
     * @param: None.
     * @return: the attribute xPosition.
     */
    public char getXPosition() {
        return this.xPosition;
    }
    
    /*
     * Accessor method for the attribute yPosition.
     * @param: None.
     * @return: the attribute yPosition.
     */
    public int getYPosition() {
        return this.yPosition;
    }
    
    /*
     * Accessor method for the attribute isWhite.
     * @param: None.
     * @return: the attribute isWhite.
     */
    public boolean getIsWhite() {
        return this.isWhite;
    }
    
    /*
     * Accessor method for the attribute name.
     * @param: None.
     * @return: the attribute name.
     */
    public String getName() {
        return this.name;
    }
    
    /*
     * Setter method for the attribute xPosition.
     * @param: a character representing the xPosition.
     *         Assume xPosition is in the board's range.
     * @return: None.
     */
    public void setXPosition(char xPosition) {
        this.xPosition = xPosition;
    }
    
    /*
     * Setter method for the attribute yPosition.
     * @param: a integer representing the yPosition.
     *         Assume xPosition is in the board's range.
     * @return: None.
     */
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }
    
    /*
     * Setter method for the attribute isWhite.
     * @param: a boolean of whether the piece is white.
     * @return: None.
     */
    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }
    
    /*
     * Setter method for the attribute name.
     * @param: a string of the piece's name.
     * @return: None.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /*
     * Method that determines if a ChessPiece can move to the x and y
     * positions based on whether the positions are within the board's
     * range.
     * @param: the x and y position to be validated.
     * @return: a boolean if the x and y positions are within the board's
     *          range, false otherwise.
     */
    public boolean validMove(char xPosition, int yPosition) {
        return (xPosition != this.xPosition) || (yPosition != this.yPosition);
    }
    
    /*
     * Method that finds the absolute difference between the current
     * xPosition and the local xPosition
     * @param: the xPosition to be subtracted
     * @return: the absolute difference of the field xPosition minus the 
     *          local variable xPosition.
     */
    protected int xDifference(char xPosition) {
        return Math.abs(this.xPosition - xPosition);
    }
    
    /*
     * Method that finds the absolute difference between the current
     * yPosition and the local yPosition
     * @param: the yPosition to be subtracted
     * @return: the absolute difference of the field yPosition minus the 
     *          local variable yPosition.
     */
    protected int yDifference(int yPosition) {
        return Math.abs(this.yPosition - yPosition);
    }
    
    /*
     * Method that determines if a ChessPiece can move diagonally
     * to reach the x and y position.
     * @param: the x and y position to be validated.
     * @return: a boolean if the x and y positions can be reached
     *          diagonally from the ChessPiece's current position.
     */
    protected boolean moveDiagonally(char xPosition, int yPosition) {
         return (this.xDifference(xPosition) != 0) && 
                (this.xDifference(xPosition) == this.yDifference(yPosition));
    }
    
    /*
     * Method that determines if the yPosition is correct based on the
     * color
     * @param: the y position and a boolean of whether the piece will be
     * .        white.
     * @return: true if the yPosition is row 1 and the piece will be white
     *          or if the piece will be black and row position is row 8.
     */
    protected static boolean correctRowBasedOnColor(int yPosition, boolean isWhite) {
        return (isWhite && yPosition == MIN_Y_POSITION) || 
                (!isWhite && yPosition == MAX_Y_POSITION);
    }
    
    /*
     * Method that creates the initial of the chess piece starting
     * with first character of the piece's name and the x and y position of the piece.
     * @param: None.
     * @return: A string of first character of name + xPosition + yPosition.
     */
    public String pieceInitial(){
        String initial = (this.isWhite) ? "W" : "B";
        
        // Whitespace for alingment.
        initial += this.name.substring(0,1) + " ";
        return initial;
    }
    
    /*
     * Method that changes the piece position to the new x and position.
     * @param: the new x and y position of the piece..
     * @return: None.
     */
    public void move(char xPosition, int yPosition) {
        this.setXPosition(xPosition);
        this.setYPosition(yPosition);
        this.setName(this.getName().substring(0, this.getName().length() - 2) + 
                     this.getXPosition() + this.getYPosition());
    }
}