/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name="stupen_vzdelania")
public class StupenVzdelania {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="vzdelanie_id")
    private long vzdelanieId;
    
    @OneToMany(mappedBy="stupenVzdelania", cascade = CascadeType.ALL)
    private List<TypovaPozicia> typovePozicie = new ArrayList<>();
    
    @Column(name="nazov")
    private String nazov;
    @Column(name="uroven")
    private String uroven;
    @Column(name="zakon")
    private String zakon;

    public StupenVzdelania() {
        
    }
    
    public StupenVzdelania(String nazov, String uroven, String zakon) {
        this.nazov = nazov;
        this.uroven = uroven;
        this.zakon = zakon;
    }

    public StupenVzdelania(long vzdelanieId, String nazov, String uroven, String zakon) {
        this.vzdelanieId = vzdelanieId;
        this.nazov = nazov;
        this.uroven = uroven;
        this.zakon = zakon;
    }

    public long getVzdelanieId() {
        return vzdelanieId;
    }

    public void setVzdelanieId(long vzdelanieId) {
        this.vzdelanieId = vzdelanieId;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getUroven() {
        return uroven;
    }

    public void setUroven(String uroven) {
        this.uroven = uroven;
    }

    public String getZakon() {
        return zakon;
    }

    public void setZakon(String zakon) {
        this.zakon = zakon;
    }

    public List<TypovaPozicia> getTypovePozicie() {
        return typovePozicie;
    }

    public void setTypovePozicie(List<TypovaPozicia> typovePozicie) {
        this.typovePozicie = typovePozicie;
    }

    @Override
    public String toString() {
        return nazov + " - " + uroven;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (int) (this.vzdelanieId ^ (this.vzdelanieId >>> 32));
        hash = 47 * hash + Objects.hashCode(this.nazov);
        hash = 47 * hash + Objects.hashCode(this.uroven);
        hash = 47 * hash + Objects.hashCode(this.zakon);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StupenVzdelania other = (StupenVzdelania) obj;
        if (this.vzdelanieId != other.vzdelanieId) {
            return false;
        }
        if (!Objects.equals(this.nazov, other.nazov)) {
            return false;
        }
        if (!Objects.equals(this.uroven, other.uroven)) {
            return false;
        }
        if (!Objects.equals(this.zakon, other.zakon)) {
            return false;
        }
        return true;
    }
    
    
}
