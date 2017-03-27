/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.dialogy;

import java.awt.Color;
import java.awt.Dialog;
import javax.swing.JOptionPane;
import krcho.fbi.databaza.tabulky.Certifikat;
import krcho.fbi.databaza.tabulky.Isco;
import krcho.fbi.databaza.tabulky.OdbornaPrax;
import krcho.fbi.databaza.tabulky.OdbornaVedomost;
import krcho.fbi.databaza.tabulky.OdbornaZrucnost;
import krcho.fbi.databaza.tabulky.OpisUrovne;
import krcho.fbi.databaza.tabulky.PopisCinnosti;
import krcho.fbi.databaza.tabulky.StupenVzdelania;
import krcho.fbi.databaza.tabulky.UrovenSposobilosti;
import krcho.fbi.gui.TextAreaFilter;
import krcho.fbi.gui.TextAreaKeyAdapter;
import krcho.fbi.hibernate.HibernateUtilities;
import org.hibernate.Session;

/**
 *
 * @author Jozef Krcho
 */
public class VytvorUpravDialog extends javax.swing.JDialog {

    /**
     * Creates new form StupenVzdelaniaVytvorDialog
     */
    private Object prvok;
    private Class trieda;
    private boolean zmena;

    public VytvorUpravDialog(java.awt.Frame parent, boolean modal, Object prvok, Class trieda) {
        super(parent, modal);
        initComponents();
        nastav(prvok, trieda);
    }

    public VytvorUpravDialog(Dialog owner, boolean modal, Object prvok, Class trieda) {
        super(owner, modal);
        initComponents();
        nastav(prvok, trieda);
    }

    private void nastav(Object prvok, Class trieda) {
        this.setLocationRelativeTo(null);
        jTextArea1.addKeyListener(new TextAreaKeyAdapter());
        jTextArea2.addKeyListener(new TextAreaKeyAdapter());
        jTextArea3.addKeyListener(new TextAreaKeyAdapter());

        this.prvok = prvok;
        this.trieda = trieda;
        this.zmena = false;
        switch (trieda.getSimpleName()) {
            case "StupenVzdelania":
                this.setTitle("Stupeň vzdelania");
                jTextArea1.setDocument(new TextAreaFilter(jTextArea1, 45));
                jTextArea2.setDocument(new TextAreaFilter(jTextArea2, 45));
                StupenVzdelania stupenVzdelania = (StupenVzdelania) prvok;
                jTextArea1.setText(stupenVzdelania.getNazov());
                jTextArea2.setText(stupenVzdelania.getUroven());
                jTextArea3.setText(stupenVzdelania.getZakon());
                break;
            case "Certifikat":
                this.setTitle("Certifikát");
                jTextArea1.setDocument(new TextAreaFilter(jTextArea1, 45));
                Certifikat certifikat = (Certifikat) prvok;
                jTextArea1.setText(certifikat.getNazov());
                jTextArea2.setText(certifikat.getPopis());
                jLabel1.setText("Názov:");
                jLabel2.setText("Popis:");
                skryTretiRiadok();
                break;
            case "OdbornaPrax":
                this.setTitle("Odborná prax");
                jTextArea1.setDocument(new TextAreaFilter(jTextArea1, 45));
                OdbornaPrax odbornaPrax = (OdbornaPrax) prvok;
                jTextArea1.setText(odbornaPrax.getPopis());
                jLabel1.setText("Popis:");
                skryDruhyRiadok();
                skryTretiRiadok();
                break;
            case "PopisCinnosti":
                this.setTitle("Popis činnosti");
                PopisCinnosti popisCinnosti = (PopisCinnosti) prvok;
                jTextArea1.setText(popisCinnosti.getPopis());
                jLabel1.setText("Popis:");
                skryDruhyRiadok();
                skryTretiRiadok();
                break;
            case "Isco":
                this.setTitle("ISCO");
                jTextArea1.setDocument(new TextAreaFilter(jTextArea1, 7));
                Isco isco = (Isco) prvok;
                jTextArea1.setText(isco.getIscoKod());
                if (isco.getIscoKod() != null) {
                    jTextArea1.setEditable(false);
                    jTextArea1.setBackground(new Color(230, 230, 230));
                }
                jLabel1.setText("Isco kód:");
                jLabel2.setText("Popis:");
                jTextArea2.setText(isco.getPopis());
                skryTretiRiadok();
                break;
            case "OdbornaZrucnost":
                this.setTitle("Odborná zručnost");
                OdbornaZrucnost odbornaZrucnost = (OdbornaZrucnost) prvok;
                jTextArea1.setText(odbornaZrucnost.getPopis());
                jLabel1.setText("Popis:");
                skryDruhyRiadok();
                skryTretiRiadok();
                break;
            case "OdbornaVedomost":
                this.setTitle("Odborná vedomosť");
                OdbornaVedomost odbornaVedomost = (OdbornaVedomost) prvok;
                jTextArea1.setText(odbornaVedomost.getPopis());
                jLabel1.setText("Popis:");
                skryDruhyRiadok();
                skryTretiRiadok();
                break;
            case "OpisUrovne":
                this.setTitle("Opis úrovne spôsobilosti");
                OpisUrovne opisUrovne = (OpisUrovne) prvok;
                jTextArea1.setText(opisUrovne.getPopis());
                jLabel1.setText("Popis:");
                skryDruhyRiadok();
                skryTretiRiadok();
                break;
            case "UrovenSposobilosti":
                this.setTitle("Úroveň spôsobilosti");
                jTextArea1.setDocument(new TextAreaFilter(jTextArea1, 45));
                UrovenSposobilosti uroven = (UrovenSposobilosti) prvok;
                jTextArea1.setText(uroven.getStupenNarocnosti());
                jLabel1.setText("Stupeň náročnosti:");
                skryDruhyRiadok();
                skryTretiRiadok();
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jPanel1 = new javax.swing.JPanel();
        zrusitButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 300));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Zákon:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel3, gridBagConstraints);

        jTextArea3.setColumns(10);
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(2);
        jTextArea3.setWrapStyleWord(true);
        jScrollPane3.setViewportView(jTextArea3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane3, gridBagConstraints);

        jTextArea2.setColumns(10);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(2);
        jTextArea2.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane2, gridBagConstraints);

        jTextArea1.setColumns(10);
        jTextArea1.setRows(1);
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jLabel1.setText("Názov:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Úroveň:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(filler1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        zrusitButton.setText("Zrušiť");
        zrusitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zrusitButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(zrusitButton, gridBagConstraints);

        jButton1.setText("Uložiť");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void zrusitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zrusitButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_zrusitButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        switch (trieda.getSimpleName()) {
            case "StupenVzdelania":
                StupenVzdelania stupenVzdelania = (StupenVzdelania) prvok;
                stupenVzdelania.setNazov(jTextArea1.getText());
                stupenVzdelania.setUroven(jTextArea2.getText());
                stupenVzdelania.setZakon(jTextArea3.getText());
                break;
            case "Certifikat":
                Certifikat certifikat = (Certifikat) prvok;
                certifikat.setNazov(jTextArea1.getText());
                certifikat.setPopis(jTextArea2.getText());
                break;
            case "OdbornaPrax":
                OdbornaPrax odbornaPrax = (OdbornaPrax) prvok;
                odbornaPrax.setPopis(jTextArea1.getText());
                break;
            case "PopisCinnosti":
                PopisCinnosti popisCinnosti = (PopisCinnosti) prvok;
                popisCinnosti.setPopis(jTextArea1.getText());
                break;
            case "Isco":
                Isco isco = (Isco) prvok;
                if (isco.getIscoKod() == null) {
                    String kod = jTextArea1.getText();
                    if (jeSpravnyKod(kod)) {
                        isco.setIscoKod(kod);
                    } else {
                        return;
                    }
                }
                isco.setPopis(jTextArea2.getText());
                break;
            case "OdbornaZrucnost":
                OdbornaZrucnost odbornaZrucnost = (OdbornaZrucnost) prvok;
                odbornaZrucnost.setPopis(jTextArea1.getText());
                break;
            case "OdbornaVedomost":
                OdbornaVedomost odbornaVedomost = (OdbornaVedomost) prvok;
                odbornaVedomost.setPopis(jTextArea1.getText());
                break;
            case "OpisUrovne":
                OpisUrovne opisUrovne = (OpisUrovne) prvok;
                opisUrovne.setPopis(jTextArea1.getText());
                break;
            case "UrovenSposobilosti":
                UrovenSposobilosti uroven = (UrovenSposobilosti) prvok;
                uroven.setStupenNarocnosti(jTextArea1.getText());
                break;
            default:
                throw new AssertionError();
        }
        this.zmena = true;
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public boolean isZmena() {
        return zmena;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JButton zrusitButton;
    // End of variables declaration//GEN-END:variables

    private boolean jeSpravnyKod(String kod) {
        if (kod.length() != 4 && kod.length() != 7) {
            JOptionPane.showMessageDialog(this, "Nesprávny formát kódu! Kód musí obsahovať 4 alebo 7 cifier!", "Chybný kód!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            int cislo = Integer.parseInt(kod);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nesprávny formát kódu! Kód musí obsahovať 4 alebo 7 cifier!", "Chybný kód!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        Session session = HibernateUtilities.getSessionFactory().openSession();
        Isco existujuceIsco = (Isco) session.get(trieda, kod);
        session.close();
        if (existujuceIsco != null) {
            JOptionPane.showMessageDialog(this, "Zadaný kód už existuje!", "Zhoda!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void skryDruhyRiadok() {
        jLabel2.setVisible(false);
        jTextArea2.setVisible(false);
        jScrollPane2.setVisible(false);
    }

    private void skryTretiRiadok() {
        jLabel3.setVisible(false);
        jTextArea3.setVisible(false);
        jScrollPane3.setVisible(false);
    }
}
