/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "opis_urovne")
public class OpisUrovne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opis_id")
    private long opisId;

    @Column(name = "popis")
    private String popis;

    @ManyToOne
    @JoinColumn(name = "sposobilost_id")
    private Sposobilost sposobilost;

    public OpisUrovne() {
    }

    public OpisUrovne(String popis) {
        this.popis = popis;
    }

    public OpisUrovne(long opisId, String popis) {
        this.opisId = opisId;
        this.popis = popis;
    }

    public long getOpisId() {
        return opisId;
    }

    public void setOpisId(long opisId) {
        this.opisId = opisId;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public Sposobilost getSposobilost() {
        return sposobilost;
    }

    public void setSposobilost(Sposobilost sposobilost) {
        this.sposobilost = sposobilost;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.opisId ^ (this.opisId >>> 32));
        hash = 97 * hash + Objects.hashCode(this.popis);
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
        final OpisUrovne other = (OpisUrovne) obj;
        if (this.opisId != other.opisId) {
            return false;
        }
        if (!Objects.equals(this.popis, other.popis)) {
            return false;
        }
        return true;
    }

}
