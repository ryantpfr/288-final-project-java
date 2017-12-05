package edu.mmatfb.cpre288.core;

public class MovementCounter {

	private double angle = 0;
	private double distance = 0;
	
	public static final float TURN_CALIBRATION = 1.05f;
	public static final float DIST_CALIBRATION = 1.00f;
	
	public void acceptAngle(byte inAngle){
		this.angle += ((double) inAngle)*TURN_CALIBRATION;
		
		if(this.angle > 180){
			angle -= 360;
		}
		if(this.angle < -180){
			angle += 360;
		}
		
		System.out.println("The bot is facing in the direction of " + (int) this.angle + " degrees");
	}
	
	public void resetDistance(){
		distance = 0;
	}
	
	public void acceptDistance(byte inDistance){
		distance += DIST_CALIBRATION * inDistance;
		
		System.out.println("The bot has moved " + distance/10 + " cm");
	}
}
