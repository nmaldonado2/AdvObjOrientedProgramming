package utep.cs3331.lab4.chess;

public class Game {
    private String id;
    private int maxTime;
    private boolean useAutoSave;
    private boolean hasChat;
    
    public Game(String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    public int getMaxTime() {
        return this.maxTime;
    }
    public boolean getUseAutoSave() {
        return this.useAutoSave;
    }
    public boolean getHasChat() {
        return this.hasChat;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
    public void setUseAutoSave(boolean useAutoSave) {
        this.useAutoSave = useAutoSave;
    }
    public void setHasChat(boolean hasChat) {
        this.hasChat = hasChat;
    }
}