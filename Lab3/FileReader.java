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

package edu.nmaldonado2.fileutil;
import edu.nmaldonado2.chesspieces.ChessPiece;
import edu.nmaldonado2.piececreation.PieceCreator;

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

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
    private ArrayList<ChessPiece> validPieces;
    private ArrayList<Integer> invalidPieces;
    
    /*
     * Default constructor that assigns each field to a
     * new ArrayList of the designated type.
     * @param: None.
     * @return: None.
     */
    public FileReader() {
        this.validPieces = new ArrayList<ChessPiece>();
        this.invalidPieces = new ArrayList<Integer>();
    }
    
    /*
     * Getter method for the field filePath
     * @param: None.
     * @return: a string of a filePath.
     */
    public String getFilePath() {
        return this.filePath;
    }
    
    /*
     * Getter method for the field validPieces
     * @param: None.
     * @return: an ArrayList of ChessPieces.
     */
    public ArrayList<ChessPiece> getValidPieces() {
        return this.validPieces;
    }
    
    /*
     * Getter method for the field invalidPieces
     * @param: None.
     * @return: an ArrayList of Integers.
     */
    public ArrayList<Integer> getInvalidPieces() {
        return this.invalidPieces;
    }
    
    /*
     * Setter method for the field filePath
     * @param: a String of a file path.
     * @return: None.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    /*
     * Setter method for the field validPieces
     * @param: an ArrayList of ChessPieces.
     * @return: None.
     */
    public void setValidPieces(ArrayList<ChessPiece> validPieces) {
        this.validPieces = validPieces;
    }
    
    /*
     * Setter method for the field invalidPieces
     * @param: an ArrayList of Integers.
     * @return: None.
     */
    public void setInvalidPieces(ArrayList<Integer> invalidPieces) {
        this.invalidPieces = invalidPieces;
    }
    
    /*
     * Method that prints the line numbers of pieces not evaluated from the file
     * represented through the field invalidPieces.
     * @param: None.
     * @return: None.
     */
    private void printUnevaluatedPieces() {
        
        System.out.print("\nThe pieces on the following lines were not added since");
        System.out.print(" one or more of the attributes did not match those expected"); 
        System.out.println(" or the 4 necessary attributes were not included.");
        
        //Prints line numbers.
        System.out.print("Line(s): ");
        for (int i = 0; i < this.invalidPieces.size(); i++) {
            System.out.print(this.invalidPieces.get(i));
            if (i < this.invalidPieces.size() - 1) {
                System.out.print(", ");
            }
            else {
                System.out.println("\n");
            }
        }
    }
    
    /*
     * Method that reads the file found and populates validPieces with 
     * ChessPiece's derived class objects.
     * @param: None.
     * @return: None.
     */
    public void retrieveAndAllocatePieces() {
        
        // Cannot read a null filePath.
        if (this.filePath == null) {
            System.out.println("Due to an invalid file path, the file will not be read");
            return;
        }
        
        int lineNum = 0;
        
        try {
            File chessFile = new File(this.filePath);
            Scanner fileReader = new Scanner(chessFile);
            PieceCreator pieceCreator = new PieceCreator();

            // Iterates through file lines.
            while (fileReader.hasNextLine()) {
                lineNum++;

                // Removes all white spaces from the line.
                String chessPieceDescription = fileReader.nextLine().replaceAll("\\s+", "");

                //Evaluates the line as long as the description is not empty.
                if (!chessPieceDescription.isEmpty()) {
                    
                    String[] chessPieceAttributes = chessPieceDescription.split(",");
                    if (chessPieceAttributes.length == 4) {
                        
                        //Uses pieceCreator to create the specific chess piece.
                        pieceCreator.prepareChessPiece(chessPieceAttributes);

                        // Adds the piece to the list if it was successfully created.
                        if (pieceCreator.getPiece() != null) {
                            this.validPieces.add(pieceCreator.getPiece());
                            
                            //Get pieceCreator ready for the next piece.
                            pieceCreator.setPiece(null);
                        }
                        else {
                            this.invalidPieces.add(lineNum);
                        }
                    }
                    else {
                        this.invalidPieces.add(lineNum);
                    } 
                }
            }

            //Prints the unevaluated pieces' line numbers.
            if (invalidPieces.size() > 0) {
                this.printUnevaluatedPieces();
            }

            // Close file scanner.
            fileReader.close();
            
        }
        
        // Catches exceptions that may occur during file reading.
        catch (IOException e) {
            System.out.print("\nAn error occurred with the file input/output operation.");
        }
    }
    
    /* Ensures that filePath includes a .txt file at the end.
     * @param: a string of the file path that will be evaluated.
     * @return: Returns false if filePath does not end in .txt and returns
     *        true otherwise.  
     */              
    private boolean isTxtFile(String filePath) {
                
        // Returns false if the filePath does not have at least 5 letters since a
        // valid .txt file name can have a minimum of 5 letters.
        if (filePath.length() < 5) {
            System.out.print("\nInvalid file. The file must be a .txt file with at ");
            System.out.println("least a one character name. Program terminating");
            return false;
        }
        
        // If the length of filePath is greater than or equal to 5, then true is
        // returned only if filePath ends in ".txt".
        if (filePath.substring(filePath.length() - 4).equals(".txt")) {
            return true;
        }
                
        System.out.println("\nInvalid file. The file must be a .txt file.");
        return false;
    }
    
    /*
     * Method that collects a file path from the user and
     * if it is valid assigns the field filePath to the filePath.
     * @param: the scanner to collect the user's input
     * @return: None.
     */
    public void collectFilePath(Scanner input) {
        System.out.print("Enter the path for the file that contains the chess pieces: ");
        String filePath = input.nextLine();

        if(this.isTxtFile(filePath)){
            this.filePath = filePath;
        }

    }
}