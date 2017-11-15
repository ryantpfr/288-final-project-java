package edu.mmatfb.cpre288.putty;

import java.io.IOException;

public class PuttyConnectionMain {

	public static void main(String[] args){
		try {
			PuttyConnection puttyConnection = new PuttyConnection();
			
			Thread.sleep(3000);
			
			puttyConnection.stop();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
