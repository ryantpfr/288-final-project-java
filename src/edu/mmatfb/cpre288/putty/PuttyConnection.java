package edu.mmatfb.cpre288.putty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PuttyConnection {

	public static final String PUTTY_SETTINGS_NAME = "wifi";
	public static final String ERROR_OUTPUT_FILENAME = "puttyError.txt";
	
	private Process process;
	private InputStream in;
	private OutputStream out;
	
	public PuttyConnection() throws IOException{

		this.initProcess();
		
		in = process.getInputStream();
		out= process.getOutputStream();
	}
	
	private void initProcess() throws IOException{
		List<String> commands = new ArrayList<String>();
		commands.add("\"C:\\Program Files\\PuTTY\\plink.exe\"");
		commands.add("-load"); commands.add(PUTTY_SETTINGS_NAME);
//		commands.add("-raw");
//		commands.add("-P"); commands.add("42880");
//		commands.add("192.168.1.1");
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		
		File f = new File(ERROR_OUTPUT_FILENAME);
		//pb.redirectOutput(f);
		pb.redirectError(f);
		
		process = pb.start();
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
