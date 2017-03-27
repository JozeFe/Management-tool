/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.OdbornaPraxDao;
import krcho.fbi.databaza.tabulky.OdbornaPrax;
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
public class OdbornaPraxTest {

    private Session session;
    private OdbornaPraxDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new OdbornaPraxDao();
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
            OdbornaPrax prax1 = new OdbornaPrax("Prax aspom 2 roky.");
            OdbornaPrax prax2 = new OdbornaPrax("Nevyzaduje sa.");

            dao.save(prax1);
            dao.save(prax2);

            assertEquals("Objekt prax1 nie je zhodny s objektom z databazy!", prax1, dao.findById(prax1.getPraxId()));
            assertEquals("Objekt prax2 nie je zhodny s objektom z databazy!", prax2, dao.findById(prax2.getPraxId()));

            int pocetRiadkov = dao.findAll().size();

            dao.delete(prax1);
            dao.delete(prax2);

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
