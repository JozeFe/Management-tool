/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.VnutorneSkupinyBRDao;
import krcho.fbi.databaza.tabulky.VnutorneSkupinyBR;
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
public class VnutorneSkupinyBRTest {
    private Session session;
    private VnutorneSkupinyBRDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new VnutorneSkupinyBRDao();
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
            VnutorneSkupinyBR skupina1 = new VnutorneSkupinyBR("Hardware");
            VnutorneSkupinyBR skupina2 = new VnutorneSkupinyBR("Software");

            dao.save(skupina1);
            dao.save(skupina2);

            assertEquals("Objekt skupina1 nie je zhodny s objektom z databazy!", skupina1, dao.findById(skupina1.getSkupinaId()));
            assertEquals("Objekt skupina2 nie je zhodny s objektom z databazy!", skupina2, dao.findById(skupina2.getSkupinaId()));
            
            int pocetRiadkov = dao.findAll().size();
            
            dao.delete(skupina1);
            dao.delete(skupina2);
            
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
