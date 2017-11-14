package edu.mmatfb.cpre288;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DS4Reader {
	
	Process process;
	InputStream inStream;
	//Scanner scan;
	BufferedReader input;
	
	public DS4Reader() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("run cyg exe.bat");
		File f = new File("out.txt");
		pb.redirectOutput(f);
		process = pb.start();	
		
		inStream = process.getInputStream();

		input = new BufferedReader(new InputStreamReader(inStream));
	}
	
	public void read() throws IOException, InterruptedException{
		
		Thread.sleep(100);
		while (true) {
		    System.out.println(input.readLine());
		}
	}
	
}
