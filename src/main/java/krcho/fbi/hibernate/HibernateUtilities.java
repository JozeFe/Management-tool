package krcho.fbi.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtilities {

    private static SessionFactory sessionFactory;

    
    public static boolean buildSessionFactory(String meno, String heslo) {
        try {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            cfg.setProperty("hibernate.connection.username", meno);
            cfg.setProperty("hibernate.connection.password", heslo);
            sessionFactory = cfg.buildSessionFactory();

        } catch (HibernateException e) {
            System.err.println("Nepodarilo sa vytvoriť SessionFactory!");
            return false;
        }
        return true;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory("appuser", "appuser");
        }
        return sessionFactory;
    }
}
