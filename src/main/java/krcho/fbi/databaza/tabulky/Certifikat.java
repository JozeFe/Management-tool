/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "certifikaty")
public class Certifikat {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certifikat_id")
    private long certifikatId;

    @Column(name = "nazov")
    private String nazov;
    @Column(name = "popis")
    private String popis;

    @OneToMany(mappedBy = "certifikat", cascade = CascadeType.ALL)
    private List<TypovaPozicia> typovePozicie = new ArrayList<>();

    public Certifikat() {
    }

    public Certifikat(String nazov, String popis) {
        this.nazov = nazov;
        this.popis = popis;
    }

    public Certifikat(long certifikatId, String nazov, String popis) {
        this.certifikatId = certifikatId;
        this.nazov = nazov;
        this.popis = popis;
    }

    public long getCertifikatId() {
        return certifikatId;
    }

    public void setCertifikatId(long certifikatId) {
        long oldCertifikatId = this.certifikatId;
        this.certifikatId = certifikatId;
        changeSupport.firePropertyChange("certifikatId", oldCertifikatId, certifikatId);
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        String oldNazov = this.nazov;
        this.nazov = nazov;
        changeSupport.firePropertyChange("nazov", oldNazov, nazov);
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        String oldPopis = this.popis;
        this.popis = popis;
        changeSupport.firePropertyChange("popis", oldPopis, popis);
    }

    public List<TypovaPozicia> getTypovePozicie() {
        return typovePozicie;
    }

    public void setTypovePozicie(List<TypovaPozicia> typovePozicie) {
        this.typovePozicie = typovePozicie;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return nazov;
    }
}
