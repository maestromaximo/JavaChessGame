package game;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Class responsible for setting the foundations for the Graphic User Interface with the Game and its subsequent JPanels, 
 * as well as the Serializable interface to save the progress of the game.
 * 
 * @author Alejandro Agustin Garcia Polo
 * @version 1.9V February 27, 2020
 * 
 */
public class Chess implements Serializable {
    
    
    /**
     * @param args the command line arguments
     * 
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException  {
        GameSave save = new GameSave();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerOfScreenX = (int)( screenSize.getWidth() / 8);
        int centerOfScreenY = (int)( screenSize.getHeight() / 10);
        final int FRAME_X = 650;
        final int FRAME_Y = 670;
        
        JFrame frame = new JFrame("Chess App");
        
        JPanel contentsPanel = new JPanel(); 
        CardLayout cards = new CardLayout();
        
        frame.setSize(FRAME_X, FRAME_Y);
        frame.setLocation(centerOfScreenX, centerOfScreenY);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(contentsPanel);
        contentsPanel.setLayout(cards);
        
        
        //GraphicsTwoChess twoPlayerPanel = new GraphicsTwoChess();
        GraphicsTwoChess twoPlayerPanel = (GraphicsTwoChess) save.readGame();
        twoPlayerPanel.loadGameConstructor();
        
        
        
        ChessIntroPanel introPanel = new ChessIntroPanel(cards, contentsPanel);
        MainMenuPanel menuPanel = new MainMenuPanel(cards, contentsPanel);
        GraphicsAiChess singlePlayerChess = new GraphicsAiChess();
        
        contentsPanel.add(introPanel, "Intro");
        contentsPanel.add(menuPanel, "Main menu");
        contentsPanel.add(twoPlayerPanel, "2Player");
        contentsPanel.add(singlePlayerChess, "Ai");
        
        
        introPanel.setContentsPanel(contentsPanel);
        menuPanel.setContentsPanel(contentsPanel);
        
        cards.show(contentsPanel, "Intro");
        
        //frame.add(panel);
        
        frame.setVisible(true);
        
        
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
             
            try {
                save.saveGame(twoPlayerPanel);
            } catch (IOException ex) {
               
            }
             
            }//windowClosing
            
        });//windowListener
        
        
        
    }//main
    
}//Chess
