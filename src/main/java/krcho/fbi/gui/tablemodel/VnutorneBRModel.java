/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.VnutorneBRDao;
import krcho.fbi.databaza.tabulky.VnutorneBR;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.Session;

/**
 *
 * @author Jozef Krcho
 */
public class VnutorneBRModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Skupina", "zranitelnosť", "príklad hrozby"};
    private List<VnutorneBR> vnutorneBezRizika = new ArrayList<>();

    public VnutorneBRModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        VnutorneBRDao vnutorneBRDao = new VnutorneBRDao();
        vnutorneBRDao.setSession(session);
        vnutorneBezRizika = vnutorneBRDao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return vnutorneBezRizika.size();
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
                return vnutorneBezRizika.get(rowIndex).getVnutorneBrId();
            case 1:
                return vnutorneBezRizika.get(rowIndex).getSkupinaBR().getNazov();
            case 2:
                return vnutorneBezRizika.get(rowIndex).getPrikladZranitelnosti();
            case 3:
                return vnutorneBezRizika.get(rowIndex).getPrikladHrozby();
            default:
                throw new AssertionError();
        }
    }

    public VnutorneBR getVnutorneBezRiziko(int row) {
        if (row >= 0 && row < this.getRowCount()) {
            return vnutorneBezRizika.get(row);
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() == 0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
