import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GG extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        graphic g = new graphic();
        frame.setResizable(false);
        frame.setBounds(0,0,800,640);
        frame.setTitle("Game");
        frame.add(g);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
