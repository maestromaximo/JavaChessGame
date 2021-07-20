package game;

/**
 * Final Static class whose purpose is to import the constant values for the Piece children classes;
 * the class provides with the name identifiers, value, and move sets for each Piece children.
 * 
 * @author Alejandro Agustin Garcia Polo
 * @version 1.4V February 27, 2020
 * 
 * @see Piece
 */
public final class ConstantClass {
    
    public static final String PAWN = "pawn";
    public static final String QUEEN = "queen";
    public static final String TOWER = "tower";
    public static final String HORSE = "horse";
    public static final String KING = "king";
    public static final String BISHOP = "bishop";
    
    public static final int VAL_PAWN = 1;
    public static final int VAL_QUEEN = 9;
    public static final int VAL_TOWER = 5;
    public static final int VAL_HORSE = 3;
    public static final int VAL_KING = 100;
    public static final int VAL_BISHOP = 4;
    
    public static final int[][] MOV_PAWN_WHITE = {{1,0},{1,-1},{1,1}};//forward,eat_left,eat_right
    public static final int[][] MOV_PAWN_BLACK = {{-1,0},{-1,-1},{-1,1}};//backwards,eat_left,eat_right
    
    public static final int[][] MOV_HORSE = {{1,2},{1,-2},{2,1},{2,-1},/**/{-1,2},{-1,-2},{-2,1},{-2,-1}};//lowLR,lowLL,highLR,highLL,inverse:
    public static final int[][] MOV_KING = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};//clockWise_starting_up
    
    public static final int[][] MOV_QUEEN = {{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0},/**/
                                            {-1,0},{-2,0},{-3,0},{-4,0},{-5,0},{-6,0},{-7,0},/**/
                                            {0,-1},{0,-2},{0,-3},{0,-4},{0,-5},{0,-6},{0,-7},/**/
                                            {0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},/**/
    
                                            {1,-1},{2,-2},{3,-3},{4,-4},{5,-5},{6,-6},{7,-7},/**/
                                            {1,1},{2,2},{3,3},{4,4},{5,5},{6,6},{7,7},/**/
                                            {-1,-1},{-2,-2},{-3,-3},{-4,-4},{-5,-5},{-6,-6},{-7,-7},/**/
                                            {-1,1},{-2,2},{-3,3},{-4,4},{-5,5},{-6,6},{-7,7}};//up,down,left,right//dia_upLeft,dia_upRight,dia_downLeft,dia_downRight
    
    public static final int[][] MOV_TOWER = {{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0},/**/
                                            {-1,0},{-2,0},{-3,0},{-4,0},{-5,0},{-6,0},{-7,0},/**/
                                            {0,-1},{0,-2},{0,-3},{0,-4},{0,-5},{0,-6},{0,-7},/**/
                                            {0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}  };//up,down,left,right
    
    public static final int[][] MOV_BISHOP = {{1,-1},{2,-2},{3,-3},{4,-4},{5,-5},{6,-6},{7,-7},/**/
                                              {1,1},{2,2},{3,3},{4,4},{5,5},{6,6},{7,7},/**/
                                              {-1,-1},{-2,-2},{-3,-3},{-4,-4},{-5,-5},{-6,-6},{-7,-7},/**/
                                              {-1,1},{-2,2},{-3,3},{-4,4},{-5,5},{-6,6},{-7,7} };//dia_upLeft,dia_upRight,dia_downLeft,dia_downRight
    
            
}//ConstantClass
