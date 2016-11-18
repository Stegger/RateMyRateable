/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.bll;

import java.util.List;
import ratemyrateable.be.Rateable;

/**
 *
 * @author pgn
 */
public class RateableManager {

    
    
    public Rateable creatNewRateable(String description, double rate) {
        //TODO Dummy implementation, must be created in DAL!
        return new Rateable(description, rate);
    }

    public Rateable getHighestRated(List<Rateable> allRateables) {
        Rateable high = allRateables.get(0);
        for (Rateable rate : allRateables) {
            if (rate.getRate() > high.getRate()) {
                high = rate;
            }
        }
        return high;
    }

    public Rateable getLowestRated(List<Rateable> allRateables) {
        Rateable low = allRateables.get(0);
        for (Rateable rate : allRateables) {
            if (rate.getRate() < low.getRate()) {
                low = rate;
            }
        }
        return low;
    }

}
