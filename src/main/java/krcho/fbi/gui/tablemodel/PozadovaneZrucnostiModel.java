/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.PozadovanaZrucnostDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.OdbornaZrucnost;
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
public class PozadovaneZrucnostiModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Požadované zručnosti", "Požiadavka", "EKR"};
    private TypovaPozicia pozicia;

    public PozadovaneZrucnostiModel() {
    }

    public PozadovaneZrucnostiModel(long poziciaId) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        TypovaPoziciaDao dao = new TypovaPoziciaDao();
        dao.setSession(session);
        this.pozicia = dao.findById(poziciaId);
        session.close();
    }

    @Override
    public int getRowCount() {
        if (pozicia != null) {
            return pozicia.getPozadovaneZrucnosti().size();
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
                return pozicia.getPozadovaneZrucnosti().get(rowIndex).getZrucnost().getZrucnostId();
            case 1:
                return pozicia.getPozadovaneZrucnosti().get(rowIndex).getZrucnost().toString();
            case 2:
                return pozicia.getPozadovaneZrucnosti().get(rowIndex).getPoziadavka();
            case 3:
                return pozicia.getPozadovaneZrucnosti().get(rowIndex).getUroven().toString();
            default:
                throw new AssertionError();
        }
    }

    public void vymazPozadovanuZrucnost(int riadok) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            PozadovanaZrucnostDao pozZrucnostDao = new PozadovanaZrucnostDao();
            pozZrucnostDao.setSession(session);

            pozZrucnostDao.delete(pozicia.getPozadovaneZrucnosti().get(riadok));
            pozicia.getPozadovaneZrucnosti().remove(riadok);

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

    public boolean jeZrucnostVModeli(long zrucnostId) {
        if (pozicia.getPozadovaneZrucnosti().stream().anyMatch(prvok -> prvok.getZrucnost().getZrucnostId() == zrucnostId)) {
            return true;
        }
        return false;
    }

    public boolean vlozPozadovanuZrucnost(PozadovanaZrucnost pozadovanaZrucnost, OdbornaZrucnost zrucnost) {
        if (jeZrucnostVModeli(zrucnost.getZrucnostId())) {
            return false;
        }
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            PozadovanaZrucnostDao pozZrucnostDao = new PozadovanaZrucnostDao();
            pozZrucnostDao.setSession(session);

            pozicia.getPozadovaneZrucnosti().add(pozadovanaZrucnost);
            pozadovanaZrucnost.setPozicia(pozicia);
            zrucnost.getPozadovaneZrucnosti().add(pozadovanaZrucnost);
            pozadovanaZrucnost.setZrucnost(zrucnost);

            pozZrucnostDao.save(pozadovanaZrucnost);
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
