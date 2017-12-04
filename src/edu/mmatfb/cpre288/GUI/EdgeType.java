package edu.mmatfb.cpre288.GUI;

public enum EdgeType {
	BUMP("Bump"),
	CLIFF("Cliff"),
	BOUND("Boundary");
	
    private final String displayName;       

    private EdgeType(String s) {
        displayName = s;
    }
    
    public String displayName(){
    	return displayName;
    }
}
