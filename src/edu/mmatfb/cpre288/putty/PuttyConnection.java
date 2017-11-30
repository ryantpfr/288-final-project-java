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
	private OnRecieve onRecieve;
	
	public PuttyConnection(OnRecieve onRecieve) throws IOException{
		this.onRecieve = onRecieve;
		
		this.initProcess();
		
		in = process.getInputStream();
		out= process.getOutputStream();
		
		this.startReader();
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
	
	private void startReader(){
		ReaderThread readerThread = new ReaderThread();
		readerThread.start();
	}
	
	private class ReaderThread extends Thread{
		
		public static final int BUFFER_SIZE = 10;
		public static final int SLEEP_MS = 7;
		
		private byte[] buffer = new byte[BUFFER_SIZE];
		
		@Override
		public void run(){
			while(true){
				try {
					Thread.sleep(SLEEP_MS);
					readOutput();
				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private void readOutput() throws IOException{
			int read = in.read(buffer);
			for(int i = 0; i < read; i++){
				onRecieve.onRecieve(buffer[i]);
			}
			if(read == BUFFER_SIZE){
				readOutput();
			}
		}
	}
}
