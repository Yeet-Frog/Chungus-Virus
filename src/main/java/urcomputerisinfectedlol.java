import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class urcomputerisinfectedlol extends JPanel {


    public static void main(String[] args) throws IOException {
        int i = 0;
        while(i < 100){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Random rand = new Random();
            int adnum = rand.nextInt(12);
            int adx = rand.nextInt((screenSize.width));
            int ady = rand.nextInt((screenSize.height));
            String[] ads = {"ad1.png", "ad2.jpg", "ad3.jpg", "ad4.jpg", "ad5.png", "ad6.jpg", "ad7.jpg", "ad8.jpg", "ad9.png", "ad10.png", "ad11.png", "ad12.jpg", "ad13.jpg"};
            ImageIcon ad = new ImageIcon(urcomputerisinfectedlol.class.getResource("Advertisments/" + ads[adnum]));
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setSize(ad.getIconWidth(), ad.getIconHeight());
            JLabel lbl = new JLabel();
            lbl.setIcon(ad);
            frame.setUndecorated(true);
            frame.add(lbl);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setLocation(adx, ady);
            frame.setType(Window.Type.UTILITY);
            frame.setVisible(true);
            i++;
        }
    }
}
