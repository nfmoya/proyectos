package com.gefa.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class ConectionManager {
		
    private static final SessionFactory sessionFactory;

    static {
        try {
        	// Create the SessionFactory from standard (hibernate.cfg.xml)
            // config file.
            System.setProperty("java.net.useSystemProxies", "false");
            //sessionFactory =new Configuration().configure("sdd/util/resources/hibernate.cfg.xml").buildSessionFactory();
          
            String path = ConfigurationFiles.get().getProperties("server");
            System.out.println("ConfigurationFilesConfigurationFilesConfigurationFiles");
            sessionFactory = new Configuration().setProperty("hibernate.connection.url", "jdbc:mysql://" + path ).configure("/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
