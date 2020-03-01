// Nichole Maldonado
// CS331 - Lab 4, ChessPlayer Class

/*
 * The ChessPlayer class that represents the current 
 * player. The class contains attributes for 
 * the player such as their name, expertise level, and
 * user color. Getters are provided for the method and not 
 * setters. Once the user is created, their expertise level,
 * user color, and name cannot change.
 */

// changelog
// [2/28/20] [Nichole Maldonado] added getters and setters for 
//                               attributes that include board,
//                               expertise level, and user color.
// [3/01/20] [Nichole Maldonado] Reorganized import statements

package utep.cs3331.lab4.players;

import utep.cs3331.lab4.players.Color;
import utep.cs3331.lab4.players.ExpertiseLevel;

/*
 * The ChessPlayer class that represents the current 
 * player. The class contains attributes for 
 * the player such as their name, expertise level, and
 * user color. Getters are provided for the method and not 
 * setters. Once the user is created, their expertise level,
 * user color, and name cannot change.
 */
public class ChessPlayer{
    private String name;
    private ExpertiseLevel expertiseLevel;
    private Color userColor;
    
    /*
     * Default constructor that initializes the name,
     * expertiseLevel, and userColor for the player.
     * @param: The name, expertiseLevel, and userColor for the player.
     * @return: None.
     */
    public ChessPlayer(String name, ExpertiseLevel expertiseLevel, Color userColor) {
        this.name = name;
        this.expertiseLevel = expertiseLevel;
        this.userColor = userColor;
    }
    
    /*
     * Getter for the player's name.
     * @param: None
     * @return: The player's name.
     */
    public String getName(){
        return this.name;
    }
    
    /*
     * Getter for the player's expertise level.
     * @param: None
     * @return: The player's name.
     */
    public ExpertiseLevel getExpertiseLevel(){
        return this.expertiseLevel;
    }
    
    /*
     * Getter for the player's color.
     * @param: None
     * @return: The player's color.
     */
    public Color getUserColor(){
        return this.userColor;
    }
}