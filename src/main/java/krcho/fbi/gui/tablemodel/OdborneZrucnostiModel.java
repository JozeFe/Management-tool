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
import krcho.fbi.databaza.dao.OdbornaZrucnostDao;
import krcho.fbi.databaza.dao.PozadovanaZrucnostDao;
import krcho.fbi.databaza.tabulky.OdbornaZrucnost;
import krcho.fbi.databaza.tabulky.PozadovanaZrucnost;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class OdborneZrucnostiModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Odborná Zručnosť"};
    private List<OdbornaZrucnost> odborneZrucnosti = new ArrayList<>();

    public OdborneZrucnostiModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        OdbornaZrucnostDao dao = new OdbornaZrucnostDao();
        dao.setSession(session);
        odborneZrucnosti = dao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return odborneZrucnosti.size();
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
                return odborneZrucnosti.get(rowIndex).getZrucnostId();
            case 1:
                return odborneZrucnosti.get(rowIndex).getPopis();
            default:
                throw new AssertionError();
        }
    }

    public OdbornaZrucnost getOdbornaZrucnost(int row) {
        return odborneZrucnosti.get(row);
    }

    public void vymazOdbornuZrucnost(int row) {
        OdbornaZrucnost zrucnost = odborneZrucnosti.remove(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            OdbornaZrucnostDao odbornaZrucnostDao = new OdbornaZrucnostDao();
            odbornaZrucnostDao.setSession(session);
            PozadovanaZrucnostDao pozadovanaZrucnostDao = new PozadovanaZrucnostDao();
            pozadovanaZrucnostDao.setSession(session);

            for (Iterator<PozadovanaZrucnost> iterator = zrucnost.getPozadovaneZrucnosti().iterator(); iterator.hasNext();) {
                PozadovanaZrucnost pozadovanaZrucnost = iterator.next();
                iterator.remove();
                pozadovanaZrucnostDao.delete(pozadovanaZrucnost);
            }
            odbornaZrucnostDao.delete(zrucnost);
            trans.commit();
            this.fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            odborneZrucnosti.add(zrucnost);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void vlozOdbornuZrucnost(OdbornaZrucnost zrucnost) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            OdbornaZrucnostDao odbornaZrucnostDao = new OdbornaZrucnostDao();
            odbornaZrucnostDao.setSession(session);

            odborneZrucnosti.add(zrucnost);
            odbornaZrucnostDao.save(zrucnost);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            odborneZrucnosti.remove(zrucnost);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void upravOdbornuZrucnost(int row, String popis) {
        OdbornaZrucnost zrucnost = odborneZrucnosti.get(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            OdbornaZrucnostDao odbornaZrucnostDao = new OdbornaZrucnostDao();
            odbornaZrucnostDao.setSession(session);

            zrucnost.setPopis(popis);
            odbornaZrucnostDao.save(zrucnost);
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
