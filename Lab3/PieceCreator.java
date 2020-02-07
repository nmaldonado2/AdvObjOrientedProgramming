// Nichole Maldonado
// CS331 - Lab 3, PieceCreator Class

/*
 * This class file contains the PieceCreator class
 * with a field called piece, of type ChessPiece.
 * a PieceCreator object can create a ChessPiece
 * based on a string of values provided. This new
 * ChessPiece will be assigned to the PieceCreator's
 * field piece. Note that the ChessPiece class is 
 * abstract so a ChessPiece creation entails creating
 * an object of one of the derived classes.
 */

// changelog
// [2/07/20] [Nichole Maldonado] created PieceCreator object to easily
//                               store the ChessPiece created and
//                               organize code by moving checks to the class.
// [2/07/20] [Nichole Maldonado] added prepareChessPiece which was modeled after
//                               the same function from Lab1.
// [2/07/20] [Nichole Maldonado] fixed the bug in prepareChessPiece which did not
//                               check if the pieceType contained the correct x
//                               and y positions.
// [2/07/20] [Nichole Maldonado] used valuof method in prepareChessPiece to seamlessly
//                               assign a value from the enum ChessPiece types.
// [2/07/20] [Nichole Maldonado] caught the IllegalArguementException that could occur
//                               if the enum value did not exist.
// [2/07/20] [Nichole Maldonado] added createChessPiece to create a new, specific
//                               ChessPiece.

package edu.nmaldonado2.piececreation;
import edu.nmaldonado2.chesspieces.ChessPiece;
import edu.nmaldonado2.chesspieces.Pawn;
import edu.nmaldonado2.chesspieces.Rook;
import edu.nmaldonado2.chesspieces.Knight;
import edu.nmaldonado2.chesspieces.Bishop;
import edu.nmaldonado2.chesspieces.King;
import edu.nmaldonado2.chesspieces.Queen;
import edu.nmaldonado2.chesspieces.ChessPieceTypes;

/*
 * The PieceCreator class whose objects have
 * the field piece which points to a ChessPiece.
 * Main behaviours include prepareChessPiece
 * which checks the attributes of the chess piece
 * and createChessPiece.
 */
public class PieceCreator {
    private ChessPiece piece;
    
    /*
     * Getter method which returns piece.
     * @param: None.
     * @return: The ChessPiece piece.
     */
    public ChessPiece getPiece(){
        return this.piece;
    }
    
    /*
     * Getter method which sets the field piece.
     * @param: The ChessPiece piece to be assigned
     *         to the field piece.
     * @return: None.
     */
    public void setPiece(ChessPiece piece){
        this.piece = piece;
    }
    
    /*
     * Method that checks the attributes of a piece before assigning the
     * piece field to the new ChessPiece.
     * @param: A string array with 4 elements that should be in the form of
     *        the chess piece [type, color, x-position, y-position]. Note
     *        that these attributes will be checked in the method.
     * @return: None.
     */
    public void prepareChessPiece(String[] pieceAttributes) {
        
        boolean isWhite;
        char xPosition;
        int yPosition;
        ChessPieceTypes pieceType;
        
        // To prevent index out of bounds when parsing the string.
        if(pieceAttributes[0].length() <= 2){
            return;
        }
        
        // The file is expected to have the type in the form of
        // pieceNameXY where X should correspond to the x - position and
        // Y should correspond to the y - position.
        String pieceTypeStr = pieceAttributes[0].substring(0, pieceAttributes[0].length() - 2).toUpperCase();
        
        try{
            
            // Get piece type.
            pieceType = ChessPieceTypes.valueOf(pieceTypeStr);
            
            // Get the xPosition.
            if (pieceAttributes[2].length() == 1) {
                xPosition = pieceAttributes[2].toUpperCase().charAt(0);
                
                // Get the yPosition.
                yPosition = Integer.parseInt(pieceAttributes[3]);
                
                // Check that the x and y position trailing the piece type match
                // the xPosition and yPosition.
                if(pieceAttributes[0].toUpperCase().charAt(pieceAttributes[0].length() - 2) == xPosition &&
                        Integer.parseInt(String.valueOf(
                        pieceAttributes[0].charAt(pieceAttributes[0].length() - 1))) == yPosition) {
                    
                    //Get isWhite.
                    if (pieceAttributes[1].toLowerCase().equals("white")) {
                        isWhite = true;   
                    }
                    else if (pieceAttributes[1].toLowerCase().equals("black")) {
                        isWhite = false;
                    }
                    else{
                        return;
                    }
                    
                    // Create the new, specific ChessPiece.
                    this.createChessPiece(pieceType, xPosition, yPosition, isWhite);

                }
            }            
        }
        
        // Catches exception but does not print error. If an exception is caught,
        // then it signals that the piece attribute should remain untouched.
        catch(NumberFormatException e){}
        catch(IllegalArgumentException e){}
    }
    
    /*
     * Method that creates a chess piece and assigns it to the
     * piece field.
     * @param: a ChessPieceTypes enum constant denoting the piece type, the xPosition
     *         and yPosition of the piece, and a boolean denoting whether the
     *         piece is black or white.
     * @return: None.
     */
    private void createChessPiece(ChessPieceTypes pieceType, char xPosition, 
            int yPosition, boolean isWhite) {
        
        // Creates a specific ChessPiece based on the pieceType.
        switch (pieceType) {
            case PAWN:
                this.piece = Pawn.createPawn(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            case ROOK:
                this.piece = Rook.createRook(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            case KNIGHT:
                this.piece = Knight.createKnight(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            case QUEEN:
                this.piece = Queen.createQueen(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            case BISHOP:
                this.piece = Bishop.createBishop(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            case KING:
                this.piece = King.createKing(pieceType.formatName(), xPosition, yPosition, isWhite);
                break;
            default:
                break;
        }
    }
}