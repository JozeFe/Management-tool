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
public class PozadovanaZrucnostId implements Serializable {

    @ManyToOne
    private TypovaPozicia pozicia;

    @ManyToOne
    private OdbornaZrucnost zrucnost;

    public TypovaPozicia getPozicia() {
        return pozicia;
    }

    public void setPozicia(TypovaPozicia pozicia) {
        this.pozicia = pozicia;
    }

    public OdbornaZrucnost getZrucnost() {
        return zrucnost;
    }

    public void setZrucnost(OdbornaZrucnost zrucnost) {
        this.zrucnost = zrucnost;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.pozicia);
        hash = 59 * hash + Objects.hashCode(this.zrucnost);
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
        final PozadovanaZrucnostId other = (PozadovanaZrucnostId) obj;
        if (!Objects.equals(this.pozicia, other.pozicia)) {
            return false;
        }
        if (!Objects.equals(this.zrucnost, other.zrucnost)) {
            return false;
        }
        return true;
    }

}
