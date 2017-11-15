package edu.mmatfb.cpre288.putty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PuttyConnection {

	public static final String PROFILE_NAME = "test";
	
	private Process process;
	private InputStream in;
	private OutputStream out;
	
	public PuttyConnection() throws IOException{
		List<String> commands = new ArrayList<String>();
		commands.add("\"C:\\Program Files\\PuTTY\\plink.exe\"");
		commands.add(" -v");
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		File f = new File("puttyOut.txt");
		//pb.redirectOutput(f);
		pb.redirectError(f);
		
		process = pb.start();
		
		in = process.getInputStream();
		out= process.getOutputStream();
		
	}
	
	public void write(byte... bytes){
		try {
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void stop(){
		
		process.destroy();
		
		
	}
	
	
}
