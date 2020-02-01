// Nichole Maldonado
// CS331 - Lab 2, Coordinates Class

/*
 * This class file contains the Coordinates class which stores
 * x and y coordinates that are within the chess boards
 * bounds. The main behavior of the coordinates class is
 * determine if the coordinate can be reached from the
 * provided x and y positions based on a specific chess
 * move.
 */

// changelog
// [1/29/20] [Nichole Maldonado] relocated the Coordinates class from Maldonado_Nichole_Lab2.java
//                               to the current class file.
// [1/30/20] [Nichole Maldonado] created a package to store the class.
// [1/30/20] [Nichole Maldonado] added setters for each field to maintain code quality.

package edu.nmaldonado2.chessconfigs;

/*
 * Class Coordinates that stores an x and y position within
 * the board's dimensions. 
 * Attributes: an x and y position.
 * Behaviors: determines if the coordinate
 *            can be reached from a point
 *            based on a specific move.
 */
public class Coordinates {
    
    private char xCoordinate;
    private int yCoordinate;
    
    // Constants for the board dimensions.
    public static final char MAX_BOARD_X_POSITION = 'H';
    public static final char MIN_BOARD_X_POSITION = 'A';
    public static final int MAX_BOARD_Y_POSITION = 8;
    public static final int MIN_BOARD_Y_POSITION = 1;
    
    /*
     * Constructor that creates a Coordinate object
     * with the x and y coordinate.
     * @param: the xCoordinate that falls in the range
     *         'A' to 'H' and the yCoordinate that falls
     *         in the range 1 to 8.
     */
    public Coordinates(char xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    
    /*
     * Returns the x coordinate.
     * @param: None.
     * @retun: a character in the range of 'A' to 'H'.
     */
    public char getXCoordinate() {
        return this.xCoordinate;
    }
    
    /*
     * Returns the y coordinate.
     * @param: None.
     * @retun: an integer in the range of 1 to 9.
     */
    public int getYCoordinate() {
        return this.yCoordinate;
    }
    
    /*
     * Sets the x coordinate.
     * @param: an x coordinate within the range of 'A' to 'H'.
     * @retun: None.
     */
    public void setXCoordinate(char xCoordinate) {
        this.xCoordinate = xCoordinate;
    }
    
    /*
     * Sets the y coordinate.
     * @param: a y coordinate within the range of 1 to 9.
     * @retun: None.
     */
    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
    
    /*
     * Method that verfies if a vertical move can be made to xPosition that
     * falls within the min and max yPosition range to the current coordinate.
     * @param: The xPosition to be verified and the minYPosition and maxYPosition 
     *         that represent a range of values that the new y-position should
     *         be located in.
     * @return: Returns true if, based on the new piece coordinates, the
     *          object will only move vertically. False is otherwise returned.
     * Assume that the x-positions are within the range of A-H and the y-positions
     * is within the range of 1-8.
     */
    protected boolean canReachVertically(char xPosition, int minYPosition, int maxYPosition) {
        
        if (xPosition != this.xCoordinate) {
            return false;
        }
        return this.yCoordinate >= minYPosition && this.yCoordinate <= maxYPosition;
    }
    
    /*
     * Method that verfies if a horizontal move can be made to current
     * coordinate from the yPosition, that fit within the 
     * max and min x-position bounds.
     * @param: The y-position to be tested. minXPosition and maxXPosition represent
     *        a range of values that the coordinate should be located in.
     * @return: Returns true if the coordinate can be reached horizontally.
     *          False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-positions
     * are within the range of 1-8.
     */
    protected boolean canReachHorizontally(int yPosition, char minXPosition, char maxXPosition) {
        
        if (yPosition != this.yCoordinate) {
            return false;
        }
        return this.xCoordinate >= minXPosition && this.xCoordinate <= maxXPosition;
    }
    
    /*
     * Method that verfies if a diagonal move can be made to current
     * coordinate from the x and y positions.
     * @param: The y and x positions to be tested. The boolean moveOneOnly
     *         determines if the diagonal can only be increased by one square.
     * @return: Returns true if the coordinate can be reached diagonally.
     *          False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-positions
     * are within the range of 1-8.
     */
    protected boolean canReachDiagonally(char xPosition, int yPosition, boolean moveOneOnly) {
        
        // The differences of the x and y positions must both be equal to
        // 1 to move one space.
        if (moveOneOnly) {
            return Math.abs(xPosition - this.xCoordinate) == 1 && 
                Math.abs(yPosition - this.yCoordinate) == 1;
        }
        
        // Otherwise, only the differences must be the same.
        return Math.abs(xPosition - this.xCoordinate) == 
            Math.abs(yPosition - this.yCoordinate);
    }
    
    /*
     * Method that verfies if an 'L' shaped move can be made to current
     * coordinate from the x and y positions.
     * @param: The y and x positions to be evaluated.
     * @return: Returns true if the coordinate can be reached in an 'L'
     *          shape. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-positions
     * are within the range of 1-8.
     */
    protected boolean canReachLShape(char xPosition, int yPosition) {
        
        // Valid move if the new position is 2 spaces horizontally and 1 space
        // vertically.
        if (Math.abs(this.xCoordinate - xPosition) == 2) {
            if (this.yCoordinate - yPosition == 1) {
                return true;
            }
        }
        
        // Valid move if the new position is 2 spaces vertically and 1 space
        // horizontally.
        else if (Math.abs(this.yCoordinate - yPosition) == 2) {
            if (this.xCoordinate - xPosition == 1) {
                return true;
            }
        }
        return false;
    }
    
}