package edu.mmatfb.cpre288.GUI;

import java.util.ArrayList;

public class DirectedNumberSet {
	
	private ArrayList<CustomPoint> pointList = new ArrayList<>();
	
	private double xOrigin;
	private double yOrigin;
	
	private double angle;
	
	private boolean toUpdate = false;
	
	public DirectedNumberSet(int x, int y,int angle){
		
		for(int i = 0; i < 100; i+=4){
			CustomPoint point = new CustomPoint(new MoveableX(i),new MoveableY(i));
			
			//xList.add(point);
		}
	}
	
	private class CustomPoint{
		private Number x;
		private Number y;
		
		private CustomPoint(Number x,Number y){
			this.x = x;
			this.y = y;
		}

		public Number getX() {
			return x;
		}

		public Number getY() {
			return y;
		}
		
		
	}
	
	private class MoveableX extends MoveableNumber{

		private static final long serialVersionUID = -8114754038810362197L;

		private MoveableX(int distance){
			super(distance);
		}
		
		@Override
		protected double calcVal() {
			return distance * Math.sin(angle);
		}
		
	}
	
	private class MoveableY extends MoveableNumber{

		private static final long serialVersionUID = -8114754038810362197L;

		private MoveableY(int distance){
			super(distance);
		}
		
		@Override
		protected double calcVal() {
			return distance * Math.cos(angle);
		}
		
	}
	
	
	private abstract class MoveableNumber extends Number{

		private static final long serialVersionUID = -4469258947253489097L;
		
		protected int distance;
		
		protected double lastVal;
		
		private MoveableNumber(int distance){
			this.distance = distance;
		}
		
		protected abstract double calcVal();

		@Override
		public double doubleValue() {
			updateIfNecesarry();
			return lastVal;
		}

		@Override
		public float floatValue() {
			updateIfNecesarry();
			return (float) lastVal;
		}

		@Override
		public int intValue() {
			updateIfNecesarry();
			return (int) lastVal;
		}

		@Override
		public long longValue() {
			updateIfNecesarry();
			return (long) lastVal;
		}
		
		private void updateIfNecesarry(){
			lastVal = calcVal();
		}
		
	}
	
}
