/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import krcho.fbi.databaza.tabulky.OpisUrovne;
import krcho.fbi.databaza.tabulky.Sposobilost;

/**
 *
 * @author Jozef Krcho
 */
public class OpisUrovneNoSQLModel extends AbstractTableModel {

    private static final String[] STLPCE = {"popis"};
    private List<OpisUrovne> opisyUrovne;

    public OpisUrovneNoSQLModel(List<OpisUrovne> opisyUrovne) {
        this.opisyUrovne = opisyUrovne;
    }

    @Override
    public int getRowCount() {
        return opisyUrovne.size();
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
                return opisyUrovne.get(rowIndex).getPopis();
            default:
                throw new AssertionError();
        }
    }

    public OpisUrovne getOpisUrovne(int row) {
        return opisyUrovne.get(row);
    }

    public void vymazOpisUrovne(int row) {
        opisyUrovne.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void upravOpisUrovne(int row, String popis) {
        opisyUrovne.get(row).setPopis(popis);
        this.fireTableRowsUpdated(row, row);
    }

    public void vlozOpisUrovne(OpisUrovne opisUrovne, Sposobilost sposobilost) {
        opisyUrovne.add(opisUrovne);
        opisUrovne.setSposobilost(sposobilost);
        this.fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() == 0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
