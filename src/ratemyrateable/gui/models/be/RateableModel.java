/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.gui.models.be;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ratemyrateable.be.Rateable;

/**
 *
 * @author pgn
 */
public class RateableModel {
    
    private Rateable rateable;
    
    private final StringProperty descriptionProperty;
    private final DoubleProperty rateProperty;
    
    /**
     * Constructs a new Rateable model.
     */
    public RateableModel()
    {
        descriptionProperty = new SimpleStringProperty("N/A");
        rateProperty = new SimpleDoubleProperty(0);
    }
    
    /**
     * Sets the rateable object that the model should represent.
     * @param rateable The rateable to set.
     */
    public void setRateable(Rateable rateable)
    {
        this.rateable = rateable;
        descriptionProperty.setValue(rateable.getDescription());
        rateProperty.setValue(rateable.getRate());
    }

    /**
     * Gets the currently set Rateable object of this RateableModel.
     * @return The currently set Rateable object.
     */
    public Rateable getRateable() {
        return rateable;
    }

    /**
     * Gets the description of the currently set Rateable.
     * @return The description of the currently set Rateable.
     */ 
    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    /**
     * Gets the rate of the currently set Rateable.
     * @return The rate of the currently set Rateable.
     */
    public DoubleProperty getRateProperty() {
        return rateProperty;
    }
    
}
