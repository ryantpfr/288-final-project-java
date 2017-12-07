package edu.mmatfb.cpre288.GUI;

import java.util.ArrayList;
import java.util.List;

/**
 * used for testing the chart when the CyBot is not connected or a more specific input is required
 * 
 * AnimatedBubbleChart must be modified to call this
 * 
 * @author rtoepfer
 *
 */
public class ChartTester {

	/**used for testing the chart when the CyBot is not connected or a more specific input is required**/
	public static void testChart(ChartController chartController){
		try{
			
//			Thread.sleep(3000);
//			chartController.edgeUpdate(EdgeDirection.FRONT_RIGHT,EdgeType.BUMP);
//			Thread.sleep(3500);
//			chartController.edgeUpdate(EdgeDirection.LEFT,EdgeType.BOUND);
//			Thread.sleep(1000);
//			chartController.edgeUpdate(EdgeDirection.LEFT,EdgeType.CLIFF);
			//chartController.edgeUpdate(EdgeDirection.LEFT,EdgeType.);
			
			for(int i = 0; i < 10; i++){
				List<ChartObstacle> data = new ArrayList<>();
					
				chartController.scanClear();
					
				for(int i2 = 0; i2< 3; i2++){
					chartController.scanUpdate(new ChartObstacle((int) (Math.random()*2000 - 1000),(int) (Math.random()*1000),200));
					Thread.sleep(300);
					//data.add(new DataObject((int) (Math.random()*2000 - 1000),(int) (Math.random()*1000),200));
				}
					
				//chartController.update(data);
					
					
				Thread.sleep(1000);
	
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
