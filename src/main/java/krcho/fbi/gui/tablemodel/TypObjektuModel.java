/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.TypObjektuDao;
import krcho.fbi.databaza.tabulky.TypObjektu;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class TypObjektuModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Názov objektu", "Popis objetku"};
    private List<TypObjektu> typyObjektov = new ArrayList<>();

    public TypObjektuModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        TypObjektuDao typObjektuDao = new TypObjektuDao();
        typObjektuDao.setSession(session);
        typyObjektov = typObjektuDao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return typyObjektov.size();
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
                return typyObjektov.get(rowIndex).getObjektId();
            case 1:
                return typyObjektov.get(rowIndex).getNazov();
            case 2:
                return typyObjektov.get(rowIndex).getPopis();
            default:
                throw new AssertionError();
        }
    }

    public boolean vlozTypObjektu(TypObjektu typObjektu) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            TypObjektuDao typObjektuDao = new TypObjektuDao();
            typObjektuDao.setSession(session);
            typObjektuDao.save(typObjektu);
            trans.commit();
            typyObjektov.add(typObjektu);
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

    public boolean upravTypObjektu(long objektId, String nazov, String popis) {
        boolean existuje = false;
        int row = 0;
        for (TypObjektu typ : typyObjektov) {
            if (typ.getObjektId() == objektId) {
                existuje = true;
                break;
            }
            row++;
        }
        if (existuje) {
            return upravTypObjektu(row, nazov, popis);
        }
        return false;
    }

    public boolean upravTypObjektu(int row, String nazov, String popis) {
        TypObjektu upraveny = typyObjektov.get(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            TypObjektuDao typObjektuDao = new TypObjektuDao();
            typObjektuDao.setSession(session);
            upraveny.setNazov(nazov);
            upraveny.setPopis(popis);
            typObjektuDao.save(upraveny);
            trans.commit();
            this.fireTableRowsUpdated(row, row);
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

    public boolean vymazTypObjektu(int row) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        TypObjektu typObjektu = typyObjektov.remove(row);
        try {
            TypObjektuDao typObjektuDao = new TypObjektuDao();
            typObjektuDao.setSession(session);
            typObjektuDao.delete(typObjektu);
            trans.commit();

            fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            typyObjektov.add(typObjektu);
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

    public TypObjektu getTypObjektu(int row) {
        if (row >= 0 && row < typyObjektov.size()) {
            return typyObjektov.get(row);
        }
        return new TypObjektu();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() == 0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
