package edu.mmatfb.cpre288.markGUI;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class ChartController {

	
	private volatile boolean read = true;
	private ConcurrentLinkedQueue<DataObject> queue = new ConcurrentLinkedQueue<>();
	private volatile boolean clear = false;

    
	public void update(Iterable<DataObject> toAdd){
		read = false;
		toAdd.forEach(queue::add);
	}
	
	public void update(DataObject toAdd){
		queue.add(toAdd);
		read = false;
	}
	
	public boolean isRead(){
		return read;
	}
	
	public void clear(){
		queue.clear();
		clear = true;
	}
	
	public boolean toClear(){
		return clear;	
	}
	
	public void setCleared(){
		clear = false;
	}
	
	public List<DataObject> read(){
		List<DataObject> rtnList = queue.stream().collect(Collectors.toList());
		
		read = true;
		queue.clear();
		return rtnList;
	}


}
