/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "sposobilost")
public class Sposobilost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sposobilost_id")
    private long sposobilostId;

    @Column(name = "nazov")
    private String nazov;

    @Column(name = "PST", columnDefinition = "BIT")
    private Boolean PST;

    @ManyToOne
    @JoinColumn(name = "uroven_id")
    private UrovenSposobilosti urovenSposobilosti;

    @OneToMany(mappedBy = "sposobilost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpisUrovne> opisyUrovni = new ArrayList<>();

    @OneToMany(mappedBy = "pozSposobilostsId.sposobilost")
    private List<PozadovanaSposobilost> pozadovaneSposobilosti = new ArrayList<>();

    public Sposobilost() {
    }

    public Sposobilost(String nazov, Boolean PST) {
        this.nazov = nazov;
        this.PST = PST;
    }

    public Sposobilost(long sposobilostId, String nazov, Boolean PST) {
        this.sposobilostId = sposobilostId;
        this.nazov = nazov;
        this.PST = PST;
    }

    public long getSposobilostId() {
        return sposobilostId;
    }

    public void setSposobilostId(long sposobilostId) {
        this.sposobilostId = sposobilostId;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public Boolean getPST() {
        return PST;
    }

    public void setPST(Boolean PST) {
        this.PST = PST;
    }

    public UrovenSposobilosti getUrovenSposobilosti() {
        return urovenSposobilosti;
    }

    public void setUrovenSposobilosti(UrovenSposobilosti urovenSposobilosti) {
        this.urovenSposobilosti = urovenSposobilosti;
    }

    public List<OpisUrovne> getOpisyUrovni() {
        return opisyUrovni;
    }

    public void setOpisyUrovni(List<OpisUrovne> opisyUrovni) {
        this.opisyUrovni = opisyUrovni;
    }

    public List<PozadovanaSposobilost> getPozadovaneSposobilosti() {
        return pozadovaneSposobilosti;
    }

    public void setPozadovaneSposobilosti(List<PozadovanaSposobilost> pozadovaneSposobilosti) {
        this.pozadovaneSposobilosti = pozadovaneSposobilosti;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.sposobilostId ^ (this.sposobilostId >>> 32));
        hash = 97 * hash + Objects.hashCode(this.nazov);
        hash = 97 * hash + Objects.hashCode(this.PST);
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
        final Sposobilost other = (Sposobilost) obj;
        if (this.sposobilostId != other.sposobilostId) {
            return false;
        }
        if (!Objects.equals(this.nazov, other.nazov)) {
            return false;
        }
        if (!Objects.equals(this.PST, other.PST)) {
            return false;
        }
        return true;
    }
    
}
