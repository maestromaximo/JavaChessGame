package game;

import static game.ConstantClass.*;
import java.awt.Color;
import java.io.Serializable;

/**
 * Bishop piece class for the creation of a Bishop Object, implements serializable.
 * 
 * @see Piece
 * @author Alejandro Agustin Garcia Polo
 * @version 1.2V February 27, 2020
 */
public class Bishop extends Piece implements Serializable{
    int[][] moveSet;
    
    /**
     * Constructor sets the <b>name</b>, <b>value</b>, and <b>faction</b> color of the Bishop Object.
     * 
     * @param factionColor the faction color of the Bishop Object.
     * 
     */
    public Bishop(Color factionColor) {
        super(BISHOP, VAL_BISHOP, factionColor);
        this.moveSet = MOV_BISHOP;
        
    }//Constructor
    
    @Override
    public int[][] getMoveSet(){
        return this.moveSet;
    }//getMoveSet
    
}//Bishop
