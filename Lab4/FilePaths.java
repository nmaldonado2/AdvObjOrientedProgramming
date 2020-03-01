package utep.cs3331.lab4.files;

import java.util.Scanner;

public class FilePaths {
    private String filePathUsers;
    private String filePathChess;
    
    /*
     * Getter method for the field filePath
     * @param: None.
     * @return: a string of a filePath.
     */
    public String getFilePathUsers() {
        return this.filePathUsers;
    }
    
    /*
     * Getter method for the field filePath
     * @param: None.
     * @return: a string of a filePath.
     */
    public String getFilePathChess() {
        return this.filePathChess;
    }
    
    public void setFilePathChess(String filePathChess) {
        this.filePathChess = filePathChess;
    }
    
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