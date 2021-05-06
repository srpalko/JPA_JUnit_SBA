package com.srp.jpa.env;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryProvider {

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("SMS");

    public static void shutdownFactory() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }

}
