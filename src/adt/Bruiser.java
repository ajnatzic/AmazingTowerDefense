package adt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Tougher enemy type that will have more health. Represented by a more menacing PNG model
 *
 */
public class Bruiser extends Enemy {
	
	/** This variable will hold the PNG that will be applied to the Bruiser */
    private BufferedImage graphic;
    
    /**
     * Initializer that puts the Bruiser at a specific position on the game map based 
     * on position variable
     * @param position	X and Y coordinates on the game map
     */
    public Bruiser(Point position) {
        super(position.x, position.y, 50, 5, 100, 2);
        initialize();
    }
    
    /**
     * Initializer that puts the Bruiser at a specific position on the game map based 
     * on x and y coordinates
     * @param x	number of pixels on x axis
     * @param y	number of pixels on y axis
     */
    public Bruiser(int x, int y) {
        super(x, y, 50, 5, 100, 2);
        initialize();
    }
    /**
     * This initialization method checks if the PNG file for the Bruiser exists,
     * if it does it applies the PNG to the graphic variable
     */
    private void initialize(){

        try{
            graphic = ImageIO.read(new File(getClass().getResource("resources/bruiser.png").toURI()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method returns the PNG image of the bruiser 
     */
    @Override
    public BufferedImage graphic(){
        return graphic;
    }
}
