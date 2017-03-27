/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.OdbornaVedomostDao;
import krcho.fbi.databaza.dao.PozadovanaVedomostDao;
import krcho.fbi.databaza.tabulky.OdbornaVedomost;
import krcho.fbi.databaza.tabulky.PozadovanaVedomost;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class OdborneVedomostiModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Odborná vedomosť"};
    private List<OdbornaVedomost> odborneVedomosti = new ArrayList<>();

    public OdborneVedomostiModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        OdbornaVedomostDao dao = new OdbornaVedomostDao();
        dao.setSession(session);
        odborneVedomosti = dao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return odborneVedomosti.size();
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
                return odborneVedomosti.get(rowIndex).getVedomostId();
            case 1:
                return odborneVedomosti.get(rowIndex).getPopis();
            default:
                throw new AssertionError();
        }
    }

    public OdbornaVedomost getOdbornaVedomost(int row) {
        return odborneVedomosti.get(row);
    }

    public void vymazOdbornuVedomost(int row) {
        OdbornaVedomost vedomost = odborneVedomosti.remove(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            OdbornaVedomostDao odbornaVedomostDao = new OdbornaVedomostDao();
            odbornaVedomostDao.setSession(session);
            PozadovanaVedomostDao pozadovanaVedomostDao = new PozadovanaVedomostDao();
            pozadovanaVedomostDao.setSession(session);

            for (Iterator<PozadovanaVedomost> iterator = vedomost.getPozadovaneVedomosti().iterator(); iterator.hasNext();) {
                PozadovanaVedomost pozadovanaVedomost = iterator.next();
                pozadovanaVedomost.getUroven().getPozadovaneVedomosti().remove(pozadovanaVedomost);
                pozadovanaVedomost.getPozicia().getPozadovaneVedomosti().remove(pozadovanaVedomost);
                iterator.remove();
                pozadovanaVedomostDao.delete(pozadovanaVedomost);
            }
            odbornaVedomostDao.delete(vedomost);
            trans.commit();
            this.fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            odborneVedomosti.add(vedomost);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void vlozOdbornuVedomost(OdbornaVedomost vedomost) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            OdbornaVedomostDao odbornaVedomostDao = new OdbornaVedomostDao();
            odbornaVedomostDao.setSession(session);

            odborneVedomosti.add(vedomost);
            odbornaVedomostDao.save(vedomost);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            odborneVedomosti.remove(vedomost);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void upravOdbornuVedomost(int row, String popis) {
        OdbornaVedomost vedomost = odborneVedomosti.get(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            OdbornaVedomostDao odbornaVedomostDao = new OdbornaVedomostDao();
            odbornaVedomostDao.setSession(session);

            vedomost.setPopis(popis);
            odbornaVedomostDao.save(vedomost);
            trans.commit();
            this.fireTableRowsUpdated(row, row);
        } catch (HibernateException e) {
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
