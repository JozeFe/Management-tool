/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.PopisCinnostiDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.PopisCinnosti;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class PopisCinnostiModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Popis činnosti"};
    private List<PopisCinnosti> popisyCinnosti;

    public PopisCinnostiModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        PopisCinnostiDao dao = new PopisCinnostiDao();
        dao.setSession(session);
        popisyCinnosti = dao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return popisyCinnosti.size();
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
                return popisyCinnosti.get(rowIndex).getCinnostId();
            case 1:
                return popisyCinnosti.get(rowIndex).getPopis();
            default:
                throw new AssertionError();
        }
    }

    public PopisCinnosti getPopisCinnosti(int row) {
        return popisyCinnosti.get(row);
    }

    public void vymazPopisCinnosti(int row) {
        PopisCinnosti popisCinnosti = popisyCinnosti.remove(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            PopisCinnostiDao popisCinnostiDao = new PopisCinnostiDao();
            popisCinnostiDao.setSession(session);
            TypovaPoziciaDao typovaPoziciaDao = new TypovaPoziciaDao();
            typovaPoziciaDao.setSession(session);

            for (Iterator<TypovaPozicia> iterator = popisCinnosti.getTypovePozicie().iterator(); iterator.hasNext();) {
                TypovaPozicia pozicia = iterator.next();
                iterator.remove();
                pozicia.getPopisyCinnosti().remove(popisCinnosti);
                typovaPoziciaDao.save(pozicia);
            }
            popisCinnostiDao.save(popisCinnosti);
            popisCinnostiDao.delete(popisCinnosti);
            trans.commit();
            this.fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            popisyCinnosti.add(popisCinnosti);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void vlozPopisCinnosti(PopisCinnosti popisCinnosti) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            PopisCinnostiDao popisCinnostiDao = new PopisCinnostiDao();
            popisCinnostiDao.setSession(session);

            popisyCinnosti.add(popisCinnosti);
            popisCinnostiDao.save(popisCinnosti);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            popisyCinnosti.remove(popisCinnosti);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void upravPopisCinnosti(int row, String popis) {
        PopisCinnosti popisCinnosti = popisyCinnosti.get(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            PopisCinnostiDao popisCinnostiDao = new PopisCinnostiDao();
            popisCinnostiDao.setSession(session);

            popisCinnosti.setPopis(popis);
            popisCinnostiDao.save(popisCinnosti);
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
