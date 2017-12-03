package edu.mmatfb.cpre288.markGUI;

public class DataObject {
    private int x;
    private int y;
    private int radius;

    public DataObject(int x, int y, int radius)
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
