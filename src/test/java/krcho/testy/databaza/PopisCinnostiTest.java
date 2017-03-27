/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import krcho.fbi.databaza.dao.PopisCinnostiDao;
import krcho.fbi.databaza.tabulky.PopisCinnosti;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author JozeFe
 */
public class PopisCinnostiTest {

    private Session session;
    private PopisCinnostiDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new PopisCinnostiDao();
        dao.setSession(session);
    }

    @After
    public void zatvor() {
        session.close();
    }

    @Test
    public void testVlozVyberVymaz() {
        Transaction trans = session.getTransaction();
        try {
            trans.begin();
            PopisCinnosti cinnost1 = new PopisCinnosti("Strazi priestor, a monitoruje okolie.");
            PopisCinnosti cinnost2 = new PopisCinnosti("Dohliada na vykonanie prace.");

            dao.save(cinnost1);
            dao.save(cinnost2);

            assertEquals("Objekt cinnost1 nie je zhodny s objektom z databazy!", cinnost1, dao.findById(cinnost1.getCinnostId()));
            assertEquals("Objekt cinnost2 nie je zhodny s objektom z databazy!", cinnost2, dao.findById(cinnost2.getCinnostId()));

            int pocetRiadkov = dao.findAll().size();

            dao.delete(cinnost1);
            dao.delete(cinnost2);

            assertEquals("Pocet riadkov v tabulke nie je spravny. Delete nefungoval!", dao.findAll().size() + 2, pocetRiadkov);

            trans.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        }
    }
}
