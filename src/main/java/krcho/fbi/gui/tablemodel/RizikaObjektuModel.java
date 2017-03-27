/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.RizikoObjektuDao;
import krcho.fbi.databaza.dao.TypObjektuDao;
import krcho.fbi.databaza.tabulky.RizikoObjektu;
import krcho.fbi.databaza.tabulky.TypObjektu;
import krcho.fbi.databaza.tabulky.VnutorneBR;
import krcho.fbi.databaza.tabulky.VonkajsieBR;
import krcho.fbi.databaza.tabulky.enumy.TypRizika;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class RizikaObjektuModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Riziko objektu", "Typ rizika"};
    private TypObjektu typObjektu;

    public RizikaObjektuModel() {
    }

    public RizikaObjektuModel(TypObjektu typObjektu) {
        this.typObjektu = typObjektu;
    }

    public RizikaObjektuModel(long TypObjektuId) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        TypObjektuDao typObjektuDao = new TypObjektuDao();
        typObjektuDao.setSession(session);
        this.typObjektu = typObjektuDao.findById(TypObjektuId);
        session.close();
    }

    @Override
    public int getRowCount() {
        if (typObjektu != null) {
            return typObjektu.getRizikaObjektu().size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return STLPCE.length;
    }

    @Override
    public String getColumnName(int column) {
        return STLPCE[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return typObjektu.getRizikaObjektu().get(rowIndex).getRizikoObjektuId();
            case 1:
                return typObjektu.getRizikaObjektu().get(rowIndex).toString();
            case 2:
                return typObjektu.getRizikaObjektu().get(rowIndex).getTypRizika().toString();
            default:
                throw new AssertionError();
        }
    }

    public boolean vlozVnutorneRizikoObjektu(VnutorneBR vnutorneBR) {
        if (typObjektu.getRizikaObjektu().stream().anyMatch(prvok -> prvok.getVnutorneRiziko() != null
                && prvok.getVnutorneRiziko().getVnutorneBrId() == vnutorneBR.getVnutorneBrId())) {
            return false;
        }
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            RizikoObjektuDao rizikoObjektuDao = new RizikoObjektuDao();
            rizikoObjektuDao.setSession(session);

            RizikoObjektu noveRiziko = new RizikoObjektu(TypRizika.VNUTORNE);
            noveRiziko.setVnutorneRiziko(vnutorneBR);
            vnutorneBR.getRizikaObjektu().add(noveRiziko);
            noveRiziko.setTypObjektu(typObjektu);
            typObjektu.getRizikaObjektu().add(noveRiziko);

            rizikoObjektuDao.save(noveRiziko);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public boolean vlozVonkajsieRizikoObjektu(VonkajsieBR vonkajsieBR) {
        if (typObjektu.getRizikaObjektu().stream().anyMatch(prvok -> prvok.getVonkajsieRiziko() != null
                && prvok.getVonkajsieRiziko().getVonkajsieBrId() == vonkajsieBR.getVonkajsieBrId())) {
            return false;
        }
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            RizikoObjektuDao rizikoObjektuDao = new RizikoObjektuDao();
            rizikoObjektuDao.setSession(session);

            RizikoObjektu noveRiziko = new RizikoObjektu(TypRizika.VONKAJSIE);
            noveRiziko.setVonkajsieRiziko(vonkajsieBR);
            vonkajsieBR.getRizikaObjektu().add(noveRiziko);
            noveRiziko.setTypObjektu(typObjektu);
            typObjektu.getRizikaObjektu().add(noveRiziko);

            rizikoObjektuDao.save(noveRiziko);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public void vymazRizikoObjektu(int riadok) {
        RizikoObjektu riziko = typObjektu.getRizikaObjektu().remove(riadok);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            RizikoObjektuDao rizikoObjektuDao = new RizikoObjektuDao();
            rizikoObjektuDao.setSession(session);
            if (riziko.getTypRizika() == TypRizika.VNUTORNE) {
                riziko.getVnutorneRiziko().getRizikaObjektu().remove(riziko);
            } else {
                riziko.getVonkajsieRiziko().getRizikaObjektu().remove(riziko);
            }
            rizikoObjektuDao.delete(riziko);
            trans.commit();
            this.fireTableRowsDeleted(riadok, riadok);
        } catch (HibernateException e) {
            typObjektu.getRizikaObjektu().add(riziko);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() == 0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
