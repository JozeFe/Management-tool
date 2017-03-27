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
import krcho.fbi.databaza.dao.OdbornaPraxDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.OdbornaPrax;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class OdbornaPraxModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Popis"};
    private List<OdbornaPrax> odbornePraxe = new ArrayList<>();

    public OdbornaPraxModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        OdbornaPraxDao odbornaPraxDao = new OdbornaPraxDao();
        odbornaPraxDao.setSession(session);
        odbornePraxe = odbornaPraxDao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return odbornePraxe.size();
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
                return odbornePraxe.get(rowIndex).getPraxId();
            case 1:
                return odbornePraxe.get(rowIndex).getPopis();
            default:
                throw new AssertionError();
        }
    }

    public OdbornaPrax getOdbornaPrax(int row) {
        return odbornePraxe.get(row);
    }

    public void vymazOdbornuPrax(int row) {
        OdbornaPrax odbornaPrax = odbornePraxe.remove(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            OdbornaPraxDao odbornaPraxDao = new OdbornaPraxDao();
            odbornaPraxDao.setSession(session);
            TypovaPoziciaDao typovaPoziciaDao = new TypovaPoziciaDao();
            typovaPoziciaDao.setSession(session);

            for (Iterator<TypovaPozicia> iterator = odbornaPrax.getTypovePozicie().iterator(); iterator.hasNext();) {
                TypovaPozicia pozicia = iterator.next();
                pozicia.setOdbornaPrax(null);
                typovaPoziciaDao.save(pozicia);
                iterator.remove();
            }
            odbornaPraxDao.delete(odbornaPrax);
            trans.commit();
            this.fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            odbornePraxe.add(odbornaPrax);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void vlozOdbornuPrax(OdbornaPrax odbornaPrax) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            OdbornaPraxDao odbornaPraxDao = new OdbornaPraxDao();
            odbornaPraxDao.setSession(session);

            odbornePraxe.add(odbornaPrax);
            odbornaPraxDao.save(odbornaPrax);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            odbornePraxe.remove(odbornaPrax);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void upravOdbornuPrax(int row, String popis) {
        OdbornaPrax odbornaPrax = odbornePraxe.get(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            OdbornaPraxDao odbornaPraxDao = new OdbornaPraxDao();
            odbornaPraxDao.setSession(session);

            odbornaPrax.setPopis(popis);
            odbornaPraxDao.save(odbornaPrax);
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
