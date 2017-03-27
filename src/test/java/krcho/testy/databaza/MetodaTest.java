/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import krcho.fbi.databaza.dao.HrozbaDao;
import krcho.fbi.databaza.dao.MetodaDao;
import krcho.fbi.databaza.dao.RizikoObjektuDao;
import krcho.fbi.databaza.dao.VonkajsiTypBRDao;
import krcho.fbi.databaza.dao.TypObjektuDao;
import krcho.fbi.databaza.dao.VnutorneSkupinyBRDao;
import krcho.fbi.databaza.dao.ZdrojHrozbyDao;
import krcho.fbi.databaza.tabulky.Hrozba;
import krcho.fbi.databaza.tabulky.Metoda;
import krcho.fbi.databaza.tabulky.RizikoObjektu;
import krcho.fbi.databaza.tabulky.VonkajsiTypBR;
import krcho.fbi.databaza.tabulky.TypObjektu;
import krcho.fbi.databaza.tabulky.VnutorneBR;
import krcho.fbi.databaza.tabulky.VnutorneSkupinyBR;
import krcho.fbi.databaza.tabulky.VonkajsieBR;
import krcho.fbi.databaza.tabulky.ZdrojHrozby;
import krcho.fbi.databaza.tabulky.enumy.TypMetody;
import krcho.fbi.databaza.tabulky.enumy.TypRizika;
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
public class MetodaTest {

    private Session session;
    private MetodaDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new MetodaDao();
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
            // vytvorenie a ulozenie vnutornych rizik
            VnutorneBR vnutorneRiziko1 = new VnutorneBR("nedostatocna udrzba", "chyba udrzby systemu");
            VnutorneSkupinyBR skupina1 = new VnutorneSkupinyBR("hardware");
            vnutorneRiziko1.setSkupinaBR(skupina1);
            skupina1.getVnutorneBR().add(vnutorneRiziko1);

            VnutorneSkupinyBRDao skupinyDao = new VnutorneSkupinyBRDao();
            skupinyDao.setSession(session);
            skupinyDao.save(skupina1);

            // vytvorenie a ulozenie vonkajsich rizik
            VonkajsieBR vonkajsieRiziko1 = new VonkajsieBR("Poziar");
            VonkajsiTypBR typ1 = new VonkajsiTypBR("Fyzicke poskodenie");
            vonkajsieRiziko1.setTypBR(typ1);
            typ1.getVonkajsieBR().add(vonkajsieRiziko1);

            VonkajsiTypBRDao typBRDao = new VonkajsiTypBRDao();
            typBRDao.setSession(session);
            typBRDao.save(typ1);

            // vytvorenie a uloznie typu objektu
            TypObjektu objektTyp1 = new TypObjektu("budova1", "Betonova stavba.");

            TypObjektuDao typObjektuDao = new TypObjektuDao();
            typObjektuDao.setSession(session);
            typObjektuDao.save(objektTyp1);

            // vytvorenie a uloznie Rizika Objektu
            RizikoObjektu rizikoObjektu1 = new RizikoObjektu(TypRizika.VNUTORNE);
            RizikoObjektu rizikoObjektu2 = new RizikoObjektu(TypRizika.VONKAJSIE);

            // vnutorne riziko pre typObjektu 1
            rizikoObjektu1.setTypObjektu(objektTyp1);
            objektTyp1.getRizikaObjektu().add(rizikoObjektu1);
            rizikoObjektu1.setVnutorneRiziko(vnutorneRiziko1);
            vnutorneRiziko1.getRizikaObjektu().add(rizikoObjektu1);

            // vonkajsie riziko pre typObjektu 1
            rizikoObjektu2.setTypObjektu(objektTyp1);
            objektTyp1.getRizikaObjektu().add(rizikoObjektu2);
            rizikoObjektu2.setVonkajsieRiziko(vonkajsieRiziko1);
            vonkajsieRiziko1.getRizikaObjektu().add(rizikoObjektu2);

            RizikoObjektuDao rizikoObjDao = new RizikoObjektuDao();
            rizikoObjDao.setSession(session);

            rizikoObjDao.save(rizikoObjektu1);
            rizikoObjDao.save(rizikoObjektu2);

            // vytvorenie a ulozenie hrozby
            Hrozba hrozba1 = new Hrozba("hacker", "vyzva", "hacking");
            Hrozba hrozba2 = new Hrozba("pocitacova kriminalita", "znicenie informacii", "Pocitacovy zlocin (napr. kyberneticke prenasledovanie)");
            HrozbaDao hrozbaDao = new HrozbaDao();
            hrozbaDao.setSession(session);
            hrozbaDao.save(hrozba1);
            hrozbaDao.save(hrozba2);

            // vytvorenie a ulozenie zdroja Hrozby            
            ZdrojHrozby zdrojHrozby1 = new ZdrojHrozby();
            ZdrojHrozby zdrojHrozby2 = new ZdrojHrozby();

            zdrojHrozby1.setHrozba(hrozba1);
            hrozba1.getZdrojeHrozieb().add(zdrojHrozby1);
            zdrojHrozby1.setRizikoObjektu(rizikoObjektu1);
            rizikoObjektu1.getZdrojeHrozieb().add(zdrojHrozby1);

            zdrojHrozby2.setHrozba(hrozba2);
            hrozba2.getZdrojeHrozieb().add(zdrojHrozby2);
            zdrojHrozby2.setRizikoObjektu(rizikoObjektu2);
            rizikoObjektu2.getZdrojeHrozieb().add(zdrojHrozby2);

            ZdrojHrozbyDao zdrojHrozbyDao = new ZdrojHrozbyDao();
            zdrojHrozbyDao.setSession(session);
            zdrojHrozbyDao.save(zdrojHrozby1);
            zdrojHrozbyDao.save(zdrojHrozby2);

            Metoda metoda1 = new Metoda("analyza", "Je myslienkove rozclenenie predmetov, javov alebo udalosti na casti.", TypMetody.VSEOBECNE);
            Metoda metoda2 = new Metoda("fyzicke pozorovanie", "Je najfrekventovanejsou metodou uplatnovanou pri vykone fyzickej ochrany.", TypMetody.SPECIALNE);

            metoda1.setZdrojHrozby(zdrojHrozby1);
            zdrojHrozby1.getMetody().add(metoda1);
            metoda2.setZdrojHrozby(zdrojHrozby2);
            zdrojHrozby2.getMetody().add(metoda2);

            dao.save(metoda1);
            dao.save(metoda2);

            assertEquals("Objekt metoda1 nie je zhodny s objektom z databazy!", metoda1, dao.findById(metoda1.getMetodaId()));
            assertEquals("Objekt metoda2 nie je zhodny s objektom z databazy!", metoda2, dao.findById(metoda2.getMetodaId()));

            int pocetRiadkov = dao.findAll().size();

            metoda1.getZdrojHrozby().getMetody().remove(metoda1);
            dao.delete(metoda1);
            metoda2.getZdrojHrozby().getMetody().remove(metoda2);
            dao.delete(metoda2);
            zdrojHrozbyDao.delete(zdrojHrozby1);
            zdrojHrozbyDao.delete(zdrojHrozby2);
            hrozbaDao.delete(hrozba1);
            hrozbaDao.delete(hrozba2);
            rizikoObjDao.delete(rizikoObjektu1);
            rizikoObjDao.delete(rizikoObjektu2);
            typObjektuDao.delete(objektTyp1);
            typBRDao.delete(typ1);
            skupinyDao.delete(skupina1);

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
