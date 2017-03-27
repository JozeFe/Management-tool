/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import static org.junit.Assert.assertEquals;

import java.util.List;
import krcho.fbi.databaza.dao.CertifikatDao;
import krcho.fbi.databaza.tabulky.Certifikat;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jozef Krcho
 */
public class CertifikatyTest {

    private Session session;
    private CertifikatDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new CertifikatDao();
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
            Certifikat certifikat1 = new Certifikat("certifikat1", "Novy certifikat1");
            Certifikat certifikat2 = new Certifikat("certifikat2", "Novy certifikat2");

            dao.save(certifikat1);
            dao.save(certifikat2);

            List<Certifikat> certifikaty = dao.findAll();
            assertEquals("Objekt certifikat1 nie je zhodny s objektom z databazy!", certifikat1, dao.findById(certifikat1.getCertifikatId()));
            assertEquals("Objekt certifikat2 nie je zhodny s objektom z databazy!", certifikat2, dao.findById(certifikat2.getCertifikatId()));
            
            int pocetRiadkov = dao.findAll().size();
            
            dao.delete(certifikat1);
            dao.delete(certifikat2);
            
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
