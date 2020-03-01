package utep.cs3331.lab4.chess;

import java.util.Scanner;
import java.util.InputMismatchException;

import utep.cs3331.lab4.chess.BoardDimensions;
import utep.cs3331.lab4.chess.Game;
import utep.cs3331.lab4.chess.GameBoard;
import utep.cs3331.lab4.players.ChessPlayer;

import org.jdom2.Element;

public class GameController implements BoardDimensions{
    private GameBoard board;
    private ChessPlayer player;
    private Game game;
    
    public GameController(ChessPlayer player, String id) {
        this.board = new GameBoard();
        this.game = new Game(id);
        this.player = player;
    }
    
    public GameBoard getBoard() {
        return this.board;
    }
    
    public Game getGame() {
        return this.game;
    }
    
    /*
     * Method that creates a new game with pieces in initial positions.
     * Input: The element pieces that will house the individual
     *        pieces created.
     * Output: None.
     */
    public void newGame(Element pieces) {
        this.board.createNewGameBoard(pieces);
    }
    
        /*
     * Method that returns a valid xPosition from 'A' to 'H'
     * based on the user's input. 0 is returned otherwise.
     * @param: the scanner to retrieve the input from the user.
     * @return: a character denoting the xPosition.
     */ 
    private static char retrieveXPosition(Scanner input) {
        boolean foundX = false;
        char xPosition = 0;

        while (!foundX) {
            System.out.print("Enter the x - position (A - H): ");
            String xPositionStr = input.nextLine().replaceAll("\\s+", "");

            if (xPositionStr.length() != 1) {
                System.out.print("Invalid x-position. The x-position can only be one character.");
                System.out.println("Please try again.");
            }
            else {
                // Converts position to character.
                xPosition = xPositionStr.toUpperCase().charAt(0);

                // xPosition must be in the chess board's range.
                if (xPosition < MIN_X_POSITION || 
                xPosition > MAX_X_POSITION) {
                    System.out.print("Invalid x-position. The x-position must be in the range A to H.");
                    System.out.println("Please try again.");
                }
                else {
                    foundX = true;
                }
            }
        }
        
        
        return xPosition;
    }
    
    //TODO : Throwing input mismatch and somehow not catching.
    /*
     * Method that returns a valid yPosition from 1 to 8.
     * based on the user's input. -1 is returned otherwise.
     * @param: the scanner to retrieve the input from the user.
     * @return: an integer denoting the yPosition.
     */
    private static int retrieveYPosition(Scanner input) {
        boolean foundY = false;
        int yPosition = 0;
        
        while (!foundY) {
            System.out.print("Enter the y - position (1 - 8): ");
            
            try {
                yPosition = input.nextInt();
                input.nextLine();
                System.out.println();

                // yPosition must be in the chess board's range.
                if (yPosition < MIN_Y_POSITION || 
                        yPosition > MAX_Y_POSITION) {

                    System.out.print("Invalid y-position. The y-position must in the range ");
                    System.out.println("of 1 to 8. Please try again.");
                }
                else {
                    foundY = true;
                }
            } catch(InputMismatchException e) {
                input.nextLine();
                System.out.println("Invalid input. The y-position can only be an integer. Please try again");
            }
        }
        
    
        return yPosition;
    }
    
    public void verifyAndInitiateMove(int xPosition, int yPosition, char newXPosition, int newYPosition) throws NullPointerException {
        
        String canMoveNotifier = "";
        boolean canMove = false;
        
        int newXPositionValue = Integer.parseInt(String.valueOf(newXPosition - 'A'));
        
        // If a chess piece does not exist at the new position, then validate the move.
        if (this.board.getChessBoard()[newYPosition - 1][newXPositionValue] == null) {
            canMove = this.board.getChessBoard()[yPosition][xPosition].validMove(newXPosition, newYPosition); 
        }
        
        // Otherwise, you cannot move the piece.
        else {
            System.out.print("A chess piece already exists at the new position, so the piece ");
            System.out.println(" cannot move to that position.");
            return;
        }
        if (!canMove) {
            canMoveNotifier = "NOT ";
        }
        System.out.printf("\n%s can %s move to %c, %d\n\n", this.board.getChessBoard()[yPosition][xPosition].getName(), 
                canMoveNotifier, newXPosition, newYPosition);
        if(canMove) {
            this.board.getChessBoard()[yPosition][xPosition].move(newXPosition, newYPosition);
            this.board.movePieces(xPosition, yPosition, newXPositionValue, newYPosition - 1);
        }
    }
    
    public boolean quitGame(Scanner input) {
        int selection = -1;
        while (selection != 1 && selection != 2) {
            System.out.println("Would you like to continue playing");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Select 1 or 2: ");
            
            try {
                selection = input.nextInt();
                input.nextLine();
                System.out.println();
                
                if (selection != 1 && selection != 2) {
                    System.out.println("Only values 1 or 2 are allowed. Please try again.");
                }
            }
            catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Invalid input. Please try again");
            }
        }
        
        return (selection == 1) ? false : true;
    }
    
    public void startGame(Scanner input) {
        this.board.drawGameBoard();
        boolean quitPlaying = false;
        while (!quitPlaying) {
 
            System.out.println("Select the current chess piece you want to move based on its coordinates");
            char xPosition = retrieveXPosition(input);
            int yPosition = retrieveYPosition(input);
            int xPositionValue = Integer.parseInt(String.valueOf(xPosition - 'A'));
                                              
            if (this.board.getChessBoard()[yPosition - 1][xPositionValue] == null) {
                System.out.printf("A chess piece does not exist at %c%d.\n", xPosition, yPosition);
            }
            else {
                System.out.println("Enter where you would like to move the chess piece based on the coordinates of the board game.");
                char newXPosition = retrieveXPosition(input);
                int newYPosition = retrieveYPosition(input);

                try {
                    verifyAndInitiateMove(xPositionValue, yPosition - 1, newXPosition, newYPosition);
                }
                catch (NullPointerException e) {
                    System.out.println("A chess piece does not exist at the current coordintes.");
                    System.out.println("No moves will be performed");
                }
                
                this.board.drawGameBoard();
            }
            quitPlaying = quitGame(input);
        }
    }
    
    public boolean saveGame(Scanner input) {
        System.out.println("Would you like to save your game?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Select 1 or 2: ");
        int selection;
        try {
            selection = input.nextInt();
            
            System.out.println();
            if (selection != 1 && selection != 2) {
                System.out.println("Invalid menu selection. Your game will be saved.");
                selection = 1;
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Your game will be saved.");
            selection = 1;
        }
        
        input.nextLine();
        
        return (selection == 1) ? true : false;
    }
}