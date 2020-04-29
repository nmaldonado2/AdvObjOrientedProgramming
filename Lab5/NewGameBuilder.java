package utep.cs3331.lab4.chess;

public class NewGameBuilder extends GameBuilder {
    public void printGameStatus(){
        System.out.println("Loading your existing game");
        System.out.println("---------------------------");
    }
    public boolean updateGame(Parser parserWriter, Controller controller, Scanner input){
        // Write chess pieces.
        this.parserWriter.writeNewChessPieces(controller, input);
            
        // Write new id to user, Write to configs.
        return this.parserWriter.updateUserGameKey(controller.getPlayer(), input) &&               
                    this.parserWriter.updateConfig(controller, input); 
    }
}