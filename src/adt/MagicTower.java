package adt;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MagicTower extends Tower {
    private BufferedImage graphic;

    /**
     * Constructor for the magic tower.
     * @param position -  a point representing where the tower will be.
     */
    public MagicTower(Point position) {
        super(position,150,12,65,200);
        initialize();
    }

    private void initialize(){

        try{
            graphic = ImageIO.read(new File(getClass().getResource("resources/Magic.png").toURI()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Overridden method of the Enemy parent class that returns the image of the specific type of enemy.
     * @return the enemy's image
     */
    @Override
    public BufferedImage graphic(){
        return graphic;
    }
}
