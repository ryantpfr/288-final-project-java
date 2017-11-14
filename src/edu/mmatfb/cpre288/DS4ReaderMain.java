package edu.mmatfb.cpre288;

import java.io.IOException;

public class DS4ReaderMain {
	
	public static void main(String[] agrs) throws IOException, InterruptedException{
		
		DS4Reader reader = new DS4Reader();
		
		long start = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - start < 3000){
			reader.read();
		}
		
		reader.stop();
		
	}

}
