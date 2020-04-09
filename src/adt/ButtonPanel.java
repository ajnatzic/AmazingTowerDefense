package adt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    private JButton placeTower;
    private JButton startRound;
    private UmbrellaPanel umbrellaPanel;
    private TDModel model;
    public ButtonPanel(TDModel modelFromUmbrella, UmbrellaPanel u){
        model = modelFromUmbrella;
        umbrellaPanel = u;
        Listener listener = new Listener();

        placeTower = new JButton("Place Tower");
        add(placeTower);
        placeTower.addActionListener(listener);

        startRound = new JButton("START ROUND");
        add(startRound);
        startRound.addActionListener(listener);
    }

    @Override
    public void paintComponent(Graphics g){

    }


    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource()  == placeTower){
                umbrellaPanel.placeTower();
            }
            else if(e.getSource() == startRound){
                umbrellaPanel.startRound();
            }

        }
    }
}
