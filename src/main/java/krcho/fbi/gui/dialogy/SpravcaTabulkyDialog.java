/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui.dialogy;

import java.awt.Dialog;
import krcho.fbi.databaza.tabulky.Certifikat;
import krcho.fbi.databaza.tabulky.OdbornaPrax;
import krcho.fbi.databaza.tabulky.StupenVzdelania;
import krcho.fbi.databaza.tabulky.UrovenSposobilosti;
import krcho.fbi.gui.tablemodel.CertifikatModel;
import krcho.fbi.gui.tablemodel.OdbornaPraxModel;
import krcho.fbi.gui.tablemodel.StupenVzdelaniaModel;
import krcho.fbi.gui.tablemodel.UrovenSposobilostiModel;

/**
 *
 * @author Jozef Krcho
 */
public class SpravcaTabulkyDialog extends javax.swing.JDialog {

    /**
     * Creates new form StupenVzdelaniaDialog
     */
    private Object vybranyPrvok;
    private Class trieda;
    private int vybranyRiadok;

    public SpravcaTabulkyDialog(Dialog owner, boolean modal, Class trieda) {
        super(owner, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.trieda = trieda;

        switch (trieda.getSimpleName()) {
            case "StupenVzdelania":
                this.setTitle("Stupne vzdelania");
                jTable1.setModel(new StupenVzdelaniaModel());
                nastavInfoStupnaVzdelania((StupenVzdelania) vybranyPrvok);
                break;
            case "Certifikat":
                this.setTitle("Certifikáty");
                jTable1.setModel(new CertifikatModel());
                nastavInfoCertifikat((Certifikat) vybranyPrvok);
                jLabel1.setText("Názov:");
                jLabel2.setText("Popis:");
                jLabel3.setVisible(false);
                jTextArea3.setVisible(false);
                jScrollPane3.setVisible(false);
                break;
            case "OdbornaPrax":
                this.setTitle("Odborná prax");
                jTable1.setModel(new OdbornaPraxModel());
                nastavInfoOdbornaPrax((OdbornaPrax) vybranyPrvok);
                jLabel1.setText("Popis:");
                jLabel2.setVisible(false);
                jTextArea2.setVisible(false);
                jScrollPane2.setVisible(false);
                jLabel3.setVisible(false);
                jTextArea3.setVisible(false);
                jScrollPane3.setVisible(false);
                break;
            case "UrovenSposobilosti":
                this.setTitle("Úroveň spôsobilosti");
                jTable1.setModel(new UrovenSposobilostiModel());
                nastavInfoUrovenSposobilosti((UrovenSposobilosti) vybranyPrvok);
                jLabel1.setText("Stupeň náročnosti:");
                jLabel2.setVisible(false);
                jTextArea2.setVisible(false);
                jScrollPane2.setVisible(false);
                jLabel3.setVisible(false);
                jTextArea3.setVisible(false);
                jScrollPane3.setVisible(false);
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

        zobrazPanel = new javax.swing.JPanel();
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
        upravButton = new javax.swing.JButton();
        zobrazButton = new javax.swing.JButton();
        vytvorButton = new javax.swing.JButton();
        vymazButton = new javax.swing.JButton();
        hotovoButton = new javax.swing.JButton();
        jScrollPane = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 400));
        setPreferredSize(new java.awt.Dimension(512, 276));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        zobrazPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        zobrazPanel.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Zákon:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        zobrazPanel.add(jLabel3, gridBagConstraints);

        jTextArea3.setEditable(false);
        jTextArea3.setBackground(new java.awt.Color(230, 230, 230));
        jTextArea3.setColumns(10);
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(2);
        jTextArea3.setWrapStyleWord(true);
        jScrollPane3.setViewportView(jTextArea3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        zobrazPanel.add(jScrollPane3, gridBagConstraints);

        jTextArea2.setEditable(false);
        jTextArea2.setBackground(new java.awt.Color(230, 230, 230));
        jTextArea2.setColumns(10);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(2);
        jTextArea2.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        zobrazPanel.add(jScrollPane2, gridBagConstraints);

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(230, 230, 230));
        jTextArea1.setColumns(10);
        jTextArea1.setRows(1);
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        zobrazPanel.add(jScrollPane1, gridBagConstraints);

        jLabel1.setText("Názov:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        zobrazPanel.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Úroveň:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        zobrazPanel.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        zobrazPanel.add(filler1, gridBagConstraints);

        upravButton.setText("Uprav");
        upravButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upravButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        zobrazPanel.add(upravButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(zobrazPanel, gridBagConstraints);

        zobrazButton.setText("Zobraz");
        zobrazButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zobrazButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(zobrazButton, gridBagConstraints);

        vytvorButton.setText("Vytvor");
        vytvorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vytvorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(vytvorButton, gridBagConstraints);

        vymazButton.setText("Vymaž");
        vymazButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vymazButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(vymazButton, gridBagConstraints);

        hotovoButton.setText("Hotovo");
        hotovoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotovoButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(hotovoButton, gridBagConstraints);

        jScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane.setPreferredSize(new java.awt.Dimension(200, 200));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new StupenVzdelaniaModel());
        jTable1.setMinimumSize(new java.awt.Dimension(150, 150));
        jTable1.setName(""); // NOI18N
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void zobrazButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zobrazButtonActionPerformed
        vybranyRiadok = jTable1.getSelectedRow();
        if (vybranyRiadok >= 0) {
            vybranyRiadok = jTable1.convertRowIndexToModel(vybranyRiadok);
            switch (trieda.getSimpleName()) {
                case "StupenVzdelania":
                    StupenVzdelaniaModel model1 = (StupenVzdelaniaModel) jTable1.getModel();
                    vybranyPrvok = model1.getStupenVzdelania(vybranyRiadok);
                    nastavInfoStupnaVzdelania((StupenVzdelania) vybranyPrvok);
                    break;
                case "Certifikat":
                    CertifikatModel model2 = (CertifikatModel) jTable1.getModel();
                    vybranyPrvok = model2.getCertifikat(vybranyRiadok);
                    nastavInfoCertifikat((Certifikat) vybranyPrvok);
                    break;
                case "OdbornaPrax":
                    OdbornaPraxModel model3 = (OdbornaPraxModel) jTable1.getModel();
                    vybranyPrvok = model3.getOdbornaPrax(vybranyRiadok);
                    nastavInfoOdbornaPrax((OdbornaPrax) vybranyPrvok);
                    break;
                case "UrovenSposobilosti":
                    UrovenSposobilostiModel model4 = (UrovenSposobilostiModel) jTable1.getModel();
                    vybranyPrvok = model4.getUrovenSposobilosti(vybranyRiadok);
                    nastavInfoUrovenSposobilosti((UrovenSposobilosti) vybranyPrvok);
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }//GEN-LAST:event_zobrazButtonActionPerformed

    private void vytvorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vytvorButtonActionPerformed
        switch (trieda.getSimpleName()) {
            case "StupenVzdelania":
                StupenVzdelania novyStupen = new StupenVzdelania();
                VytvorUpravDialog dialog1 = new VytvorUpravDialog(this, true, novyStupen, trieda);
                dialog1.setVisible(true);
                if (dialog1.isZmena()) {
                    StupenVzdelaniaModel model = (StupenVzdelaniaModel) jTable1.getModel();
                    model.vlozStupenVzdelania(novyStupen);

                    vybranyPrvok = novyStupen;
                    vybranyRiadok = jTable1.getRowCount() - 1;
                    nastavInfoStupnaVzdelania((StupenVzdelania) vybranyPrvok);
                }
                break;
            case "Certifikat":
                Certifikat novyCertifikat = new Certifikat();
                VytvorUpravDialog dialog2 = new VytvorUpravDialog(this, true, novyCertifikat, trieda);
                dialog2.setVisible(true);
                if (dialog2.isZmena()) {
                    CertifikatModel model = (CertifikatModel) jTable1.getModel();
                    model.vlozCertifikat(novyCertifikat);

                    vybranyPrvok = novyCertifikat;
                    vybranyRiadok = jTable1.getRowCount() - 1;
                    nastavInfoCertifikat((Certifikat) vybranyPrvok);
                }
                break;
            case "OdbornaPrax":
                OdbornaPrax novaPrax = new OdbornaPrax();
                VytvorUpravDialog dialog3 = new VytvorUpravDialog(this, true, novaPrax, trieda);
                dialog3.setVisible(true);
                if (dialog3.isZmena()) {
                    OdbornaPraxModel model = (OdbornaPraxModel) jTable1.getModel();
                    model.vlozOdbornuPrax(novaPrax);

                    vybranyPrvok = novaPrax;
                    vybranyRiadok = jTable1.getRowCount() - 1;
                    nastavInfoOdbornaPrax((OdbornaPrax) vybranyPrvok);
                }
                break;
            case "UrovenSposobilosti":
                UrovenSposobilosti novaUroven = new UrovenSposobilosti();
                VytvorUpravDialog dialog4 = new VytvorUpravDialog(this, true, novaUroven, trieda);
                dialog4.setVisible(true);
                if (dialog4.isZmena()) {
                    UrovenSposobilostiModel model = (UrovenSposobilostiModel) jTable1.getModel();
                    model.vlozUrovenSposobilosti(novaUroven);

                    vybranyPrvok = novaUroven;
                    vybranyRiadok = jTable1.getRowCount() - 1;
                    nastavInfoUrovenSposobilosti((UrovenSposobilosti) vybranyPrvok);
                }
                break;
            default:
                throw new AssertionError();
        }

    }//GEN-LAST:event_vytvorButtonActionPerformed

    private void vymazButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vymazButtonActionPerformed
        vybranyRiadok = jTable1.getSelectedRow();
        if (vybranyRiadok >= 0) {
            vybranyRiadok = jTable1.convertRowIndexToModel(vybranyRiadok);
            switch (trieda.getSimpleName()) {
                case "StupenVzdelania":
                    StupenVzdelaniaModel model1 = (StupenVzdelaniaModel) jTable1.getModel();
                    model1.vymazStupenVzdelania(vybranyRiadok);

                    vybranyPrvok = null;
                    nastavInfoStupnaVzdelania((StupenVzdelania) vybranyPrvok);
                    break;
                case "Certifikat":
                    CertifikatModel model2 = (CertifikatModel) jTable1.getModel();
                    model2.vymazCertifikat(vybranyRiadok);

                    vybranyPrvok = null;
                    nastavInfoCertifikat((Certifikat) vybranyPrvok);
                    break;
                case "OdbornaPrax":
                    OdbornaPraxModel model3 = (OdbornaPraxModel) jTable1.getModel();
                    model3.vymazOdbornuPrax(vybranyRiadok);

                    vybranyPrvok = null;
                    nastavInfoOdbornaPrax((OdbornaPrax) vybranyPrvok);
                    break;
                case "UrovenSposobilosti":
                    UrovenSposobilostiModel model4 = (UrovenSposobilostiModel) jTable1.getModel();
                    model4.vymazUrovenSposobilosti(vybranyRiadok);

                    vybranyPrvok = null;
                    nastavInfoUrovenSposobilosti((UrovenSposobilosti) vybranyPrvok);
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }//GEN-LAST:event_vymazButtonActionPerformed

    private void hotovoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotovoButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_hotovoButtonActionPerformed

    private void upravButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upravButtonActionPerformed
        if (vybranyPrvok != null) {
            switch (trieda.getSimpleName()) {
                case "StupenVzdelania":
                    VytvorUpravDialog dialog1 = new VytvorUpravDialog(this, true, vybranyPrvok, trieda);
                    dialog1.setVisible(true);
                    if (dialog1.isZmena()) {
                        StupenVzdelaniaModel model = (StupenVzdelaniaModel) jTable1.getModel();
                        StupenVzdelania upravenyStupen = (StupenVzdelania) vybranyPrvok;
                        model.upravStupenVzdelania(vybranyRiadok, upravenyStupen.getNazov(), upravenyStupen.getUroven(), upravenyStupen.getZakon());

                        nastavInfoStupnaVzdelania((StupenVzdelania) vybranyPrvok);
                    }
                    break;
                case "Certifikat":
                    VytvorUpravDialog dialog2 = new VytvorUpravDialog(this, true, vybranyPrvok, trieda);
                    dialog2.setVisible(true);
                    if (dialog2.isZmena()) {
                        CertifikatModel model = (CertifikatModel) jTable1.getModel();
                        Certifikat upravenyCertifikat = (Certifikat) vybranyPrvok;
                        model.upravCertifikat(vybranyRiadok, upravenyCertifikat.getNazov(), upravenyCertifikat.getPopis());

                        nastavInfoCertifikat((Certifikat) vybranyPrvok);
                    }
                    break;
                case "OdbornaPrax":
                    VytvorUpravDialog dialog3 = new VytvorUpravDialog(this, true, vybranyPrvok, trieda);
                    dialog3.setVisible(true);
                    if (dialog3.isZmena()) {
                        OdbornaPraxModel model = (OdbornaPraxModel) jTable1.getModel();
                        OdbornaPrax upravenaPrax = (OdbornaPrax) vybranyPrvok;
                        model.upravOdbornuPrax(vybranyRiadok, upravenaPrax.getPopis());

                        nastavInfoOdbornaPrax((OdbornaPrax) vybranyPrvok);
                    }
                    break;
                case "UrovenSposobilosti":
                    VytvorUpravDialog dialog4 = new VytvorUpravDialog(this, true, vybranyPrvok, trieda);
                    dialog4.setVisible(true);
                    if (dialog4.isZmena()) {
                        UrovenSposobilostiModel model = (UrovenSposobilostiModel) jTable1.getModel();
                        UrovenSposobilosti upravenaUroven = (UrovenSposobilosti) vybranyPrvok;
                        model.upravUrovenSposobilosti(vybranyRiadok, upravenaUroven.getStupenNarocnosti());

                        nastavInfoUrovenSposobilosti((UrovenSposobilosti) vybranyPrvok);
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }//GEN-LAST:event_upravButtonActionPerformed

    private void nastavInfoStupnaVzdelania(StupenVzdelania vybranyStupen) {
        if (vybranyStupen == null) {
            vybranyRiadok = -1;
            jTextArea1.setText("");
            jTextArea2.setText("");
            jTextArea3.setText("");
        } else {
            jTextArea1.setText(vybranyStupen.getNazov());
            jTextArea2.setText(vybranyStupen.getUroven());
            jTextArea3.setText(vybranyStupen.getZakon());
        }
    }

    private void nastavInfoCertifikat(Certifikat certifikat) {
        if (certifikat == null) {
            vybranyRiadok = -1;
            jTextArea1.setText("");
            jTextArea2.setText("");
        } else {
            jTextArea1.setText(certifikat.getNazov());
            jTextArea2.setText(certifikat.getPopis());
        }
    }

    private void nastavInfoOdbornaPrax(OdbornaPrax odbornaPrax) {
        if (odbornaPrax == null) {
            vybranyRiadok = -1;
            jTextArea1.setText("");
        } else {
            jTextArea1.setText(odbornaPrax.getPopis());
        }
    }

    private void nastavInfoUrovenSposobilosti(UrovenSposobilosti urovenSposobilosti) {
        if (urovenSposobilosti == null) {
            vybranyRiadok = -1;
            jTextArea1.setText("");
        } else {
            jTextArea1.setText(urovenSposobilosti.getStupenNarocnosti());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton hotovoButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JButton upravButton;
    private javax.swing.JButton vymazButton;
    private javax.swing.JButton vytvorButton;
    private javax.swing.JButton zobrazButton;
    private javax.swing.JPanel zobrazPanel;
    // End of variables declaration//GEN-END:variables

}
