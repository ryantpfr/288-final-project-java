package edu.mmatfb.cpre288.GUI;

/**
 * Holds which CyBot sensor was triggered by a boundary, bump, or cliff
 * even though there are only 2 bump sensors, they should use FRONT_LEFT and FRONT_RIGHT
 * 
 * @author rtoepfer
 */
public enum EdgeDirection {
	LEFT,
	FRONT_LEFT,
	FRONT_RIGHT,
	RIGHT
}
