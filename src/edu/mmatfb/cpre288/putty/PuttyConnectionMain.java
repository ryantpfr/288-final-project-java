package edu.mmatfb.cpre288.putty;

import java.io.IOException;

public class PuttyConnectionMain {

	public static void main(String[] args){
		try {
			PuttyConnection puttyConnection = new PuttyConnection( c -> System.out.print((char) c));
			
			Thread.sleep(100);
			
			puttyConnection.write((byte) 'c');
			
//			for(char c : "hello Ryan, I am communicating with you".toCharArray()){
//				Thread.sleep(6);
//				puttyConnection.write((byte) c);
//			}
			
			Thread.sleep(30000);
			
			puttyConnection.stop();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
