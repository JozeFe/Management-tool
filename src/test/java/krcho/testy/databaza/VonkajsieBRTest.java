/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.VonkajsiTypBRDao;
import krcho.fbi.databaza.dao.VonkajsieBRDao;
import krcho.fbi.databaza.tabulky.VonkajsiTypBR;
import krcho.fbi.databaza.tabulky.VonkajsieBR;
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
public class VonkajsieBRTest {
    private Session session;
    private VonkajsieBRDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new VonkajsieBRDao();
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
            VonkajsiTypBR typ1 = new VonkajsiTypBR("Fyzicke poskodenie");
            VonkajsiTypBR typ2 = new VonkajsiTypBR("Prirodni javy");
            
            VonkajsiTypBRDao typBRDao = new VonkajsiTypBRDao();
            typBRDao.setSession(session);
            typBRDao.save(typ1);
            typBRDao.save(typ2);
            
            VonkajsieBR vonkajsieRiziko1 = new VonkajsieBR("Poziar");
            VonkajsieBR vonkajsieRiziko2 = new VonkajsieBR("Klimaticky jav");
            
            vonkajsieRiziko1.setTypBR(typ1);
            typ1.getVonkajsieBR().add(vonkajsieRiziko1);
            vonkajsieRiziko2.setTypBR(typ2);
            typ2.getVonkajsieBR().add(vonkajsieRiziko2);

            dao.save(vonkajsieRiziko1);
            dao.save(vonkajsieRiziko2);

            assertEquals("Objekt vonkajsieRiziko1 nie je zhodny s objektom z databazy!", vonkajsieRiziko1, dao.findById(vonkajsieRiziko1.getVonkajsieBrId()));
            assertEquals("Objekt vonkajsieRiziko2 nie je zhodny s objektom z databazy!", vonkajsieRiziko2, dao.findById(vonkajsieRiziko2.getVonkajsieBrId()));
            
            int pocetRiadkov = dao.findAll().size();

            typ1.getVonkajsieBR().remove(vonkajsieRiziko1);
            dao.delete(vonkajsieRiziko1);
            typ2.getVonkajsieBR().remove(vonkajsieRiziko2);
            dao.delete(vonkajsieRiziko2);
            typBRDao.delete(typ1);
            typBRDao.delete(typ2);

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
