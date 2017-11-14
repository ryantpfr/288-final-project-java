package edu.mmatfb.cpre288;

import java.io.IOException;

public class ControllerReaderMain {
	
	public static void main(String[] agrs) throws IOException, InterruptedException{
		ControllerReader reader = null;
		try{
			reader = new ControllerReader();
		
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
