package edu.mmatfb.cpre288.putty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PuttyConnection {

	private Process process;
	public static final String PROFILE_NAME = "test";
	
	public PuttyConnection() throws IOException{
		List<String> commands = new ArrayList<String>();
		commands.add("\"C:\\Program Files\\PuTTY\\plink.exe\"");
		commands.add(" -v");
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		File f = new File("puttyOut.txt");
		//pb.redirectOutput(f);
		pb.redirectError(f);
		
		process = pb.start();
		
	}
	
	
	
	public void stop(){
		
		process.destroy();
		
		
	}
	
	
}
