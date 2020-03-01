// Nichole Maldonado
// CS331 - Lab 4, FileWriter Class

/*
 * The FileWritter class solely writes changes to the
 * xml file. These changes can include updating the
 * user game key, creating a new game segment, 
 * removing an old game, or updating a current game.
 */

// changelog
// [2/28/20] [Nichole Maldonado] added getters and setters for filePathChess and filePathUsers
// [2/29/20] [Nichole Maldonado] created writeNewGame, updateGame, and removeOldGame
//                               to modfiy the relationship between the xml files and the game.
// [2/29/20] [Nichole Maldonado] refactored redundant code into a method that
//                               creates an chess template without pieces.
// [2/29/20] [Nichole Maldonado] created sendToFile which saves changes to the xml file.
// [3/01/20] [Nichole Maldonado] fixed possible null pointer exceptions in the
//                               writeNewGame, updateGame, removeOldGame functions.
// [3/01/20] [Nichole Maldonado] orderer import statements.

package utep.cs3331.lab4.files;

import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import utep.cs3331.lab4.chess.BoardDimensions;
import utep.cs3331.lab4.chess.chesspieces.ChessPiece;
import utep.cs3331.lab4.chess.GameBoard;
import utep.cs3331.lab4.chess.GameController;
import utep.cs3331.lab4.files.exceptions.ExceptionHandler;
import utep.cs3331.lab4.files.FilePaths;
import utep.cs3331.lab4.files.FileReader;
import utep.cs3331.lab4.players.ChessPlayer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/*
 * The FileWritter class solely writes changes to the
 * xml file. These changes can include updating the
 * user game key, creating a new game segment, 
 * removing an old game, or updating a current game.
 */
public class FileWriter implements ExceptionHandler {
    private FilePaths filePaths;
    
    /*
     * Default constructor that assigns filePaths
     * to its field.
     * Input: The filePaths object.
     * Output: None.
     */
    public FileWriter(FilePaths filePaths){
        this.filePaths = filePaths;
    }
    
    /*
     * Getter for the filePaths attribute.
     * Input: None.
     * Output: The filePaths.
     */
    public FilePaths getFilePaths() {
        return this.filePaths;
    }
    
    /*
     * Setter for the filePaths attribute.
     * Input: The new filePaths to be assigned to the current field.
     * Output: None.
     */
    public void setFilePaths(FilePaths filePaths) {
        this.filePaths = filePaths;
    }
    
    /*
     * Method that writes a player to an xml file.
     * Input: The player whose attributes will be written to.
     * Output: A unique id that kill represent the key that unites
     *         the game and the user.
     * A FileNotFoundException may be thrown. The program should terminate
     * if so since it is unknown whether then user will re-input the file
     * that has already been searched for the player.
     */
    public String storePlayer(ChessPlayer player, Scanner input) throws FileNotFoundException{
        String timeStamp = null;
        try {
            // Read the XML file
            File inputFile = new File(this.filePaths.getFilePathUsers());
            FileInputStream fileStream = new FileInputStream(inputFile);
            
            // Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();
          
            //Create a DOM tree Obj
            Document configFile = saxBuilder.build(inputFile);
            
            // Adds user tag to xml.
            Element userInfo = configFile.getRootElement();
            Element user = new Element("user");
            userInfo.addContent(user);
            
            // Creates xml elements and copies information from player.
            user.addContent(new Element("name"));
            user.addContent(new Element("expertise_level"));
            user.addContent(new Element("user_color"));
            user.addContent(new Element("user_game_key"));
            
            user.getChild("name").setText(player.getName());
            user.getChild("expertise_level").setText(player.getExpertiseLevel().formatName());
            user.getChild("user_color").setText(player.getUserColor().formatName());
            timeStamp = this.createId();
            user.getChild("user_game_key").setText(timeStamp);
            
            fileStream.close();
            this.sendToFile(configFile, inputFile, input);
        }
        
        // Allow users to recall the the method in the event an exception occured.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return storePlayer(player, input);
            }
            return null;    
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                return storePlayer(player, input);
            }
            return null;
        }
        return timeStamp;
    }
    
    /*
     * Creates an id based on the current time stamp.
     * Input: None.
     * Output: a unique id.
     */
    public static String createId(){
        return new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
    }
    
    /*
     * Method that creates a game template with board and game elements only.
     * Input: The chess element that will house the new elements
     *        and the controller which will be used to populate some of the fields.
     * Output: None.
     */
    public void createNoPieceGameTemplate(Element chess, GameController controller) {
        chess.addContent(new Element("board"));
        chess.addContent(new Element("game"));

        chess.getChild("board").addContent(new Element("rows"));
        chess.getChild("board").addContent(new Element("columns"));
        chess.getChild("board").getChild("rows").setText(Integer.toString(
                controller.getBoard().getNumRows()));
        chess.getChild("board").getChild("columns").setText(Integer.toString(
                controller.getBoard().getNumColumns()));

        chess.getChild("game").addContent(new Element("id"));
        chess.getChild("game").addContent(new Element("max_time"));
        chess.getChild("game").addContent(new Element("chat"));
        chess.getChild("game").addContent(new Element("auto_save"));
        chess.getChild("game").getChild("id").setText(controller.getGame().getId());
        chess.getChild("game").getChild("max_time").setText(Integer.toString(
                controller.getGame().getMaxTime()));
        chess.getChild("game").getChild("chat").setText(
                (controller.getGame().getHasChat()) ? "enable" : "disable");
        chess.getChild("game").getChild("auto_save").setText(
                (controller.getGame().getUseAutoSave()) ? "active" : "inactive");
    }
    
    /*
     * Method that writes a new chess game to an xml file
     * while simultaneously initializing all the pieces in the game.
     * Input: The game controller that contains the game board and
     *        a scanner for user input.
     * Output: None.
     */
    public boolean writeNewGame(GameController controller, Scanner input) {
        try {
            //read the XML file
            File inputFile = new File(this.filePaths.getFilePathChess());
            FileInputStream fileStream = new FileInputStream(inputFile);

            //Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a DOM tree Obj
            Document configFile = saxBuilder.build(inputFile);
            
            Element root = configFile.getRootElement();
            Element chess = new Element("chess");
            root.addContent(chess);
            Element pieces = new Element("pieces");
            chess.addContent(pieces);
            
            // Adds the pieces
            controller.newGame(pieces);
            
            // Adds game and board elements.
            controller.getGame().setTimeChatSave(input);
            this.createNoPieceGameTemplate(chess, controller);
            
            fileStream.close();
            this.sendToFile(configFile, inputFile, input);
        }
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return this.writeNewGame(controller, input);
            }
            return false;
        } 
        catch (FileNotFoundException e) {
            if (ExceptionHandler.handleException("trying to find the chess xml file", "Enter new file", input)) {
                this.filePaths.collectFilePath(input, "chess");
                return this.writeNewGame(controller, input);
            }
            return false;
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                return this.writeNewGame(controller, input);
            }
            return false;
        }
        return true;
    }
    
    /*
     * Method that retrieves the name of the chess game with the
     * provided id.
     * Input: the id of the chess game to be found and a list
     *        of chess game elements.
     * Output: the name of the game if found, or null otherwise.
     */
    private String getChessGameName(String id, List<Element> chessGames) {
        for (Element chess : chessGames) {
            try {
                if (chess.getChild("game").getChild("id").getText().equals(id)) {
                    return chess.getQualifiedName();
                }
            }
            
            // In event game or game id does not exist, invalid so remove game entry.
            catch (NullPointerException e) {
                Element root = chess.getParentElement();
                root.removeContent(chess);
            }
        }
        return null;
    }
    
    /*
     * Method that removes the game with the provided id.
     * Input: the id of the game to be removed.
     * Output: false if the user wants to quit the game as a
     * .       result of the exceptions thrown, or true otherwise.
     */
    public boolean removeOldGame(String id, Scanner input) {
        try {
            //read the XML file
            File inputFile = new File(this.filePaths.getFilePathChess());
            FileInputStream fileStream = new FileInputStream(inputFile);

            //Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a DOM tree Obj
            Document configFile = saxBuilder.build(inputFile);
            
            Element root = configFile.getRootElement();
            List<Element> rootChildren = root.getChildren();
            
            String gameName = getChessGameName(id, rootChildren);
            
            if (gameName != null) {
                root.removeChild(gameName);
            }
            
            fileStream.close();
            this.sendToFile(configFile, inputFile, input);
        }
        
        // Retries in the event of an error.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return this.removeOldGame(id, input);
            }
            return false;
        } 
        catch (FileNotFoundException e) {
            if (ExceptionHandler.handleException("trying to find the chess xml file", "Enter new file", input)) {
                this.filePaths.collectFilePath(input, "chess");
                return this.removeOldGame(id, input);
            }
            return false;
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                return this.removeOldGame(id, input);
            }
            return false;
        }
        return true;
    }
    
    private void findUser(String newUserGameKey, String oldUserGameKey, List<Element> users) {
        for (Element user : users) {
            try {
                Element gameKeyElement = user.getChild("user_game_key");
                if (gameKeyElement.getText().equals(oldUserGameKey)) {
                    gameKeyElement.setText(newUserGameKey);
                    return;
                }
            } 
            // In event user game key does not exist, invalid so remove game entry.
            catch (NullPointerException e) {
                Element root = user.getParentElement();
                root.removeContent(user);
            }
        }
    }
    
    /*
     * Method that updates user with new game key.
     * Input: The new game key that will replace the old key
     * .      and a scanner for user input.
     * Output: false if the user decided to quit the game in the
     *         event of an exception, or true otherwise.
     */
    public boolean updateUserGameKey(String newUserGameKey, String oldUserGameKey, Scanner input) {
        try {
            // Read the XML file
            File inputFile = new File(this.filePaths.getFilePathUsers());
            FileInputStream fileStream = new FileInputStream(inputFile);

            //Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a DOM tree Obj
            Document configFile = saxBuilder.build(inputFile);
            
            Element root = configFile.getRootElement();
            List<Element> rootChildren = root.getChildren();
            
            // Change unique user game key.
            findUser(newUserGameKey, oldUserGameKey, rootChildren);
            
            fileStream.close();
            this.sendToFile(configFile, inputFile, input);
        }
        
        // Retries in the event of an error.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return this.updateUserGameKey(newUserGameKey, oldUserGameKey, input);
            }
            return false;
        } 
        catch (FileNotFoundException e) {
            if (ExceptionHandler.handleException("trying to find the chess xml file", "Enter new file", input)) {
                this.filePaths.collectFilePath(input, "user");
                return this.updateUserGameKey(newUserGameKey, oldUserGameKey, input);
            }
            return false;
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                return this.updateUserGameKey(newUserGameKey, oldUserGameKey, input);
            }
            return false;
        }
        return true;
    }
    
    /*
     * Method that creates a piece element parsing the
     * color, x and y location, piece type, and whether it can play
     * from a string in the format of "name + xPosition + yPosition".
     * Input: a string in the format of "name + xPosition + yPosition"
     *        and a boolean that denotes the piece color.
     * Output: an Element with the specified attributes.
     */
    public static Element createPiece(String chessPieceName, boolean isWhite) {
        Element piece = new Element("piece");
        piece.setAttribute("color", (isWhite) ? "white" : "black");
        int length = chessPieceName.length();
        piece.setAttribute("locationX", chessPieceName.substring(length - 2, length - 1));
        piece.setAttribute("locationY", chessPieceName.substring(length - 1, length));
        piece.setAttribute("play", "true");
        piece.setText(chessPieceName.substring(0, length - 2).toLowerCase());
        return piece;
    }
    
    /*
     * Method that writes the changes made to the
     * document to the actual file.
     * Input: the document and the file to be written to.
     * Output: None.
     */
    public void sendToFile(Document configFile, File inputFile, Scanner input) {
        try {
            XMLOutputter xmlOutput = new XMLOutputter();

            xmlOutput.setFormat(Format.getPrettyFormat());
            FileOutputStream stream = new FileOutputStream(inputFile);
            xmlOutput.output(configFile, stream);
            stream.close();
            
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                this.sendToFile(configFile, inputFile, input);
            }
        }
    }
    
    /*
     * Method that writes chess pieces stored in chess board to 
     * pieces, the parent elements.
     * Input: the chessBoard with all the chess pieces and
     *        the parent, pieces.
     * Output: None.
     */
    private void boardToFile(ChessPiece[][] chessBoard, Element pieces) {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] != null) {
                    Element piece = createPiece(chessBoard[i][j].getName(), chessBoard[i][j].getIsWhite());
                    pieces.addContent(piece);
                }
            }
        }
    }
    
    /*
     * Method that finds the chess pieces for a game with the given id.
     * Input: the id to search for and the list of chess elements to
     *        be searched through.
     * Output: The chess pieces if they are found, or null otherwise.
     */
    private Element findChessPieces(String id, List<Element> chess) {
        for (Element chessGame : chess) {
            try {
                if (chessGame.getChild("game").getChild("id").getText().equals(id)) {
                    return chessGame.getChild("pieces");
                }
            }
            
            // In event game or game id does not exist, invalid so remove game entry.
            catch (NullPointerException e) {
                Element root = chessGame.getParentElement();
                root.removeContent(chessGame);
            }
            
        }
        return null;
    }
    
    /*
     * Method that updates the xml file to reflect the new file changes.
     * Input: the GameController which house the game and game board.
     * Output: None.
     */
    public void updateGame(GameController controller, Scanner input) {
        try {
            // Read the XML file
            File inputFile = new File(this.filePaths.getFilePathChess());
            FileInputStream fileStream = new FileInputStream(inputFile);

            //Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a DOM tree Obj
            Document configFile = saxBuilder.build(inputFile);
            
            Element chessGame = configFile.getRootElement();
            List<Element> chessElements = chessGame.getChildren();
            
            // Change unique user game key.
            Element pieces = findChessPieces(controller.getGame().getId(), chessElements);
            
            // Create new template if game does not exist.
            if (pieces == null) {
                Element chess = new Element("chess");
                chessGame.addContent(chess);
                pieces = new Element("pieces");
                chess.addContent(pieces);
                this.createNoPieceGameTemplate(chess, controller);
            }
            // Otherwise remove all the old pieces.
            else {
                pieces.removeChildren("piece");
            }
            
            // Sync file with the new board changes.
            this.boardToFile(controller.getBoard().getChessBoard(), pieces);
            
            fileStream.close();
            this.sendToFile(configFile, inputFile, input);
        }
        
        // Retries in the event of an error.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                this.updateGame(controller, input);
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("The file could not be found to update the chess game");
            System.out.println("No further action can be taken, so the game will not be saved.");
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                this.updateGame(controller, input);
            }
        }
    }
}