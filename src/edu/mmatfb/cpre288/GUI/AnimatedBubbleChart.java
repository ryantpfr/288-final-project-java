package edu.mmatfb.cpre288.GUI;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import edu.mmatfb.cpre288.core.App;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

/**
 * JavaFX bubble chart to display scanner readings and the bump and cliff and edge sensors
 * @author rtoepfer
 */
public class AnimatedBubbleChart extends Application {

    private XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    
    private ChartController chartController = new ChartController();

    private XYChart.Series<Number, Number> left = new XYChart.Series<>();
    private XYChart.Series<Number, Number> frontLeft = new XYChart.Series<>();
    private XYChart.Series<Number, Number> frontRight = new XYChart.Series<>();
    private XYChart.Series<Number, Number> right = new XYChart.Series<>();
    private long leftExpiry = 0;
    private long frontLeftExpiry = 0;
    private long frontRightExpiry = 0;
    private long rightExpiry = 0;
    
    /** the amount of time an edge should be displayed display **/
    public static final long EDGE_DISPLAY_TIME = 3000;
    
    private ExecutorService executor;
	private  BubbleChart<Number, Number> bubbleChart;
	
	/**
	 * initializes the cleared stage
	 * @param primaryStage
	 */
    private void init(Stage primaryStage) {

    	final NumberAxis xAxis = new NumberAxis(-1000, 1000, 10);
        final NumberAxis yAxis = new NumberAxis(0, 1000, 10);
        
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);


        // Create a BubbleChart
        bubbleChart = new BubbleChart<Number, Number>(xAxis, yAxis);

        bubbleChart.setAnimated(false);
        bubbleChart.setTitle("Animated Bubble Chart");
        bubbleChart.setHorizontalGridLinesVisible(true);

        
        Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Bot");
        series1.getData().add(new XYChart.Data<Number, Number>(0, 0, 100));

        //angleSeries.setName("Data");
        //angleSeries.getData().add(new XYChart.Data<Number, Number>(new RandoNum(), 0, 100));

        series2 = new XYChart.Series<Number, Number>();
        series2.setName("Data");
        
        //mark styling
        //yAxis.setStyle(" .axis-label{font-style: italic;");
        bubbleChart.setStyle(".chart-plot-background {-fx-background-color: red;}");
        
    
        initBoundarySeries();

        // Add Chart Series
        bubbleChart.getData().addAll(series1,series2);
        
        primaryStage.setScene(new Scene(bubbleChart));
    }

    /**
     * runs the entire program
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("288 GUI");
        stage.setMaximized(true);
        init(stage);
        stage.show();


        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });

        RestOfApplication addToQueue = new RestOfApplication();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();
        
    }
    
    
    /**
     * allows you to specify running the main application or chart tests with un-commenting
     * @author rtoepfer
     */
	private class RestOfApplication implements Runnable {
        public void run() {
			//PuttyConnectionMain.run(chartController);
        	//.testChart(chartController);
			App.run(chartController);
        }
    }

    /**Timeline gets called in the JavaFX Main thread*/
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
            	updateChart();
            }
        }.start();
    }
    
    /**
     * updates chart based on the controller
     */
    private void updateChart(){
    	
    	addDataToSeries();
    	handleEdges();
    }
    
    /**
     * updates the edges on the chart
     */
    private void handleEdges(){
    	
    	long currentTime = System.currentTimeMillis();
    	
    	setEdges(currentTime);
    	expireEdges(currentTime);
    }
    
    /**
     * sets new edges on the chart
     * @param currentTime
     */
    private void setEdges(long currentTime) {
		if(chartController.hasEdgeUpdates()){
			
			System.out.println("updating edges");
			
			Map<EdgeDirection,EdgeType> edgeMap = chartController.readEdges();
			
			if(edgeMap.containsKey(EdgeDirection.LEFT)){
				left.setName(edgeMap.get(EdgeDirection.LEFT).displayName());
				leftExpiry = currentTime + EDGE_DISPLAY_TIME;
				addSeriesIfMissing(left);
			}
			if(edgeMap.containsKey(EdgeDirection.FRONT_LEFT)){
				frontLeft.setName(edgeMap.get(EdgeDirection.FRONT_LEFT).displayName());
				frontLeftExpiry = currentTime + EDGE_DISPLAY_TIME;
				addSeriesIfMissing(frontLeft);
			}
			if(edgeMap.containsKey(EdgeDirection.FRONT_RIGHT)){
				frontRight.setName(edgeMap.get(EdgeDirection.FRONT_RIGHT).displayName());
				frontRightExpiry = currentTime + EDGE_DISPLAY_TIME;
				addSeriesIfMissing(frontRight);
			}
			if(edgeMap.containsKey(EdgeDirection.RIGHT)){
				right.setName(edgeMap.get(EdgeDirection.RIGHT).displayName());
				rightExpiry = currentTime + EDGE_DISPLAY_TIME;
				addSeriesIfMissing(right);
			}

		}
	}
    
    /**
     * adds a series to the chart if it doesn't exist already
     * @param series
     */
    private void addSeriesIfMissing(XYChart.Series<Number,Number> series){
		if(!bubbleChart.getData().contains(series)){
			bubbleChart.getData().add(series);
		}
    }
    
    /**
     * removes edges after they have been displayed for the specified amount of time
     * @param currentTime
     */
    private void expireEdges(long currentTime){
    	if(leftExpiry < currentTime){
    		bubbleChart.getData().remove(left);
    	}
    	if(frontLeftExpiry < currentTime){
    		bubbleChart.getData().remove(frontLeft);
    	}
    	if(frontRightExpiry < currentTime){
    		bubbleChart.getData().remove(frontRight);
    	}
    	if(rightExpiry < currentTime){
    		bubbleChart.getData().remove(right);
    	}
    }

    /**
     * adds the new scan results to the chart
     */
	private void addDataToSeries() {
    	
    	ObservableList<Data<Number, Number>> data = series2.getData();
    	
    	if(chartController.toScanClear()){
    		data.clear();
    		chartController.setCleared();
    	}
    	
    	if(chartController.hasScanUpdates()){
    		
    		System.out.println("updating chart");
        	
    		chartController.scanRead().stream().map(this::dataObsToChartData).forEach(data::add);
    	}
    	
    	series2.getData();

    }

	/**
	 * launches the application
	 * @param args
	 */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * converts an application specific ChartObstacle into an FX data point
     * @param dataObject
     * @return
     */
    private XYChart.Data<Number, Number> dataObsToChartData(ChartObstacle dataObject){
        return new XYChart.Data<Number, Number>(dataObject.getX(),dataObject.getY(),dataObject.getRadius());
    }
    
    /**
     * builds the series displayed when there is a boundary
     */
    private void initBoundarySeries(){
    	
    	//frontRight.getNode().lookup(".chart-series-line").setStyle(BLACK);
    	
    	double base = 100/Math.sqrt(2);
    	double increment = Math.sqrt(2)/2;
    	
    	for(int i = -100; i <= 100; i+= 4){
    		
    		frontLeft.getData().add(new Data<Number,Number>(-base+i*increment,base+i*increment,5));
    		frontRight.getData().add(new Data<Number,Number>(base-i*increment,base+i*increment,5));
    		
    		if(i > 0){
	    		left.getData().add(new Data<Number, Number>(-100,i,5));
	    		right.getData().add(new Data<Number, Number>(100,i,5));
    		}
    	}
    }
}