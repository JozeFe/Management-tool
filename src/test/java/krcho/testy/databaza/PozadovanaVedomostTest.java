/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import krcho.fbi.databaza.dao.NKRVedomostDao;
import krcho.fbi.databaza.dao.OdbornaVedomostDao;
import krcho.fbi.databaza.dao.PozadovanaVedomostDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.NKRVedomost;
import krcho.fbi.databaza.tabulky.OdbornaVedomost;
import krcho.fbi.databaza.tabulky.PozadovanaVedomost;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.databaza.tabulky.enumy.Poziadavka;
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
public class PozadovanaVedomostTest {

    private Session session;
    private PozadovanaVedomostDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new PozadovanaVedomostDao();
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

            OdbornaVedomostDao odbornaVedomostDao = new OdbornaVedomostDao();
            odbornaVedomostDao.setSession(session);
            odbornaVedomostDao.save(vedomost1);
            odbornaVedomostDao.save(vedomost2);

            TypovaPozicia pozicia1 = new TypovaPozicia("Straznik", "Strazi objekt.");
            TypovaPozicia pozicia2 = new TypovaPozicia("velitel straze", "Urcuje rozkazy pre straznikov.");

            TypovaPoziciaDao typovaPoziciaDao = new TypovaPoziciaDao();
            typovaPoziciaDao.setSession(session);
            typovaPoziciaDao.save(pozicia1);
            typovaPoziciaDao.save(pozicia2);

            PozadovanaVedomost pozVedomost1 = new PozadovanaVedomost(Poziadavka.VYHODNA);
            PozadovanaVedomost pozVedomost2 = new PozadovanaVedomost(Poziadavka.VYHODNA);
            PozadovanaVedomost pozVedomost3 = new PozadovanaVedomost(Poziadavka.NUTNA);
            PozadovanaVedomost pozVedomost4 = new PozadovanaVedomost(Poziadavka.NUTNA);
            
            NKRVedomost nkrVedomost = new NKRVedomost(1000001, "uroven milion");
            NKRVedomostDao nkrVedomostDao = new NKRVedomostDao();
            nkrVedomostDao.setSession(session);
            nkrVedomostDao.save(nkrVedomost);

            // pozVedomost 1, pozicia1 a vedomost1
            pozVedomost1.setPozicia(pozicia1);
            pozicia1.getPozadovaneVedomosti().add(pozVedomost1);
            pozVedomost1.setVedomost(vedomost1);
            vedomost1.getPozadovaneVedomosti().add(pozVedomost1);
            pozVedomost1.setUroven(nkrVedomost);
            nkrVedomost.getPozadovaneVedomosti().add(pozVedomost1);

            // pozVedomost 2, pozicia1 a vedomost2
            pozVedomost2.setPozicia(pozicia1);
            pozicia1.getPozadovaneVedomosti().add(pozVedomost2);
            pozVedomost2.setVedomost(vedomost2);
            vedomost2.getPozadovaneVedomosti().add(pozVedomost2);
            pozVedomost2.setUroven(nkrVedomost);
            nkrVedomost.getPozadovaneVedomosti().add(pozVedomost2);

            // pozVedomost 3, pozicia2 a vedomost1
            pozVedomost3.setPozicia(pozicia2);
            pozicia2.getPozadovaneVedomosti().add(pozVedomost3);
            pozVedomost3.setVedomost(vedomost1);
            vedomost1.getPozadovaneVedomosti().add(pozVedomost3);
            pozVedomost3.setUroven(nkrVedomost);
            nkrVedomost.getPozadovaneVedomosti().add(pozVedomost3);

            // pozVedomost 4, pozicia2 a vedomost2
            pozVedomost4.setPozicia(pozicia2);
            pozicia2.getPozadovaneVedomosti().add(pozVedomost4);
            pozVedomost4.setVedomost(vedomost2);
            vedomost2.getPozadovaneVedomosti().add(pozVedomost4);
            pozVedomost4.setUroven(nkrVedomost);
            nkrVedomost.getPozadovaneVedomosti().add(pozVedomost4);

            dao.save(pozVedomost1);
            dao.save(pozVedomost2);
            dao.save(pozVedomost3);
            dao.save(pozVedomost4);

            assertEquals("Objekt pozVedomost1 nie je zhodny s objektom z databazy!", pozVedomost1, dao.findById(pozVedomost1.getPozVedomostId()));
            assertEquals("Objekt pozVedomost2 nie je zhodny s objektom z databazy!", pozVedomost2, dao.findById(pozVedomost2.getPozVedomostId()));
            assertEquals("Objekt pozVedomost3 nie je zhodny s objektom z databazy!", pozVedomost3, dao.findById(pozVedomost3.getPozVedomostId()));
            assertEquals("Objekt pozVedomost4 nie je zhodny s objektom z databazy!", pozVedomost4, dao.findById(pozVedomost4.getPozVedomostId()));

            int pocetRiadkov = dao.findAll().size();

            dao.delete(pozVedomost1);
            dao.delete(pozVedomost2);
            dao.delete(pozVedomost3);
            dao.delete(pozVedomost4);
            typovaPoziciaDao.delete(pozicia1);
            typovaPoziciaDao.delete(pozicia2);
            odbornaVedomostDao.delete(vedomost1);
            odbornaVedomostDao.delete(vedomost2);
            nkrVedomostDao.delete(nkrVedomost);

            assertEquals("Pocet riadkov v tabulke nie je spravny. Delete nefungoval!", dao.findAll().size() + 4, pocetRiadkov);

            trans.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        }
    }

}
