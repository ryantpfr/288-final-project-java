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

    
	public void update(Iterable<DataObject> toAdd){
		queue.clear();
		read = false;
		toAdd.forEach(queue::add);
	}
	
	public boolean isRead(){
		return read;
	}
	
	public List<DataObject> read(){
		List<DataObject> rtnList = queue.stream().collect(Collectors.toList());
		
		read = true;
		return rtnList;
	}


}
