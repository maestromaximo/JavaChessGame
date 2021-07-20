
package game;

import java.awt.Color;

/**
 * Class responsible for holding an AI moves identifiers.
 * 
 * @author Alejandro Agustin Garcia Polo
 * @version 1.2V February 27, 2020.
 * 
 * @see ChessBoard
 * @see GraphicsTwoChess
 */
public class AiMoveNode {
    private Tile fromTile, toTile;
    private Color factionColor;
    private int moveValue;
    
    /**
     * Constructor for the <b>AiMoveNode</b> class that creates a node that effectively holds a plausible move and
     * its subsequent value.
     * 
     * @param fromTile The tile from which the piece shall move from.
     * @param toTile The tile to which the piece shall move too.
     * @param factionColor The faction color of the piece in question.
     */
    public AiMoveNode(Tile fromTile, Tile toTile, Color factionColor){
        
        this.fromTile = fromTile;
        this.toTile = toTile;
        this.factionColor = factionColor;
        
    }//Constructor
    
    /**
     * Accessor method that retrieves the tile from which to move from.
     * 
     * @return The tile from which the piece shall move from.
     */
    public Tile getFromTile(){
        return this.fromTile;
    }//getFromTile
    
    /**
     * Accessor method that retrieves the tile to which to move to.
     * 
     * @return The tile to which the piece shall move too.
     */
    public Tile getToTile(){
        return this.toTile;
    }//getToTile
    
    /**
     * Accessor method in charge of retrieving the value that the move would give if done.
     * 
     * @return The value (the value of the piece eaten) that the player will acquire if activated.
     */
    public int getMoveValue(){
        return this.moveValue;
    }//getMoveValue
    
    /**
     * Mutator method in charge of assigning the value that the move would have if done.
     * 
     * @param moveValue The value (the value of the piece eaten) that the player will acquire if activated.
     */
    public void setMoveValue(int moveValue){
        this.moveValue = moveValue;
    }//setMoveValue
}//AiMoveNode
