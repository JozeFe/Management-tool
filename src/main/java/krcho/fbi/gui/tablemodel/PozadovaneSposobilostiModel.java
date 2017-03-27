/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.PozadovanaSposobilostDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.PozadovanaSposobilost;
import krcho.fbi.databaza.tabulky.Sposobilost;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class PozadovaneSposobilostiModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Požadované Spôsobilosti", "Požiadavka", "Úroveň", "PST"};
    private TypovaPozicia pozicia;

    public PozadovaneSposobilostiModel() {
    }

    public PozadovaneSposobilostiModel(long poziciaId) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        TypovaPoziciaDao dao = new TypovaPoziciaDao();
        dao.setSession(session);
        this.pozicia = dao.findById(poziciaId);
        session.close();
    }

    @Override
    public int getRowCount() {
        if (pozicia != null) {
            return pozicia.getPozadovaneSposobilosti().size();
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
        switch (columnIndex) {
            case 0:
                return pozicia.getPozadovaneSposobilosti().get(rowIndex).getSposobilost().getSposobilostId();
            case 1:
                return pozicia.getPozadovaneSposobilosti().get(rowIndex).getSposobilost().getNazov();
            case 2:
                return pozicia.getPozadovaneSposobilosti().get(rowIndex).getPoziadavka();
            case 3:
                return pozicia.getPozadovaneSposobilosti().get(rowIndex).getSposobilost().getUrovenSposobilosti().toString();
            case 4:
                if (pozicia.getPozadovaneSposobilosti().get(rowIndex).getSposobilost().getPST()) {
                    return "1";
                } else {
                    return "0";
                }
            default:
                throw new AssertionError();
        }
    }

    public void vymazPozadovanuSposobilost(int riadok) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            PozadovanaSposobilostDao pozSposobilostDao = new PozadovanaSposobilostDao();
            pozSposobilostDao.setSession(session);

            pozSposobilostDao.delete(pozicia.getPozadovaneSposobilosti().get(riadok));
            pozicia.getPozadovaneSposobilosti().remove(riadok);

            trans.commit();
            this.fireTableRowsDeleted(riadok, riadok);
        } catch (HibernateException e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public boolean jeSposobilostVModeli(long sposobilostId) {
        if (pozicia.getPozadovaneSposobilosti().stream().anyMatch(prvok -> prvok.getSposobilost().getSposobilostId() == sposobilostId)) {
            return true;
        }
        return false;
    }

    public boolean vlozPozadovanuSposobilost(PozadovanaSposobilost pozadovanaSposobilost, Sposobilost sposobilost) {
        if (jeSposobilostVModeli(sposobilost.getSposobilostId())) {
            return false;
        }
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            PozadovanaSposobilostDao pozSposobilostDao = new PozadovanaSposobilostDao();
            pozSposobilostDao.setSession(session);

            pozicia.getPozadovaneSposobilosti().add(pozadovanaSposobilost);
            pozadovanaSposobilost.setPozicia(pozicia);
            sposobilost.getPozadovaneSposobilosti().add(pozadovanaSposobilost);
            pozadovanaSposobilost.setSposobilost(sposobilost);

            pozSposobilostDao.save(pozadovanaSposobilost);
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
        }
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() == 0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
