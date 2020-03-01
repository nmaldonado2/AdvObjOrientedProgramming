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

package utep.cs3331.lab4.files;

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
    private String filePathChess;
    
    /*
     * Getter method for the field filePathUsers.
     * @param: None.
     * @return: a string of filePathUsers.
     */
    public String getFilePathUsers() {
        return this.filePathUsers;
    }
    
    /*
     * Getter method for the field filePathChess
     * @param: None.
     * @return: a string of filePathChess.
     */
    public String getFilePathChess() {
        return this.filePathChess;
    }
    
    /*
     * Setter method for the field filePathChess.
     * Input: a new file path to be assigned to filePathChess.
     * Output: None.
     */
    public void setFilePathChess(String filePathChess) {
        this.filePathChess = filePathChess;
    }
    
    /*
     * Setter method for the field filePathUsers.
     * Input: a new file path to be assigned to filePathUsers.
     * Output: None.
     */
    public void setFilePathUsers(String filePathUsers) {
        this.filePathUsers = filePathUsers;
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
        int num_tries = 5;
        boolean found_path = false;
        
        while (num_tries > 0 && !found_path) {
//            System.out.printf("Enter the path for the %s xml file: ", fileType);
//            String filePath = input.nextLine();
//            
//            found_path = this.isXmlFile(filePath);
            found_path = true;
            if(found_path){
                if (fileType.equals("user")) {
                    this.filePathUsers = "/Users/nichole_maldonado/Desktop/Lab4/users.xml";
//                    this.filePathUsers = filePath;
                }
                else {
//                    this.filePathChess = filePath;
                    this.filePathChess = "/Users/nichole_maldonado/Desktop/Lab4/chess.xml";
                }
                
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