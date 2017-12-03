package edu.mmatfb.cpre288.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import edu.mmatfb.cpre288.putty.PuttyConnection;

public class ControllerReader {
	
	private Process process;
	private BufferedReader input;
	
	private ControllerLine current = new ControllerLine();
	private ControllerLine last;
	
	private PuttyConnection putty;
	
	public ControllerReader(PuttyConnection putty) throws IOException {
		ProcessBuilder pb = new ProcessBuilder("cygread.bat");
		File f = new File("out.txt");
		pb.redirectOutput(f);
		process = pb.start();	
		
		//inStream = process.getInputStream();

		input = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		
		this.putty = putty;
	}
	
	public void read() throws IOException, InterruptedException{
		
		String line;
		while ((line = input.readLine()) != null) {
		    System.out.println(line);
		}
	}

	public void stop() throws IOException {
		process.destroy();
		Runtime.getRuntime().exec("taskkill /F /IM explore.exe");
	}

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
	
	public void respondToReading(){
		if(last == null){
			System.out.println("must have at least one reading before execution");
			//throw new IllegalStateException("must have at least one reading before execution");
		}
		
		if(current.vertJ < -300 && last.vertJ > -300){
			putty.write((byte) 0,(byte) 100);
			System.out.println("!! sent go command, speed 100 !!");
		}
		if(current.vertJ > -300 && last.vertJ < -300){
			putty.write((byte) 0,(byte) 0);
			System.out.println("!! sent go command, speed 0 !!");
		}
		if(current.vertJ > 300 && last.vertJ < 300){
			putty.write((byte) 0,(byte) 0);
			System.out.println("!! sent go command, speed -100 !!");
		}
		if(current.vertJ < 300 && last.vertJ > 300){
			putty.write((byte) 0,(byte) 0);
			System.out.println("!! sent go command, speed 0 !!");
		}
		if(current.topB && !last.topB){
			putty.write((byte) 2);
			System.out.println("!! sent scan !!");
		}
	}
}
