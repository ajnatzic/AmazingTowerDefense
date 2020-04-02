package adt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Tougher enemy type that will have more health. Represented by a more menacing png model
 *
 */
public class Bruiser extends Enemy {
    private BufferedImage graphic;
    /**
     * Initializers for the Bruiser that will put the enemy at the beginning of the path
     * @param position
     */
    public Bruiser(Point position) {
        super(position.x, position.y, 50, 5, 100, 2);
        initialize();
    }

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
