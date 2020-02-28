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
import java.util.InputMismatchException;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.util.Hashtable;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import utep.cs3331.lab4.players.ChessPlayer;
import utep.cs3331.lab4.files.UserCreator;
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
public class FileReader {
    private String filePath;
    private ChessPlayer player;
    private Element chessGame;
    
    /*
     * Default constructor that assigns each field to a
     * new ArrayList of the designated type.
     * @param: None.
     * @return: None.
     */
    public FileReader() {}
    
    public ChessPlayer getPlayer() {
        return player;
    }
    
    public Element getChessGame() {
        return chessGame;
    }
    
    /*
     * Getter method for the field filePath
     * @param: None.
     * @return: a string of a filePath.
     */
    public String getFilePath() {
        return this.filePath;
    }
    
    public void findExistingUser(Scanner input) {
        String userName = UserCreator.getUserName(input);
        
        try {
            //read the XML file
            File inputFile = new File(this.filePath);

            //Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a DOM tree Obj
            Document configFile = saxBuilder.build(inputFile);

            //get the root element
            Element root = configFile.getRootElement();
            List<Element> rootChildren = root.getChildren();
            
            for (Element piece : rootChildren) {
                List<Element> usersList = piece.getChildren("user");
                for (int i = 0; i < usersList.size(); i++) {
                    if (usersList.get(i).getChild("name").getValue().toLowerCase().equals(userName.toLowerCase())) {
                        this.player = UserCreator.createExistingPlayer(userName, input, usersList.get(i));
                        this.chessGame = usersList.get(i);
                        return;   
                    }
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("The specified username was not found so a new user will be created.");
        this.player = UserCreator.createPlayer(userName, input);
    }
    
    // ask if new or existing.
    // if existing get user name
    // if not existing create user
    // then extract name from new user obj
    // now i have a string with the name in 
    // both scenarios
    
    // when creating new user get user info
    // iterate through xml file while hashing
    // if you find name, mark as found
    // but finish hashing. then ask user
    // for new names while checking hash table
    //
    
    public void writeNewUserToXML(Scanner input) {
        String userName = UserCreator.getUserName(input);
        
        Hashtable<String, Element> users = new Hashtable<String, Element>();
        
        try {
            //read the XML file
            File inputFile = new File(this.filePath);

            //Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            //Create a DOM tree Obj
            Document configFile = saxBuilder.build(inputFile);

            //get the root element
            Element root = configFile.getRootElement();
            List<Element> rootChildren = root.getChildren();
            
            boolean foundMatchingName = false;
            
            for (Element piece : rootChildren) {
                List<Element> usersList = piece.getChildren("user");
                for (int i = 0; i < usersList.size(); i++) {
                    if (usersList.get(i).getChild("name").getValue().toLowerCase().equals(userName.toLowerCase())) {
                        foundMatchingName = true;
                    }
//                    System.out.printf("%s\n", usersList.get(i).getChild("name").getValue());
                    users.put(usersList.get(i).getChild("name").getValue().toLowerCase(), piece);
                }
            }
            
            if (foundMatchingName) {
                userName = UserCreator.resolveMatchingUserNames(users, input);
            }
            if (userName != null) {
                this.player = UserCreator.createPlayer(userName, input);
            }

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("A problem occured with the input/output. Would you: ");
            System.out.println("1. Try again");
            System.out.println("2. Quit");
            try {
                int menuNum = input.nextInt();
                if (menuNum == 1) {
                    this.collectFilePath(input);
                }
                else if (menuNum != 2) {
                    throw new InputMismatchException();
                }
            }
            catch(InputMismatchException o){
                System.out.println("Invalid input. Program terminating.");
            }
        }
    }
    
    public void paseXMLFile() {
//        try {
//            //read the XML file
//            File inputFile = new File("ChessConfig.xml");
//
//            //Create a document builder
//            SAXBuilder saxBuilder = new SAXBuilder();
//
//            //Create a DOM tree Obj
//            Document configFile = saxBuilder.build(inputFile);
//
//            //get the root element
//            Element root = configFile.getRootElement();
//            List<Element> rootChildren = root.getChildren();
//            Element pieces = root.getChildren().get(0);
//            List<Element> listPieces = pieces.getChildren();
//            for (Element piece: listPieces ) {
//                System.out.println(piece.getAttributeValue("color"));
//                System.out.println(piece.getAttributeValue("locationX"));
//                System.out.println(piece.getAttributeValue("locationY"));
//                System.out.println(piece.getAttributeValue("play"));
//                System.out.println(piece.getValue()+"\n");
//            }
//            //read the board config
//            Element boardSize = root.getChild("board");
//            System.out.println(boardSize.getChild("rows").getValue() +", "+ boardSize.getChild("columns").getValue());
//
//            //read users profile
//            List<Element> Users = root.getChildren("user");
//
//            for (Element user :Users) {
//                System.out.println(user.getChild("name").getValue());
//                System.out.println(user.getChild("expertise_level").getValue());
//                System.out.println(user.getChild("user_color").getValue());
//
//            }
//
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
    }
   
    
    /* Ensures that filePath includes a .txt file at the end.
     * @param: a string of the file path that will be evaluated.
     * @return: Returns false if filePath does not end in .txt and returns
     *        true otherwise.  
     */              
    private boolean isXmlFile(String filePath) {
                
        // Returns false if the filePath does not have at least 5 letters since a
        // valid .txt file name can have a minimum of 5 letters.
        if (filePath.length() < 5) {
            System.out.print("\nInvalid file. The file must be a .xml file with at ");
            System.out.println("least a one character name.");
            return false;
        }
        
        // If the length of filePath is greater than or equal to 5, then true is
        // returned only if filePath ends in ".txt".
        if (filePath.substring(filePath.length() - 4).equals(".xml")) {
            return true;
        }
                
        System.out.println("\nInvalid file. The file must be a .xml file.");
        return false;
    }
    
//    public void setDefaultFilePath(String errorMessage){
//        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
////        Path currentRelativePath = Paths.get("");
//        this.filePath = path.toString();
//        this.filePath = this.filePath.substring(0, this.filePath.length() - 1) + "chessinfo.xml";
//        
//        System.out.printf("Due to %s, a new XML document will be created in: %s\n", errorMessage, this.filePath);
//    }
    
    /*
     * Method that collects a file path from the user and
     * if it is valid assigns the field filePath to the filePath.
     * @param: the scanner to collect the user's input
     * @return: None.
     */
    public void collectFilePath(Scanner input) {
        int num_tries = 5;
        boolean found_path = false;
        
        while (num_tries > 0 && !found_path) {
            System.out.print("Enter the path for the xml file: ");
            String filePath = input.nextLine();
            
            found_path = this.isXmlFile(filePath);
            if(found_path){
                this.filePath = filePath;
            }
            else {
                num_tries--;
                System.out.printf("Number of tries left: %d\n\n", num_tries);
            }
        }
        
        if(!found_path){
            System.out.println("Exceeded tries. Please try again later.");
        }
    }
}