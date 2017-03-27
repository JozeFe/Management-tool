/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Jozef Krcho
 */
public class TypovaPoziciaTest {
    private Session session;
    private TypovaPoziciaDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new TypovaPoziciaDao();
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
            TypovaPozicia pozicia1 = new TypovaPozicia("Straznik", "Strazi objekt.");
            TypovaPozicia pozicia2 = new TypovaPozicia("velitel straze", "Urcuje rozkazy pre straznikov.");

            dao.save(pozicia1);
            dao.save(pozicia2);

            assertEquals("Objekt pozicia1 nie je zhodny s objektom z databazy!", pozicia1, dao.findById(pozicia1.getPoziciaId()));
            assertEquals("Objekt pozicia2 nie je zhodny s objektom z databazy!", pozicia2, dao.findById(pozicia2.getPoziciaId()));
            
            int pocetRiadkov = dao.findAll().size();
            
            dao.delete(pozicia1);
            dao.delete(pozicia2);
            
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
