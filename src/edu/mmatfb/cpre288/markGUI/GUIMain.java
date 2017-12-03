package edu.mmatfb.cpre288.markGUI;

import javafx.application.Application;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

import edu.mmatfb.cpre288.putty.PuttyConnectionMain;

public class GUIMain extends Application {
	
   @Override public void start(Stage stage) {

       ChartContainer chartContainer = new ChartContainer(stage);
       ChartController chartController = chartContainer.getController();

       List<DataObject> data = Arrays.asList(new DataObject(700,700,50));
       chartController.update(data);
       
       PuttyConnectionMain.run(chartController);
   }

    public static void main(String[] args) {
    	launch(args);
    }
    
}
