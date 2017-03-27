/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.StupenVzdelaniaDao;
import krcho.fbi.databaza.tabulky.StupenVzdelania;
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
public class StupenVzdelaniaTest {

    private Session session;
    private StupenVzdelaniaDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new StupenVzdelaniaDao();
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
            StupenVzdelania vzdelanie1 = new StupenVzdelania("Vysokoskolske vzdelania", "bakalarske", "zakon c.34/b");
            StupenVzdelania vzdelanie2 = new StupenVzdelania("Stredoskolske vzdelanie", "zakladna", "zakon c.34/b");

            dao.save(vzdelanie1);
            dao.save(vzdelanie2);

            assertEquals("Objekt vzdelanie1 nie je zhodny s objektom z databazy!", vzdelanie1, dao.findById(vzdelanie1.getVzdelanieId()));
            assertEquals("Objekt vzdelanie2 nie je zhodny s objektom z databazy!", vzdelanie2, dao.findById(vzdelanie2.getVzdelanieId()));
            
            int pocetRiadkov = dao.findAll().size();

            dao.delete(vzdelanie1);
            dao.delete(vzdelanie2);

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
