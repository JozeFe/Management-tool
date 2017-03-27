/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import krcho.fbi.databaza.dao.OpisUrovneDao;
import krcho.fbi.databaza.dao.SposobilostDao;
import krcho.fbi.databaza.dao.UrovenSposobilostiDao;
import krcho.fbi.databaza.tabulky.OpisUrovne;
import krcho.fbi.databaza.tabulky.Sposobilost;
import krcho.fbi.databaza.tabulky.UrovenSposobilosti;
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
public class OpisUrovneTest {
    private Session session;
    private OpisUrovneDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new OpisUrovneDao();
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
            
            OpisUrovne opisUrovne1 = new OpisUrovne("formovanie cielov");
            OpisUrovne opisUrovne2 = new OpisUrovne("identifikovanie ludi vhodnych na urcite ulohy");
            OpisUrovne opisUrovne3 = new OpisUrovne("zmena stylu rokovania podla situacie");
            OpisUrovne opisUrovne4 = new OpisUrovne("nachadzanie a pouzivanie spravnych argumentov");
            
            sposobilost1.getOpisyUrovni().add(opisUrovne1);
            opisUrovne1.setSposobilost(sposobilost1);
            sposobilost1.getOpisyUrovni().add(opisUrovne2);
            opisUrovne2.setSposobilost(sposobilost1);
            
            sposobilost2.getOpisyUrovni().add(opisUrovne3);
            opisUrovne3.setSposobilost(sposobilost2);
            sposobilost2.getOpisyUrovni().add(opisUrovne4);
            opisUrovne4.setSposobilost(sposobilost2);
            
            dao.save(opisUrovne1);
            dao.save(opisUrovne2);
            dao.save(opisUrovne3);
            dao.save(opisUrovne4);

            assertEquals("Objekt opisUrovne1 nie je zhodny s objektom z databazy!", opisUrovne1, dao.findById(opisUrovne1.getOpisId()));
            assertEquals("Objekt opisUrovne2 nie je zhodny s objektom z databazy!", opisUrovne2, dao.findById(opisUrovne2.getOpisId()));
            assertEquals("Objekt opisUrovne3 nie je zhodny s objektom z databazy!", opisUrovne3, dao.findById(opisUrovne3.getOpisId()));
            assertEquals("Objekt opisUrovne4 nie je zhodny s objektom z databazy!", opisUrovne4, dao.findById(opisUrovne4.getOpisId()));
            
            int pocetRiadkov = dao.findAll().size();

            opisUrovne1.getSposobilost().getOpisyUrovni().remove(opisUrovne1);
            dao.delete(opisUrovne1);
            opisUrovne2.getSposobilost().getOpisyUrovni().remove(opisUrovne2);
            dao.delete(opisUrovne2);
            opisUrovne3.getSposobilost().getOpisyUrovni().remove(opisUrovne3);
            dao.delete(opisUrovne3);
            opisUrovne4.getSposobilost().getOpisyUrovni().remove(opisUrovne4);
            dao.delete(opisUrovne4);
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
