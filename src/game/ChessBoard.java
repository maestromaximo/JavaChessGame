package game;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class works as an extension of the <b>Tile</b> class. It permits the illiteration of a two dimensional Array of <b>Tile</b> Objects. 
 * Additionally, it instantiates all the necessary <b>Piece</b> objects. The class also permits for the manipulation of the ChessBoard and provides methods
 * for the creation of a chess AI of depth 1. Implements Serializable.
 * 
 * @author Alejandro Agustin Garcia Polo
 * @version 17.3V, February 27, 2021.
 * @since 1.0
 * @see Tile 
 * @see Piece
 * @see Chess
 * 
 * @serial 
 */
public class ChessBoard extends Tile implements Serializable{
    private ArrayList<AiMoveNode> pieceNodeList;
    private Tile[][] board;
    private int colorMod;
    private boolean didItMove, checkMate;
    private final Color white = Color.WHITE;
    private final Color black = Color.BLACK;
    private static final Color lightBrown = new Color(245, 216, 149);
    private static final Color darkBrown = new Color(94, 69, 10);//148, 107, 13
    private Tile aiFrom, aiTo;
    
    private Player playerOne;
    private Player playerTwo;
    
    private Piece pawnBlack0, pawnBlack1, pawnBlack2,
            pawnBlack3, pawnBlack4, pawnBlack5, pawnBlack6, pawnBlack7; 
    
    private Piece queenWhite, horse0White, horse1White, kingWhite, bishop0White,
            bishop1White, tower0White, tower1White;
    
    private Piece queenBlack, horse0Black, horse1Black, kingBlack, bishop0Black,
            bishop1Black, tower0Black, tower1Black;
    
    private Piece pawnWhite0, pawnWhite1, pawnWhite2,
            pawnWhite3, pawnWhite4, pawnWhite5, pawnWhite6, pawnWhite7; 
    
    /**
     *Constructor for the <b>ChessBoard</b> class, it calls the Constructor for the <b>Tile</b> class as well as calling the
     * initialization, creation, and coordinate placements of the board, player, and pieces objects.
     */
    public ChessBoard(){
        super();
        this.checkMate = false;
        this.colorMod = 0;
        this.board = new Tile[8][8];
        
        this.playerOne = new Player("Player One", white);
        this.playerTwo = new Player("Player Two", black);
        
        this.initializePieces(); 
        this.createBoard();
        this.setTileCords();
    }//Constructor
    
    /**
     * Private method that initializes all the <b>Piece</b> objects that will be used in the board field.
     */
    private void initializePieces(){
        
         this.pawnBlack0 = new Pawn(black);
        this.pawnBlack1 = new Pawn(black);
        this.pawnBlack2 = new Pawn(black);
        this.pawnBlack3 = new Pawn(black);
        this.pawnBlack4 = new Pawn(black);
        this.pawnBlack5 = new Pawn(black);
        this.pawnBlack6 = new Pawn(black);
        this.pawnBlack7 = new Pawn(black);
        
        this.tower0Black = new Tower(black);
        this.horse0Black = new Horse(black);
        this.bishop0Black = new Bishop(black);
        this.queenBlack = new Queen(black);
        this.kingBlack = new King(black);
        this.bishop1Black = new Bishop(black);
        this.horse1Black = new Horse(black);
        this.tower1Black = new Tower(black);
        
        this.pawnWhite0 = new Pawn(white);
        this.pawnWhite1 = new Pawn(white);
        this.pawnWhite2 = new Pawn(white);
        this.pawnWhite3 = new Pawn(white);
        this.pawnWhite4 = new Pawn(white);
        this.pawnWhite5 = new Pawn(white);
        this.pawnWhite6 = new Pawn(white);
        this.pawnWhite7 = new Pawn(white);
        
        this.tower0White = new Tower(white);
        this.horse0White = new Horse(white);
        this.bishop0White = new Bishop(white);
        this.queenWhite = new Queen(white);
        this.kingWhite = new King(white);
        this.bishop1White = new Bishop(white);
        this.horse1White = new Horse(white);
        this.tower1White = new Tower(white);
        
    }//initializePieces
    
    /**
     * Private method in charge of calling for the paintTheBoard method as well as the startingSetup method.
     */
    private void createBoard(){
        
        paintTheBoard();
        startingSetup();
        
        
    }//createBoard
    
    /**
     * Private method in charge of illiterating trough the board field and assigning a faction color to each tile object on the board array.
     */
    private void paintTheBoard(){
        
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                
                Tile tempTile = new Tile();
                Color theColor = assignColor(colorMod);
                
                
                tempTile.setFaction(theColor);
                
                
                board[row][col] = tempTile;
                colorMod += 1;
            }//
            colorMod += 1;
        }
        
    }//paintTheBoard
    
    /**
     * Private method used to determine which color to assigning to a Tile object in base to the even or odd nature
     * of the a number.
     * 
     * @param number the number in question for the assignment of the color of the Tile Object.
     * 
     * @return the color to be assigned to the Tile Object.
     */
    private Color assignColor(int number){
        if(number % 2 == 0){
            return lightBrown;
        }
        else{
            return darkBrown;
        }
    } //assignColor
    
    /**
     * Private method in charge of allocating the legal initial positions for all the pieces into the board field.
     * 
     * It accesses the Mutator method <b>setPiece</b> of the tile objects in order to do so.
     */
    private void startingSetup(){
        
        playerOne.setTurnToPlay(true);
        
        board[0][0].setPiece(this.tower0Black);
        board[0][1].setPiece(this.horse0Black);
        board[0][2].setPiece(this.bishop0Black);
        board[0][3].setPiece(this.queenBlack);
        board[0][4].setPiece(this.kingBlack);
        board[0][5].setPiece(this.bishop1Black);
        board[0][6].setPiece(this.horse1Black);
        board[0][7].setPiece(this.tower1Black);
        
        board[1][0].setPiece(this.pawnBlack0);
        board[1][1].setPiece(this.pawnBlack1);
        board[1][2].setPiece(this.pawnBlack2);
        board[1][3].setPiece(this.pawnBlack3);
        board[1][4].setPiece(this.pawnBlack4);
        board[1][5].setPiece(this.pawnBlack5);
        board[1][6].setPiece(this.pawnBlack6);
        board[1][7].setPiece(this.pawnBlack7);
        
        board[7][0].setPiece(this.tower0White);
        board[7][1].setPiece(this.horse0White);
        board[7][2].setPiece(this.bishop0White);
        board[7][3].setPiece(this.queenWhite);
        board[7][4].setPiece(this.kingWhite);
        board[7][5].setPiece(this.bishop1White);
        board[7][6].setPiece(this.horse1White);
        board[7][7].setPiece(this.tower1White);
        
        board[6][0].setPiece(this.pawnWhite0);
        board[6][1].setPiece(this.pawnWhite1);
        board[6][2].setPiece(this.pawnWhite2);
        board[6][3].setPiece(this.pawnWhite3);
        board[6][4].setPiece(this.pawnWhite4);
        board[6][5].setPiece(this.pawnWhite5);
        board[6][6].setPiece(this.pawnWhite6);
        board[6][7].setPiece(this.pawnWhite7);
        
    }//startingSetup
    
    /**
     * Accessor method that is in charge of returning the board field that contains all of the logic of the game inside.
     * 
     * @return a <b>Tile[][]</b> object representing the current Chess Board.
     */
    public Tile[][] getBoard(){
        return board;
    }//getBoard
    
    /**
     *Mutator method in charge of modifying the value of the board field on the ChessBoard class.
     * 
     * @param board the new Tile[][] object configuration representative of a chess board.
     */
    public void setBoard(Tile[][] board){
        this.board = board;
    }//setBoard
    
    /**
     * Accessor method that determines and returns the Current player of the turn.
     * 
     * @return the player whom is supposed to move.
     */
    public Player getPlayer(){
        if(playerOne.getTurnToPlay() == true){
            return playerOne;
        }
        else{
            return playerTwo;
        }
    }//getPlayer
    
    /**
     * Mutator method in charge of modifying the field didItMove, which is used
     * to determine if a move was made.
     * 
     * @param didItMove whether a piece from the <b>Player</b> moved.
     */
    public void setDidItMove(boolean didItMove){
        this.didItMove = didItMove;
    }//seDidItMove
    
    /**
     *Accessor method in charge of obtaining the didItMove field in regards to a <b>Player</b> object.
     * 
     * @return if a piece from the player moved.
     */
    public boolean getDidItMove(){
        return didItMove;
    }//getDidItMove
    
    /**
     * Private Mutator method in charge of setting both the row -Y- and col -X- coordinates of a tile in relation to the board field
     * metalocation as well as calling setLabelCords method.
     */
    private void setTileCords(){
         for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                
                board[row][col].setX(col);
                board[row][col].setY(row);
                
            }//
         }
         setLabelCords();
    }//setTileCords
    
    /**
     * Private method in charge of obtaining the pixel relative coordinates of a label in relation to a <b>Tile's</b> 
     * row and col coordinates.
     */
    private void setLabelCords(){
        
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                
                int pixelRow = Math.round( ((75*row) + 5) );
                int pixelCol = Math.round( ((75*col) + 5) );
                
                board[row][col].setLabelCoordinates(pixelRow, pixelCol);
                
            }//
         }
    }//setLabelCords
    
    /**
     * This method prints out into the System's console the current status of the board field; in other words,
     * a representation of a chess game.
     * 
     */
    public void printBoard(){
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                System.out.print(board[row][col].toString() + ",");
            }//
            System.out.println("");
        }
        System.out.println("");
    }//printBoard
    
    /**
     * This method takes a <b>Tile</b> object, checks whether there's a piece, if so, then takes the next <b>Tile</b> object and verifies if it can move there
     * alone, or eat a piece while there, or if it need to coronate a pawn, and if it is a pawn, whether it can move 2 tiles on its first
     * activation.
     * 
     * @param tileOfPiece The tile from which to move from.
     * @param tileToMove The tile to move to.
     */
    public void movePiece(Tile tileOfPiece, Tile tileToMove){
        Player playerMoving = getPlayer();
        
        Piece fromPiece = tileOfPiece.getPiece();
        int fromCordX = tileOfPiece.getX();
        int fromCordY = tileOfPiece.getY();
        
        Piece toPiece = tileToMove.getPiece();
        int toCordX = tileToMove.getX();
        int toCordY = tileToMove.getY();
        
        int performingMoveX = (toCordX - fromCordX);
        int performingMoveY = (fromCordY - toCordY);
     
        int[][] moveSet = fromPiece.getMoveSet();
        int[] moveToPerform = {performingMoveY, performingMoveX};
        
        if(fromPiece.toString().equals("pawn")){
            if(fromPiece.getFirstTimeMoved() && (performingMoveX == 0) && (performingMoveY == -2 || performingMoveY == 2) && isLegalMove(tileOfPiece, tileToMove, fromPiece, toPiece, playerMoving, moveToPerform)){
                    tileToMove.setPiece(fromPiece);
                    tileOfPiece.setPiece(null);
                    
                    fromPiece.setFirstTimeMoved(false);
                    
                    didItMove = true;
                    nextPlayerTurn();
            }//pawn double
            else if(isValidMove(fromPiece, toPiece, moveToPerform, moveSet, playerMoving) == true){
                if(toPiece == null && (isValidPawnEat(fromPiece, toPiece, moveToPerform, moveSet, playerMoving))){
                    System.out.println("Not a valid move");
                    didItMove = false;
            //CALLS FOR THE MOVEPIECE LISTENER AGAIN AND MOVEPIECE*************************//
                }
                else if(toPiece != null && !(isValidPawnEat(fromPiece, toPiece, moveToPerform, moveSet, playerMoving)) ){
                    System.out.println("Not a valid move");
                    didItMove = false;
            //CALLS FOR THE MOVEPIECE LISTENER AGAIN AND MOVEPIECE*************************//
                }
                else if(toPiece == null && !(isValidPawnEat(fromPiece, toPiece, moveToPerform, moveSet, playerMoving)) && isLegalMove(tileOfPiece, tileToMove, fromPiece, toPiece, playerMoving, moveToPerform)){
                    tileToMove.setPiece(fromPiece);
                    tileOfPiece.setPiece(null);
                    
                    didItMove = true;
                    nextPlayerTurn();
                }
                else if(toPiece != null && isValidPawnEat(fromPiece, toPiece, moveToPerform, moveSet, playerMoving)){
                    eatPiece(tileOfPiece, tileToMove, fromPiece, toPiece, playerMoving);
                    nextPlayerTurn();
                    didItMove = true; //**************************************************************************************//
                }
            }//pawn eating
            else if( !(isValidMove(fromPiece, toPiece, moveToPerform, moveSet, playerMoving)) ){
                System.out.println("Not a valid move");
                didItMove = false;
            }
        }//pawn
        else if(isValidMove(fromPiece, toPiece, moveToPerform, moveSet, playerMoving) && isLegalMove(tileOfPiece, tileToMove, fromPiece, toPiece, playerMoving, moveToPerform)){
                if(toPiece == null){
                    tileToMove.setPiece(fromPiece);
                    tileOfPiece.setPiece(null);
                    
                    
                    //nextPlayerTurn(); //(*)//
                }else{
                    eatPiece(tileOfPiece, tileToMove, fromPiece, toPiece, playerMoving);
                }
                /*pawnCoronation(tileToMove);
                nextPlayerTurn();*/
                didItMove = true;
                nextPlayerTurn();
        }
        else{
            System.out.println("Not a valid move");
            didItMove = false;
            //CALLS FOR THE MOVEPIECE LISTENER AGAIN AND MOVEPIECE*************************//
        }
        pawnCoronation(tileToMove);
        
    }//movePiece
    
    /**
     * Method that works as an extension of the isValidMove() method, it checks whether there is any pieces in between
     * the selected move, if so, it returns <b>false</b>; otherwise, it returns <b>true</b>; It <b>must</b> be called
     * after calling the isValidMove() method, for proper functioning.
     * 
     * @param tileOfPiece The tile that the object comes from.
     * @param tileToMove The tile that the object wants to go too.
     * @param fromPiece The Piece selected.
     * @param toPiece  The Piece object to which the selected piece is moving too, may be null.
     * @param playerMoving The current player whose its making the former move.
     * @param moveToPerform An array containing the move delta coordinates of the move to make on the format (row, col).
     * 
     * @return a true or false statement in regards to if a valid move is a legal move.
     */
    private boolean isLegalMove(Tile tileOfPiece, Tile tileToMove, Piece fromPiece, Piece toPiece, Player playerMoving, int[] moveToPerform){
        boolean isLegal = true;
        
        if(fromPiece.getName().equals("horse")){
            return true;
        }
        
        int rowIncrement = 0;
        int colIncrement = 0;
        Color playerFaction = playerMoving.getFaction();
        
        int toRow = tileToMove.getY();
        int toCol = tileToMove.getX();
        
        if(fromPiece.getName().equals("pawn")){
           
            return true;
    }//pawn
        
        if(moveToPerform[0] > 0){
            rowIncrement = -1;
        }
        if(moveToPerform[0] == 0){
            rowIncrement = 0;
        }
        if(moveToPerform[0] < 0){
            rowIncrement = 1;
        }
        
        
        if(moveToPerform[1] > 0){
            colIncrement = 1;
        }
        if(moveToPerform[1] == 0){
            colIncrement = 0;
        }
        if(moveToPerform[1] < 0){
            colIncrement = -1;
        }
        int row = tileOfPiece.getY();
        int col = tileOfPiece.getX();
        if(rowIncrement != 0 && colIncrement !=0){
            for(row = row; row != toRow; ){
                row += rowIncrement;
                
                    col += colIncrement;
               
                    if(board[row][col].getPiece() != null && col != toCol && row != toRow){
                        return false;
                    }
               
                
            }
        }//
        else if(rowIncrement == 0 && colIncrement !=0){
                
                    for(col = col; col != toCol;){
                        col += colIncrement;
               
                        if(board[toRow][col].getPiece() != null && col != toCol){
                            return false;
                        }
               
                    }//
                
        }//
        else if(rowIncrement != 0 && colIncrement == 0){
                for(row = row; row != toRow; ){
                    row += rowIncrement;
                        if(board[row][toCol].getPiece() != null && row != toRow){
                            return false;
                        }
                }
        }//
        
        
        return isLegal;
        
    }//isLegalMove
    
    
    /**
     * Method in charge of determining whether a performing move falls within a move set from the moving piece.
     * 
     * @param fromPiece The Piece selected.
     * @param toPiece  The Piece object to which the selected piece is moving too, may be null.
     * @param playerMoving The current player whose its making the former move.
     * @param moveToPerform An array containing the move delta coordinates of the move to make on the format (row, col).
     * @param moveSet the set of moves permitted for the selected piece.
     * 
     * @return whether a move is inside the Piece's move set.
     */
    private boolean isValidMove(Piece fromPiece, Piece toPiece, int[] moveToPerform, int[][] moveSet, Player playerMoving){
        boolean isValid = false;
        
        if(toPiece != null){
            if(fromPiece.getFactionColor().getRGB() == toPiece.getFactionColor().getRGB() || !(fromPiece.getFactionColor().getRGB() == playerMoving.getFaction().getRGB()) ){
                return false;
            }//
        }
        if( !(fromPiece.getFactionColor().equals(playerMoving.getFaction())) ){
            return false;
        }//
        
        for(int i = 0; i < moveSet.length; i++){
            isValid = Arrays.equals(moveToPerform, moveSet[i]);
            if(isValid == true){
               return isValid; 
            }//
        }
        
        return isValid;
    }//isValidMove
    
    /**
     * Similar to isValidMove method, this method verifies whether a pawn eating movement is valid inside the chess rules.
     * 
     * @param fromPiece The Pawn object selected.
     * @param toPiece  The Pawn object to which the selected piece is eating.
     * @param playerMoving The current player whose its making the former move.
     * @param moveSet An array containing the permitted move delta coordinates of the Pawn selected on the format (row, col).
     * 
     * @return whether the pawn eating move is valid or not.
     */
    private boolean isValidPawnEat(Piece fromPiece, Piece toPiece, int[] moveToPerform, int[][] moveSet, Player playerMoving){
        boolean isValid = false;
        
        if(toPiece != null){
            
            if(fromPiece.getFactionColor().equals(toPiece.getFactionColor())){
                return false;
            }
        }
        
        for(int i = 1; i < moveSet.length; i++){
            isValid = Arrays.equals(moveToPerform, moveSet[i]);
            if(isValid == true){
               return isValid; 
            }//
        }
        return isValid;
    }//eatPawn
    
    /**
     * Removes the piece object that the moving piece moved too and adds it to the Players piece count.
     * 
     * @param tileOfPiece The tile that the object comes from.
     * @param tileToMove The tile that the object wants to go eat.
     * @param fromPiece The Piece selected.
     * @param toPiece  The Piece object to which the selected piece is moving too, may be null.
     * @param playerMoving The current player whose its making the former move.
     * 
     */
    private void eatPiece(Tile tileOfPiece, Tile tileToMove, Piece fromPiece, Piece toPiece, Player playerMoving){
        
        playerMoving.addToList(toPiece);
        tileToMove.setPiece(fromPiece);
        tileOfPiece.setPiece(null);
    }//eatPiece
    
    /**
     * Private method in charge of checking whether a pawn is on the extremas of the Y axis, and if so
     * changes them into a new <b>Queen</b> object.
     * 
     * @param tileToMove The tile that the object formerly moved too.
     * 
     */
    private void pawnCoronation(Tile tileToMove){
        if(tileToMove.getPiece().getName().equals("pawn") && (tileToMove.getY() == 0 || tileToMove.getY() == 7)){
            
            if(tileToMove.getPiece().getFactionColor() == white){
                tileToMove.setPiece(new Queen(white));
            }
            else{
                tileToMove.setPiece(new Queen(black));
            }
        }//
    }//pawnCoronation
    
    /**
     * Method that flips the current player status on the game.
     */
    public void nextPlayerTurn(){
        
        boolean playerOneStatus = !(playerOne.getTurnToPlay());
        boolean playerTwoStatus = !(playerTwo.getTurnToPlay());
        
        playerOne.setTurnToPlay(playerOneStatus);
        playerTwo.setTurnToPlay(playerTwoStatus);
    }//nextPlayerTurn
    
    /**
     * This method looks for every legal direction to which the king may be in danger .
     * 
     * @return whether or not the white king is in check.
     */
    public boolean isKingWhiteChecked(){
        boolean checked = false;
        boolean safeSquare = false;
        int[] kingCoords = searchForPiece("king", white);
        
        int kingRow = kingCoords[0];
        int kingCol = kingCoords[1];
        // board[row][col].getPiece().getName().equals("pawn")
        
        try{
            int row = kingRow - 1;
            int col = kingCol - 1;
            
            if( board[row][col].getPiece().getName().equals("pawn") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//pawnLeft
        
        try{
            int row = kingRow - 1;
            int col = kingCol + 1;
            
            if( board[row][col].getPiece().getName().equals("pawn") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//pawnLeft
        
        try{
            int row = kingRow + 2;
            int col = kingCol - 1;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow + 2;
            int col = kingCol + 1;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow + 1;
            int col = kingCol - 2;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow + 1;
            int col = kingCol + 2;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow - 1;
            int col = kingCol + 2;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow - 1;
            int col = kingCol - 2;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow - 2;
            int col = kingCol + 1;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow - 2;
            int col = kingCol - 1;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(black)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        safeSquare = false;
        
        try{
        for(int row = kingRow; row < board.length && safeSquare == false; ){
            
            row++;
            if(board[row][kingCol].getPiece() != null){
                
               if(board[row][kingCol].getPiece().getFactionColor() == white){
                   safeSquare = true;
               }//
               else if( (board[row][kingCol].getPiece().getName().equals("queen") || board[row][kingCol].getPiece().getName().equals("tower")) ){
                   return true;
               }
           
            }
        }//
        }catch(Exception e){
        }//up
        safeSquare = false;
        
        try{
        for(int row = kingRow; row < board.length && safeSquare == false; ){
            
            row--;
            if(board[row][kingCol].getPiece() != null){
                
               if(board[row][kingCol].getPiece().getFactionColor() == white){
                   safeSquare = true;
               }//
               else if( (board[row][kingCol].getPiece().getName().equals("queen") || board[row][kingCol].getPiece().getName().equals("tower")) ){
                   return true;
               }
           
            }
        }//
        }catch(Exception e){
        }//down
        safeSquare = false;
        
        try{
        for(int col = kingCol; col < board[0].length && safeSquare == false; ){
            
            col++;
            if(board[kingRow][col].getPiece() != null){
                
               if(board[kingRow][col].getPiece().getFactionColor() == white){
                   safeSquare = true;
               }//
               else if( (board[kingRow][col].getPiece().getName().equals("queen") || board[kingRow][col].getPiece().getName().equals("tower")) ){
                   return true;
               }
           
            }
        }//
        }catch(Exception e){
        }//right
        safeSquare = false;
        
        try{
        for(int col = kingCol; col < board[0].length && safeSquare == false; ){
            
               col--;
            if(board[kingRow][col].getPiece() != null){
                
               if(board[kingRow][col].getPiece().getFactionColor() == white){
                   safeSquare = true;
               }//
               else if( (board[kingRow][col].getPiece().getName().equals("queen") || board[kingRow][col].getPiece().getName().equals("tower")) ){
                   return true;
               }
           
            }
        }//
        }catch(Exception e){
        }//left
        safeSquare = false;
        
        try{  
            for(int row = kingRow; row < board.length && safeSquare == false; ){
                for(int col = kingCol; col < board[0].length && safeSquare == false; ){
                    row++;
                    col--;
                    if(board[row][col].getPiece() != null){
                        
                        if(board[row][col].getPiece().getFactionColor() == white){
                        safeSquare = true;
                         }//
                        else if( (board[row][col].getPiece().getName().equals("queen") || board[row][col].getPiece().getName().equals("bishop") ) ){
                        return true;
                        }
                    
                    }
                }//
            }
        }catch(Exception e){
        }//DiagonalLeftUp
        safeSquare = false;
        
        try{  
            for(int row = kingRow; row < board.length && safeSquare == false; ){
                for(int col = kingCol; col < board[0].length && safeSquare == false; ){
                    row++;
                    col++;
                    if(board[row][col].getPiece().getFactionColor() == white){
                        safeSquare = true;
                    }//
                    else if( (board[row][col].getPiece().getName().equals("queen") || board[row][col].getPiece().getName().equals("bishop") ) ){
                        return true;
                    }
                }//
            }
        }catch(Exception e){
        }//DiagonalRightUp
        safeSquare = false;
        
        try{  
            for(int row = kingRow; row < board.length && safeSquare == false; ){
                for(int col = kingCol; col < board[0].length && safeSquare == false; ){
                    row--;
                    col++;
                    if(board[row][col].getPiece() != null){
                        
                    if(board[row][col].getPiece().getFactionColor() == white){
                        safeSquare = true;
                    }//
                    else if( (board[row][col].getPiece().getName().equals("queen") || board[row][col].getPiece().getName().equals("bishop") ) ){
                        return true;
                    }
                    
                    }
                }//
            }
        }catch(Exception e){
        }//DiagonalRightDown
        safeSquare = false;
        
        try{  
            for(int row = kingRow; row < board.length && safeSquare == false; ){
                for(int col = kingCol; col < board[0].length && safeSquare == false; ){
                    row--;
                    col--;
                    if(board[row][col].getPiece() != null){
                        
                    if(board[row][col].getPiece().getFactionColor() == white){
                        safeSquare = true;
                    }//
                    else if( (board[row][col].getPiece().getName().equals("queen") || board[row][col].getPiece().getName().equals("bishop") ) ){
                        return true;
                    }
                    
                    }
                }//
            }
        }catch(Exception e){
        }//DiagonalLeftDown
        safeSquare = false;
        
        return checked;
    }//isKingWhiteChecked
    
    /**
     * This method looks for every legal direction to which the king may be in danger.
     * 
     * @return whether or not the black king is in check.
     */
    public boolean isKingBlackChecked(){
        boolean checked = false;
        boolean safeSquare = false;
        int[] kingCoords = searchForPiece("king", black);
        
        int kingRow = kingCoords[0];
        int kingCol = kingCoords[1];
        
        try{
            int row = kingRow + 1;
            int col = kingCol - 1;
            
            if( board[row][col].getPiece().getName().equals("pawn") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//pawnLeft
        
        try{
            int row = kingRow + 1;
            int col = kingCol + 1;
            
            if( board[row][col].getPiece().getName().equals("pawn") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//pawnLeft
        
        try{
            int row = kingRow + 2;
            int col = kingCol - 1;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow + 2;
            int col = kingCol + 1;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow + 1;
            int col = kingCol - 2;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow + 1;
            int col = kingCol + 2;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow - 1;
            int col = kingCol + 2;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow - 1;
            int col = kingCol - 2;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow - 2;
            int col = kingCol + 1;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
            int row = kingRow - 2;
            int col = kingCol - 1;
            
            if( board[row][col].getPiece().getName().equals("horse") &&  board[row][col].getPiece().getFactionColor().equals(white)){//*
                   return true;
               }
        }catch(Exception e){   
        }//horse
        
        try{
        for(int row = kingRow; row < board.length && safeSquare == false; ){
            
            row++;
            if(board[row][kingCol].getPiece() != null){
               if(board[row][kingCol].getPiece().getFactionColor().equals(black)){
                   safeSquare = true;
               }//
               else if( (board[row][kingCol].getPiece().getName().equals("queen") || board[row][kingCol].getPiece().getName().equals("tower")) ){
                   return true;
               }
            }
        }//
        }catch(Exception e){
        }//up
        safeSquare = false;
        
        try{
        for(int row = kingRow; row < board.length && safeSquare == false; ){
            
            row--;
            if(board[row][kingCol].getPiece() != null){
               if(board[row][kingCol].getPiece().getFactionColor().equals(black)){
                   safeSquare = true;
               }//
               else if( (board[row][kingCol].getPiece().getName().equals("queen") || board[row][kingCol].getPiece().getName().equals("tower")) ){
                   return true;
               }
            }
        }//
        }catch(Exception e){
        }//down
        safeSquare = false;
        
        try{
        for(int col = kingCol; col < board[0].length && safeSquare == false; ){
            
            col++;
            if(board[kingRow][col].getPiece() != null){
               if(board[kingRow][col].getPiece().getFactionColor().equals(black)){
                   safeSquare = true;
               }//
               else if( (board[kingRow][col].getPiece().getName().equals("queen") || board[kingRow][col].getPiece().getName().equals("tower")) ){
                   return true;
               }
            }
        }//
        }catch(Exception e){
        }//right
        safeSquare = false;
        
        try{
        for(int col = kingCol; col < board[0].length && safeSquare == false; ){
            
            col--;
            if(board[kingRow][col].getPiece() != null){
               if(board[kingRow][col].getPiece().getFactionColor().equals(black)){
                   safeSquare = true;
               }//
               else if( (board[kingRow][col].getPiece().getName().equals("queen") || board[kingRow][col].getPiece().getName().equals("tower")) ){
                   return true;
               }
            }
        }//
        }catch(Exception e){
        }//left
        safeSquare = false;
        
        try{  
            for(int row = kingRow; row < board.length && safeSquare == false; ){
                for(int col = kingCol; col < board[0].length && safeSquare == false; ){
                    row++;
                    col--;
                    if(board[row][col].getPiece() != null){
                        
                    if(board[row][col].getPiece().getFactionColor().equals(black)){
                        safeSquare = true;
                    }//
                    else if( (board[row][col].getPiece().getName().equals("queen") || board[row][col].getPiece().getName().equals("bishop") || board[row][col].getPiece().getName().equals("pawn") ) ){
                        return true;
                    }
                    
                    }
                }//
            }
        }catch(Exception e){
        }//DiagonalLeftUp
        safeSquare = false;
        
        try{  
            for(int row = kingRow; row < board.length && safeSquare == false; ){
                for(int col = kingCol; col < board[0].length && safeSquare == false; ){
                    row++;
                    col++;
                    if(board[row][col].getPiece() != null){
                        
                    
                    if(board[row][col].getPiece().getFactionColor().equals(black)){
                        safeSquare = true;
                    }//
                    else if( (board[row][col].getPiece().getName().equals("queen") || board[row][col].getPiece().getName().equals("bishop") || board[row][col].getPiece().getName().equals("pawn") ) ){
                        return true;
                    }
                    
                }
                }//
            }
        }catch(Exception e){
        }//DiagonalRightUp
        safeSquare = false;
        
        try{  
            for(int row = kingRow; row < board.length && safeSquare == false; ){
                for(int col = kingCol; col < board[0].length && safeSquare == false; ){
                    row--;
                    col++;
                    if(board[row][col].getPiece() != null){
                        
                    if(board[row][col].getPiece().getFactionColor().equals(black)){
                        safeSquare = true;
                    }//
                    else if( (board[row][col].getPiece().getName().equals("queen") || board[row][col].getPiece().getName().equals("bishop") || board[row][col].getPiece().getName().equals("pawn") ) ){
                        return true;
                    }
                    
                    }
                }//
            }
        }catch(Exception e){
        }//DiagonalRightDown
        safeSquare = false;
        
        try{  
            for(int row = kingRow; row < board.length && safeSquare == false; ){
                for(int col = kingCol; col < board[0].length && safeSquare == false; ){
                    row--;
                    col--;
                    if(board[row][col].getPiece() != null){
                        
                    if(board[row][col].getPiece().getFactionColor().equals(black)){
                        safeSquare = true;
                    }//
                    else if( (board[row][col].getPiece().getName().equals("queen") || board[row][col].getPiece().getName().equals("bishop") || board[row][col].getPiece().getName().equals("pawn") ) ){
                        return true;
                    }
                    
                    }
                }//
            }
        }catch(Exception e){
        }//DiagonalLeftDown
        safeSquare = false;
        
        return checked;
    }//isKingBlackChecked
    
    /**
     * Searching method that is used to find the first instance of a type of Piece object on the board field,
     * it starts at (0,0).
     * 
     * @param pieceName Identifier name for the searched piece.
     * @param pieceFaction Faction color of the searched piece.
     * @return an array of order row, col; with the board coordinates of the Piece object.
     */
    public int[] searchForPiece(String pieceName, Color pieceFaction){
        int rowCoor = -1;
        int colCoor = -1;
        boolean found = false;
        
        for(int row = 0; row < board.length && found == false; row++){
           for(int col = 0; col < board[0].length && found == false; col++){
                
               if(board[row][col].getPiece() != null){
                   if(board[row][col].getPiece().getName().equals(pieceName) && board[row][col].getPiece().getFactionColor().equals(pieceFaction)){
                       rowCoor = row;
                       colCoor = col;
                       found = true;
                   }//
               }//
           }
        }
        int[] pieceCoords = {rowCoor, colCoor};
        
        return pieceCoords;
    }//searchForPiece

    /**
     * This method is used to call for all the methods that make up the behavior of the "MAX-ONLY" AI;
     * additionally, it has a protection system that only allows it to function at <b>this.getPlayer().equal("Player Two")</b>.
     * 
     */
    public void callAiToMove(){
        Player player = this.getPlayer();
        
        if( !(player.getPlayerName().equals("Player one")) ){
            
            allMoveScenarios(player);
            
            
        }//2
        
    }//callAiToMove
    
    /**
     * Private method called after <b> callAiToMove()</b> is called, it fills all the possible moves for 
     * the current board instance for the player and gives them a value; furthermore, it fills the moves into 
     * <b> AiMoveNode</b> objects.
     * 
     * @param player The player which the machine shall predict its moves.
     * 
     * @see AiMoveNode
     */
    private void allMoveScenarios(Player player) {
        Color piecesFaction = player.getFaction();
        this.pieceNodeList = new ArrayList<AiMoveNode>();
                
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                
                if(board[row][col].getPiece() != null){
                    if(board[row][col].getPiece().getFactionColor().equals(player.getFaction()) ){
                   
                        allPosibleMovesPiece(board[row][col]);
                    }//Color Barrier
                }
                
            }
         }//
        
        int finalFirstMove = selectTheBestMove(0, 0, 0);
        
        movePieceAi(finalFirstMove);
        
        }//allMoveScenarios
    
    /**
     * Method that acts as the final step on the AI processing chain, it selects the node to activate and moves its properties
     * on the <b>ChessBoard</b> instance.
     * 
     * @param finalFirstMove the index which to extract the <b>AiMoveNode</b> that pertains to the best move currently from the pieceNodeList field.
     */
    public void movePieceAi(int finalFirstMove){
        
        AiMoveNode moveNode = pieceNodeList.get(finalFirstMove);
        
        Tile tempFrom = moveNode.getFromTile();
        Tile tempTo = moveNode.getToTile();
        
        this.aiFrom = tempFrom;
        this.aiTo = tempTo;
        
        int fromCol = tempFrom.getX();
        int fromRow = tempFrom.getY();
        
        int toCol = tempTo.getX();
        int toRow = tempTo.getY();
        
        
        this.movePiece(board[fromRow][fromCol], board[toRow][toCol]);
        
    }//movePieceAi
    
    /**
     * Accessor method that retrieves the Tile from which the AI final move will be from.
     * 
     * @return The final Tile <b>from which</b> the AI will make a move from.
     */
    public Tile getAiFrom(){
        return this.aiFrom;
    }//getAiFrom
    
    /**
     *
     * @return the final Tile <b>to which</b> the AI will make a move to.
     */
    public Tile getAiTo(){
        return this.aiTo;
    }//getAiTo
    
    /**
     * Private recursive call that transverses the pieceNodeList field in order to track the most valuable move and register the node's <b>index</b>.
     * 
     * @param initialIndex It will always intake a value of 0 from the calling method; where to begin the search from.
     * @param highestNum It will always intake a value of 0 from the calling method; the highest value found.
     * @param numIndex It will always intake a value of 0 from the calling method, the final index of the node where the best move is held.
     * 
     * @return The index of the node for the AI to choose to activate.
     */
    private int selectTheBestMove(int initialIndex, int highestNum, int numIndex){
        
        if(initialIndex == (pieceNodeList.size() - 1) ){
            if(pieceNodeList.get(initialIndex).getMoveValue() > highestNum){
                
                numIndex = initialIndex;
            }
            if(highestNum == 0){
                return (int)(Math.random()* (pieceNodeList.size() - 1));
            }
            
            return numIndex;
        }
        else{
            if(pieceNodeList.get(initialIndex).getMoveValue() > highestNum){
                
                numIndex = initialIndex;
                highestNum = pieceNodeList.get(initialIndex).getMoveValue();
                
                
            }
            return selectTheBestMove( (initialIndex + 1), highestNum, numIndex);
        }
        
        
    }//selectTheBestMove
    
    /**
     * Private method that acts as a caller for a piece on a selected Tile object to find every possible and legal move avalible at the time.
     * 
     * @param tileSelected The Tile of the Piece object to be analyzed.
     */
    private void allPosibleMovesPiece(Tile tileSelected) {
       Piece piece = tileSelected.getPiece();
       int[][] moveSet = piece.getMoveSet();
       
       for(int i = 0; i < moveSet.length; i++){
           
           AiMove(tileSelected, moveSet[i]);
           
       }//
       
       
       
    }//allPosibleMoves
    
    /**
     * Private method that calls <b>checkMoveAi</b> to validate, and if legal, save the move to the <b>AiMoveNode</b> list.
     * 
     * @param tileSelected The tile of the piece to analyze.
     * @param performingMove The move to validate and perform.
     * 
     */
    private void AiMove(Tile tileSelected, int[] performingMove) {
        
        int initialRow = tileSelected.getY();
        int initialCol = tileSelected.getX();
        
        
        int newRow = initialRow + (performingMove[0] * -1);
        int newCol = initialCol + performingMove[1];
        
        Tile goToTile = null;
        try {
            goToTile = board[newRow][newCol];
        } catch (Exception e) {
        return;
        }
        
        
        if(goToTile.getPiece() == null){
            checkMoveAi(tileSelected, goToTile);
            if(didItMove == true){
                AiMoveNode noMove = new AiMoveNode(tileSelected, goToTile, getPlayer().getFaction());
                noMove.setMoveValue(0);
                pieceNodeList.add(noMove);
            }
        }
        else{
            
            checkMoveAi(tileSelected, goToTile);
            
            if(this.getDidItMove()){
                AiMoveNode move = new AiMoveNode(tileSelected, goToTile, getPlayer().getFaction());
                move.setMoveValue(goToTile.getPiece().getValue());
                pieceNodeList.add(move);
            }//
        }
        
    }//AiMove
    
    /**
     * Alias to the <b>movePiece(..)</b> method, this method follows the same verification processes of calling <b>isValidMove(), isLegalMove(), isValidPawnEat()</b>,
     * in order to verify the validity of the move chosen and displaying the result on the <b>didItMove</b> field; however, without actually moving
     * the selected piece.
     * 
     * @param tileOfPiece Tile of the piece to move from.
     * @param tileToMove Tile of the piece to move to.
     */
    public void checkMoveAi(Tile tileOfPiece, Tile tileToMove){
        Player playerMoving = getPlayer();
        
        Piece fromPiece = tileOfPiece.getPiece();
        int fromCordX = tileOfPiece.getX();
        int fromCordY = tileOfPiece.getY();
        
        Piece toPiece = tileToMove.getPiece();
        int toCordX = tileToMove.getX();
        int toCordY = tileToMove.getY();
        
        int performingMoveX = (toCordX - fromCordX);
        int performingMoveY = (fromCordY - toCordY);
     
        int[][] moveSet = fromPiece.getMoveSet();
        int[] moveToPerform = {performingMoveY, performingMoveX};
        
        if(fromPiece.toString().equals("pawn")){
            if(fromPiece.getFirstTimeMoved() && (performingMoveX == 0) && (performingMoveY == -2 || performingMoveY == 2) && isLegalMove(tileOfPiece, tileToMove, fromPiece, toPiece, playerMoving, moveToPerform)){
                    
                    didItMove = true;
                    
            }//pawn double
            else if(isValidMove(fromPiece, toPiece, moveToPerform, moveSet, playerMoving) == true){
                if(toPiece == null && (isValidPawnEat(fromPiece, toPiece, moveToPerform, moveSet, playerMoving))){
                   
                    didItMove = false;
            //CALLS FOR THE MOVEPIECE LISTENER AGAIN AND MOVEPIECE*************************//
                }
                else if(toPiece != null && !(isValidPawnEat(fromPiece, toPiece, moveToPerform, moveSet, playerMoving)) ){
                    
                    didItMove = false;
            //CALLS FOR THE MOVEPIECE LISTENER AGAIN AND MOVEPIECE*************************//
                }
                else if(toPiece == null && !(isValidPawnEat(fromPiece, toPiece, moveToPerform, moveSet, playerMoving)) && isLegalMove(tileOfPiece, tileToMove, fromPiece, toPiece, playerMoving, moveToPerform)){
                   
                    didItMove = true;
                    
                }
                else if(toPiece != null && isValidPawnEat(fromPiece, toPiece, moveToPerform, moveSet, playerMoving)){
                    didItMove = true;
                }
            }//pawn eating
            else if( !(isValidMove(fromPiece, toPiece, moveToPerform, moveSet, playerMoving)) ){
               
                didItMove = false;
            }
        }//pawn
        else if(isValidMove(fromPiece, toPiece, moveToPerform, moveSet, playerMoving) && isLegalMove(tileOfPiece, tileToMove, fromPiece, toPiece, playerMoving, moveToPerform)){
                
                
                didItMove = true;
                
        }//
        else{
            
            didItMove = false;
            //CALLS FOR THE MOVEPIECE LISTENER AGAIN AND MOVEPIECE*************************//
        }
       // pawnCoronation(tileToMove);
        
    }//checkMoveAi

    
}//ChessBoard