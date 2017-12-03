package edu.mmatfb.cpre288.markGUI;
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

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import edu.mmatfb.cpre288.putty.PuttyConnectionMain;


public class AnimatedBubbleChart extends Application {

    private static final int MAX_DATA_POINTS = 50;
    private int xSeriesData = 0;
    private XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private ChartController chartController = new ChartController();

    private ExecutorService executor;

    private void init(Stage primaryStage) {

    	final NumberAxis xAxis = new NumberAxis(-1000, 1000, 10);
        final NumberAxis yAxis = new NumberAxis(0, 1000, 10);
        
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);


        // Create a LineChart
        final BubbleChart<Number, Number> bubbleChart = new BubbleChart<Number, Number>(xAxis, yAxis);

        bubbleChart.setAnimated(false);
        bubbleChart.setTitle("Animated Bubble Chart");
        bubbleChart.setHorizontalGridLinesVisible(true);

        
        Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Bot");
        series1.getData().add(new XYChart.Data<Number, Number>(0, 0, 100));



        series2 = new XYChart.Series<Number, Number>();
        series2.setName("Data");
        //series2.getData().add(new XYChart.Data<Number, Number>(300, 80,30));

        // Add Chart Series
        bubbleChart.getData().addAll(series1,series2);

        primaryStage.setScene(new Scene(bubbleChart));
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("Animated Line Chart Sample");
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
        
        
        runOtherThread();
    }

    private void runOtherThread() {
		
    	new Thread(){
    		
    	}.start();
		
	}

	private class RestOfApplication implements Runnable {
        public void run() {
        	
			PuttyConnectionMain.run(chartController);
			
        }
    }

    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }
    
    private volatile int chartNum = 0;

    private void addDataToSeries() {
    	
    	if(!chartController.isRead()){
    		
    		System.out.println("updating chart");
        	
    		ObservableList<Data<Number, Number>> data = series2.getData();
    		data.clear();
    		chartController.read().stream().map(this::dataObjToChartData).forEach(data::add);
    	}
    	
    	series2.getData();
    	

    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private XYChart.Data<Number, Number> dataObjToChartData(DataObject dataObject){
        return new XYChart.Data<Number, Number>(dataObject.getX(),dataObject.getY(),dataObject.getRadius());
    }
}