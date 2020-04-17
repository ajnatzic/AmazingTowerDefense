package adt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * A weaker enemy type that will move faster and have less health.
 *  Represented by its own PNG file in the game
 */
public class Grunt extends Enemy {
	
	/** This variable will hold the PNG that will be applied to the Grunt*/
    private BufferedImage graphic;
    
    /**
     * Initializer that puts the grunt at a specific position on the game map based 
     * on position variable
     * @param position	X and Y coordinates on the game map
     */
    public Grunt(Point position, long frame) {
        super(position.x, position.y, 20, 1, 20, 8, frame);
        initialize();
    }

    /**
     * Initializer that puts the grunt at a specific position on the game map based 
     * on x and y coordinates
     * @param x	number of pixels on x axis
     * @param y	number of pixels on y axis
     */
    public Grunt(int x, int y, long frame) {
        super(x, y, 20, 1, 20, 8, frame);
        initialize();
    }
    
    /**
     * This method checks if the grunt PNG is available. If it is it is assigned
     * to the graphic variable. Otherwise an exception is thrown. 
     */
    private void initialize(){
        try{
            graphic = ImageIO.read(new File(getClass().getResource("resources/grunt.png").toURI()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * This method returns the PNG image of the grunt 
     */
    @Override
    public BufferedImage graphic(){
        return graphic;
    }
}
