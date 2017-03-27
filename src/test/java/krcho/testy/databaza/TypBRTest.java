/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.VonkajsiTypBRDao;
import krcho.fbi.databaza.tabulky.VonkajsiTypBR;
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
public class TypBRTest {

    private Session session;
    private VonkajsiTypBRDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new VonkajsiTypBRDao();
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
            VonkajsiTypBR typBr1 = new VonkajsiTypBR("Fyzicke poskodenie");
            VonkajsiTypBR typBr2 = new VonkajsiTypBR("Prirodni javy");

            dao.save(typBr1);
            dao.save(typBr2);

            assertEquals("Objekt typBr1 nie je zhodny s objektom z databazy!", typBr1, dao.findById(typBr1.getTypBrId()));
            assertEquals("Objekt typBr2 nie je zhodny s objektom z databazy!", typBr2, dao.findById(typBr2.getTypBrId()));

            int pocetRiadkov = dao.findAll().size();

            dao.delete(typBr1);
            dao.delete(typBr2);

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
