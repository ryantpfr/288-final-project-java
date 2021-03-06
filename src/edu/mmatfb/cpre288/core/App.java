package edu.mmatfb.cpre288.core;

import java.io.IOException;

import edu.mmatfb.cpre288.GUI.ChartController;
import edu.mmatfb.cpre288.controller.ControllerReader;
import edu.mmatfb.cpre288.putty.PuttyConnection;

/**
 * Builds and starts the non FX portion of the application
 * @author rtoepfer
 *
 */
public class App {

	/**
	 * Initializes CyBot Connection
	 * Initializes controller connection
	 * 
	 * Begins sending and receiving commands
	 * 
	 * @param chartController
	 */
	public static void run(ChartController chartController) {
		// TODO Auto-generated method stub
		ControllerReader reader = null;
		try{
			MovementCounter mc = new MovementCounter();
			
			BufferedCommandExecutor bce = new BufferedCommandExecutor(chartController,mc);
			PuttyConnection puttyConnection = new PuttyConnection(bce::read);
			
			reader = new ControllerReader(puttyConnection,mc);
			
			
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
