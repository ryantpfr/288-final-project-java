package edu.mmatfb.cpre288.GUI;

/**
 * Internally holds the x, y, and radius of a scanned object to be sent to the AnimatedBubbleChart
 * 
 * @author rtoepfer
 */
public class ChartObstacle {
    private int x;
    private int y;
    private int radius;

    /**
     * @param x
     * @param y
     * @param radius
     */
    public ChartObstacle(int x, int y, int radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }
}
