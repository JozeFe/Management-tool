/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import java.util.Iterator;
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
public class PodrobnaCharakteristikaModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Podrobná charakteristika činnosti"};
    private TypovaPozicia pozicia;

    public PodrobnaCharakteristikaModel() {
    }

    public PodrobnaCharakteristikaModel(long typovaPoziciaId) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        TypovaPoziciaDao dao = new TypovaPoziciaDao();
        dao.setSession(session);
        this.pozicia = dao.findById(typovaPoziciaId);
        session.close();
    }

    @Override
    public int getRowCount() {
        if (pozicia != null) {
            return pozicia.getPopisyCinnosti().size();
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
        for (Iterator<PopisCinnosti> iterator = pozicia.getPopisyCinnosti().iterator(); iterator.hasNext();) {
            if (index == rowIndex) {
                switch (columnIndex) {
                    case 0:
                        return iterator.next().getCinnostId();
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

    public void vymazPopisCinnosti(int rowIndex) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            TypovaPoziciaDao poziciaDao = new TypovaPoziciaDao();
            poziciaDao.setSession(session);

            int index = 0;
            for (Iterator<PopisCinnosti> iterator = pozicia.getPopisyCinnosti().iterator(); iterator.hasNext();) {
                iterator.next();
                if (index == rowIndex) {
                    iterator.remove();
                }
                index++;
            }
            poziciaDao.save(pozicia);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public boolean vlozPopisCinnosti(PopisCinnosti cinnost) {
        for (Iterator<PopisCinnosti> i = pozicia.getPopisyCinnosti().iterator(); i.hasNext();) {
            PopisCinnosti cinnostPozicie = i.next();
            if (cinnostPozicie.getCinnostId() == cinnost.getCinnostId()) {
                return false;
            }
        }
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            pozicia.getPopisyCinnosti().add(cinnost);
            cinnost.getTypovePozicie().add(pozicia);
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
