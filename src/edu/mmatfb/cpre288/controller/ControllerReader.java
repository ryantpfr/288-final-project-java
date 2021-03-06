package edu.mmatfb.cpre288.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import edu.mmatfb.cpre288.core.MovementCounter;
import edu.mmatfb.cpre288.putty.PuttyConnection;

/**
 * Class contains a process that runs a .bat file which runs a .sh file on cygwin
 * and writes the output to a file. which is read by readLast();
 * 
 * 
 * @author rtoepfer
 */
public class ControllerReader {
	
	private Process process;
	
	private BufferedReader input;
	
	private ControllerLine current = new ControllerLine();
	private ControllerLine last;
	
	private PuttyConnection putty;
	
	private MovementCounter movementCounter;
	
	/**
	 * construct a new Controller reader.
	 * @param putty PuttyConnection to write commands to the bot through
	 * @param mc MovementController is used for resetting distance
	 * @throws IOException
	 */
	public ControllerReader(PuttyConnection putty,MovementCounter mc) throws IOException {
		this.movementCounter = mc;
		
		ProcessBuilder pb = new ProcessBuilder("cygread.bat");
		File f = new File("out.txt");
		pb.redirectOutput(f);
		process = pb.start();	
		
		//inStream = process.getInputStream();

		input = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		
		this.putty = putty;
	}

	/**
	 * destroys the controller reading process
	 * @throws IOException
	 */
	public void stop() throws IOException {
		process.destroy();
		Runtime.getRuntime().exec("taskkill /F /IM explore.exe");
	}
	
	/**
	 * Reads the last line written by the controller program
	 * @throws IOException
	 */
	public void readLast() throws IOException {
		last = current;
		
		String line = null;
		String nextLine;
		while ((nextLine = input.readLine()) != null) {
			line = nextLine;
		}
		
		if(line == null){
			return;
		}
		
		if(line.matches("U:.*") || line.equals("")){
			//System.out.println(line);
			return;
		}
		
		scanLine(line);
		
		//System.out.println(current);
	}
	
	/**
	 * uses a scanner to parse the last line read into the controller into a ControllerLineObject
	 * @param line
	 */
	private void scanLine(String line){
		
		current = new ControllerLine();
		
		Scanner scan = new Scanner(line);
		
		scan.useDelimiter(",\\s*|\\s+");
		current.bottomB = scan.nextInt() != 0;
		current.topB = scan.nextInt() != 0;
		current.leftB = scan.nextInt() != 0;
		current.rightB = scan.nextInt() != 0;
		scan.nextInt();scan.nextInt();scan.nextInt();scan.nextInt();scan.nextInt();scan.nextInt();
		current.horizJ = scan.nextInt();
		current.vertJ = scan.nextInt();
		scan.close();
		
	}
	
	/**
	 * private class mirrors the structure of the controller output for internal use.
	 * @author rtoepfer
	 */
	private class ControllerLine{
		private boolean topB = false;
		private boolean rightB = false;
		private boolean bottomB = false;
		private boolean leftB = false;
		
		private int vertJ = 0;
		private int horizJ = 0;
		
		@Override
		public String toString(){
			return "top: " + topB 
					+ " | bottom: " + bottomB 
					+ " | left: " + leftB
					+ " | right: " + rightB
					+ " | horiz: " + horizJ
					+ " | vert: " + vertJ;
			
		}
		
	}
	
	/**
	 * performs an action based on the last result of readLastLine, such as sending a Go command to the bot
	 */
	public void respondToReading(){
		if(last == null){
			System.out.println("must have at least one reading before execution");
			//throw new IllegalStateException("must have at least one reading before execution");
		}
		
		if(current.vertJ < -300 && last.vertJ > -300){ //go forward
			putty.write((byte) 1);
			System.out.println("!! sent go command !!");
		}
		if(current.vertJ > -300 && last.vertJ < -300){ // stop from forward
			putty.write((byte) 6);
			System.out.println("!! sent stop command !!");
		}
		if(current.vertJ > 300 && last.vertJ < 300){ // reverse
			putty.write((byte) 2);
			System.out.println("!! sent go command backwards !!");
		}
		if(current.vertJ < 300 && last.vertJ > 300){ //stop from backwards
			putty.write((byte) 6);
			System.out.println("!! sent stop command !!");
		}
		
		if(current.horizJ < -300 && last.horizJ > -300){ //turn right
			putty.write((byte) 4);
			System.out.println("!! turning left !!");
		}
		if(current.horizJ > -300 && last.horizJ < -300){ // stop right
			putty.write((byte) 6);
			System.out.println("!! sent stop !!");
		}
		if(current.horizJ > 300 && last.horizJ < 300){ // turn left
			putty.write((byte) 3);
			System.out.println("!! turning right !!");
		}
		if(current.horizJ < 300 && last.horizJ > 300){ //stop from left
			putty.write((byte) 6);
			System.out.println("!! stop !!");
		}
		
		if(current.topB && !last.topB){
			putty.write((byte) 5);
			System.out.println("!! sent scan !!");
		}
		
		if(current.bottomB && !last.bottomB){
			System.out.println("!! resetting distance !!");
			movementCounter.resetDistance();
		}
	}
}
