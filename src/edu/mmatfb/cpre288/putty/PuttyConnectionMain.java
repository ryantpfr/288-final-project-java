package edu.mmatfb.cpre288.putty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.mmatfb.cpre288.core.BufferedCommandExecutor;
import edu.mmatfb.cpre288.markGUI.ChartController;
import edu.mmatfb.cpre288.markGUI.DataObject;
import edu.mmatfb.cpre288.markGUI.GUIMain;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

public class PuttyConnectionMain {

//	public static void main(String[] args){
//		try {
//			
//			BufferedCommandExecutor bce = new BufferedCommandExecutor();
//			
//			PuttyConnection puttyConnection = new PuttyConnection(bce::read);
//			
//			Thread.sleep(1000);
//						
//			//puttyConnection.write((byte) 'c');
//			
//			Thread.sleep(500);
//			
//			puttyConnection.write((byte) 5);
//			
//			
//			
////			for(char c : "hello Ryan, I am communicating with you".toCharArray()){
////				Thread.sleep(6);
////				puttyConnection.write((byte) c);
////			}
//			
//			Thread.sleep(30000);
//			
//			puttyConnection.stop();
//			
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void run(ChartController chartController){
		try {
			
			List<DataObject> data = Arrays.asList(new DataObject(700,700,50),new DataObject(-700,700,50));
		    chartController.update(data);
			
			BufferedCommandExecutor bce = new BufferedCommandExecutor();
			
			PuttyConnection puttyConnection = new PuttyConnection(bce::read);
			
	        
			
			puttyConnection.write((byte) 5);
			
			
			
//			for(char c : "hello Ryan, I am communicating with you".toCharArray()){
//				Thread.sleep(6);
//				puttyConnection.write((byte) c);
//			}
			
			//Thread.sleep(30000);
			
			puttyConnection.stop();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
