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
import krcho.fbi.databaza.dao.StupenVzdelaniaDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.StupenVzdelania;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class StupenVzdelaniaModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Názov", "Úroveň", "Zákon"};
    private List<StupenVzdelania> stupneVzdelania = new ArrayList<>();

    public StupenVzdelaniaModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        StupenVzdelaniaDao stupenVzdelaniaDao = new StupenVzdelaniaDao();
        stupenVzdelaniaDao.setSession(session);
        stupneVzdelania = stupenVzdelaniaDao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return stupneVzdelania.size();
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
                return stupneVzdelania.get(rowIndex).getVzdelanieId();
            case 1:
                return stupneVzdelania.get(rowIndex).getNazov();
            case 2:
                return stupneVzdelania.get(rowIndex).getUroven();
            case 3:
                return stupneVzdelania.get(rowIndex).getZakon();
            default:
                throw new AssertionError();
        }
    }

    public StupenVzdelania getStupenVzdelania(int row) {
        return stupneVzdelania.get(row);
    }

    public void vymazStupenVzdelania(int row) {
        StupenVzdelania stupen = stupneVzdelania.remove(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            StupenVzdelaniaDao stupenVzdelaniaDao = new StupenVzdelaniaDao();
            stupenVzdelaniaDao.setSession(session);
            TypovaPoziciaDao typovaPoziciaDao = new TypovaPoziciaDao();
            typovaPoziciaDao.setSession(session);

            for (Iterator<TypovaPozicia> iterator = stupen.getTypovePozicie().iterator(); iterator.hasNext();) {
                TypovaPozicia pozicia = iterator.next();
                pozicia.setStupenVzdelania(null);
                typovaPoziciaDao.save(pozicia);
                iterator.remove();
            }
            stupenVzdelaniaDao.delete(stupen);
            trans.commit();
            this.fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            stupneVzdelania.add(stupen);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void vlozStupenVzdelania(StupenVzdelania stupen) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            StupenVzdelaniaDao stupenVzdelaniaDao = new StupenVzdelaniaDao();
            stupenVzdelaniaDao.setSession(session);

            stupneVzdelania.add(stupen);
            stupenVzdelaniaDao.save(stupen);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            stupneVzdelania.remove(stupen);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void upravStupenVzdelania(int row, String nazov, String uroven, String zakon) {
        StupenVzdelania stupen = stupneVzdelania.get(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            StupenVzdelaniaDao stupenVzdelaniaDao = new StupenVzdelaniaDao();
            stupenVzdelaniaDao.setSession(session);

            stupen.setNazov(nazov);
            stupen.setUroven(uroven);
            stupen.setZakon(zakon);
            stupenVzdelaniaDao.save(stupen);
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
