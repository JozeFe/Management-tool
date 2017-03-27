/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import krcho.fbi.databaza.dao.OdbornaVedomostDao;
import krcho.fbi.databaza.tabulky.OdbornaVedomost;
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
public class OdbornaVedomostTest {

    private Session session;
    private OdbornaVedomostDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new OdbornaVedomostDao();
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
            OdbornaVedomost vedomost1 = new OdbornaVedomost("cinnosti straznika");
            OdbornaVedomost vedomost2 = new OdbornaVedomost("ochrana a obsluha objektov, osob a majetku");

            dao.save(vedomost1);
            dao.save(vedomost2);

            assertEquals("Objekt vedomost1 nie je zhodny s objektom z databazy!", vedomost1, dao.findById(vedomost1.getVedomostId()));
            assertEquals("Objekt vedomost2 nie je zhodny s objektom z databazy!", vedomost2, dao.findById(vedomost2.getVedomostId()));

            int pocetRiadkov = dao.findAll().size();

            dao.delete(vedomost1);
            dao.delete(vedomost2);

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
