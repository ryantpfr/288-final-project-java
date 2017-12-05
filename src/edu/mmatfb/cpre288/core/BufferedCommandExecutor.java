package edu.mmatfb.cpre288.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import edu.mmatfb.cpre288.GUI.ChartController;
import edu.mmatfb.cpre288.GUI.DataObject;
import edu.mmatfb.cpre288.GUI.EdgeDirection;
import edu.mmatfb.cpre288.GUI.EdgeType;

public class BufferedCommandExecutor {

    private ConcurrentLinkedQueue<Byte> queue = new ConcurrentLinkedQueue<>();
    private volatile int unrecievedBytes = -1; //-1 == awaiting command bit
    private ChartController chartController;
    
    private MovementCounter moveCounter;

    //private volatile boolean busy = false;

    private static final List<Byte> startCharacters = Collections.unmodifiableList(Arrays.asList(new Byte("1"),new Byte("2")));

    public BufferedCommandExecutor(){ }
    
    public BufferedCommandExecutor(ChartController chartController,MovementCounter moveCounter){
    	this.chartController = chartController;
    	this.moveCounter = moveCounter;
    }

    public void read(Byte b) {
    	
    	//System.out.println("reading " + (int) b);
    	
        queue.add(b);

        respondToCharacter();

    }

    private void respondToCharacter(){
        Byte b = queue.peek();
        if(unrecievedBytes == -1){
            unrecievedBytes = remainingBytesByCommand();
        }else if(unrecievedBytes == 1){
            unrecievedBytes = -1;
            runCommand(); //this will deque all bytes
            if(!queue.isEmpty()){
                respondToCharacter();
            }
        }else{
            unrecievedBytes--;
        }
    }

    private void runCommand(){
    	
    	//System.out.println("running " + queue.peek());
    	
        switch(queue.remove()){
            case 1 : respondTo1(); return;
            case 2 : respondTo2(); return;
            case 4 : respondTo4(); return;
            case 5 : respondTo5(); return;
            case 7 : respondTo7(); return;
            case 9 : respondTo9(); return;
        }
        throw new IllegalStateException("Invalid command byte");
    }


    private void respondTo9() {
    	byte b1 = queue.remove();
    	
    	moveCounter.acceptDistance(b1);
	}

	private void respondTo7() {
    	byte b1 = queue.remove();
		
    	moveCounter.acceptAngle(b1);
	}

	private void respondTo1(){

        byte b1 = queue.remove();
        
        EdgeDirection eDir = null;
        if(b1 == 2){
        	eDir = EdgeDirection.FRONT_LEFT;
        }else if(b1 == 3){
        	eDir = EdgeDirection.FRONT_RIGHT;
        }else{
        	System.out.println("recieved invalid bump direction");
        	return;
        }
       
        
        chartController.edgeUpdate(eDir, EdgeType.BUMP);
    }

    private void respondTo2(){
        byte b1 = queue.remove();
        byte b2 = queue.remove();
        System.out.println(b1 + "*" + b2+ "=" +(b1*b2));
    }
    
    private void respondTo4(){
        System.out.println("starting scan");
        
        chartController.scanClear();
    }
    
    private void respondTo5(){
    	int b1 = unsign(queue.remove());
        int b2 = unsign(queue.remove());
        int b3 = unsign(queue.remove());
        int b4 = unsign(queue.remove());
    	
        double theta = Math.toRadians(b2-b1);
        double midAngle = (Math.toRadians(b2) - theta/2);
        int dist = (b3 << 8) + b4;
        int size = (int) (dist * Math.tan(theta/2));
        int x = (int) (dist * Math.cos(midAngle));
        int y = (int) (dist * Math.sin(midAngle));
        
        System.out.println("found an object from degrees "+ b1 + " to " + b2 + " with mid distance " + dist);
        System.out.println("size = " + size);
        
        if(chartController != null){
        	System.out.println("displaying data obj at point ("+x+","+y+") with size " + size);
        	DataObject data = new DataObject(x,y,size);
            chartController.scanUpdate(data);
        }
       
    }

    private int remainingBytesByCommand(){
        switch(queue.peek()){
            case(1) : return 1;
            case(2) : return 2;
            case(4) : return 1;
            case(5) : return 4;
            case(7) : return 1;
            case(9) : return 1;
        }
        throw new IllegalStateException("Invalid command byte");
    }
    
    public static int unsign(byte b) {
        return b & 0xFF;
      }


}
