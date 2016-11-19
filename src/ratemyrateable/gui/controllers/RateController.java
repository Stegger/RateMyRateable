/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.gui.controllers;

import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ratemyrateable.be.Rateable;
import ratemyrateable.bll.RateableManager;
import ratemyrateable.gui.models.RateMyModel;

/**
 *
 * @author pgn
 */
public class RateController implements Initializable
{
    
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtRate;
    @FXML
    private TableView<Rateable> tblAllRateables;
    @FXML
    private TableColumn<Rateable, String> colDescription;
    @FXML
    private TableColumn<Rateable, Double> colRate;
    @FXML
    private TextField txtLowDescription;
    @FXML
    private TextField txtLowRate;
    @FXML
    private TextField txtHighDescription;
    @FXML
    private TextField txtHighRate;
    @FXML
    private Label lblAverageRate;
    @FXML
    private LineChart<Number, Number> chartRatingDistribution;
    
    private RateMyModel rateMyModel;
    private RateableManager rateManager;
    
    public RateController()
    {
        rateMyModel = RateMyModel.getInstance();
        rateManager = new RateableManager();
    }

    /**
     * Adds a new rateable to the list.
     *
     * @param event
     */
    @FXML
    private void handleAddNewRateable(ActionEvent event)
    {
        //First I create a new Rateable:
        String description = txtDescription.getText().trim();
        double rate = Double.parseDouble(txtRate.getText().trim());
        Rateable rat = rateManager.creatNewRateable(description, rate);

        //Then I add it to the model
        rateMyModel.addNewRateAble(rat);

        //I update my models highest and lowest objects:
        Rateable high = rateManager.getHighestRated(rateMyModel.getAllRateables());
        rateMyModel.setHighestRatedModel(high);
        Rateable low = rateManager.getLowestRated(rateMyModel.getAllRateables());
        rateMyModel.setLowestRatedModel(low);

        //I calculate and update the average of the model:
        double average = rateManager.average(rateMyModel.getAllRateables());
        rateMyModel.setAverage(average);

        //We update the chart:
        setChartData();

        //I reset the GUI for adding new rateables
        txtDescription.clear();
        txtRate.clear();
    }
    
    public void setChartData()
    {
        Map<Double, Integer> data = rateManager.distributionOfRatings(rateMyModel.getAllRateables());
        XYChart.Series<Number, Number> series = new XYChart.Series();
        series.setName("Ratings");
        for (Double d : data.keySet())
        {
            series.getData().add(new XYChart.Data<Number, Number>(d, data.get(d)));
        }
        chartRatingDistribution.getData().clear();
        chartRatingDistribution.getData().add(series);
    }

    /**
     * Clears all ratings.
     *
     * @param event
     */
    @FXML
    private void handleClearAllRateables(ActionEvent event)
    {
        rateMyModel.clearAll();
        chartRatingDistribution.getData().clear();
    }

    /**
     * Initialises the RateController. Primarily does databinding.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        correctLineChart();
        dataBind();
    }

    /**
     * Binds all controls to relevant parts of the model.
     */
    private void dataBind()
    {
        //I define the mapping of the table's columns to the objects that are added to it.
        colDescription.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDescription()));
        colRate.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getRate()));

        //I bind the table to a list of data (Empty at startup):
        tblAllRateables.setItems(rateMyModel.getAllRateables());

        //Binding og high and low:
        txtHighDescription.textProperty().bind(rateMyModel.getHighestRated().getDescriptionProperty());
        txtHighRate.textProperty().bind(rateMyModel.getHighestRated().getRateProperty().asString());
        txtLowDescription.textProperty().bind(rateMyModel.getLowestRated().getDescriptionProperty());
        txtLowRate.textProperty().bind(rateMyModel.getLowestRated().getRateProperty().asString());

        //And binding for the average label:
        lblAverageRate.textProperty().bind(rateMyModel.getAverage().asString());      
    }

    /**
     * Saves the current ratings to a file.
     *
     * @param event
     */
    @FXML
    private void handleSaveRatings(ActionEvent event)
    {
        //TODO Save all ratings to file!
    }

    /**
     * Load a new set of ratings from a file.
     *
     * @param event
     */
    @FXML
    private void handleLoadRatings(ActionEvent event)
    {
        //TODO Load all ratings from file!
    }
    
    private void correctLineChart()
    {
        chartRatingDistribution.getXAxis().setLabel("Ratings");
        chartRatingDistribution.getXAxis().autoRangingProperty().set(true);
        chartRatingDistribution.getYAxis().setLabel("Occurences");
        chartRatingDistribution.getYAxis().autoRangingProperty().set(true);
    }
    
}
