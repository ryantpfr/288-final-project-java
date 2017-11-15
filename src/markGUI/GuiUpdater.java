package markGUI;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Window;

import javax.swing.JFrame;

public class GuiUpdater {


	private JFrame frame;
	private Graphics graphics;
	
	
	public GuiUpdater(JFrame frame){
		this.frame = frame;
		
		frame.
		this.graphics = frame.getGraphics();
		
		frame.setVisible(true);
		frame.setSize(600, 600);
		
		System.out.println(graphics);
		
		graphics.setColor(Color.CYAN);
		graphics.fillOval(300, 300, 50, 50);
	}
	
	public void start(){
		
		while(true){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			updateGraphics();
		}
	}
	
	private void updateGraphics(){
		
	}
	
	
}
