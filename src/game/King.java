
package game;

import static game.ConstantClass.*;
import java.awt.Color;
import java.io.Serializable;

/**
 * King piece class for the creation of a King Object, implements Serializable
 * 
 * @see Piece
 * @author Alejandro Agustin Garcia Polo
 * @version 1.9V February 27, 2020
 */
public class King extends Piece implements Serializable{
    private int[][] moveSet;
   // private boolean isChecked;
    
    /**
     * Constructor sets the <b>name</b>, <b>value</b>, and <b>faction</b> color of the King Object.
     * 
     * @param factionColor the faction color of the King Object.
     * 
     */
    public King(Color factionColor) {
        super(KING, VAL_KING, factionColor);
        
        //this.isChecked = false;
        this.moveSet = MOV_KING;
        
    }//Constructor
    
    @Override
    public int[][] getMoveSet(){
        return this.moveSet;
    }//getMoveSet
    
   
}//Bishop
