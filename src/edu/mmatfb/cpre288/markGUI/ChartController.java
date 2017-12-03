package edu.mmatfb.cpre288.markGUI;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

import java.util.List;
import java.util.Scanner;

public class ChartController {

    private XYChart.Series botSeries;
    private XYChart.Series dataSeries;

    private ChartContainer container;


    public ChartController(XYChart.Series botSeries, XYChart.Series dataSeries, ChartContainer container) {
        this.botSeries = botSeries;
        this.dataSeries = dataSeries;
        this.container = container;
    }


    public void update(List<DataObject> list){

        ObservableList<XYChart.Data> dataList = dataSeries.getData();

        dataList.clear();

        for(int i = 0; i < list.size(); i ++)
        {
            dataList.add(dataObjToChartData(list.get(i)));
        }
        
        
        //dataSeries.getData().clear();
    }

    private XYChart.Data<Integer, Integer> dataObjToChartData(DataObject dataObject){
        return new XYChart.Data<Integer, Integer>(dataObject.getX(),dataObject.getY(),dataObject.getRadius());
    }
}
