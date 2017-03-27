/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.testy.databaza;

import java.util.List;
import krcho.fbi.databaza.dao.RizikoObjektuDao;
import krcho.fbi.databaza.dao.VonkajsiTypBRDao;
import krcho.fbi.databaza.dao.TypObjektuDao;
import krcho.fbi.databaza.dao.VnutorneSkupinyBRDao;
import krcho.fbi.databaza.tabulky.RizikoObjektu;
import krcho.fbi.databaza.tabulky.VonkajsiTypBR;
import krcho.fbi.databaza.tabulky.TypObjektu;
import krcho.fbi.databaza.tabulky.VnutorneBR;
import krcho.fbi.databaza.tabulky.VnutorneSkupinyBR;
import krcho.fbi.databaza.tabulky.VonkajsieBR;
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
public class RizikoObjektuTest {

    private Session session;
    private RizikoObjektuDao dao;

    @Before
    public void init() {
        session = HibernateUtilities.getSessionFactory().openSession();
        dao = new RizikoObjektuDao();
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
            VnutorneBR vnutorneRiziko2 = new VnutorneBR("nedostatocna udrzba", "chyba udrzby systemu");
            VnutorneSkupinyBR skupina1 = new VnutorneSkupinyBR("hardware");
            VnutorneSkupinyBR skupina2 = new VnutorneSkupinyBR("software");
            vnutorneRiziko1.setSkupinaBR(skupina1);
            skupina1.getVnutorneBR().add(vnutorneRiziko1);
            vnutorneRiziko2.setSkupinaBR(skupina2);
            skupina2.getVnutorneBR().add(vnutorneRiziko2);

            VnutorneSkupinyBRDao skupinyDao = new VnutorneSkupinyBRDao();
            skupinyDao.setSession(session);
            skupinyDao.save(skupina1);
            skupinyDao.save(skupina2);

            // vytvorenie a ulozenie vonkajsich rizik
            VonkajsieBR vonkajsieRiziko1 = new VonkajsieBR("Poziar");
            VonkajsieBR vonkajsieRiziko2 = new VonkajsieBR("Klimaticky jav");
            VonkajsiTypBR typ1 = new VonkajsiTypBR("Fyzicke poskodenie");
            VonkajsiTypBR typ2 = new VonkajsiTypBR("Prirodni javy");
            vonkajsieRiziko1.setTypBR(typ1);
            typ1.getVonkajsieBR().add(vonkajsieRiziko1);
            vonkajsieRiziko2.setTypBR(typ2);
            typ2.getVonkajsieBR().add(vonkajsieRiziko2);

            VonkajsiTypBRDao typBRDao = new VonkajsiTypBRDao();
            typBRDao.setSession(session);
            typBRDao.save(typ1);
            typBRDao.save(typ2);

            // vytvorenie a uloznie typu objektu
            TypObjektu objektTyp1 = new TypObjektu("budova1", "Betonova stavba.");
            TypObjektu objektTyp2 = new TypObjektu("budova2", "Policajna stanica");

            TypObjektuDao typObjektuDao = new TypObjektuDao();
            typObjektuDao.setSession(session);
            typObjektuDao.save(objektTyp1);
            typObjektuDao.save(objektTyp2);

            // vytvorenie a uloznie Rizika Objektu
            RizikoObjektu rizikoObjektu1 = new RizikoObjektu(TypRizika.VNUTORNE);
            RizikoObjektu rizikoObjektu2 = new RizikoObjektu(TypRizika.VONKAJSIE);
            RizikoObjektu rizikoObjektu3 = new RizikoObjektu(TypRizika.VNUTORNE);
            RizikoObjektu rizikoObjektu4 = new RizikoObjektu(TypRizika.VONKAJSIE);

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

            // vnutorne riziko pre typObjektu 2
            rizikoObjektu3.setTypObjektu(objektTyp2);
            objektTyp2.getRizikaObjektu().add(rizikoObjektu3);
            rizikoObjektu3.setVnutorneRiziko(vnutorneRiziko2);
            vnutorneRiziko2.getRizikaObjektu().add(rizikoObjektu3);

            // vonkajsie riziko pre typObjektu 2
            rizikoObjektu4.setTypObjektu(objektTyp2);
            objektTyp2.getRizikaObjektu().add(rizikoObjektu4);
            rizikoObjektu4.setVonkajsieRiziko(vonkajsieRiziko2);
            vonkajsieRiziko2.getRizikaObjektu().add(rizikoObjektu4);

            dao.save(rizikoObjektu1);
            dao.save(rizikoObjektu2);
            dao.save(rizikoObjektu3);
            dao.save(rizikoObjektu4);
            
            assertEquals("Objekt rizikoObjektu1 nie je zhodny s objektom z databazy!", rizikoObjektu1, dao.findById(rizikoObjektu1.getRizikoObjektuId()));
            assertEquals("Objekt rizikoObjektu2 nie je zhodny s objektom z databazy!", rizikoObjektu2, dao.findById(rizikoObjektu2.getRizikoObjektuId()));
            assertEquals("Objekt rizikoObjektu3 nie je zhodny s objektom z databazy!", rizikoObjektu3, dao.findById(rizikoObjektu3.getRizikoObjektuId()));
            assertEquals("Objekt rizikoObjektu4 nie je zhodny s objektom z databazy!", rizikoObjektu4, dao.findById(rizikoObjektu4.getRizikoObjektuId()));
            
            int pocetRiadkov = dao.findAll().size();
            
            dao.delete(rizikoObjektu1);
            dao.delete(rizikoObjektu2);
            dao.delete(rizikoObjektu3);
            dao.delete(rizikoObjektu4);
            typObjektuDao.delete(objektTyp1);
            typObjektuDao.delete(objektTyp2);
            typBRDao.delete(typ1);
            typBRDao.delete(typ2);
            skupinyDao.delete(skupina1);
            skupinyDao.delete(skupina2);
            
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
