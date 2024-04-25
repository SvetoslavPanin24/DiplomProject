package Biometrics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

class BJPanel extends JPanel
{

    public BufferedImage bi;
    public BJPanel (){
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent m){
                JOptionPane.showMessageDialog (null,"("+Integer.toString(m.getPoint().x)+";"+Integer.toString(m.getPoint().y)+")","Point",JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
    public BJPanel (BufferedImage bi){
        this.bi = bi;
        setPreferredSize(new Dimension(bi.getWidth(),bi.getHeight())) ;
    }
    public void setBufferedImage(BufferedImage bi)
    {
        if(bi!=null) {
            this.bi = bi;
            bi.setRGB(224,227,231);
            setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
            this.repaint();
        }

    }
    public void paintComponent(Graphics g)
    {
        g.drawImage(bi,0,0,this) ;
    }
}