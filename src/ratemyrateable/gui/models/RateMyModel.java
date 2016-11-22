/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.gui.models;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import ratemyrateable.be.Rateable;
import ratemyrateable.bll.RateableManager;
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
    private final Property<ObservableList<Series<Number, Number>>> chartDistributionData;
    private ObservableList<Series<Number, Number>> internalChartDistData;

    private RateableManager rateableManager;

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
        rateableManager = new RateableManager();

        allRateables = FXCollections.observableArrayList();
        highestRated = new RateableModel();
        lowestRated = new RateableModel();
        average = new SimpleDoubleProperty(0);
        chartDistributionData = new SimpleObjectProperty<>();
        internalChartDistData = FXCollections.observableArrayList();
        chartDistributionData.setValue(internalChartDistData);
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
     * Clears the entire model.
     */
    public void clearAll()
    {
        allRateables.clear();
        highestRated.clear();
        lowestRated.clear();
        average.set(0);
        internalChartDistData.clear();
    }

    public RateableModel getHighestRated()
    {
        return highestRated;
    }

    public RateableModel getLowestRated()
    {
        return lowestRated;
    }

    public DoubleProperty getAverage()
    {
        return average;
    }

    public void addNewRateAble(String description, double rate)
    {
        Rateable rating = rateableManager.creatNewRateable(description, rate);

        allRateables.add(rating);

        //We refresh the high, low and average:
        refreshDerivedData();

        //We update the chart:
        setChartData();
    }

    private void refreshDerivedData()
    {
        //THe highest in the list:
        Rateable highRat = rateableManager.getHighestRated(allRateables);
        highestRated.setRateable(highRat);

        //The lowest in the list:
        Rateable lowRat = rateableManager.getLowestRated(allRateables);
        lowestRated.setRateable(lowRat);

        //I calculate and update the average of the model:
        double avg = rateableManager.average(allRateables);
        average.set(avg);
    }

    private void setChartData()
    {
        Map<Double, Integer> data = rateableManager.distributionOfRatings(allRateables);
        XYChart.Series<Number, Number> serie = new Series();
        for (Double d : data.keySet())
        {
            serie.getData().add(new XYChart.Data<Number, Number>(d, data.get(d)));
        }
        internalChartDistData.clear();
        internalChartDistData.add(serie);
    }

    public Property<ObservableList<Series<Number, Number>>> getChartData()
    {
        return chartDistributionData;
    }

    public void SaveRateablesToFile(File file) throws IOException
    {
        rateableManager.saveRatings(allRateables, file);
    }

    public void LoadRateablesFromFile(File file) throws IOException, ClassNotFoundException
    {
        List<Rateable> newAllRateables = rateableManager.loadRatings(file);
        allRateables.setAll(newAllRateables);
        refreshDerivedData();
        setChartData();
    }

}
