// Nichole Maldonado
// CS331 - Lab4, ChessConfigurations Interface

/*
 * This interface defines the minimum and maximum values
 * for the boards x and y dimensions.
 */

// changelog
// [2/13/20] [Nichole Maldonado] created BoardDimensions interface which defines
//                               the minimum and maximum x and y positions on
//                               the board.

package utep.cs3331.lab4.chess;

/*
 * BoardDimensions interface declares the
 * minimum and maximum x and y positions on the
 * alloted chess board.
 */
public interface BoardDimensions {
    public static final char MIN_X_POSITION = 'A';
    public static final char MAX_X_POSITION = 'H';
    public static final int MIN_Y_POSITION = 1;
    public static final int MAX_Y_POSITION = 8;
}