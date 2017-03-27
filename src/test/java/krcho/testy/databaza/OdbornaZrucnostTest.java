/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.OdbornaZrucnostDao;
import krcho.fbi.databaza.tabulky.OdbornaZrucnost;
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
public class OdbornaZrucnostTest {

    private Session session;
    private OdbornaZrucnostDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new OdbornaZrucnostDao();
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
            OdbornaZrucnost zrucnost1 = new OdbornaZrucnost("dozor v objektoch a na verejnych priestranstvach");
            OdbornaZrucnost zrucnost2 = new OdbornaZrucnost("kontrolovanie stavu zabezpecenia vstupu do objektov a budov");

            dao.save(zrucnost1);
            dao.save(zrucnost2);

            assertEquals("Objekt zrucnost1 nie je zhodny s objektom z databazy!", zrucnost1, dao.findById(zrucnost1.getZrucnostId()));
            assertEquals("Objekt zrucnost2 nie je zhodny s objektom z databazy!", zrucnost2, dao.findById(zrucnost2.getZrucnostId()));
            
            int pocetRiadkov = dao.findAll().size();

            dao.delete(zrucnost1);
            dao.delete(zrucnost2);

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
