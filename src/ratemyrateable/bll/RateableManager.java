/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import ratemyrateable.be.Rateable;

/**
 *
 * @author pgn
 */
public class RateableManager
{

    public Rateable creatNewRateable(String description, double rate)
    {
        //TODO Dummy implementation, must be created in DAL!
        return new Rateable(description, rate);
    }

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
        for(Rateable rate : rateables)
        {
            int occurencesOfRate = 1;
            if(distribution.containsKey(rate.getRate()))
                occurencesOfRate += distribution.get(rate.getRate());
            distribution.put(rate.getRate(), occurencesOfRate);
        }
        return distribution;
    }
}
