
package game;

import static game.ConstantClass.*;
import java.awt.Color;
import java.io.Serializable;

/**
 * Horse piece class for the creation of a Horse Object, implements serializable.
 * 
 * @see Piece
 * @author Alejandro Agustin Garcia Polo
 * @version 1.2V February 27, 2020
 */
public class Horse extends Piece implements Serializable {
    int[][] moveSet;
    
    /**
     * Constructor sets the <b>name</b>, <b>value</b>, and <b>faction</b> color of the Horse Object.
     * 
     * @param factionColor the faction color of the Horse object.
     * 
     */
    public Horse(Color factionColor) {
        super(HORSE, VAL_HORSE, factionColor);
        
        this.moveSet = MOV_HORSE;
        
    }//Constructor
    
    @Override
    public int[][] getMoveSet(){
        return this.moveSet;
    }//getMoveSet
    
}//Horse
