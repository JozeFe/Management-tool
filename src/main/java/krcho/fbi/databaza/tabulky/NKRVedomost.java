/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "NKR_Vedomost")
public class NKRVedomost {

    @Id
    @Column(name = "uroven")
    private long uroven;

    @Column(name = "popis")
    private String popis;

    @OneToMany(mappedBy = "uroven")
    private List<PozadovanaVedomost> pozadovaneVedomosti = new ArrayList<>();

    public NKRVedomost() {
    }

    public NKRVedomost(String popis) {
        this.popis = popis;
    }

    public NKRVedomost(long uroven, String popis) {
        this.uroven = uroven;
        this.popis = popis;
    }

    public long getUroven() {
        return uroven;
    }

    public void setUroven(long uroven) {
        this.uroven = uroven;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public List<PozadovanaVedomost> getPozadovaneVedomosti() {
        return pozadovaneVedomosti;
    }

    public void setPozadovaneVedomosti(List<PozadovanaVedomost> pozadovaneVedomosti) {
        this.pozadovaneVedomosti = pozadovaneVedomosti;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.uroven ^ (this.uroven >>> 32));
        hash = 79 * hash + Objects.hashCode(this.popis);
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
        final NKRVedomost other = (NKRVedomost) obj;
        if (this.uroven != other.uroven) {
            return false;
        }
        if (!Objects.equals(this.popis, other.popis)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return uroven + "";
    }

}
