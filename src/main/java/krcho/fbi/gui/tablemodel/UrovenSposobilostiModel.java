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
import krcho.fbi.databaza.dao.UrovenSposobilostiDao;
import krcho.fbi.databaza.tabulky.PozadovanaSposobilost;
import krcho.fbi.databaza.tabulky.Sposobilost;
import krcho.fbi.databaza.tabulky.UrovenSposobilosti;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class UrovenSposobilostiModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Stupeň náročnosti"};
    private List<UrovenSposobilosti> urovneSposobilosti = new ArrayList<>();

    public UrovenSposobilostiModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        UrovenSposobilostiDao urovenSposobilostiDao = new UrovenSposobilostiDao();
        urovenSposobilostiDao.setSession(session);
        urovneSposobilosti = urovenSposobilostiDao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return urovneSposobilosti.size();
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
                return urovneSposobilosti.get(rowIndex).getUrovenId();
            case 1:
                return urovneSposobilosti.get(rowIndex).getStupenNarocnosti();
            default:
                throw new AssertionError();
        }
    }

    public UrovenSposobilosti getUrovenSposobilosti(int row) {
        return urovneSposobilosti.get(row);
    }

    public void vymazUrovenSposobilosti(int row) {
        UrovenSposobilosti uroven = urovneSposobilosti.remove(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            UrovenSposobilostiDao urovenSposobilostiDao = new UrovenSposobilostiDao();
            urovenSposobilostiDao.setSession(session);
            SposobilostDao sposobilostDao = new SposobilostDao();
            sposobilostDao.setSession(session);
            PozadovanaSposobilostDao pozadovanaSposobilostDao = new PozadovanaSposobilostDao();
            pozadovanaSposobilostDao.setSession(session);

            for (Iterator<Sposobilost> iterator = uroven.getSposobilosti().iterator(); iterator.hasNext();) {
                Sposobilost sposobilost = iterator.next();
                for (Iterator<PozadovanaSposobilost> iterator1 = sposobilost.getPozadovaneSposobilosti().iterator(); iterator1.hasNext();) {
                    PozadovanaSposobilost pozadovanaSposobilost = iterator1.next();
                    pozadovanaSposobilost.getPozicia().getPozadovaneSposobilosti().remove(pozadovanaSposobilost);
                    iterator1.remove();
                    pozadovanaSposobilostDao.delete(pozadovanaSposobilost);
                }
                iterator.remove();
                sposobilostDao.delete(sposobilost);
            }

            urovenSposobilostiDao.delete(uroven);
            trans.commit();
            this.fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            urovneSposobilosti.add(uroven);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void vlozUrovenSposobilosti(UrovenSposobilosti uroven) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            UrovenSposobilostiDao urovenSposobilostiDao = new UrovenSposobilostiDao();
            urovenSposobilostiDao.setSession(session);

            urovneSposobilosti.add(uroven);
            urovenSposobilostiDao.save(uroven);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            urovneSposobilosti.remove(uroven);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void upravUrovenSposobilosti(int row, String stupenNarocnosti) {
        UrovenSposobilosti uroven = urovneSposobilosti.get(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            UrovenSposobilostiDao urovenSposobilostiDao = new UrovenSposobilostiDao();
            urovenSposobilostiDao.setSession(session);

            uroven.setStupenNarocnosti(stupenNarocnosti);
            urovenSposobilostiDao.save(uroven);
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
