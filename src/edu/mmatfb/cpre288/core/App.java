package edu.mmatfb.cpre288.core;

import java.io.IOException;

import edu.mmatfb.cpre288.GUI.ChartController;
import edu.mmatfb.cpre288.controller.ControllerReader;
import edu.mmatfb.cpre288.putty.PuttyConnection;

public class App {

	
	public static void run(ChartController chartController) {
		// TODO Auto-generated method stub
		ControllerReader reader = null;
		try{
			BufferedCommandExecutor bce = new BufferedCommandExecutor(chartController);
			PuttyConnection puttyConnection = new PuttyConnection(bce::read);
			
			reader = new ControllerReader(puttyConnection);
			
			
			System.out.println("setup complete");
			
			
			
			
			while(true){
				Thread.sleep(10);
				reader.readLast();
				reader.respondToReading();
			}
		
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}finally{
			try {
				reader.stop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
