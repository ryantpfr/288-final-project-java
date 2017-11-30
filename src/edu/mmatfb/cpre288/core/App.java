package edu.mmatfb.cpre288.core;

import java.io.IOException;

import edu.mmatfb.cpre288.controller.ControllerReader;
import edu.mmatfb.cpre288.putty.PuttyConnection;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControllerReader reader = null;
		try{
			PuttyConnection puttyConnection = new PuttyConnection( c -> System.out.print((char) c));
			
			Thread.sleep(200);
			puttyConnection.write((byte) 'c'); //send connection byte
			
			reader = new ControllerReader(puttyConnection);
		
			long start = System.currentTimeMillis();
			
			while(System.currentTimeMillis() - start < 30000){
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
