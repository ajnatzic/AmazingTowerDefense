package adt;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MagicTower extends Tower {
    private BufferedImage graphic;
    public MagicTower(Point position) {
        super(position,150,20,20,200);
        initialize();
    }

    private void initialize(){

        try{
            graphic = ImageIO.read(new File(getClass().getResource("resources/Magic.png").toURI()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage graphic(){
        return graphic;
    }
}
