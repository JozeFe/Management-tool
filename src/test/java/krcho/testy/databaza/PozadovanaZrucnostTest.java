/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import krcho.fbi.databaza.dao.NKRZrucnostDao;
import krcho.fbi.databaza.dao.OdbornaZrucnostDao;
import krcho.fbi.databaza.dao.PozadovanaZrucnostDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.NKRZrucnost;
import krcho.fbi.databaza.tabulky.OdbornaZrucnost;
import krcho.fbi.databaza.tabulky.PozadovanaZrucnost;
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
public class PozadovanaZrucnostTest {

    private Session session;
    private PozadovanaZrucnostDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new PozadovanaZrucnostDao();
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

            OdbornaZrucnostDao odbornaZrucnostDao = new OdbornaZrucnostDao();
            odbornaZrucnostDao.setSession(session);
            odbornaZrucnostDao.save(zrucnost1);
            odbornaZrucnostDao.save(zrucnost2);

            TypovaPozicia pozicia1 = new TypovaPozicia("Straznik", "Strazi objekt.");
            TypovaPozicia pozicia2 = new TypovaPozicia("velitel straze", "Urcuje rozkazy pre straznikov.");

            TypovaPoziciaDao typovaPoziciaDao = new TypovaPoziciaDao();
            typovaPoziciaDao.setSession(session);
            typovaPoziciaDao.save(pozicia1);
            typovaPoziciaDao.save(pozicia2);

            PozadovanaZrucnost pozZrucnost1 = new PozadovanaZrucnost(Poziadavka.VYHODNA);
            PozadovanaZrucnost pozZrucnost2 = new PozadovanaZrucnost(Poziadavka.VYHODNA);
            PozadovanaZrucnost pozZrucnost3 = new PozadovanaZrucnost(Poziadavka.NUTNA);
            PozadovanaZrucnost pozZrucnost4 = new PozadovanaZrucnost(Poziadavka.NUTNA);

            NKRZrucnost nkrZrucnost = new NKRZrucnost(1000001, "uroven milion");
            NKRZrucnostDao nkrVedomostDao = new NKRZrucnostDao();
            nkrVedomostDao.setSession(session);
            nkrVedomostDao.save(nkrZrucnost);

            // pozZrucnost 1, pozicia1 a zrucnost1
            pozZrucnost1.setPozicia(pozicia1);
            pozicia1.getPozadovaneZrucnosti().add(pozZrucnost1);
            pozZrucnost1.setZrucnost(zrucnost1);
            zrucnost1.getPozadovaneZrucnosti().add(pozZrucnost1);
            pozZrucnost1.setUroven(nkrZrucnost);
            nkrZrucnost.getPozadovaneZrucnosti().add(pozZrucnost1);

            // pozZrucnost 2, pozicia1 a zrucnost2
            pozZrucnost2.setPozicia(pozicia1);
            pozicia1.getPozadovaneZrucnosti().add(pozZrucnost2);
            pozZrucnost2.setZrucnost(zrucnost2);
            zrucnost2.getPozadovaneZrucnosti().add(pozZrucnost2);
            pozZrucnost2.setUroven(nkrZrucnost);
            nkrZrucnost.getPozadovaneZrucnosti().add(pozZrucnost2);

            // pozZrucnost 3, pozicia2 a zrucnost1
            pozZrucnost3.setPozicia(pozicia2);
            pozicia2.getPozadovaneZrucnosti().add(pozZrucnost3);
            pozZrucnost3.setZrucnost(zrucnost1);
            zrucnost1.getPozadovaneZrucnosti().add(pozZrucnost3);
            pozZrucnost3.setUroven(nkrZrucnost);
            nkrZrucnost.getPozadovaneZrucnosti().add(pozZrucnost3);

            // pozZrucnost 4, pozicia2 a zrucnost2
            pozZrucnost4.setPozicia(pozicia2);
            pozicia2.getPozadovaneZrucnosti().add(pozZrucnost4);
            pozZrucnost4.setZrucnost(zrucnost2);
            zrucnost2.getPozadovaneZrucnosti().add(pozZrucnost4);
            pozZrucnost4.setUroven(nkrZrucnost);
            nkrZrucnost.getPozadovaneZrucnosti().add(pozZrucnost4);

            dao.save(pozZrucnost1);
            dao.save(pozZrucnost2);
            dao.save(pozZrucnost3);
            dao.save(pozZrucnost4);

            assertEquals("Objekt pozZrucnost1 nie je zhodny s objektom z databazy!", pozZrucnost1, dao.findById(pozZrucnost1.getPozZrucnostId()));
            assertEquals("Objekt pozZrucnost2 nie je zhodny s objektom z databazy!", pozZrucnost2, dao.findById(pozZrucnost2.getPozZrucnostId()));
            assertEquals("Objekt pozZrucnost3 nie je zhodny s objektom z databazy!", pozZrucnost3, dao.findById(pozZrucnost3.getPozZrucnostId()));
            assertEquals("Objekt pozZrucnost4 nie je zhodny s objektom z databazy!", pozZrucnost4, dao.findById(pozZrucnost4.getPozZrucnostId()));

            int pocetRiadkov = dao.findAll().size();

            dao.delete(pozZrucnost1);
            dao.delete(pozZrucnost2);
            dao.delete(pozZrucnost3);
            dao.delete(pozZrucnost4);
            typovaPoziciaDao.delete(pozicia1);
            typovaPoziciaDao.delete(pozicia2);
            odbornaZrucnostDao.delete(zrucnost1);
            odbornaZrucnostDao.delete(zrucnost2);
            nkrVedomostDao.delete(nkrZrucnost);

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
