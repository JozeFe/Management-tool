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
import krcho.fbi.databaza.dao.CertifikatDao;
import krcho.fbi.databaza.dao.TypovaPoziciaDao;
import krcho.fbi.databaza.tabulky.Certifikat;
import krcho.fbi.databaza.tabulky.TypovaPozicia;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jozef Krcho
 */
public class CertifikatModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Názov", "Popis"};
    private List<Certifikat> certifikaty = new ArrayList<>();

    public CertifikatModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        CertifikatDao certifikatDao = new CertifikatDao();
        certifikatDao.setSession(session);
        certifikaty = certifikatDao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return certifikaty.size();
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
                return certifikaty.get(rowIndex).getCertifikatId();
            case 1:
                return certifikaty.get(rowIndex).getNazov();
            case 2:
                return certifikaty.get(rowIndex).getPopis();
            default:
                throw new AssertionError();
        }
    }

    public Certifikat getCertifikat(int row) {
        return certifikaty.get(row);
    }

    public void vymazCertifikat(int row) {
        Certifikat certifikat = certifikaty.remove(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            CertifikatDao certifikatDao = new CertifikatDao();
            certifikatDao.setSession(session);
            TypovaPoziciaDao typovaPoziciaDao = new TypovaPoziciaDao();
            typovaPoziciaDao.setSession(session);

            for (Iterator<TypovaPozicia> iterator = certifikat.getTypovePozicie().iterator(); iterator.hasNext();) {
                TypovaPozicia pozicia = iterator.next();
                pozicia.setCertifikat(null);
                typovaPoziciaDao.save(pozicia);
                iterator.remove();
            }
            certifikatDao.delete(certifikat);
            trans.commit();
            this.fireTableRowsDeleted(row, row);
        } catch (HibernateException e) {
            certifikaty.add(certifikat);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void vlozCertifikat(Certifikat certifikat) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            CertifikatDao certifikatDao = new CertifikatDao();
            certifikatDao.setSession(session);

            certifikaty.add(certifikat);
            certifikatDao.save(certifikat);
            trans.commit();
            this.fireTableDataChanged();
        } catch (HibernateException e) {
            certifikaty.remove(certifikat);
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void upravCertifikat(int row, String nazov, String popis) {
        Certifikat certifikat = certifikaty.get(row);
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            CertifikatDao certifikatDao = new CertifikatDao();
            certifikatDao.setSession(session);

            certifikat.setNazov(nazov);
            certifikat.setPopis(popis);
            certifikatDao.save(certifikat);
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
