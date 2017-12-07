package edu.mmatfb.cpre288.GUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;


/**
 * Thread-safe type used by the application to interact with the bubble chart
 * 
 * @author rtoepfer
 */
public class ChartController {

	private ConcurrentLinkedQueue<ChartObstacle> scanQueue = new ConcurrentLinkedQueue<>();
	
	private volatile boolean scanClear = false;
	
	private ConcurrentHashMap<EdgeDirection,EdgeType> edgeMap = new ConcurrentHashMap<>();
    
	/**
	 * sends additional scan results to be included in the chart
	 * @param toAdd
	 */
	public void scanUpdate(Iterable<ChartObstacle> toAdd){
	
		toAdd.forEach(scanQueue::add);
	}
	
	/**
	 * sends an additional scan results to be included in the chart
	 * @param toAdd
	 */
	public void scanUpdate(ChartObstacle toAdd){
		scanQueue.add(toAdd);
	}
	
	/**
	 * returns true if there are additional ChartObstacles to display on the chart
	 */
	public boolean hasScanUpdates(){
		return !scanQueue.isEmpty();
	}
	
	/**
	 * Instructs the chart to remove all of it's current obstacles
	 */
	public void scanClear(){
		scanQueue.clear();
		scanClear = true;
	}
	
	/**
	 * returns true if scanClear() has been called, but the chart has not cleared yet
	 */
	public boolean toScanClear(){
		return scanClear;	
	}
	
	/**
	 * for the chart to say it's cleared it's data
	 */
	protected void setCleared(){
		scanClear = false;
	}
	
	/**
	 * returns the queued ChartObstacles
	 */
	public List<ChartObstacle> scanRead(){
		List<ChartObstacle> rtnList = scanQueue.stream().collect(Collectors.toList());
		
		scanQueue.clear();
		return rtnList;
	}
	
	/**
	 * reads the map of current edge directions and types
	 * and clears the queued edges
	 */
	protected Map<EdgeDirection,EdgeType> readEdges(){
		Map<EdgeDirection,EdgeType> rtn = new HashMap<>(edgeMap);
		edgeMap.clear();
		return rtn;
	}
	
	/**
	 * adds a read edge to the chart
	 * @param dir
	 * @param type
	 */
	public void edgeUpdate(EdgeDirection dir,EdgeType type){
		edgeMap.put(dir, type);
	}
	
	/**
	 * returns true if there are pending edges
	 * @return
	 */
	public boolean hasEdgeUpdates(){
		return !edgeMap.isEmpty();
	}

}
