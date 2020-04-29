// Nichole Maldonado
// CS331 - Lab 5, GameDeleter Class

/*
 * GameDeleter class removes games based on the user's selection,
 * updates the queue, removes the game file, and removes the game
 * from the config game files.
 */

// changelog
// [4/26/20] [Nichole Maldonado] added class to remove games when GameSelector saw
//                               that the games would exceed the max capacity.
// [4/26/20] [Nichole Maldonado] fixed bug in deleteGameConfigs that was not properly
//                               deleting the game configs.

package utep.cs3331.lab5.chess;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Iterator;

import utep.cs3331.lab5.files.FilePaths;
import utep.cs3331.lab5.files.ParserWriter;
import utep.cs3331.lab5.files.ParserReader;
import utep.cs3331.lab5.chess.GameSelector;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

/*
 * GameDeleter class removes games based on the user's selection,
 * updates the queue, removes the game file, and removes the game
 * from the config game files.
 */
public class GameDeleter{
    private HashSet<String> gamesToDelete;
    
    /*
     * Default Constructor that creates the hashset.
     * @param: None.
     * @return: None.
     */
    public GameDeleter() {
        gamesToDelete = new HashSet<String>();
    }
    
    /*
     * Method that determines which games the user wants to delete and deletes them.
     * @param: Scanner for input and the gameSelector.
     * @return: None.
     */
    public void deleteGames(GameSelector gameSelector, Scanner input) throws IOException, JDOMException {
        
        boolean deletedGame = false;
        Iterator<String> iter = gameSelector.getIdQueue().iterator();
        System.out.println("Select");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("To delete a game file.\n");
        while (iter.hasNext()) {
            String id = iter.next();
            try {
            
                String idString = gameSelector.timestampToDay(id);
                System.out.println("Would you like to delete: " + idString.substring(0,16));
                System.out.print("Select 1 (for yes) or 2 (for no): ");
                int selection = 0;

                while (selection != 1 && selection != 2) {
                    try {
                        selection = input.nextInt();
                        input.nextLine();
                    }
                    catch (InputMismatchException e) {
                        input.nextLine();
                        selection = 0;
                    }

                    if (selection != 1 && selection != 2) {
                        System.out.println("\nInvalid input. Please try");
                        System.out.println();
                        System.out.println("Would you like to delete: " + idString);
                        System.out.print("Select 1 (for yes) or 2 (for no): ");
                    }
                }
                
                // Remove selected games.
                if (selection == 1) {
                    deletedGame = true;
                    this.gamesToDelete.add(id);
                    iter.remove();
                }
            }
            //Remove selected games if a problem with the id occured.
            catch (ParseException e) {
                iter.remove();
            }
        }
        
        // Remove a game if user never selected any games.
        if (!deletedGame){
            System.out.println("You did not select any games to delete. Your oldest game will be deleted");
            gamesToDelete.add(gameSelector.getIdQueue().removeFirst());
        }
        
        // Delete games from configs and the .xml file.
        this.deleteGameFiles(gameSelector.getFilePaths().getFilePathChessTemplate());
        this.deleteGameConfigs(gameSelector.getFilePaths().getFilePathConfigs(), input);
    }
    
    /*
     * Method that deletes game files that have the
     * gameid appended to the end of "chessTemplate"
     * @param: The file path of the chess template.
     * @return: None.
     */
    private void deleteGameFiles(String filePathChessTemplate) {
        for (String id: this.gamesToDelete) {
            File gameFile = new File(filePathChessTemplate.substring(0, filePathChessTemplate.length() - 4) + id + ".xml");
            gameFile.delete();
        }
    }
    
    /*
     * Method that deletes games from the game config file if they
     * are in the gamesToDelete hashset.
     * @param: The file path of the config file.
     * @return: None. 
     * NOTE: Throws IOException. Crucial to throw back to controller, the game keys were there
     * but and the file accessed but now it can no longer be accessed.
     */
    private void deleteGameConfigs(String gameConfigFilePath, Scanner input) throws IOException, JDOMException {
        
        // Get loaded game xml file.
        File inputFile = new File(gameConfigFilePath);
        
        FileInputStream fileStream = new FileInputStream(inputFile);
        SAXBuilder saxBuilder = new SAXBuilder();
        Document configFile = saxBuilder.build(inputFile);
        
        Element chessConfigs = configFile.getRootElement();
        List<Element> gameList = chessConfigs.getChildren("Chess");

        if (gameList != null) {
            int numToRemove = this.gamesToDelete.size();
            
            // Iterate through chess configs.
            for (int i = 0; i < gameList.size() && numToRemove > 0; i++) {
                Element game = gameList.get(i);
                
                // Remove if in the set.
                if (game.getChild("game") != null && game.getChild("game").getChild("id") != null && this.gamesToDelete.contains(game.getChild("game").getChild("id").getText())) {
                    chessConfigs.removeContent(game);
                    numToRemove--;
                }
            }
        }
        
        ParserWriter.sendToFile(configFile, inputFile, input);
        fileStream.close();
    }
}