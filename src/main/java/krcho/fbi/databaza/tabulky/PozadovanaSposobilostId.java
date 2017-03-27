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
public class PozadovanaSposobilostId implements Serializable {

    @ManyToOne
    private TypovaPozicia pozicia;
    
    @ManyToOne
    private Sposobilost sposobilost;

    public TypovaPozicia getPozicia() {
        return pozicia;
    }

    public void setPozicia(TypovaPozicia pozicia) {
        this.pozicia = pozicia;
    }

    public Sposobilost getSposobilost() {
        return sposobilost;
    }

    public void setSposobilost(Sposobilost sposobilost) {
        this.sposobilost = sposobilost;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.pozicia);
        hash = 29 * hash + Objects.hashCode(this.sposobilost);
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
        final PozadovanaSposobilostId other = (PozadovanaSposobilostId) obj;
        if (!Objects.equals(this.pozicia, other.pozicia)) {
            return false;
        }
        if (!Objects.equals(this.sposobilost, other.sposobilost)) {
            return false;
        }
        return true;
    }
    
    

}
