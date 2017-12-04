package edu.mmatfb.cpre288.GUI;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class ChartController {

	private ConcurrentLinkedQueue<DataObject> scanQueue = new ConcurrentLinkedQueue<>();
	
	private volatile boolean scanClear = false;
	
	private ConcurrentHashMap<EdgeDirection,EdgeType> edgeMap = new ConcurrentHashMap<>();
    
	public void scanUpdate(Iterable<DataObject> toAdd){
	
		toAdd.forEach(scanQueue::add);
	}
	
	public void scanUpdate(DataObject toAdd){
		scanQueue.add(toAdd);
	}
	
	public boolean hasScanUpdates(){
		return !scanQueue.isEmpty();
	}
	
	public void scanClear(){
		scanQueue.clear();
		scanClear = true;
	}
	
	public boolean toScanClear(){
		return scanClear;	
	}
	
	public void setCleared(){
		scanClear = false;
	}
	
	public List<DataObject> scanRead(){
		List<DataObject> rtnList = scanQueue.stream().collect(Collectors.toList());
		
		scanQueue.clear();
		return rtnList;
	}
	
	public Map<EdgeDirection,EdgeType> readEdges(){
		Map<EdgeDirection,EdgeType> rtn = new HashMap<>(edgeMap);
		edgeMap.clear();
		return rtn;
	}
	
	public void edgeUpdate(EdgeDirection dir,EdgeType type){
		edgeMap.put(dir, type);
	}
	
	public boolean hasEdgeUpdates(){
		return !edgeMap.isEmpty();
	}

}
