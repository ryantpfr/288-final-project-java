package edu.mmatfb.cpre288;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
		ProcessBuilder pb = new ProcessBuilder("cygread.bat");
		File f = new File("out.txt");
		pb.redirectOutput(f);
		process = pb.start();	
		
		inStream = process.getInputStream();

		input = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
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
	
}
