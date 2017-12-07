package edu.mmatfb.cpre288.core;

/**
 * Keeps track of the current angle and distance of the bot
 * @author rtoepfer
 */
public class MovementCounter {

	private double angle = 0;
	private double distance = 0;
	
	/** constant multiple to correct for the error in the angle sensors **/ 
	public static final float TURN_CALIBRATION = 1.05f;
	
	/** constant multiple to correct for the error in the distance sensors **/ 
	public static final float DIST_CALIBRATION = 1.00f;
	
	/**
	 * accepts an angle sensor reading and stores the total angle
	 * @param inAngle
	 */
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
	
	/**
	 * resets the distance displayed on the screen
	 */
	public void resetDistance(){
		distance = 0;
	}
	
	/**
	 * accepts a new distance measurement
	 * @param inDistance
	 */
	public void acceptDistance(byte inDistance){
		distance += DIST_CALIBRATION * inDistance;
		
		System.out.println("The bot has moved " + distance/10 + " cm");
	}
}
