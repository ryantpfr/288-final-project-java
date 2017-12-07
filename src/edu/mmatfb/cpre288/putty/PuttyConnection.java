package edu.mmatfb.cpre288.putty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls a pLink connection to the CyBot using pre-saved settings configured on Putty
 * Also starts a thread which checks for input on a pre-defined interval.
 * 
 * @author rtoepfer
 */
public class PuttyConnection {
	/**name of Putty settings to use**/
	public static final String PUTTY_SETTINGS_NAME = "wifi";
	/**name of file to redirect the error stream to **/
	public static final String OUTPUT_FILENAME = "puttyError.txt";
	
	/** pLink process **/
	private Process process;
	private InputStream in;
	private OutputStream out;
	
	private OnRecieve onRecieve;
	
	/**
	 * Instantiates a pLink connection
	 * @param onRecieve specifies what to do with received bytes 
	 * @throws IOException
	 */
	public PuttyConnection(OnRecieve onRecieve) throws IOException{
		this.onRecieve = onRecieve;
		
		this.initProcess();
		
		in = process.getInputStream();
		out= process.getOutputStream();
		
		this.startReader();
	}
	
	/**
	 * Starts the pLink process
	 * @throws IOException
	 */
	private void initProcess() throws IOException{
		List<String> commands = new ArrayList<String>();
		commands.add("\"C:\\Program Files\\PuTTY\\plink.exe\"");
		commands.add("-load"); commands.add(PUTTY_SETTINGS_NAME);
//		commands.add("-raw");
//		commands.add("-P"); commands.add("42880");
//		commands.add("192.168.1.1");
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		
		File f = new File(OUTPUT_FILENAME);
		//pb.redirectOutput(f);
		pb.redirectError(f);
		
		process = pb.start();
		
		
	}
	
	/**
	 * sends arguments to the CyBot
	 * @param bytes
	 */
	public void write(byte... bytes){
		try {
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ends the pLink process
	 */
	public void stop(){
		process.destroy();
	}
	
	/**
	 * starts a thread to read from the CyBot at an interval defined by SLEEP_MS
	 */
	private void startReader(){
		ReaderThread readerThread = new ReaderThread();
		readerThread.start();
	}
	
	
	/**
	 * Class to read output from the CyBot on an interval, and use onRecieve to choose an action
	 * @author rtoepfer
	 */
	private class ReaderThread extends Thread{
		
		/** internally used for storing input in an array, should be set above largest command size */
		public static final int BUFFER_SIZE = 10;
		/** how long the thread should sleep between readings **/
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
		
		/**reads and processes CyBot output**/
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
