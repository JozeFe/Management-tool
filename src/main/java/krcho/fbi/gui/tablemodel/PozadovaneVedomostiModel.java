/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.PozadovanaVedomostDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.OdbornaVedomost;
import krcho.fbi.databaza.tabulky.PozadovanaVedomost;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class PozadovaneVedomostiModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Požadované vedomosti", "Požiadavka", "EKR"};
    private TypovaPozicia pozicia;

    public PozadovaneVedomostiModel() {
    }

    public PozadovaneVedomostiModel(long poziciaId) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        TypovaPoziciaDao dao = new TypovaPoziciaDao();
        dao.setSession(session);
        this.pozicia = dao.findById(poziciaId);
        session.close();
    }

    @Override
    public int getRowCount() {
        if (pozicia != null) {
            return pozicia.getPozadovaneVedomosti().size();
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
                return pozicia.getPozadovaneVedomosti().get(rowIndex).getVedomost().getVedomostId();
            case 1:
                return pozicia.getPozadovaneVedomosti().get(rowIndex).getVedomost().getPopis();
            case 2:
                return pozicia.getPozadovaneVedomosti().get(rowIndex).getPoziadavka();
            case 3:
                return pozicia.getPozadovaneVedomosti().get(rowIndex).getUroven().toString();
            default:
                throw new AssertionError();
        }
    }

    public void vymazPozadovanuVedomost(int riadok) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            PozadovanaVedomostDao pozVedomostDao = new PozadovanaVedomostDao();
            pozVedomostDao.setSession(session);

            pozVedomostDao.delete(pozicia.getPozadovaneVedomosti().get(riadok));
            pozicia.getPozadovaneVedomosti().remove(riadok);

            trans.commit();
            this.fireTableRowsDeleted(riadok, riadok);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public boolean jeVedomostVModeli(long vedomostId) {
        if (pozicia.getPozadovaneVedomosti().stream().anyMatch(prvok -> prvok.getVedomost().getVedomostId() == vedomostId)) {
            return true;
        }
        return false;
    }

    public boolean vlozPozadovanuVedomost(PozadovanaVedomost pozadovanaVedomost, OdbornaVedomost vedomost) {
        if (jeVedomostVModeli(vedomost.getVedomostId())) {
            return false;
        }
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            PozadovanaVedomostDao pozVedomostDao = new PozadovanaVedomostDao();
            pozVedomostDao.setSession(session);

            pozicia.getPozadovaneVedomosti().add(pozadovanaVedomost);
            pozadovanaVedomost.setPozicia(pozicia);
            vedomost.getPozadovaneVedomosti().add(pozadovanaVedomost);
            pozadovanaVedomost.setVedomost(vedomost);

            pozVedomostDao.save(pozadovanaVedomost);
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

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() == 0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
