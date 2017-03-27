/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import krcho.fbi.databaza.dao.TypObjektuDao;
import krcho.fbi.databaza.tabulky.TypObjektu;
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
public class TypObjektuTest {
    private Session session;
    private TypObjektuDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new TypObjektuDao();
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
            TypObjektu objektTyp1 = new TypObjektu("budova1", "Betonova stavba.");
            TypObjektu objektTyp2 = new TypObjektu("budova2", "Policajna stanica");

            dao.save(objektTyp1);
            dao.save(objektTyp2);

            assertEquals("Objekt objektTyp1 nie je zhodny s objektom z databazy!", objektTyp1, dao.findById(objektTyp1.getObjektId()));
            assertEquals("Objekt objektTyp2 nie je zhodny s objektom z databazy!", objektTyp2, dao.findById(objektTyp2.getObjektId()));
            
            int pocetRiadkov = dao.findAll().size();
            
            dao.delete(objektTyp1);
            dao.delete(objektTyp2);
            
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
