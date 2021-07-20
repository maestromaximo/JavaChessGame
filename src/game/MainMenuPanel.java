
package game;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class for the creation of the main menu interactions panel for the Chess Class
 * 
 * @author Alejandro Agustin Garcia Polo
 * @version 1.4V February 27, 2020
 */
public class MainMenuPanel extends JPanel {
    private SoundClass clips;
    private CardLayout cards;
    private JPanel contentsPanel;
  //  private BoxLayout box;
    private JButton twoPlayerButton, singlePlayerButton;
    
    /**
     *Constructor of the MainMenuPanel, in charge of creating and preparing all the components and aspects of the panel.
     * 
     * @param cards the CardLayout manager passed from the main JFrame.
     * @param contentsPanel the main panel to which the card layout main reference from.
     */
    public MainMenuPanel(CardLayout cards, JPanel contentsPanel){
        this.setBackground(new Color(104,44,60));                   //blood wine
        
        this.twoPlayerButton = new JButton("Two Player Game");
        this.singlePlayerButton = new JButton("Single Player Game");
        
       // this.box = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.cards = cards;
        this.contentsPanel = contentsPanel;
        this.clips = new SoundClass();
        
        this.setLayout(null);
        
        setButtons();
        addButtons();
        addListeners();
        
        
    }//constructor
    
    /**
     * Method in charge of allocating the mouse lister to the corresponding buttons (Single player and Two Player ones).
     * 
     */
    private void addListeners(){
        
        singlePlayerButton.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent evt) {
                singlePlayerButtonClicked(evt);
            }//actionPerformed
             
    });//addActionListener
        
        twoPlayerButton.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent evt) {
                twoPlayerButtonClicked(evt);
            }//actionPerformed
             
    });//addActionListener
        
    }//addListeners
    
    /**
     * This method adds the JButton Components into the JPanel.
     * 
     */
    private void addButtons(){
        this.add(singlePlayerButton);
        this.add(twoPlayerButton);
       
    }//addButtons 
    
    /**
     * Private method in charge of transferring to the single player JPanel.
     * 
     * @param evt the click event.
     */
    private void singlePlayerButtonClicked(ActionEvent evt){
        clips.pieceMoved();
        cards.show(contentsPanel, "Ai");
    }//singlePlayerButtonClicked
    
    /**
     * Private method in charge of transferring to the Two Player JPanel.
     * 
     * @param evt the click event.
     */
    private void twoPlayerButtonClicked(ActionEvent evt){
        clips.pieceMoved();
        cards.show(contentsPanel, "2Player");
    }//twoPlayerButtonClicked
    
    /**
     * Mutator method in charge of setting the contents panel field.
     * 
     * @param contentsPanel the main panel from which to reference from.
     */
    public void setContentsPanel(JPanel contentsPanel){
        this.contentsPanel = contentsPanel;
    }//setContentsPanel

    /**
     * Private method in charge of allocating the proper boundaries to the JButton Objects.
     * 
     */
    private void setButtons() {
        
        singlePlayerButton.setBounds(140, 200, 350, 80);
        twoPlayerButton.setBounds(140, 370, 350, 80);
    }//setButtons
    
}//MainMenuPanel
