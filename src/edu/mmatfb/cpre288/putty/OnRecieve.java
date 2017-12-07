package edu.mmatfb.cpre288.putty;


/**
 * used to specify behavior for a PuttyConnection upon receiving a byte
 * The behavior specified by this interface must be Thread-safe
 * 
 * @author rtoepfer
 */

@FunctionalInterface
public interface OnRecieve {

	public void onRecieve(byte b);
	
}
