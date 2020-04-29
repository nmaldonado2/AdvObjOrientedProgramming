// Nichole Maldonado
// CS331 - Lab 4, FilePaths Class

/*
 * The FilePaths class contains a filePathUsers
 * and filePathChess attribute which contain the
 * file directory for the xml user and chess files.
 * Available methods include setters and getters
 * for the fields.
 */

// changelog
// [2/29/20] [Nichole Maldonado] refactored code by creating a single
//                               filePath that will store the file paths
//                               for the user and chess xml at any time.
// [2/29/20] [Nichole Maldonado] Created getters and setters for the attributes.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.files;

import java.util.Scanner;

/*
 * The FilePaths class contains a filePathUsers
 * and filePathChess attribute which contain the
 * file directory for the xml user and chess files.
 * Available behaviours include setters and getters
 * for the fields.
 */
public class FilePaths {
    private String filePathUsers;
    private String filePathConfigs;
    private String filePathChessTemplate;
    
    /*
     * Getter method for the field filePathUsers.
     * @param: None.
     * @return: a string of filePathUsers.
     */
    public String getFilePathUsers() {
        return this.filePathUsers;
    }
    
    /*
     * Getter method for the field filePathConfigs
     * @param: None.
     * @return: a string of filePathConfigs.
     */
    public String getFilePathConfigs() {
        return this.filePathConfigs;
    }
    
    /*
     * Getter method for the field filePathChessTemplate
     * @param: None.
     * @return: a string of filePathChessTemplate.
     */
    public String getFilePathChessTemplate() {
        return this.filePathChessTemplate;
    }
    
    /*
     * Setter method for the field filePathUsers.
     * Input: a new file path to be assigned to filePathUsers.
     * Output: None.
     */
    public void setFilePathUsers(String filePathUsers) {
        this.filePathUsers = filePathUsers;
    }
    
    /*
     * Setter method for the field filePathConfigs.
     * Input: a new file path to be assigned to filePathConfigs.
     * Output: None.
     */
    public void setFilePathConfigs(String filePathConfigs) {
        this.filePathConfigs = filePathConfigs;
    }
    
    /*
     * Getter method for the field filePathChessTemplate
     * @param: None.
     * @return: a string of filePathChessTemplate.
     */
    public void setFilePathChessTemplate(String filePathChessTemplate) {
        this.filePathChessTemplate = filePathChessTemplate;
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
    
    /*
     * Method that collects a file path from the user and
     * if it is valid assigns the field filePath to the corresponding
     * filePath.
     * @param: the scanner to collect the user's input. fileType 
     *         is either chess for filePathChess or user for filePathUsers.
     * @return: None.
     */
    public void collectFilePath(Scanner input, String fileType) {
        boolean foundPath = false;
        
        while (!foundPath) {
            System.out.printf("Enter the path for the %s xml file: ", fileType);
            String filePath = input.nextLine();
            
            //foundPath = this.isXmlFile(filePath);
            foundPath = true;
            if(foundPath){
                if (fileType.equals("user")) {
                    //this.filePathUsers = filePath;
                    this.filePathUsers = "/Users/nichole_maldonado/Desktop/Lab5/utep/cs3331/lab5/files/users.xml";
                }
                else if (fileType.equals("configs")) {
                    //this.filePathConfigs = filePath;
                    this.filePathConfigs = "/Users/nichole_maldonado/Desktop/Lab5/utep/cs3331/lab5/files/gameConfigs.xml";
                }
                else {
                    //this.filePathChessTemplate = filePath;
                    this.filePathChessTemplate = "/Users/nichole_maldonado/Desktop/Lab5/utep/cs3331/lab5/files/chessTemplate.xml";
                }
            }
        }
    }
}