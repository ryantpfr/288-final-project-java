package edu.mmatfb.cpre288.putty;

import java.io.IOException;

public class PuttyConnectionMain {

	public static void main(String[] args){
		try {
			PuttyConnection puttyConnection = new PuttyConnection();
			
			Thread.sleep(100);
			
			for(char c : "hello Ryan, I am communicating with you".toCharArray()){
				Thread.sleep(500);
				puttyConnection.write((byte) c);
			}
			
			Thread.sleep(100);
			
			puttyConnection.stop();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
