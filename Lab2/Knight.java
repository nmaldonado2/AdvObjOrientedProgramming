/*
 * Class ChessPiece that stores the type of chess piece (name),
 * the x-position on the chess board, the current y-position on
 * the chess board, and a boolean of whether the chessboard is white
 * or black.
 * Assume that the chess piece names can either be Pawn, Rook, Bishop,
 * King, Queen, or Knight. Also assume that xPosition is a character from
 * 'A' to 'H' and yPosition is an integer from 1 - 8.
 */
public class Knight {
    private char xPosition;
    private int yPosition;
    private boolean isWhite;
    
    public Knight(){
        this.xPosition = Coordinates.MIN_BOARD_X_POSITION;
        this.yPosition = Coordinates.MIN_BOARD_Y_POSITION;
    }
    
    public Knight(int position, boolean isWhite){
        this.xPosition = (char)position;
        this.yPosition = position;
        this.isWhite = isWhite;
    }
    
    /*
     * Constructor for ChessPieceClass that initializes the attributes
     * name, xPosition, yPosition, and isWhite.
     * @param: the name, xPosition, yPosition, and isWhite values that
     *         will be assigned to the fields.
     * @return: None.
     */
    public Knight(char xPosition, int yPosition, boolean isWhite){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isWhite = isWhite;
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
     * Method that verfies if a knight ChessPiece can move to the new
     * x and y position.
     * @param: The chess piece and the x and y positions that will be verified.
     * @return: Returns true if the piece can move to the square marked by the
     *         x and y positions. False is otherwise returned.
     * Assume that the x-position is within the range of A-H and the y-position
     * is within the range of 1-8. Assume the piece's name is "Knight".
     */
    public boolean validateMove(Coordinates position) {
        return position.canReachLShape(this.xPosition, this.yPosition);
    }
    
    public String listPosition(){
        return "Knight at " + this.xPosition + ", " + this.yPosition;
    }
    
    // Based on class instructions, only accessors are allowed.
    // Thus no setters are included.
}