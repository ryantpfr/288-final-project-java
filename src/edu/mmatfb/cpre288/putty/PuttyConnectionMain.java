package edu.mmatfb.cpre288.putty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.mmatfb.cpre288.GUI.ChartController;
import edu.mmatfb.cpre288.GUI.DataObject;
import edu.mmatfb.cpre288.core.BufferedCommandExecutor;
import edu.mmatfb.cpre288.core.MovementCounter;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

public class PuttyConnectionMain {

	public static void main(String[] args){
		try {
			
			BufferedCommandExecutor bce = new BufferedCommandExecutor();
			
			PuttyConnection puttyConnection = new PuttyConnection(bce::read);
			
			Thread.sleep(1000);
						
			//puttyConnection.write((byte) 'c');
			
			Thread.sleep(500);
			
			puttyConnection.write((byte) 5);
			
			
			
//			for(char c : "hello Ryan, I am communicating with you".toCharArray()){
//				Thread.sleep(6);
//				puttyConnection.write((byte) c);
//			}
			
			Thread.sleep(30000);
			
			puttyConnection.stop();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void run(ChartController chartController){
		try {
			
			BufferedCommandExecutor bce = new BufferedCommandExecutor(chartController,new MovementCounter());
			
			PuttyConnection puttyConnection = new PuttyConnection(bce::read);
			
			Thread.sleep(500);
			
			//puttyConnection.write((byte) 122);
			
			Thread.sleep(30000);
			
			puttyConnection.stop();
			
			
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
		
	
	
	
}
