/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.IscoDao;
import krcho.fbi.databaza.tabulky.Isco;
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
public class IscoTest {

    private Session session;
    private IscoDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new IscoDao();
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
            Isco isco1 = new Isco("0003451", "Isco jedna.");
            Isco isco2 = new Isco("0051", "Isco dva.");

            dao.save(isco1);
            dao.save(isco2);

            assertEquals("Objekt isco1 nie je zhodny s objektom z databazy!", isco1, dao.findById(isco1.getIscoKod()));
            assertEquals("Objekt isco2 nie je zhodny s objektom z databazy!", isco2, dao.findById(isco2.getIscoKod()));

            int pocetRiadkov = dao.findAll().size();

            dao.delete(isco1);
            dao.delete(isco2);

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
