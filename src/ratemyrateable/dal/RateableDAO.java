/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratemyrateable.dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import ratemyrateable.be.Rateable;

/**
 *
 * @author Stegger
 */
public class RateableDAO
{

    /**
     * Writes a list of rateables to the given file.
     *
     * @param allRateables The list to save.
     * @param file The file to save to.
     * @throws IOException
     */
    public void saveRateablesToFile(List<Rateable> allRateables, File file) throws IOException
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
        {
            oos.writeObject(allRateables);
        }
    }

    public List<Rateable> loadRateablesFromFile(File file) throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
        {
            return (List<Rateable>) ois.readObject();
        }
    }

}
