// Nichole Maldonado
// CS331 - Lab 3, ChessPieceTypes enum

/*
 * This enum file contains the ChessPieceTypes enum which defines
 * the 6 types of chess pieces. It also includes the function format
 * name which returns a String of the first letter capitalized
 * followed by lower case letters.
 */

// changelog
// [1/30/20] [Nichole Maldonado] created enum with the 6 different types of 
//                               chess pieces.
// [1/30/20] [Nichole Maldonado] created a formatName method to allow the
//                               chess pieces' classes' name fields to
//                               be assigned to a proper string.

package utep.cs3331.lab4.players;

/*
 * Enum which defines the 6 different chess pieces
 * and the method format name which returns s String of
 * the first letter capitalized followed by lower case letters.
 */
public enum Color {
    BLUE, GREEN, YELLOW, RED, BLACK, ORANGE, PURPLE, PINK, WHITE;
    
    /*
     * Method that returns a String of the current instances first
     * letter capitalized followed by lower case letters.
     * @param: None.
     * @return: a String of the current instance's first letter capitalized
     *          followed by lower case letters.
     */
    public String formatName() {
        return this.name().toLowerCase();
    }
}