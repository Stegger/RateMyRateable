/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.bll;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import ratemyrateable.be.Rateable;
import ratemyrateable.dal.RateableDAO;

/**
 *
 * @author pgn
 */
public class RateableManager
{
    /**
     * Our object for persistence:
     */
    private RateableDAO rateableDAO;

    /**
     * Constructs a rateable manager
     */
    public RateableManager()
    {
        rateableDAO = new RateableDAO();
    }
    
    /**
     * Creates annd returns a new Rateable object.
     * @param description The description of the rateable.
     * @param rate The Rate of the rateable.
     * @return The new Rateable object.
     */
    public Rateable creatNewRateable(String description, double rate)
    {
        return new Rateable(description, rate);
    }

    /**
     * Finds the highest rated rateable in a list of rateables.
     * @param allRateables The list of rateables.
     * @return The highest rated item in the list.
     */
    public Rateable getHighestRated(List<Rateable> allRateables)
    {
        Rateable high = allRateables.get(0);
        for (Rateable rate : allRateables)
        {
            if (rate.getRate() > high.getRate())
            {
                high = rate;
            }
        }
        return high;
    }

    public Rateable getLowestRated(List<Rateable> allRateables)
    {
        Rateable low = allRateables.get(0);
        for (Rateable rate : allRateables)
        {
            if (rate.getRate() < low.getRate())
            {
                low = rate;
            }
        }
        return low;
    }

    public double average(List<Rateable> rateables)
    {
        double total = 0;
        for (Rateable rateable : rateables)
        {
            total += rateable.getRate();
        }
        return total / rateables.size();
    }

    public Map<Double, Integer> distributionOfRatings(List<Rateable> rateables)
    {
        SortedMap<Double, Integer> distribution = new TreeMap<>();
        for (Rateable rate : rateables)
        {
            int occurencesOfRate = 1;
            if (distribution.containsKey(rate.getRate()))
            {
                occurencesOfRate += distribution.get(rate.getRate());
            }
            distribution.put(rate.getRate(), occurencesOfRate);
        }
        return distribution;
    }

    public void saveRatings(List<Rateable> allRateables, File file) throws IOException
    {
        //Because the observerable list is not serializable we have to convert it to a normal arraylist.
        ArrayList<Rateable> serializableList = new ArrayList<>(allRateables);
        rateableDAO.saveRateablesToFile(serializableList, file);
    }

    public List<Rateable> loadRatings(File file) throws IOException, ClassNotFoundException
    {
       return rateableDAO.loadRateablesFromFile(file);
    }
}
