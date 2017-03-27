/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.HrozbaDao;
import krcho.fbi.databaza.tabulky.Hrozba;
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
public class HrozbaTest {

    private Session session;
    private HrozbaDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new HrozbaDao();
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
            Hrozba hrozba1 = new Hrozba("hacker", "vyzva", "hacking");
            Hrozba hrozba2 = new Hrozba("pocitacova kriminalita", "znicenie informacii", "Pocitacovy zlocin (napr. kyberneticke prenasledovanie)");

            dao.save(hrozba1);
            dao.save(hrozba2);

            List<Hrozba> hrozby = dao.findAll();
            assertEquals("Objekt hrozba1 nie je zhodny s objektom z databazy!", hrozba1, dao.findById(hrozba1.getHrozbaId()));
            assertEquals("Objekt hrozba2 nie je zhodny s objektom z databazy!", hrozba2, dao.findById(hrozba2.getHrozbaId()));

            int pocetRiadkov = dao.findAll().size();

            dao.delete(hrozba1);
            dao.delete(hrozba2);

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
