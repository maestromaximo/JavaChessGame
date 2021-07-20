package game;

import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;//
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectOutputStream;

/**
 * Main body Class for the graphical interface of the Two player selection for the chess App. It implements the Serializable interface 
 * and extends JPanel.
 * 
 * @author Alejandro Agustin Garcia Polo
 * @version 7.9V February 27, 2020
 */
public class GraphicsTwoChess extends JPanel implements Serializable {
    private transient SoundClass makeSound;
    private static final int TILE_SIDE_DIMENSION = 75;
    private static final int IMAGE_SIDE_DIMENSION = 60;
    private static final Color BLACK_BROWN = new Color(64, 47, 6);
    private Point firstClick, nextClick;
    private Piece selectedPiece;
    private Tile selectedTile, nextTile;
    private boolean isFirstClick, firstTime, playerOneTimeLost, playerTwoTimeLost;
    private int[] nextProperClick, lastProperClick, translatedLast, translatedNext;
    //private Timer timer = new Timer();
    //private Graphics panelPaint;
    private JLabel timerLabel, timerLabel2, whichPlayerLabel;
    private Thread timerThread, timerThread2;
    private int sessionCounter;
    
    
    private ChessBoard board = new ChessBoard();
    
    private JLabel labelPawnBlack0, labelPawnBlack1, labelPawnBlack2,
            labelPawnBlack3, labelPawnBlack4, labelPawnBlack5, labelPawnBlack6, labelPawnBlack7; 
    
    private JLabel labelQueenWhite, labelHorse0White, labelHorse1White, labelKingWhite, labelBishop0White,
            labelBishop1White, labelTower0White, labelTower1White;
    
    private JLabel labelQueenBlack, labelHorse0Black, labelHorse1Black, labelKingBlack, labelBishop0Black,
            labelBishop1Black, labelTower0Black, labelTower1Black;
    
    private JLabel labelPawnWhite0, labelPawnWhite1, labelPawnWhite2,
            labelPawnWhite3, labelPawnWhite4, labelPawnWhite5, labelPawnWhite6, labelPawnWhite7; 
    
    
    
    private ImageIcon iconQueenWhite, iconHorseWhite, iconKingWhite, iconBishopWhite,
             iconTowerWhite, iconPawnWhite;
    
    private ImageIcon iconQueenBlack, iconHorseBlack, iconKingBlack, iconBishopBlack,
            iconTowerBlack, iconPawnBlack ;
    
    /**
     * Constructor for the GraphicsTwoChess class, it adds a mouse listener for the JPanel and initializes logical fields.
     * 
     */
    public GraphicsTwoChess(){
        
        this.playerTwoTimeLost = false;
        this.playerOneTimeLost = false;
        this.sessionCounter = 0;
        this.makeSound = new SoundClass();
        this.firstTime = true;
        this.isFirstClick = true;
        this.firstClick = new Point();
        this.nextClick = new Point();
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boardMouseClicked(evt);
            }
        });
        
        
    }//Constructor 
    
    /**
     * Method called after the Class is de-serialized in order to re-insatiate transient-like fields and event listeners.
     * 
     */
    public void loadGameConstructor(){
        this.makeSound = new SoundClass();
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boardMouseClicked(evt);
            }
        });
    }//loadGameConstructor
    
    /**
     * Private method called after a click on the board happens, if its the first click: it sets that click as the selected click and 
     * selects whichever piece is at that location; if it is called after it sets the first click, then the latter is set as the next click,
     * from there it moves the selected piece to the selected tile.
     * 
     * @param evt the click event.
     */
    private void boardMouseClicked(java.awt.event.MouseEvent evt) {                                     
        int x = (int)evt.getPoint().getX();
        int y = (int)evt.getPoint().getY();
        
        
        
        
         synchronized(GraphicsTwoChess.this){
              
              if(isFirstClick){
                    firstClick.setLocation(x, y);
                    selectPiece(x, y);
              }else{
                  try{
                    nextClick.setLocation(x, y); 
                    movePieceTo(x, y);
                    //timerSelection();
                  }catch(Exception NullPointerException){
                      board.printBoard();
                      isFirstClick = true;
                      
                  }
              }
              GraphicsTwoChess.this.notifyAll();
          }
         
    }//boardMouseClicked        
    
    /**
     * Private method called to select the tile selected in regards to the
     * board field.
     * 
     * @param col colum clicked.
     * @param  row row clicked.
     */
    private void selectPiece(int col, int row){
        
        
        
        
        lastProperClick = runTileInverseParameters(col, row);
        
        
        selectedTile = board.getBoard()[lastProperClick[0]][lastProperClick[1]];
        
        isFirstClick = false;
        //lightUpSelected(selectedTile);
    }//selectPiece
    
    
    /**
     * Method in charge of verifying that is a valid move, and if it, 
     * of calling for the movement of the graphics of the class; additionally, 
     * it checks for both Kings checked and checkmate status.
     * 
     * @param col col to move to.
     * @param row row to move to.
     * 
     */
    public void movePieceTo(int col, int row){
        board.nextPlayerTurn();
        this.whichPlayerLabel.setText(board.getPlayer().getPlayerName());
        board.nextPlayerTurn();
        
        nextProperClick = runTileInverseParameters(col, row);
        
        nextTile = board.getBoard()[nextProperClick[0]][nextProperClick[1]];
        
        board.movePiece(selectedTile, nextTile);
        
        if(board.getDidItMove()){
            moveGraphics();
            makeSound.pieceMoved();
            if(board.isKingBlackChecked()){
                makeSound.check();
                System.out.println("checkBlack");
            }//
            if(board.isKingWhiteChecked()){
                makeSound.check();
                System.out.println("checkWhite");
            }//
        }
        board.nextPlayerTurn();
        if(board.getPlayer().isKingEaten()){
            makeSound.checkMate();
            
            if(board.getPlayer().getPlayerName().equals("Player One")){
                whichPlayerLabel.setText("Player two Wins.");
                resetGame();
            }
            else{
                whichPlayerLabel.setText("Player one Wins.");
                resetGame();
            }
        }
        board.nextPlayerTurn();
        
        makeSound.wrongMove();
        isFirstClick = true;
    }//movePieceTo
    
    /**
     * Private method in charge of creating the moving graphical displacement of the JLabel components of the JPanel, as well as
     * displaying pawn coronation.
     * 
     */
    private void moveGraphics(){
        
        JLabel componentAt = (JLabel)this.getComponentAt(firstClick);
        Component componentTo = this.getComponentAt(nextClick);
        
            if( (componentAt.getIcon().equals(iconPawnBlack) || componentAt.getIcon().equals(iconPawnWhite)) && (nextTile.getY() == 0 || nextTile.getY() == 7) ){
                
                if(componentAt.getIcon().equals(iconPawnBlack)){
                    componentAt.setIcon(iconQueenBlack);
                    componentAt.setBounds(nextTile.getLabelCoordinates()[1], nextTile.getLabelCoordinates()[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
                    if(componentTo instanceof JLabel){
                        componentTo.setBounds(800, 800, 60, 60);
                    }
                }else{
                    componentAt.setIcon(iconQueenWhite);
                    componentAt.setBounds(nextTile.getLabelCoordinates()[1], nextTile.getLabelCoordinates()[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
                    if(componentTo instanceof JLabel){
                        componentTo.setBounds(800, 800, 60, 60);
                    }
                }
                
            }//coronation
            else if(componentTo instanceof JLabel){
               // componentTo.setVisible(false);
                componentTo.setBounds(800, 800, 60, 60);
                componentAt.setBounds(nextTile.getLabelCoordinates()[1], nextTile.getLabelCoordinates()[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
            
            }//
            else{
                componentAt.setBounds(nextTile.getLabelCoordinates()[1], nextTile.getLabelCoordinates()[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
            }
        this.revalidate();
        this.repaint();
    }//moveGraphics
    
    /**
     * This method translates the pixel axis' to board field axis'.
     * 
     * @return The new Coordinates in the row, col format.
     */
    private int[] runTileInverseParameters(int col, int row){
        int[] boardClick = new int[2];
        int boardRow = row / 75;
        int boardCol = col / 75;
        
        boardClick[0] = boardRow;
        boardClick[1] = boardCol;
        
        return boardClick;
    }//runTileParameters
    
    /**
     * Ran after runTileInverseParameters, this method sets the translated coordinates for the Class.
     * 
     * @deprecated 
     */
    private void translateParameters(){
        translatedLast[0] = lastProperClick[0];
        translatedNext[1] = nextProperClick[0];
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        //panelPaint = g;
        super.paintComponent(g);
        int row = 0;
        int col = 0;
        
        for(int y = 0; y < 600; y += 75){
            for(int x = 0; x <  600; x += 75){
                
                Color tileColor = board.getBoard()[row][col].getFaction();
                g.setColor(tileColor);
                g.fillRect(x, y, TILE_SIDE_DIMENSION, TILE_SIDE_DIMENSION);
                col++;
            }
            col = 0;
            row++;
        }//
        
        g.setColor(BLACK_BROWN);
        g.fillRect(0, 600, 650, 70);
        g.fillRect(600, 0, 50, 670);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 600, 600, 10);
        g.fillRect(600, 0, 10, 610);
        
        setLayout( null );
        if(firstTime){
            addLabels();
            firstTime = false;
        }
    }//paintComponent

    /**
     * This private method adds all of the JLabels created on the class into the JPanel.
     * 
     */
   private void addLabels(){
       initializeIcons();
       initializeLabels();
       setLabelBounds();
        
       
       
       
       this.add(labelPawnBlack0);
       this.add(labelPawnBlack1);
       this.add(labelPawnBlack2);
       this.add(labelPawnBlack3);
       this.add(labelPawnBlack2);
       this.add(labelPawnBlack3);
       this.add(labelPawnBlack4);
       this.add(labelPawnBlack5);
       this.add(labelPawnBlack6);
       this.add(labelPawnBlack7);
       
       this.add(labelPawnWhite0);
       this.add(labelPawnWhite1);
       this.add(labelPawnWhite2);
       this.add(labelPawnWhite3);
       this.add(labelPawnWhite4);
       this.add(labelPawnWhite5);
       this.add(labelPawnWhite6);
       this.add(labelPawnWhite7);
       
       this.add(labelBishop0Black);
       this.add(labelBishop0White);
       
       this.add(labelBishop1Black);
       this.add(labelBishop1White);
       
       this.add(labelHorse0Black);
       this.add(labelHorse0White);
       
       this.add(labelHorse1Black);
       this.add(labelHorse1White);
       
       this.add(labelKingBlack);
       this.add(labelKingWhite);
       
       this.add(labelQueenBlack);
       this.add(labelQueenWhite);
       
       this.add(labelTower0Black);
       this.add(labelTower0White);
       
       this.add(labelTower1Black);
       this.add(labelTower1White);
       
       addWhichPlayerLabel();
       addTimerLabels();
       
    
   }//addLabels
   
   /**
     * Facilitator method used to simplify the code on the setBounds method.
     * 
     * @param row The row of the label.
     * @param col The colum of the label.
     * 
     * @return New coordinates
     */
   private int[] updatePosition(int row, int col){
       
            int[] position = board.getBoard()[row][col].getLabelCoordinates();
   
            return position;
   }//updatePosition
   
   /**
    * Private method in charge of setting the location of every JLabel object into the JPanel.
    * 
    */
   private void setLabelBounds(){
       int[] position = updatePosition(1,0);
       int row = 1;
       int col = 0;
       
       this.labelPawnBlack0.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnBlack1.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnBlack2.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnBlack3.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnBlack4.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnBlack5.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnBlack6.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnBlack7.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col = 0;
       row = 6;
       position = updatePosition(row, col);
       
       
       this.labelPawnWhite0.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnWhite1.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnWhite2.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnWhite3.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnWhite4.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnWhite5.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnWhite6.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       col++;
       position = updatePosition(row, col);
       
       this.labelPawnWhite7.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       
       col = 2;
       row = 0;
       position = updatePosition(row, col);
       this.labelBishop0Black.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       row = 7;
       position = updatePosition(row, col);
       this.labelBishop0White.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       
       col = 5;
       row = 0;
       position = updatePosition(row, col);
       this.labelBishop1Black.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       row = 7;
       position = updatePosition(row, col);
       this.labelBishop1White.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       
       col = 1;
       row = 0;
       position = updatePosition(row, col);
       this.labelHorse0Black.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       row = 7;
       position = updatePosition(row, col);
       this.labelHorse0White.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       
       col = 6;
       row = 0;
       position = updatePosition(row, col);
       this.labelHorse1Black.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       row = 7;
       position = updatePosition(row, col);
       this.labelHorse1White.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       
       col = 4;
       row = 0;
       position = updatePosition(row, col);
       this.labelKingBlack.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       row = 7;
       position = updatePosition(row, col);
       this.labelKingWhite.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       
       col = 3;
       row = 0;
       position = updatePosition(row, col);
       this.labelQueenBlack.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       row = 7;
       position = updatePosition(row, col);
       this.labelQueenWhite.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       
       col = 0;
       row = 0;
       position = updatePosition(row, col);
       this.labelTower0Black.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       row = 7;
       position = updatePosition(row, col);
       this.labelTower0White.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       
       col = 7;
       row = 0;
       position = updatePosition(row, col);
       this.labelTower1Black.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       row = 7;
       position = updatePosition(row, col);
       this.labelTower1White.setBounds(position[1], position[0], IMAGE_SIDE_DIMENSION, IMAGE_SIDE_DIMENSION);
       
     
       
   }//setLabelBounds
   
   /**
    * Private method in charge of initializing the JLabel objects.
    * 
    */
   private void initializeLabels(){
       
      this.labelPawnBlack0 = new JLabel(iconPawnBlack);
      this.labelPawnBlack1 = new JLabel(iconPawnBlack);
      this.labelPawnBlack2 = new JLabel(iconPawnBlack);
      this.labelPawnBlack3 = new JLabel(iconPawnBlack);
      this.labelPawnBlack4 = new JLabel(iconPawnBlack);
      this.labelPawnBlack5 = new JLabel(iconPawnBlack);
      this.labelPawnBlack6 = new JLabel(iconPawnBlack);
      this.labelPawnBlack7 = new JLabel(iconPawnBlack);
       
      this.labelPawnWhite0 = new JLabel(iconPawnWhite);
      this.labelPawnWhite1 = new JLabel(iconPawnWhite);
      this.labelPawnWhite2 = new JLabel(iconPawnWhite);
      this.labelPawnWhite3 = new JLabel(iconPawnWhite);
      this.labelPawnWhite4 = new JLabel(iconPawnWhite);
      this.labelPawnWhite5 = new JLabel(iconPawnWhite);
      this.labelPawnWhite6 = new JLabel(iconPawnWhite);
      this.labelPawnWhite7 = new JLabel(iconPawnWhite);
      
      this.labelBishop0Black = new JLabel(iconBishopBlack);
      this.labelBishop0White = new JLabel(iconBishopWhite);
      
      this.labelBishop1Black = new JLabel(iconBishopBlack);
      this.labelBishop1White = new JLabel(iconBishopWhite);
      
      this.labelHorse0Black = new JLabel(iconHorseBlack);
      this.labelHorse0White = new JLabel(iconHorseWhite);
      
      this.labelHorse1Black = new JLabel(iconHorseBlack);
      this.labelHorse1White = new JLabel(iconHorseWhite);
      
      this.labelKingBlack = new JLabel(iconKingBlack);
      this.labelKingWhite = new JLabel(iconKingWhite);
      
      this.labelQueenBlack = new JLabel(iconQueenBlack);
      this.labelQueenWhite = new JLabel(iconQueenWhite);
      
      this.labelTower0Black = new JLabel(iconTowerBlack);
      this.labelTower0White = new JLabel(iconTowerWhite);
      
      this.labelTower1Black = new JLabel(iconTowerBlack);
      this.labelTower1White = new JLabel(iconTowerWhite);
      
      
      
      
       
   }//initializeLabels
   
   /**
    * Private method in charge of initializing all of the ImageIcon objects.
    * 
    */
   private void initializeIcons(){
       
       this.iconPawnBlack = new ImageIcon(getClass().getResource("/game/NetPieces/pawnBlack.png"));
       this.iconPawnWhite = new ImageIcon(getClass().getResource("/game/NetPieces/pawnWhite.png"));
       
       this.iconBishopBlack = new ImageIcon(getClass().getResource("/game/NetPieces/bishopBlack.png"));
       this.iconBishopWhite = new ImageIcon(getClass().getResource("/game/NetPieces/bishopWhite.png"));
       
       this.iconHorseBlack = new ImageIcon(getClass().getResource("/game/NetPieces/horseBlack.png"));
       this.iconHorseWhite = new ImageIcon(getClass().getResource("/game/NetPieces/horseWhite.png"));
       
       this.iconKingBlack = new ImageIcon(getClass().getResource("/game/NetPieces/kingBlack.png"));
       this.iconKingWhite = new ImageIcon(getClass().getResource("/game/NetPieces/kingWhite.png"));
       
       this.iconQueenBlack = new ImageIcon(getClass().getResource("/game/NetPieces/queenBlack.png"));
       this.iconQueenWhite = new ImageIcon(getClass().getResource("/game/NetPieces/queenWhite.png"));
       
       this.iconTowerBlack = new ImageIcon(getClass().getResource("/game/NetPieces/towerBlack.png"));
       this.iconTowerWhite = new ImageIcon(getClass().getResource("/game/NetPieces/towerWhite.png"));
       
   }//initializeIcons

   /**
    * Method for the running of the player one timer.
    * 
    */
    private void startTimerPlayer1(){
       
        this.timerThread = new Thread(new Runnable(){
            
            @Override
            public void run() {
              for(int minutes = 9; minutes >= 0; minutes--){
                  for(int seconds = 59; seconds >= 0; seconds--){
                      synchronized(GraphicsTwoChess.this.timerThread){
                      try { 
                          timerThread.wait(1000);
                          String time = (minutes + ":" + seconds);
                      timerLabel.setText(time);
                      if(minutes == 0 && seconds == 0){
                          playerOneTimeLost = true;
                      }
                      GraphicsTwoChess.this.timerThread.notifyAll();
                      }catch (InterruptedException ex) {}
                      
                      
                      }
                      
                  }
              }
                
                
            }//run
            
        });
        
       // timerThread.start();
        
        
    }//startTimer
    
    /**
    * Method for the running of the player 2 timer.
    */
    private void startTimerPlayer2(){
       
        this.timerThread2 = new Thread(new Runnable(){
            
            @Override
            public void run() {
              for(int minutes = 9; minutes >= 0; minutes--){
                  for(int seconds = 59; seconds >= 0; seconds--){
                      synchronized(GraphicsTwoChess.this.timerThread2){
                      try { 
                          timerThread2.wait(1000);
                          String time = (minutes + ":" + seconds);
                      timerLabel2.setText(time);
                      
                      if(minutes == 0 && seconds == 0){
                          playerTwoTimeLost = true;
                      }
                      GraphicsTwoChess.this.timerThread2.notifyAll();
                      }catch (InterruptedException ex) {}
                      
                      
                      }
                      
                  }
              }
                
                
            }//run
            
        });
        
        
       // timerThread2.start();
        
        
    }//startTimerPlayer2
    
    /**
    * Pause the timer player 2.
    */
    private void pauseTimerPlayer2(){
        
        
    }//pauseTimerPlayer2
    
    /**
    * Pause the timer player 1.
    * 
    */
    private void pauseTimerPlayer1(){
        
        
    }//pauseTimerPlayer1

    /**
    * Private method in charge of adding the timer JLabels into the JPanel.
    * 
    */
    private void addTimerLabels(){
        this.timerLabel = new JLabel();
        this.timerLabel.setBounds(620, 615, 80, 40);
        this.timerLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        this.add(timerLabel);
        
        this.timerLabel2 = new JLabel();
        this.timerLabel2.setBounds(620, 10, 80, 40);
        this.timerLabel2.setFont(new Font("Times New Roman", Font.BOLD, 18));
        this.add(timerLabel2);
        
    }

    /**
    * Private method in charge of adding the whichPlayer JLabels into the JPanel.
    * 
    */
    private void addWhichPlayerLabel() {
        this.whichPlayerLabel = new JLabel();
        this.whichPlayerLabel.setBounds(1, 615, 300, 10);
        this.whichPlayerLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        this.whichPlayerLabel.setForeground(board.getBoard()[0][0].getFaction());
        
        this.add(whichPlayerLabel);
    }//addWhichPlayer

    /**
     * Method for the proper passing of the timer threads time.
     * 
     */
    private void timerSelection() {
        
        if(sessionCounter == 0){
            startTimerPlayer1();
            startTimerPlayer2();
            sessionCounter++;
            //timerThread.interrupt();
            //timerThread2.interrupt();
        }
        
        Player player = board.getPlayer();
        
        if(player.getPlayerName().equals("Player One")){
            timerThread.start();
            timerThread2.interrupt();
        }else{
            timerThread2.start();
            timerThread.interrupt();
        }
        
    }//timerSelection
    
    /**
     * Private method in charge of reseting both the graphic, and internal logic for a new game start if the JVM does not close and there is a check mate.
     */
    private void resetGame() {
        this.removeAll();
        this.setLayout(null);
        addLabels();
        ChessBoard tempBoard;
        
        tempBoard = new ChessBoard();
        
        this.board = tempBoard;
        board.nextPlayerTurn();
    }//resetGame
    
    
    
    
}//GraphicsTwoChess
