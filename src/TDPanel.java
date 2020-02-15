import javax.swing.*;
import java.util.EventListener;
import java.awt.event.*;

public class TDPanel extends JPanel {
    private JButton placeTower;
    private TDModel model;





    private class listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()  == placeTower){
                Tower t = new Tower();
            }
        }
    }
}
