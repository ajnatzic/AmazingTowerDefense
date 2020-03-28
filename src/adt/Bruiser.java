package adt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bruiser extends Enemy {
    private BufferedImage graphic;
    public Bruiser(Point position) {
        super(position.x, position.y, 50, 5, 100, 2);
        initialize();
    }

    public Bruiser(int x, int y) {
        super(x, y, 50, 5, 100, 2);
        initialize();
    }
    private void initialize(){

        try{
            graphic = ImageIO.read(new File(getClass().getResource("resources/bruiser.png").toURI()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage graphic(){
        return graphic;
    }
}
