import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GG extends JFrame {

    public static void main(String[] args) {
        JFrame ramka = new JFrame();
        graphic g = new graphic();
        ramka.setResizable(false);
        ramka.setBounds(0,0,800,640);
        ramka.setTitle("Gra");
        ramka.add(g);
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setVisible(true);
    }
}
