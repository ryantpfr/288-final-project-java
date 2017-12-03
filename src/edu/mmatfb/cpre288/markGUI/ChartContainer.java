package edu.mmatfb.cpre288.markGUI;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ChartContainer {

    private ChartController chartController;
	private ExecutorService executor;

    public ChartContainer(Stage stage){
        stage.setTitle("Bubble Chart Sample");
        final NumberAxis xAxis = new NumberAxis(-1000, 1000, 10);
        final NumberAxis yAxis = new NumberAxis(0, 1000, 10);
        final BubbleChart<Number,Number> blc = new
                BubbleChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("X Direction");
        yAxis.setLabel("Y Direction");
        blc.setTitle("Grid");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Bot");
        series1.getData().add(new XYChart.Data(0, 0, 100));



        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Data");
        series2.getData().add(new XYChart.Data(300, 80,30));


        Scene scene  = new Scene(blc);
        blc.getData().addAll(series1, series2);

        stage.setScene(scene);
        stage.show();


        chartController = new ChartController(series1,series2,this);
        
        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
       
        
        Runnable runLater = new Runnable(){

			@Override
			public void run() {
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 
				 System.out.println("Hello");
				 System.out.println(Thread.currentThread().getName());
				 
				 //chartController.update(Arrays.asList(new DataObject(300,(int) Math.random()*500,30)));
				 
				 executor.execute(this);
			}
        	
        };
        
        executor.execute(runLater);
        
        prepareTimeline();
    }
    
    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
            	chartController.update(Arrays.asList(new DataObject(300,500,30)));
            }
        }.start();
    }


    public ChartController getController(){
        return this.chartController;
    }
}
