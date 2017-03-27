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
import krcho.fbi.databaza.dao.IscoDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.Isco;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class IscoModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Kod", "ISCO"};
    private List<Isco> isca = new ArrayList<>();

    public IscoModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        IscoDao dao = new IscoDao();
        dao.setSession(session);
        isca = dao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return isca.size();
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
                return isca.get(rowIndex).getIscoKod();
            case 1:
                return isca.get(rowIndex).getPopis();
            default:
                throw new AssertionError();
        }
    }

    public Isco getIsco(int row) {
        return isca.get(row);
    }

    public void vymazIsco(int row) {
        Isco isco = isca.remove(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            IscoDao iscoDao = new IscoDao();
            iscoDao.setSession(session);
            TypovaPoziciaDao typovaPoziciaDao = new TypovaPoziciaDao();
            typovaPoziciaDao.setSession(session);

            for (Iterator<TypovaPozicia> iterator = isco.getTypovePozicie().iterator(); iterator.hasNext();) {
                TypovaPozicia pozicia = iterator.next();
                iterator.remove();
                pozicia.getIsco().remove(isco);
                typovaPoziciaDao.save(pozicia);
            }
            iscoDao.save(isco);
            iscoDao.delete(isco);
            trans.commit();
            this.fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            isca.add(isco);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void vlozIsco(Isco isco) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            IscoDao iscoDao = new IscoDao();
            iscoDao.setSession(session);

            isca.add(isco);
            iscoDao.save(isco);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            isca.remove(isco);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void upravIsco(int row, String popis) {
        Isco isco = isca.get(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            IscoDao iscoDao = new IscoDao();
            iscoDao.setSession(session);

            isco.setPopis(popis);
            iscoDao.save(isco);
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
