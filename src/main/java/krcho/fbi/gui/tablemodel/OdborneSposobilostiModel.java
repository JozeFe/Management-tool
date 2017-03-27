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
import krcho.fbi.databaza.dao.PozadovanaSposobilostDao;
import krcho.fbi.databaza.dao.SposobilostDao;
import krcho.fbi.databaza.tabulky.PozadovanaSposobilost;
import krcho.fbi.databaza.tabulky.Sposobilost;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class OdborneSposobilostiModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Odborne Spôsobilosti", "Úroveň", "PST"};
    private List<Sposobilost> sposobilosti = new ArrayList<>();

    public OdborneSposobilostiModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        SposobilostDao dao = new SposobilostDao();
        dao.setSession(session);
        sposobilosti = dao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return sposobilosti.size();
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
                return sposobilosti.get(rowIndex).getSposobilostId();
            case 1:
                return sposobilosti.get(rowIndex).getNazov();
            case 2:
                return sposobilosti.get(rowIndex).getUrovenSposobilosti().toString();
            case 3:
                if (sposobilosti.get(rowIndex).getPST()) {
                    return "1";
                } else {
                    return "0";
                }
            default:
                throw new AssertionError();
        }
    }

    public Sposobilost getSposobilost(int row) {
        return sposobilosti.get(row);
    }
    
    public Sposobilost setSposobilost(int row, Sposobilost sposobilost) {
        return sposobilosti.set(row, sposobilost);
    }

    public void vymazSposobilost(int row) {
        Sposobilost sposobilost = sposobilosti.remove(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            SposobilostDao sposobilostDao = new SposobilostDao();
            sposobilostDao.setSession(session);
            PozadovanaSposobilostDao pozadovanaSposobilostDao = new PozadovanaSposobilostDao();
            pozadovanaSposobilostDao.setSession(session);

            for (Iterator<PozadovanaSposobilost> iterator = sposobilost.getPozadovaneSposobilosti().iterator(); iterator.hasNext();) {
                PozadovanaSposobilost pozadovanaSposobilost = iterator.next();
                pozadovanaSposobilost.getPozicia().getPozadovaneSposobilosti().remove(pozadovanaSposobilost);
                iterator.remove();
                pozadovanaSposobilostDao.delete(pozadovanaSposobilost);
            }
            sposobilostDao.delete(sposobilost);
            trans.commit();
            this.fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            sposobilosti.add(sposobilost);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void upravSposobilost(int row, Sposobilost sposobilost) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            SposobilostDao sposobilostDao = new SposobilostDao();
            sposobilostDao.setSession(session);

            sposobilostDao.save(sposobilost);
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

    public void vlozSposobilost(Sposobilost sposobilost) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            SposobilostDao sposobilostDao = new SposobilostDao();
            sposobilostDao.setSession(session);

            sposobilosti.add(sposobilost);
            sposobilostDao.save(sposobilost);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            sposobilosti.remove(sposobilost);
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
