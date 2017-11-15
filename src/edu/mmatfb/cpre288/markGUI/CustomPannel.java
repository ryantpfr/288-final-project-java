package edu.mmatfb.cpre288.markGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CustomPannel extends JPanel {


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[] x = new int[]{65, 122, 77, 20};
        int[] y = new int[]{226, 258, 341, 310};
        
        g.setColor(Color.RED);
        //g.drawPolygon(x, y, x.length);
        g.fillOval(300, 300, 80, 80);
    }

    //so our panel is the corerct size when pack() is called on Jframe
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
	
}
