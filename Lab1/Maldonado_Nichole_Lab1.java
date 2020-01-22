import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;
import java.util.Arrays;

class ChessPiece{
    char xPosition;
    int yPosition;
    boolean isWhite;
    
    public ChessPiece(char xPosition, int yPosition, boolean isWhite){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isWhite = isWhite;
    }
    public boolean canMove(char xPosition, int yPosition){
        return true;
    }
    
    public boolean canMoveHorizontally(char xPosition, int yPosition, char minXPosition, char maxXPosition){
        if(this.yPosition != yPosition || xPosition < 'A' || xPosition > 'H'){
            return false;
        }
        return xPosition >= minXPosition && xPosition <= maxXPosition;
    }
    public boolean canMoveVertically(char xPosition, int yPosition, int minYPosition, int maxYPosition){
        if(this.xPosition != xPosition || yPosition < 1 || yPosition > 8){
            return false;
        }
        return yPosition >= minYPosition && yPosition <= maxYPosition;
    }
    public boolean canMoveDiagonally(char xPosition, int yPosition, int minDiagonal, int maxDiagonal){
        if(xPosition > 'H' || xPosition < 'A' || yPosition < 1 || yPosition > 8){
            return false;   
        }
        int xPositionDifference = Math.abs(this.xPosition - xPosition);
        if(xPositionDifference == Math.abs(this.yPosition - yPosition)){
            return xPositionDifference >= minDiagonal && xPositionDifference <= maxDiagonal;
        }
        return false;
    }
}

class Pawn extends ChessPiece{
    
    public Pawn(char xPosition, int yPosition, boolean isWhite){
        super(xPosition, yPosition, isWhite);
    }
    
    public static Pawn createPawn(char xPosition, int yPosition, boolean isWhite){
        if(xPosition < 'A' || xPosition > 'H'){
            return null;
        }
        if((isWhite && yPosition == 2) || (!isWhite && yPosition == 7)){
            return new Pawn(xPosition, yPosition, isWhite);
        }
        return null;
    }
    
    //Can move 1 or 2 spaces foward.
    public boolean canMove(char xPosition, int yPosition){
        return this.canMoveVertically(xPosition, yPosition, this.xPosition + 1, this.xPosition + 2);
    }
    
    @Override
    public String toString(){
        return "Pawn";
    }
}

class Rook extends ChessPiece{
    
    public Rook(char xPosition, int yPosition, boolean isWhite){
        super(xPosition, yPosition, isWhite);
    }
    
    public static Rook createRook(char xPosition, int yPosition, boolean isWhite){
        if(xPosition != 'A' && xPosition != 'H'){
            return null;
        }
        if((isWhite && yPosition == 1) || (!isWhite && yPosition == 8)){
            return new Rook(xPosition, yPosition, isWhite);
        }
        return null;
    }
    
    // cna move any num squares vertically or horizontally
    public boolean canMove(char xPosition, int yPosition){
//        if(yPosition < 1 || yPosition > 8){
//            return false;
//        }
//        // FIXME: Check to make sure we can't stay in same spot???? What about for all of them.
//        if(this.xPosition == xPosition && this.yPosition != yPosition){
//            return true;
//        }
//        if(this.yPosition == yPosition && this.xPosition != xPosition){
//            return true;
//        }
//        return false;
        return this.canMoveVertically(xPosition, yPosition, this.yPosition + 1, 8) || this.canMoveHorizontally(xPosition, yPosition, (char)(this.xPosition + 1), 'H');
    }
    
    @Override
    public String toString(){
        return "Rook";
    }
}

class Knight extends ChessPiece{
    
    public Knight(char xPosition, int yPosition, boolean isWhite){
        super(xPosition, yPosition, isWhite);
    }
    
    public static Knight createKnight(char xPosition, int yPosition, boolean isWhite){
        if(xPosition != 'B' && xPosition != 'G'){
            return null;
        }
        if((isWhite && yPosition == 1) || (!isWhite && yPosition == 8)){
            return new Knight(xPosition, yPosition, isWhite);
        }
        return null;
    }
    
    public boolean canMove(char xPosition, int yPosition){
        if(xPosition >= 'A' && xPosition <= 'H' && yPosition <= 8 && yPosition >= 1){
            if(Math.abs(this.xPosition - xPosition) == 2){
                if(Math.abs(this.yPosition - yPosition) == 1){
                    return true;
                }
            }
            else if(Math.abs(this.yPosition - yPosition) == 2){
                if(Math.abs(this.xPosition - xPosition) == 1){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public String toString(){
        return "Knight";
    }
}

class Bishop extends ChessPiece{
    
    public Bishop(char xPosition, int yPosition, boolean isWhite){
        super(xPosition, yPosition, isWhite);
    }
    
    public static Bishop createBishop(char xPosition, int yPosition, boolean isWhite){
        if(xPosition != 'C' && xPosition != 'F'){
            return null;
        }
        if((isWhite && yPosition == 1) || (!isWhite && yPosition == 8)){
            return new Bishop(xPosition, yPosition, isWhite);
        }
        return null;
    }
    
    //Move diagonally.
    public boolean canMove(char xPosition, int yPosition){
//        if(xPosition >= 'A' && xPosition <= 'H' && yPosition > 0 && yPosition < 9){
//           if(Math.abs(this.xPosition - xPosition) == Math.abs(this.yPosition - yPosition)){
//               return true;
//           }
//        }
//        return false;
        return canMoveDiagonally(xPosition, yPosition, 1, Math.min('H' - this.xPosition, 8 - this.yPosition));
    }
    @Override
    public String toString(){
        return "Bishop";
    }
}

class Queen extends ChessPiece{
    
    public Queen(char xPosition, int yPosition, boolean isWhite){
        super(xPosition, yPosition, isWhite);
    }
    
    public static Queen createQueen(char xPosition, int yPosition, boolean isWhite){
        if(xPosition != 'D'){
            return null;
        }
        if((isWhite && yPosition == 1) || (!isWhite && yPosition == 8)){
            return new Queen(xPosition, yPosition, isWhite);
        }
        return null;
    }
    
    public boolean canMove(char xPosition, int yPosition){
        if(this.canMoveVertically(xPosition, yPosition, 1, 8)){
            return true;   
        }
        if(this.canMoveHorizontally(xPosition, yPosition, 'A', 'H')){
            return true;
        }
        return this.canMoveDiagonally(xPosition, yPosition, 1, Math.min('H' - this.xPosition, 8 - this.yPosition));
    }
    @Override
    public String toString(){
        return "Queen";
    }
}

class King extends ChessPiece{
    
    public King(char xPosition, int yPosition, boolean isWhite){
        super(xPosition, yPosition, isWhite);
    }
    
    public static King createKing(char xPosition, int yPosition, boolean isWhite){
        if(xPosition != 'E'){
            return null;
        }
        if((isWhite && yPosition == 1) || (!isWhite && yPosition == 8)){
            return new King(xPosition, yPosition, isWhite);
        }
        return null;
    }
    
    public boolean canMove(char xPosition, int yPosition){
        if(this.canMoveVertically(xPosition, yPosition, this.yPosition - 1, this.yPosition + 1)){
            return true;   
        }
        if(this.canMoveHorizontally(xPosition, yPosition, (char)(this.xPosition - 1), (char)(this.xPosition + 1))){
            return true;
        }
        return this.canMoveDiagonally(xPosition, yPosition, 1, 1);
    }
    @Override
    public String toString(){
        return "King";
    }
}

public class Maldonado_Nichole_Lab1{
    
    //Assume pieceType is in lower case.
    public static boolean validPosition(boolean isWhite, char xPosition, int yPosition, String pieceType, ArrayList<ChessPiece> chessPieceArr){    
        if(pieceType.equals("pawn")){
            Pawn pawnPiece = Pawn.createPawn(xPosition, yPosition, isWhite);
            if(pawnPiece != null){
                chessPieceArr.add(pawnPiece);
                return true;
            }
            return false;
        }
        if(pieceType.equals("rook")){
            Rook rookPiece = Rook.createRook(xPosition, yPosition, isWhite);
            if(rookPiece != null){
                chessPieceArr.add(rookPiece);
                return true;
            }
            return false;
        }
        else if(pieceType.equals("knight")){
            Knight knightPiece = Knight.createKnight(xPosition, yPosition, isWhite);
            if(knightPiece != null){
                chessPieceArr.add(knightPiece);
                return true;
            }
            return false;
        }
        else if(pieceType.equals("bishop")){
            Bishop bishopPiece = Bishop.createBishop(xPosition, yPosition, isWhite);
            if(bishopPiece != null){
                chessPieceArr.add(bishopPiece);
                return true;
            }
            return false;
        }
        else if(pieceType.equals("queen")){
            Queen queenPiece = Queen.createQueen(xPosition, yPosition, isWhite);
            if(queenPiece != null){
                chessPieceArr.add(queenPiece);
                return true;
            }
            return false;
        }
        else if(pieceType.equals("king")){
            King kingPiece = King.createKing(xPosition, yPosition, isWhite);
            if(kingPiece != null){
                chessPieceArr.add(kingPiece);
                return true;
            }
            return false;
        }
        return false;
    }
    
    public static boolean createChessPiece(ArrayList<ChessPiece> chessPieceArr, String[] pieceAttributes){
        boolean isWhite;
        char xPosition;
        int yPosition;
        
        try{
            if(pieceAttributes[1].toLowerCase().equals("white")){
                isWhite = true;   
            }
            else if(pieceAttributes[1].toLowerCase().equals("black")){
                isWhite = false;
            }
            else{
                return false;
            }
            
            if(pieceAttributes[2].length() == 1){
                xPosition = pieceAttributes[2].toUpperCase().charAt(0);
            }
            else{
                return false;
            }
            yPosition = Integer.parseInt(pieceAttributes[3]);
        } 
        catch(NumberFormatException e){
            return false;
        }
        
        return validPosition(isWhite, xPosition, yPosition, pieceAttributes[0].toLowerCase(), chessPieceArr);
    }
    
    public static ArrayList<ChessPiece> recieveAllocatePieces(String fileDirectory) throws FileNotFoundException{
        ArrayList<ChessPiece> chessPieceArr = new ArrayList<ChessPiece>();
        int lineNum = 0;
        
        File chessFile = new File(fileDirectory);
        Scanner fileReader = new Scanner(chessFile);
        while(fileReader.hasNextLine()){
            lineNum++;
            String[] chessPieceAttributes = fileReader.nextLine().replaceAll("\\s+", "").split(",");
            
            if(chessPieceAttributes.length == 4){
                if(!createChessPiece(chessPieceArr, chessPieceAttributes)){
                    System.out.printf("\nThe piece on line %d was not added since one or more of the attributes did not match those expected\n", lineNum);
                }
            }
            else{
                System.out.printf("\nThe piece on line %d was not added since it did not contain the correct amount of attributes\n", lineNum);
            }
            
        }
        
        return  chessPieceArr;
    }
    
    public static void verifyNewPosition(ArrayList<ChessPiece> chessPieceArr, char xPosition, int yPosition){
        
        for(int i = 0; i < chessPieceArr.size(); i++){
            String verificationNotifier = "";
            if(!chessPieceArr.get(i).canMove(xPosition, yPosition)){
                verificationNotifier = "NOT ";
            }
            System.out.print(chessPieceArr.get(i).toString() + " at " + chessPieceArr.get(i).xPosition);
            System.out.print(", " + chessPieceArr.get(i).yPosition + " can " + verificationNotifier);
            System.out.println("move to " + xPosition + ", " + yPosition);
        }
    }
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
//        System.out.print("Enter the directory for the file that contains the chess pieces: ");
//        String fileDirectory = input.nextLine();
        String fileDirectory = "/Users/nichole_maldonado/Desktop/chess.txt";
        System.out.println(fileDirectory);
        try{
            ArrayList<ChessPiece> chessPieceArr = recieveAllocatePieces(fileDirectory);
            
            System.out.print("Enter the new x - position: ");
            char xPosition = input.nextLine().toUpperCase().charAt(0);

            System.out.print("Enter the new y - position: ");
            int yPosition = input.nextInt();
            System.out.println();

            verifyNewPosition(chessPieceArr, xPosition, yPosition);
        } 
        catch(FileNotFoundException e){
            System.out.println("The fille could not be found. Program terminating.");
        }
        

        
    }
}

