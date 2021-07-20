
package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;

/**
 * I/O Class in charge of serializing the two player JPanel which in turn holds all of the necessary fields for the Game to function perfectly.
 * 
 * @author Alejandro Agustin Garcia Polo
 * @version 1.9V February 27, 2020
 */
public class GameSave implements Serializable{
    private static final String FILE_NAME = "GameSaveData.ser";
    FileOutputStream file;
    ObjectOutputStream out;
    FileInputStream fileIn;
    ObjectInputStream in;
            
    public GameSave() {
        
        
    }//Constructor
    
    /**
     * Method in charge of initializing the Object serialization classes, writing too and closing of the file.
     * 
     * @param objectToSerialize the object which to serialize into the "FILE_NAME".ser file.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveGame(Object objectToSerialize) throws FileNotFoundException, IOException{
        this.file = new FileOutputStream(FILE_NAME); 
        this.out = new ObjectOutputStream(file); 
        
        out.writeObject(objectToSerialize);
        
        out.close();
        file.close();
    }//saveGame
    
    /**
     * Method in charge of initializing the Object serialization reader classes, reading too and closing of the file.
     * 
     * @return the translated Object from the serialized file.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object readGame() throws FileNotFoundException, IOException, ClassNotFoundException{
       this.fileIn = new FileInputStream(FILE_NAME);
       this.in = new ObjectInputStream(fileIn);
       
       Object objectRead = in.readObject();
       
       return objectRead;
    }
    
}//GameSave
