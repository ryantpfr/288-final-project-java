package edu.mmatfb.cpre288.GUI;


/**
 * Specifies the type of edge the cybot hits
 * 
 * @author rtoepfer
 */
public enum EdgeType {
	BUMP("Bump"),
	CLIFF("Cliff"),
	BOUND("Boundary");
	
    private final String displayName;       

    private EdgeType(String s) {
        displayName = s;
    }
    
    /**
     * the name that should be displayed on graph labels
     * @return
     */
    public String displayName(){
    	return displayName;
    }
}
