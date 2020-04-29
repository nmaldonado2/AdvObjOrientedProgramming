// Nichole Maldonado
// CS331 - Lab 4, Maldonado_Nichole_Lab4

/*
 * Class that drives the program and initiates the reading of
 * user and chess xml files as well as starting the game
 * controller which will start the game.
 *
 * The program first starts by retrieving the user xml file which
 * houses user data and the chess xml file which houses the chess pieces,
 * board, and game. Users are then asked if they are an existing user or
 * new user. Then the program will allow the user to load
 * a game or create a new one. Afterwards, the user can move the pieces
 * and leave the game when ready. The users are given the option to save
 * their game at the end if autosave is disabled.
 */

// changelog
// [2/28/20] [Nichole Maldonado] created main program class and created
//                               a FileReader and FileWriter to allow for
//                               the correspondence between the game dispalyed and
//                               the one stored in the xml file.
// [3/1/20] [Nichole Maldonado] moved code to retrieve game attributes to the game class.  
// [3/1/20] [Nichole Maldonado] refactored code in main to more logical code blocks.
// [4/25/20] [Nichole Maldonado] changed package to lab5.

/* javac -d . DiagonalMoveValidator.java ExceptionHandler.java ExpertiseLevel.java FilePaths.java MinimumBoardDimensions.java ChessPiece.java ChessPieceTypes.java Pawn.java Rook.java Knight.java Queen.java Knight.java King.java  Bishop.java ChessPlayer.java ParserWriter.java UserCreator.java GameController.java Game.java ParserReader.java Parser.java GameBoard.java
Nicholes-MacBook-Pro:Lab5 nichole_maldonado$ javac -d . DiagonalMoveValidator.java ExceptionHandler.java ExpertiseLevel.java FilePaths.java MinimumBoardDimensions.java ChessPiece.java ChessPieceTypes.java Pawn.java Rook.java Knight.java Queen.java Knight.java King.java  Bishop.java ChessPlayer.java ParserWriter.java UserCreator.java GameController.java Game.java ParserReader.java Parser.java GameBoard.java Maldonado_Nichole_Lab5.java 


export CLASSPATH=$CLASSPATH:/Users/nichole_maldonado/Desktop/Lab5/jdom-2.0.6.jar
*/
package utep.cs3331.lab5.chess;

import java.util.Scanner;

import utep.cs3331.lab5.chess.Controller;


/*
 * Class that drives the program and initiates the reading of
 * user and chess xml files as well as starting the game
 * controller which will start the game.
 */
public class Maldonado_Nichole_Lab5 {
    
    /*
     * Main method that initiates the start of the program.
     * @param: None.
     * @return: None.
     */
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Controller controller = new Controller();
        controller.runChessInterface(input);
    }
}
