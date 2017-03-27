/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jozef Krcho
 */
public class PozadovanaVedomostId implements Serializable {

    @ManyToOne
    private TypovaPozicia pozicia;

    @ManyToOne
    private OdbornaVedomost vedomost;

    public TypovaPozicia getPozicia() {
        return pozicia;
    }

    public void setPozicia(TypovaPozicia pozicia) {
        this.pozicia = pozicia;
    }

    public OdbornaVedomost getVedomost() {
        return vedomost;
    }

    public void setVedomost(OdbornaVedomost vedomost) {
        this.vedomost = vedomost;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.pozicia);
        hash = 79 * hash + Objects.hashCode(this.vedomost);
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
        final PozadovanaVedomostId other = (PozadovanaVedomostId) obj;
        if (!Objects.equals(this.pozicia, other.pozicia)) {
            return false;
        }
        if (!Objects.equals(this.vedomost, other.vedomost)) {
            return false;
        }
        return true;
    }

}
