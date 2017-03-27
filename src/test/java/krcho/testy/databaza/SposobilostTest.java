/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import krcho.fbi.databaza.dao.SposobilostDao;
import krcho.fbi.databaza.dao.UrovenSposobilostiDao;
import krcho.fbi.databaza.tabulky.Sposobilost;
import krcho.fbi.databaza.tabulky.UrovenSposobilosti;
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
 * @author Jozef Krcho
 */
public class SposobilostTest {

    private Session session;
    private SposobilostDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new SposobilostDao();
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
            UrovenSposobilosti urovenSposobilosti1 = new UrovenSposobilosti("elementarna");
            UrovenSposobilosti urovenSposobilosti2 = new UrovenSposobilosti("pokrocila");
            UrovenSposobilostiDao urovenSposobilostiDao = new UrovenSposobilostiDao();
            urovenSposobilostiDao.setSession(session);
            urovenSposobilostiDao.save(urovenSposobilosti1);
            urovenSposobilostiDao.save(urovenSposobilosti2);
            
            Sposobilost sposobilost1 = new Sposobilost("Vedenie ludi", true);
            Sposobilost sposobilost2 = new Sposobilost("Vyjednavanie", false);
            sposobilost1.setUrovenSposobilosti(urovenSposobilosti1);
            urovenSposobilosti1.getSposobilosti().add(sposobilost1);
            sposobilost2.setUrovenSposobilosti(urovenSposobilosti2);
            urovenSposobilosti2.getSposobilosti().add(sposobilost2);

            dao.save(sposobilost1);
            dao.save(sposobilost2);

            assertEquals("Objekt sposobilost1 nie je zhodny s objektom z databazy!", sposobilost1, dao.findById(sposobilost1.getSposobilostId()));
            assertEquals("Objekt sposobilost2 nie je zhodny s objektom z databazy!", sposobilost2, dao.findById(sposobilost2.getSposobilostId()));
            
            int pocetRiadkov = dao.findAll().size();

            sposobilost1.getUrovenSposobilosti().getSposobilosti().remove(sposobilost1);
            dao.delete(sposobilost1);
            sposobilost2.getUrovenSposobilosti().getSposobilosti().remove(sposobilost2);
            dao.delete(sposobilost2);
            urovenSposobilostiDao.delete(urovenSposobilosti1);
            urovenSposobilostiDao.delete(urovenSposobilosti2);

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
