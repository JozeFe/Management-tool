/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.PozadovanaSposobilostDao;
import krcho.fbi.databaza.dao.PozadovanaVedomostDao;
import krcho.fbi.databaza.dao.PozadovanaZrucnostDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.Isco;
import krcho.fbi.databaza.tabulky.PopisCinnosti;
import krcho.fbi.databaza.tabulky.PozadovanaSposobilost;
import krcho.fbi.databaza.tabulky.PozadovanaVedomost;
import krcho.fbi.databaza.tabulky.PozadovanaZrucnost;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class TypovaPoziciaModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Typová pozicia"};
    private List<TypovaPozicia> typovePozicie;

    public TypovaPoziciaModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        TypovaPoziciaDao dao = new TypovaPoziciaDao();
        dao.setSession(session);
        this.typovePozicie = dao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return typovePozicie.size();
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
                return typovePozicie.get(rowIndex).getPoziciaId();
            case 1:
                return typovePozicie.get(rowIndex).getNazov();
            default:
                return null;
        }

    }

    public TypovaPozicia getPozicia(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < this.getRowCount()) {
            return typovePozicie.get(rowIndex);
        }
        return new TypovaPozicia();
    }

    public void vymazPoziciu(int rowIndex) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            TypovaPoziciaDao poziciaDao = new TypovaPoziciaDao();
            poziciaDao.setSession(session);
            PozadovanaZrucnostDao pozZrucnostDao = new PozadovanaZrucnostDao();
            pozZrucnostDao.setSession(session);
            PozadovanaVedomostDao pozVedomostDao = new PozadovanaVedomostDao();
            pozVedomostDao.setSession(session);
            PozadovanaSposobilostDao pozSposobilostDao = new PozadovanaSposobilostDao();
            pozSposobilostDao.setSession(session);

            TypovaPozicia pozicia = typovePozicie.get(rowIndex);
            for (Iterator<PopisCinnosti> iterator = pozicia.getPopisyCinnosti().iterator(); iterator.hasNext();) {
                PopisCinnosti cinnost = iterator.next();
                iterator.remove();
            }
            for (Iterator<Isco> iterator = pozicia.getIsco().iterator(); iterator.hasNext();) {
                Isco isco = iterator.next();
                iterator.remove();
            }
            poziciaDao.save(pozicia);

            for (Iterator<PozadovanaZrucnost> iterator = pozicia.getPozadovaneZrucnosti().iterator(); iterator.hasNext();) {
                PozadovanaZrucnost pozZrucnost = iterator.next();
                //pozZrucnost.getZrucnost().getPozadovaneZrucnosti().remove(pozZrucnost);
                pozZrucnostDao.delete(pozZrucnost);
                iterator.remove();
            }
            for (Iterator<PozadovanaVedomost> iterator = pozicia.getPozadovaneVedomosti().iterator(); iterator.hasNext();) {
                PozadovanaVedomost pozVedomost = iterator.next();
                pozVedomostDao.delete(pozVedomost);
                iterator.remove();
            }
            for (Iterator<PozadovanaSposobilost> iterator = pozicia.getPozadovaneSposobilosti().iterator(); iterator.hasNext();) {
                PozadovanaSposobilost pozSposobilost = iterator.next();
                pozSposobilostDao.delete(pozSposobilost);
                iterator.remove();
            }
            poziciaDao.delete(pozicia);
            trans.commit();
            typovePozicie.remove(rowIndex);
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

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() == 0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
