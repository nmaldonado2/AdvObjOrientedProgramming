package utep.cs3331.lab4.players;

import utep.cs3331.lab4.players.ExpertiseLevel;
import utep.cs3331.lab4.players.Color;

public class ChessPlayer{
    private String name;
    private ExpertiseLevel expertiseLevel;
    private Color userColor;
    
    public ChessPlayer(String name, ExpertiseLevel expertiseLevel, Color userColor) {
        this.name = name;
        this.expertiseLevel = expertiseLevel;
        this.userColor = userColor;
    }
  
    public String getName(){
        return this.name;
    }
    
    public ExpertiseLevel getExpertiseLevel(){
        return this.expertiseLevel;
    }
    
    public Color getUserColor(){
        return this.userColor;
    }
}