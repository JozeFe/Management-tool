/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.dao.VonkajsieBRDao;
import krcho.fbi.databaza.tabulky.VonkajsieBR;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.Session;

/**
 *
 * @author Jozef Krcho
 */
public class VonkajsieBRModel extends AbstractTableModel {

    private static final String[] STLPCE = {"Id", "Typ", "Hrozba"};
    private List<VonkajsieBR> vonkajsieBezRizika = new ArrayList<>();

    public VonkajsieBRModel() {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        VonkajsieBRDao vonkajsieBRDao = new VonkajsieBRDao();
        vonkajsieBRDao.setSession(session);
        vonkajsieBezRizika = vonkajsieBRDao.findAll();
        session.close();
    }

    @Override
    public int getRowCount() {
        return vonkajsieBezRizika.size();
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
                return vonkajsieBezRizika.get(rowIndex).getVonkajsieBrId();
            case 1:
                return vonkajsieBezRizika.get(rowIndex).getTypBR().getNazov();
            case 2:
                return vonkajsieBezRizika.get(rowIndex).getHrozba();
            default:
                throw new AssertionError();
        }
    }

    public VonkajsieBR getVonkajsieBezRiziko(int row) {
        if (row >= 0 && row < this.getRowCount()) {
            return vonkajsieBezRizika.get(row);
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
