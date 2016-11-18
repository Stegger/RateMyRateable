/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.be;

import java.io.Serializable;

/**
 *
 * @author pgn
 */
public class Rateable implements Serializable {

    /**
     * What is it that we are rating
     */
    private final String description;

    /**
     * The rate of the rateable
     */
    private final double rate;

    /**
     * Constructs a Rateable object with a description and a rate
     *
     * @param description
     * @param rate
     */
    public Rateable(String description, double rate) {
        this.description = description;
        this.rate = rate;
    }

    /**
     * Gets the description of the rateable object.
     *
     * @return The description of the rateable
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the rate of the rateable object.
     *
     * @return The rate of the rateable
     */
    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return description + ":" + rate;
    }

}
