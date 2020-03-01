// Nichole Maldonado
// CS331 - Lab 3, FileReader Class

/*
 * This class file contains the FileReader class
 * with fields filePath, validPieces, and invalidPieces.
 * The class is mainly used to read a file and store
 * successfully created ChessPiece derived class objects
 * in the validPieces array.
 */

// changelog
// [2/07/20] [Nichole Maldonado] created FileReader class to read the file and
//                               store the successfully created ChessPiece derived
//                               class objects.
// [2/07/20] [Nichole Maldonado] added retrieveAndAllocatePieces which was modeled after
//                               the same function from Lab1.
// [2/07/20] [Nichole Maldonado] added collectFilePath to get the file path from the user
//                               immediately.
// [2/07/20] [Nichole Maldonado] fixed bug with using multiple scanners throughout program
//                               by passing a scanner to collectFilePath.
// [2/07/20] [Nichole Maldonado] changed the ArrayList from storing Generic type T objects
//                               to ChessPiece derived class objects since the FileReader class
//                               is using the PieceCreator class.
// [2/09/20] [Nichole Maldonado] added printUnevaluated method to print the invalidPieces.

package utep.cs3331.lab4.files;
//import edu.nmaldonado2.chesspieces.ChessPiece;
//import edu.nmaldonado2.piececreation.PieceCreator;


import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.util.Hashtable;
import java.io.FileInputStream;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import utep.cs3331.lab4.files.FilePaths;
import utep.cs3331.lab4.files.FileWriter;
import utep.cs3331.lab4.files.XmlFileConfigs;
import utep.cs3331.lab4.chess.GameBoard;
import utep.cs3331.lab4.chess.GameController;
import utep.cs3331.lab4.players.ChessPlayer;
import utep.cs3331.lab4.files.UserCreator;
import utep.cs3331.lab4.files.DomEditor;
import utep.cs3331.lab4.files.exceptions.ExceptionHandler;
import utep.cs3331.lab4.chess.BoardDimensions;
import utep.cs3331.lab4.chess.chesspieces.ChessPieceTypes;
/*
 * The class FileReader contains the fields
 * filePath, validPieces, and invalidPieces
 * The main behaviours include retrieve and
 * allocate pieces which reads the files and
 * strips each line of the piece attributes.
 * The FileReader class relies on the PieceCreator
 * class to create a ChessPiece's derived class object
 * with the attributes. These objects are stored in validPieces.
 */
public class FileReader implements ExceptionHandler, XmlFileConfigs {
    private FilePaths filePaths;
    private ChessPlayer player;
    private String userGameKey;
    //private Element chessGame;
//    private DomEditor editor;
    
    /*
     * Default constructor that assigns each field to a
     * new ArrayList of the designated type.
     * @param: None.
     * @return: None.
     */
    public FileReader() {
        filePaths = new FilePaths();
    }
    
    public ChessPlayer getPlayer() {
        return player;
    }
    
    /*
     * Getter function that returns the userGameKey
     * attribute.
     * Input: None.
     * Output: a string of the userGameKey.
     */
    public String getUserGameKey() {
        return this.userGameKey;
    }
    
    public void setUserGameKey(String userGameKey) {
        this.userGameKey = userGameKey;
    }
    
//    public DomEditor getDomEditor() {
//        return this.editor;
//    }
//    
//    public void setDomEditor() {
//        this.editor = null;
//    }
    
    /*
     * Getter method for the field filePath
     * @param: None.
     * @return: a string of a filePath.
     */
    public FilePaths getFilePaths() {
        return this.filePaths;
    }
    
    /*
     * Method that returns the user with the corresponding
     * userName.
     * Input: the user's name to find and a list of users.
     * Output: The user with the userName if found, or null otherwise.
     */
    private Element retrieveUserByName(String userName, List<Element> users) {
        for (Element user : users) {
            Element userNameElement = user.getChild("name");
            if (userNameElement != null && userNameElement.getValue().equals(userName)) {
                return user;   
            }
        }
        return null;
    }
    
    /* Method that finds an existing user based on the 
     * the user name collected.
     * Input: Scanner to retrieve user input.
     * Output: True if a user name was finally found.
     *         false is returned otherwise.
     */
    public boolean findExistingUser(Scanner input) {
        String userName = UserCreator.getUserName(input);
        
        try {
            // Read the XML file
            File inputFile = new File(this.filePaths.getFilePathUsers());
            FileInputStream fileStream = new FileInputStream(inputFile);

            //Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a DOM tree Obj
            Document configFile = saxBuilder.build(fileStream);
            
            // Get the root element
            Element root = configFile.getRootElement();
            
            // If the root is not null, continue to parse the file
            // to try to find the user.
            if (root != null) {
                
                List<Element> users = root.getChildren();
                
                // Finds user if they exist.
                Element user = retrieveUserByName(userName, users);
                
                // If the user was successfully found, create and store
                // the user and game key.
                if (user != null) {
                    this.player = UserCreator.createExistingPlayer(userName, input, user);
                    this.userGameKey = user.getChild("user_game_key").getValue();
                    fileStream.close();
                    return true;
                }
            }
            
            fileStream.close();
        } 
        
        // Allows user to redo method if an exception occured.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return findExistingUser(input);
            }
            return false;
        } 
        catch (FileNotFoundException e) {
            if (ExceptionHandler.handleException("trying to find the user xml file", "Enter new file", input)) {
                
                // Update user file path and recall the method.
                this.filePaths.collectFilePath(input, "user");
                return findExistingUser(input);
            }
            return false;
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                return findExistingUser(input);
            }
            return false;
        }
        
        // Re-enter user name in worst case.
        System.out.println("The specified username could not be found");
        if (UserCreator.reEnterUserName(input)){
            return findExistingUser(input);
        }
        return false;
    }
    
    /*
     * Method that populates a hash table with users and 
     * returns true if the userName was never found.
     * Input: the user's name to find, a list of users,
     *        and a potential hash table to store the users.
     * Output: The user with the userName if found, or null otherwise.
     */
    private boolean userDoesNotExist(String userName, List<Element> users, Hashtable<String, String> userStorage) {
        boolean userNotFound = true;
        
        for (Element user : users) {
            Element userNameElement = user.getChild("name");
            if (userNameElement != null) {
                
                // Hashes users in case a user is found. In which case, the user names
                // will need to be compared with the new user names that the user selects.
                userStorage.put(userNameElement.getValue(), userNameElement.getValue());    
                
                if (userNameElement.getValue().equals(userName)) {
                    userNotFound = false;   
                }  
            }                   
        }
        return userNotFound;
    }
    
    /*
     * Method that reads an xml file to ensure that a user name
     * does not already exist before creating the player.
     * Input: The scanner to retrieve the user input.
     * Output: None.
     */
    public boolean createPlayerFromXML(Scanner input) {
        String userName = UserCreator.getUserName(input);
        
        Hashtable<String, String> userHash = new Hashtable<String, String>();
        
        try {
            
            // Read the XML file
            File inputFile = new File(this.filePaths.getFilePathUsers());
            FileInputStream fileStream = new FileInputStream(inputFile);

            // Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            // Create a DOM tree Obj
            Document configFile = saxBuilder.build(fileStream);

            // Get the root userInfo element
            Element userInfo = configFile.getRootElement();
            
            // Blank file, so prepare to create user.
            if (userInfo == null) {
                return true;
            }
            
            List<Element> userElements= userInfo.getChildren();
            boolean foundMatchingName = false;
            
            boolean userNotFound = userDoesNotExist(userName, userElements, userHash);
            
            // If the user was found, then the discrepcies among the user needs
            // to be fixed.
            if (!userNotFound) {
                userName = UserCreator.resolveMatchingUserNames(userHash, input);
            }
            
            // If userName is not null, then a new user is ready to be created.
            if (userName != null) {
                this.player = UserCreator.createPlayer(userName, input);
            }
            
            // If userName is null, then user wants to quit.
            else {
                return false;
            }

        } 
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return createPlayerFromXML(input);
            }
            return false;
        } 
        catch (FileNotFoundException e) {
            if (ExceptionHandler.handleException("trying to find the user xml file", "Enter new file", input)) {
                this.filePaths.collectFilePath(input, "user");
                return createPlayerFromXML(input);
            }
            return false;
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                return createPlayerFromXML(input);
            }
            return false;
        }
        return true;
    }
    
    /*
     * Method that returns the parent element of the game with the
     * provided id.
     * Input: the id to be searched for the chess games to 
     *        searched through.
     * Output: the parent of the game with the provided id if found or
     *         null otherwise.
     * A NullPointer exception may occur if the chess game does not have
     * a game or id element.
     */
    private Element findChessPiecesParent(String id, List<Element> chess) throws NullPointerException {
        for (Element chessGame : chess) {
            if (chessGame.getChild("game").getChild("id").getText().equals(id)) {
                return chessGame;
            }
        }
        return null;
    }
    
    // returns true if board was populated correctly.
    private boolean populateBoard(GameBoard board, List<Element> chessPieces) {
        for (Element piece : chessPieces) {

            boolean isWhite;
            char xPosition;
            int yPosition;
            ChessPieceTypes pieceType;

            // The file is expected to have the type in the form of
            // pieceNameXY where X should correspond to the x - position and
            // Y should correspond to the y - position.
            String pieceTypeStr = piece.getText().toUpperCase();

            try {

                // Get piece type.
                pieceType = ChessPieceTypes.valueOf(pieceTypeStr);

                // Get the xPosition.
                if (piece.getAttribute("locationX").getValue().length() == 1) {
                    xPosition = piece.getAttribute("locationX").getValue().toUpperCase().charAt(0);

                    if (xPosition < BoardDimensions.MIN_X_POSITION || xPosition > BoardDimensions.MAX_X_POSITION) {
                        return false;
                    }

                    // Get the yPosition.
                    yPosition = Integer.parseInt(piece.getAttribute("locationY").getValue());

                    if (yPosition < BoardDimensions.MIN_Y_POSITION || yPosition > BoardDimensions.MAX_Y_POSITION) {
                        return false;
                    }

                    //Get isWhite.
                    if (piece.getAttribute("color").getValue().toLowerCase().equals("white")) {
                        isWhite = true;   
                    }
                    else if (piece.getAttribute("color").getValue().toLowerCase().equals("black")) {
                        isWhite = false;
                    }
                    else {
                        System.out.println("in here");
                        return false;
                    }

                    // Pawn piece can only move forward. Thus, a white pawn can not
                    // exist at row 1 and a black pawn cannot exist at row 8.
                    if (pieceType == ChessPieceTypes.PAWN && isWhite && yPosition == BoardDimensions.MIN_Y_POSITION) {
                        return false;
                    }
                    if(pieceType == ChessPieceTypes.PAWN && !isWhite && yPosition == BoardDimensions.MAX_Y_POSITION) {
                        return false;
                    }

                    // Create the new, specific ChessPiece.
                    board.addChessPiece(pieceType, xPosition, yPosition, isWhite);
                } 
                else {
                    return false;
                }
            }

            // Catches exception but does not print error. If an exception is caught,
            // then it signals that the piece attribute should remain untouched.
            catch(NumberFormatException e){
                return false;
            }
            catch(IllegalArgumentException e){
                return false;
            }
        }
        return true;
    }
    
    /*
     * Method that updates the controller's game attributes based 
     * on the game element.
     * Input: The controller, whose game attributes will be updated,
     *        and the game element.
     * Output: true if the element did not contain any false values
     *         and was able to update the controller successfully, 
     *         or false is otherwise returned.
     */
    private boolean fileUpdateController(Element game, GameController controller) {
        
        // Check and update autosave.
        try {
            String autoSaveLabel = game.getChild("auto_save").getText().toLowerCase();
            
            // Auto-save can only be active or inactive.
            boolean useAutoSave = false;
            if (autoSaveLabel.equals("active")) {
                useAutoSave = true;
            }
            else if (!autoSaveLabel.equals("inactive")) {
                System.out.println("Auto save can only be active or inactive.");
                return false;
            }
            controller.getGame().setUseAutoSave(useAutoSave); 
        }
        catch (NullPointerException e) {
            System.out.println("A problem occured while loading your auto-save setting. Auto-save defaults to inactive.");
            controller.getGame().setUseAutoSave(false);
        }
        
        // Check and update max time.
        try {
            int maxTime = Integer.parseInt(game.getChild("max_time").getText());
            
            // maxTime can only be in the range [1, 100].
            if (maxTime < 1 || maxTime > 100) {
                System.out.println("Max time can only be in the range from [1, 100]");
                return false;
            }
            controller.getGame().setMaxTime(maxTime); 
        }
        catch (NullPointerException e) {
            System.out.println("A problem occured while loading your max-time setting. Max-time defaults to 30 minutes.");
            controller.getGame().setMaxTime(30);
        }
        
        // Check and update chat.
        try {
            String hasChatLabel = game.getChild("chat").getText().toLowerCase();
            boolean hasChat = false;
            if (hasChatLabel.equals("enable")) {
                hasChat = true;
            }
            else if (!hasChatLabel.equals("disable")) {
                System.out.println("Chat can only be disable or enable");
                return false;
            }
            controller.getGame().setHasChat(hasChat); 
        }
        catch (NullPointerException e) {
            System.out.println("A problem occured while loading your chat setting. Chats defaults to disabled.");
            controller.getGame().setHasChat(false);
        }
        return true;
    }
    
    /*
     * Method that returns true if the board does not contain
     * 8 rows and 8 columns.
     * Input: The board element to be evaluated.
     * Output: None.
     */
    private boolean invalidBoardSize(Element board) {
        return !board.getChild("rows").equals("8") || !board.getChild("columns").equals("8");
    }
    
    /*
     * Method that creates the game board based on an id. 
     * Input: The controller which contains the id and board,
     *        a scanner to retrieve input, and the file writer
     *        to make changes to the xml in the event that improper xml structure
     *        or exceptions occur.
     * Output: false if the user decided to quit the game due to an exception, or
     *         true otherwise.
     */
    public boolean fileToBoard(GameController controller, Scanner input, FileWriter fileWriter) {

        try {
            
            // Read the XML file
            File inputFile = new File(this.filePaths.getFilePathChess());
            FileInputStream fileStream = new FileInputStream(inputFile);

            //Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a DOM tree Obj
            Document configFile = saxBuilder.build(fileStream);
            
            // Get the root element
            Element chessGame = configFile.getRootElement();
            List<Element> rootChildren = chessGame.getChildren();
            
            Element chess = findChessPiecesParent(controller.getGame().getId(), rootChildren);
        
            if (chess == null) {
                System.out.println("The loaded game does not exist.");
                System.out.println("A new game will be created.");
                fileStream.close();
                fileWriter.writeNewGame(controller, input);
                return true;
            }
            
            // If the board contained improper syntax,the board will be erased.
            if (invalidBoardSize(chess.getChild("board")) || 
                    !populateBoard(controller.getBoard(), chess.getChild("pieces").getChildren("piece")) ||
                    this.fileUpdateController(chess.getChild("game"), controller)) {
                
                System.out.println("The loaded game contained incorrect syntax.");
                System.out.println("The game will be overwritten.");
                fileStream.close();
                fileWriter.removeOldGame(controller.getGame().getId(), input);
                fileWriter.writeNewGame(controller, input);
            }      
            
            fileStream.close();
        } 
        
        // Recalls the method if an exception occurs.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return fileToBoard(controller, input, fileWriter);
            }
            return false;
        } 
        
        // Changes file path if the user user decides to.
        catch (FileNotFoundException e) {
            if (ExceptionHandler.handleException("trying to find the chess xml file", "Enter new file", input)) {
                this.filePaths.collectFilePath(input, "user");
                return fileToBoard(controller, input, fileWriter);
            }
            return false;
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                return fileToBoard(controller, input, fileWriter);
            }
            return false;
        }
        catch (NullPointerException e) {
            System.out.println("The syntax of the game was incorrect or missing element.");
            System.out.println("The game will be overwritten with a new game.");
            
            fileWriter.removeOldGame(controller.getGame().getId(), input);
            fileWriter.writeNewGame(controller, input);
        }
        return true;
    }
}