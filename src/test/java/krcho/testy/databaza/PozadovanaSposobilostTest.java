package krcho.testy.databaza;

import krcho.fbi.databaza.dao.PozadovanaSposobilostDao;
import krcho.fbi.databaza.dao.SposobilostDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.dao.UrovenSposobilostiDao;
import krcho.fbi.databaza.tabulky.PozadovanaSposobilost;
import krcho.fbi.databaza.tabulky.Sposobilost;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.databaza.tabulky.UrovenSposobilosti;
import krcho.fbi.databaza.tabulky.enumy.Poziadavka;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jozef Krcho
 */
public class PozadovanaSposobilostTest {

    private Session session;
    private PozadovanaSposobilostDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new PozadovanaSposobilostDao();
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
            UrovenSposobilosti urovenSposobilosti1 = new UrovenSposobilosti("elementarna");
            UrovenSposobilosti urovenSposobilosti2 = new UrovenSposobilosti("pokrocila");
            UrovenSposobilostiDao urovenSposobilostiDao = new UrovenSposobilostiDao();
            urovenSposobilostiDao.setSession(session);
            urovenSposobilostiDao.save(urovenSposobilosti1);
            urovenSposobilostiDao.save(urovenSposobilosti2);

            Sposobilost sposobilost1 = new Sposobilost("Vedenie ludi", true);
            Sposobilost sposobilost2 = new Sposobilost("Vyjednavanie", false);
            sposobilost1.setUrovenSposobilosti(urovenSposobilosti1);
            urovenSposobilosti1.getSposobilosti().add(sposobilost1);
            sposobilost2.setUrovenSposobilosti(urovenSposobilosti2);
            urovenSposobilosti2.getSposobilosti().add(sposobilost2);

            SposobilostDao sposobilostDao = new SposobilostDao();
            sposobilostDao.setSession(session);
            sposobilostDao.save(sposobilost1);
            sposobilostDao.save(sposobilost2);

            TypovaPozicia pozicia1 = new TypovaPozicia("Straznik", "Strazi objekt.");
            TypovaPozicia pozicia2 = new TypovaPozicia("velitel straze", "Urcuje rozkazy pre straznikov.");

            TypovaPoziciaDao typovaPoziciaDao = new TypovaPoziciaDao();
            typovaPoziciaDao.setSession(session);
            typovaPoziciaDao.save(pozicia1);
            typovaPoziciaDao.save(pozicia2);

            PozadovanaSposobilost pozSposobilost1 = new PozadovanaSposobilost(Poziadavka.VYHODNA);
            PozadovanaSposobilost pozSposobilost2 = new PozadovanaSposobilost(Poziadavka.VYHODNA);
            PozadovanaSposobilost pozSposobilost3 = new PozadovanaSposobilost(Poziadavka.NUTNA);
            PozadovanaSposobilost pozSposobilost4 = new PozadovanaSposobilost(Poziadavka.NUTNA);

            // pozSposbilost 1, pozicia1 a sposobilost1
            pozSposobilost1.setPozicia(pozicia1);
            pozicia1.getPozadovaneSposobilosti().add(pozSposobilost1);
            pozSposobilost1.setSposobilost(sposobilost1);
            sposobilost1.getPozadovaneSposobilosti().add(pozSposobilost1);

            // pozSposbilost 2, pozicia1 a sposobilost2
            pozSposobilost2.setPozicia(pozicia1);
            pozicia1.getPozadovaneSposobilosti().add(pozSposobilost2);
            pozSposobilost2.setSposobilost(sposobilost2);
            sposobilost2.getPozadovaneSposobilosti().add(pozSposobilost2);

            // pozSposbilost 3, pozicia2 a sposobilost1
            pozSposobilost3.setPozicia(pozicia2);
            pozicia2.getPozadovaneSposobilosti().add(pozSposobilost3);
            pozSposobilost3.setSposobilost(sposobilost1);
            sposobilost1.getPozadovaneSposobilosti().add(pozSposobilost3);

            // pozSposbilost 4, pozicia2 a sposobilost2
            pozSposobilost4.setPozicia(pozicia2);
            pozicia2.getPozadovaneSposobilosti().add(pozSposobilost4);
            pozSposobilost4.setSposobilost(sposobilost2);
            sposobilost2.getPozadovaneSposobilosti().add(pozSposobilost4);

            dao.save(pozSposobilost1);
            dao.save(pozSposobilost2);
            dao.save(pozSposobilost3);
            dao.save(pozSposobilost4);

            assertEquals("Objekt pozSposobilost1 nie je zhodny s objektom z databazy!", pozSposobilost1, dao.findById(pozSposobilost1.getPozSposobilostsId()));
            assertEquals("Objekt pozSposobilost2 nie je zhodny s objektom z databazy!", pozSposobilost2, dao.findById(pozSposobilost2.getPozSposobilostsId()));
            assertEquals("Objekt pozSposobilost3 nie je zhodny s objektom z databazy!", pozSposobilost3, dao.findById(pozSposobilost3.getPozSposobilostsId()));
            assertEquals("Objekt pozSposobilost4 nie je zhodny s objektom z databazy!", pozSposobilost4, dao.findById(pozSposobilost4.getPozSposobilostsId()));

            int pocetRiadkov = dao.findAll().size();

            dao.delete(pozSposobilost1);
            dao.delete(pozSposobilost2);
            dao.delete(pozSposobilost3);
            dao.delete(pozSposobilost4);
            typovaPoziciaDao.delete(pozicia1);
            typovaPoziciaDao.delete(pozicia2);
            sposobilostDao.delete(sposobilost1);
            sposobilostDao.delete(sposobilost2);
            urovenSposobilostiDao.delete(urovenSposobilosti1);
            urovenSposobilostiDao.delete(urovenSposobilosti2);

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
