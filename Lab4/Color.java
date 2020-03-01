// Nichole Maldonado
// CS331 - Lab 4, Color enum

/*
 * This enum file contains the ChessPieceTypes enum which defines
 * the 9 types of user colors. It also includes the function format
 * name which returns a String of the first letter capitalized
 * followed by lower case letters.
 */

// changelog
// [2/28/20] [Nichole Maldonado] created enum with the 9 different types of 
//                               user colors.
// [2/28/20] [Nichole Maldonado] created a formatName method to allow the
//                               colors to be displayed in lower case.

package utep.cs3331.lab4.players;

/*
 * Enum which defines the 9 different colors that the user can
 * set as their user color.
 */
public enum Color {
    BLUE, GREEN, YELLOW, RED, BLACK, ORANGE, PURPLE, PINK, WHITE;
    
    /*
     * Method that returns a String of the current instances
     * in lower case.
     * @param: None.
     * @return: a String of the current instance in lower case.
     */
    public String formatName() {
        return this.name().toLowerCase();
    }
}