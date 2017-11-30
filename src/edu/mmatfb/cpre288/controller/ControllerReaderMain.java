package edu.mmatfb.cpre288.controller;

import java.io.IOException;

public class ControllerReaderMain {
	
	public static void main(String[] agrs) throws IOException, InterruptedException{
		ControllerReader reader = null;
		try{
			reader = new ControllerReader(null);
		
			long start = System.currentTimeMillis();
			while(System.currentTimeMillis() - start < 3000){
				Thread.sleep(100);
				reader.readLast();
			}
			
		}finally{
			reader.stop();
		}
		
	}

}
