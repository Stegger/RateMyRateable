/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
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
    @FXML
    private AnchorPane root;

    private RateMyModel rateMyModel;

    public RateController()
    {
        rateMyModel = RateMyModel.getInstance();
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
     * Adds a new rateable to the model.
     *
     * @param event
     */
    @FXML
    private void handleAddNewRateable(ActionEvent event)
    {
        //First I create a new Rateable:
        String description = txtDescription.getText().trim();
        double rate = Double.parseDouble(txtRate.getText().trim());
        rateMyModel.addNewRateAble(description, rate);

        //I reset the GUI for adding new rateables
        txtDescription.clear();
        txtDescription.requestFocus();
        txtRate.clear();
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

        //Binds to the chart data:
        chartRatingDistribution.dataProperty().bind(rateMyModel.getChartData());
    }

    /**
     * Saves the current ratings to a file.
     *
     * @param event
     */
    @FXML
    private void handleSaveRatings(ActionEvent event)
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            Window win = root.getScene().getWindow();
            File file = fileChooser.showSaveDialog(win);
            rateMyModel.SaveRateablesToFile(file);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Save error");
            alert.setHeaderText("Error when saving ratings:");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Load a new set of ratings from a file.
     *
     * @param event
     */
    @FXML
    private void handleLoadRatings(ActionEvent event)
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            Window win = root.getScene().getWindow();
            File file = fileChooser.showOpenDialog(win);
            rateMyModel.LoadRateablesFromFile(file);
        } catch (Exception ex)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Load error");
            alert.setHeaderText("Error when loading ratings:");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void correctLineChart()
    {
        chartRatingDistribution.getXAxis().setLabel("Ratings");
        chartRatingDistribution.getXAxis().autoRangingProperty().set(true);
        chartRatingDistribution.getYAxis().setLabel("Occurences");
        chartRatingDistribution.getYAxis().autoRangingProperty().set(true);
    }

}
