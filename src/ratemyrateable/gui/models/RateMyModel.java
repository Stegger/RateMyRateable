/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.gui.models;

import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import ratemyrateable.be.Rateable;
import ratemyrateable.gui.models.be.RateableModel;

/**
 *
 * @author pgn
 */
public class RateMyModel
{

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

    /**
     * The average of all the ratings
     */
    private final DoubleProperty average;

    /**
     * The distribution of the different ratings
     */
    private final ObservableList<Series<Double, Integer>> chartDistributionData;

    /**
     * The method to get a reference to this Singleton:
     *
     * @return
     */
    public static synchronized RateMyModel getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new RateMyModel();
        }
        return INSTANCE;
    }

    /**
     * Constructs a new RateMyModel
     */
    private RateMyModel()
    {
        allRateables = FXCollections.observableArrayList();
        highestRated = new RateableModel();
        lowestRated = new RateableModel();
        average = new SimpleDoubleProperty(0);
        chartDistributionData = FXCollections.observableArrayList();
    }

    /**
     * Gets the list of all Rateables added to the system.
     *
     * @return
     */
    public ObservableList<Rateable> getAllRateables()
    {
        return allRateables;
    }

    /**
     * Adds a new rateable to this model.
     *
     * @param ratModel
     */
    public void addNewRateAble(Rateable ratModel)
    {
        allRateables.add(ratModel);        
    }

    /**
     * Clears the entire model.
     */
    public void clearAll()
    {
        allRateables.clear();
        highestRated.clear();
        lowestRated.clear();
        average.set(0);
        chartDistributionData.clear();
    }

    public void setHighestRatedModel(Rateable model)
    {
        highestRated.setRateable(model);
    }

    public RateableModel getHighestRated()
    {
        return highestRated;
    }

    public void setLowestRatedModel(Rateable model)
    {
        lowestRated.setRateable(model);
    }

    public RateableModel getLowestRated()
    {
        return lowestRated;
    }

    public DoubleProperty getAverage()
    {
        return average;
    }

    public void setAverage(double average)
    {
        this.average.set(average);
    }

    
    
    public ObservableList<Series<Double, Integer>> getDistributionData()
    {
        return chartDistributionData;
    }

}
