/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class RateController implements Initializable {

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

    private RateMyModel rateMyModel;
    private RateableManager rateManager;
    
    public RateController() {
        rateMyModel = RateMyModel.getInstance();
        rateManager = new RateableManager();
    }

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
        
        //TODO Do average
        
        
        //TODO Do chart
        
        
        //I reset the GUI for adding new rateables
        txtDescription.clear();
        txtRate.clear();
    }

    @FXML
    private void handleClearAllRateables(ActionEvent event) {
        rateMyModel.clearAll();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBind();
    }

    private void dataBind() {
        //I define the mapping of the table's columns to the objects that are added to it.
        colDescription.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDescription()));
        colRate.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getRate()));
        
        //I bind the table to a list of data (Empty at startup):
        tblAllRateables.setItems(rateMyModel.getAllRateables());
    }

}