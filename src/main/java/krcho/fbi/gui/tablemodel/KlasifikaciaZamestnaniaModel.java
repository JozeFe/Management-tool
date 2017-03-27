/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import java.util.Iterator;
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
public class KlasifikaciaZamestnaniaModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Kod", "Klasifikácia zamestnaní"};
    private TypovaPozicia pozicia;

    public KlasifikaciaZamestnaniaModel() {
    }

    public KlasifikaciaZamestnaniaModel(long poziciaId) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        TypovaPoziciaDao poziciaDao = new TypovaPoziciaDao();
        poziciaDao.setSession(session);
        this.pozicia = poziciaDao.findById(poziciaId);
        session.close();
    }

    @Override
    public int getRowCount() {
        if (pozicia != null) {
            return pozicia.getIsco().size();
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
        int index = 0;
        for (Iterator<Isco> iterator = pozicia.getIsco().iterator(); iterator.hasNext();) {
            if (index == rowIndex) {
                switch (columnIndex) {
                    case 0:
                        return iterator.next().getIscoKod();
                    case 1:
                        return iterator.next().getPopis();
                    default:
                        throw new AssertionError();
                }
            }
            iterator.next();
            index++;
        }
        return null;
    }

    public void vymazKlasifikaciuZamestnaia(int rowIndex) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            TypovaPoziciaDao poziciaDao = new TypovaPoziciaDao();
            poziciaDao.setSession(session);

            int index = 0;
            for (Iterator<Isco> iterator = pozicia.getIsco().iterator(); iterator.hasNext();) {
                iterator.next();
                if (index == rowIndex) {
                    iterator.remove();
                }
                index++;
            }
            poziciaDao.save(pozicia);
            trans.commit();
            this.fireTableRowsDeleted(rowIndex, rowIndex);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public boolean vlozKlasifikaciuZamestnania(Isco isco) {
        for (Iterator<Isco> iterator = pozicia.getIsco().iterator(); iterator.hasNext();) {
            Isco pomIsco = iterator.next();
            if (pomIsco.getIscoKod().equals(isco.getIscoKod())) {
                return false;
            }
        }
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            pozicia.getIsco().add(isco);
            isco.getTypovePozicie().add(pozicia);
            TypovaPoziciaDao poziciaDao = new TypovaPoziciaDao();
            poziciaDao.setSession(session);
            poziciaDao.save(pozicia);

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
            return true;
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
