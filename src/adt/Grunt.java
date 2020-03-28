package adt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Grunt extends Enemy {
    private BufferedImage graphic;
    public Grunt(Point position) {
        super(position.x, position.y, 20, 1, 20, 8);
        initialize();
    }

    public Grunt(int x, int y) {
        super(x, y, 20, 1, 20, 8);
        initialize();
    }
    private void initialize(){
        try{
            graphic = ImageIO.read(new File(getClass().getResource("resources/grunt.png").toURI()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public BufferedImage graphic(){
        return graphic;
    }
}
