/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import krcho.fbi.databaza.dao.VnutorneBRDao;
import krcho.fbi.databaza.dao.VnutorneSkupinyBRDao;
import krcho.fbi.databaza.tabulky.VnutorneBR;
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
public class VnutorneBRTest {

    private Session session;
    private VnutorneBRDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new VnutorneBRDao();
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
            VnutorneSkupinyBR skupina1 = new VnutorneSkupinyBR("hardware");
            VnutorneSkupinyBR skupina2 = new VnutorneSkupinyBR("software");
            
            VnutorneSkupinyBRDao skupinyDao = new VnutorneSkupinyBRDao();
            skupinyDao.setSession(session);
            skupinyDao.save(skupina1);
            skupinyDao.save(skupina2);
            
            VnutorneBR vnutorneRiziko1 = new VnutorneBR("nedostatocna udrzba", "chyba udrzby systemu");
            VnutorneBR vnutorneRiziko2 = new VnutorneBR("nedostatocna udrzba", "chyba udrzby systemu");
            
            vnutorneRiziko1.setSkupinaBR(skupina1);
            skupina1.getVnutorneBR().add(vnutorneRiziko1);
            vnutorneRiziko2.setSkupinaBR(skupina2);
            skupina2.getVnutorneBR().add(vnutorneRiziko2);
            
            dao.save(vnutorneRiziko1);
            dao.save(vnutorneRiziko2);

            assertEquals("Objekt vnutorneRiziko1 nie je zhodny s objektom z databazy!", vnutorneRiziko1, dao.findById(vnutorneRiziko1.getVnutorneBrId()));
            assertEquals("Objekt vnutorneRiziko2 nie je zhodny s objektom z databazy!", vnutorneRiziko2, dao.findById(vnutorneRiziko2.getVnutorneBrId()));
            
            int pocetRiadkov = dao.findAll().size();

            skupina1.getVnutorneBR().remove(vnutorneRiziko1);
            dao.delete(vnutorneRiziko1);
            skupina2.getVnutorneBR().remove(vnutorneRiziko2);
            dao.delete(vnutorneRiziko2);
            skupinyDao.delete(skupina1);
            skupinyDao.delete(skupina2);

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
