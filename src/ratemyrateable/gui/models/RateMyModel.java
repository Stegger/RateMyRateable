/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ratemyrateable.be.Rateable;
import ratemyrateable.gui.models.be.RateableModel;

/**
 *
 * @author pgn
 */
public class RateMyModel {
    
    private static RateMyModel INSTANCE;

    /**
     * The list of all rateables currently in view
     */
    private final ObservableList<Rateable> allRateables;

    /**
     * The highest rated of all rateables in the model
     */
    private final RateableModel highestRated;

    /**
     * The lowest rated of all rateables in the model.
     */
    private final RateableModel lowestRated;
    
    public static synchronized RateMyModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RateMyModel();
        }
        return INSTANCE;
    }

    /**
     * Constructs a new RateMyModel
     */
    private RateMyModel() {
        allRateables = FXCollections.observableArrayList();
        highestRated = new RateableModel();
        lowestRated = new RateableModel();
    }

    /**
     * Gets the list of all Rateables added to the system.
     *
     * @return
     */
    public ObservableList<Rateable> getAllRateables() {
        return allRateables;
    }
    
    public void addNewRateAble(Rateable ratModel) {
        allRateables.add(ratModel);
    }
    
    public void clearAll() {
        allRateables.clear();
    }
    
    public void setHighestRatedModel(Rateable model) {
        highestRated.setRateable(model);
    }
    
    public RateableModel getHighestRated() {
        return highestRated;
    }
    
    public void setLowestRatedModel(Rateable model) {
        lowestRated.setRateable(model);
    }
    
    public RateableModel getLowestRated() {
        return lowestRated;
    }
    
}