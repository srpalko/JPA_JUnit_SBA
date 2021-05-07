package com.srp.jpa.env;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class to contain the EntityManagerFactory.
 */
public class FactoryProvider {

    /**
     * Initialized the EntityManagerFactory
     */
    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("SMS");

    /**
     * Shuts down EntityManagerFactory
     */
    public static void shutdownFactory() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }

}
