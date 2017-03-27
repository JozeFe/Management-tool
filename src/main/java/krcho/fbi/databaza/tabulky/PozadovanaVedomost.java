/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.Objects;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import krcho.fbi.databaza.tabulky.enumy.Poziadavka;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "pozadovane_vedomosti")
@AssociationOverrides({
    @AssociationOverride(name = "pozVedomostId.pozicia", joinColumns = @JoinColumn(name = "pozicia_id")),
    @AssociationOverride(name = "pozVedomostId.vedomost", joinColumns = @JoinColumn(name = "vedomost_id"))})
public class PozadovanaVedomost {

    @EmbeddedId
    private PozadovanaVedomostId pozVedomostId = new PozadovanaVedomostId();

    @Enumerated(EnumType.STRING)
    @Column(name = "poziadavka")
    private Poziadavka poziadavka;

    @ManyToOne
    @JoinColumn(name = "uroven")
    private NKRVedomost uroven;

    public PozadovanaVedomost() {
    }

    public PozadovanaVedomost(Poziadavka poziadavka) {
        this.poziadavka = poziadavka;
    }

    public PozadovanaVedomostId getPozVedomostId() {
        return pozVedomostId;
    }

    public void setPozVedomostId(PozadovanaVedomostId pozVedomostId) {
        this.pozVedomostId = pozVedomostId;
    }

    public Poziadavka getPoziadavka() {
        return poziadavka;
    }

    public void setPoziadavka(Poziadavka poziadavka) {
        this.poziadavka = poziadavka;
    }

    public TypovaPozicia getPozicia() {
        return getPozVedomostId().getPozicia();
    }

    public void setPozicia(TypovaPozicia pozicia) {
        getPozVedomostId().setPozicia(pozicia);
    }

    public OdbornaVedomost getVedomost() {
        return getPozVedomostId().getVedomost();
    }

    public void setVedomost(OdbornaVedomost vedomost) {
        getPozVedomostId().setVedomost(vedomost);
    }

    public NKRVedomost getUroven() {
        return uroven;
    }

    public void setUroven(NKRVedomost uroven) {
        this.uroven = uroven;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.pozVedomostId);
        hash = 37 * hash + Objects.hashCode(this.poziadavka);
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
        final PozadovanaVedomost other = (PozadovanaVedomost) obj;
        if (!Objects.equals(this.pozVedomostId, other.pozVedomostId)) {
            return false;
        }
        if (this.poziadavka != other.poziadavka) {
            return false;
        }
        return true;
    }

    
}
