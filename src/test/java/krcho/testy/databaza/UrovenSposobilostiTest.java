/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.UrovenSposobilostiDao;
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
public class UrovenSposobilostiTest {

    private Session session;
    private UrovenSposobilostiDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new UrovenSposobilostiDao();
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

            dao.save(urovenSposobilosti1);
            dao.save(urovenSposobilosti2);

            assertEquals("Objekt urovenSposobilosti1 nie je zhodny s objektom z databazy!", urovenSposobilosti1, dao.findById(urovenSposobilosti1.getUrovenId()));
            assertEquals("Objekt urovenSposobilosti2 nie je zhodny s objektom z databazy!", urovenSposobilosti2, dao.findById(urovenSposobilosti2.getUrovenId()));
            
            int pocetRiadkov = dao.findAll().size();

            dao.delete(urovenSposobilosti1);
            dao.delete(urovenSposobilosti2);

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
