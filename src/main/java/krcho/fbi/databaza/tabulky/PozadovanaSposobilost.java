/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import krcho.fbi.databaza.tabulky.enumy.Poziadavka;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "pozadovane_sposobilosti")
@AssociationOverrides({
    @AssociationOverride(name = "pozSposobilostsId.pozicia", joinColumns = @JoinColumn(name = "pozicia_id")),
    @AssociationOverride(name = "pozSposobilostsId.sposobilost", joinColumns = @JoinColumn(name = "sposobilost_id"))})
public class PozadovanaSposobilost {

    @EmbeddedId
    private PozadovanaSposobilostId pozSposobilostsId = new PozadovanaSposobilostId();

    @Enumerated(EnumType.STRING)
    @Column(name = "poziadavka")
    private Poziadavka poziadavka;

    public PozadovanaSposobilost() {
    }

    public PozadovanaSposobilost(Poziadavka poziadavka) {
        this.poziadavka = poziadavka;
    }

    public Poziadavka getPoziadavka() {
        return poziadavka;
    }

    public void setPoziadavka(Poziadavka poziadavka) {
        this.poziadavka = poziadavka;
    }

    public PozadovanaSposobilostId getPozSposobilostsId() {
        return pozSposobilostsId;
    }

    public void setPozSposobilostsId(PozadovanaSposobilostId pozSposobilostsId) {
        this.pozSposobilostsId = pozSposobilostsId;
    }

    public TypovaPozicia getPozicia() {
        return getPozSposobilostsId().getPozicia();
    }

    public void setPozicia(TypovaPozicia pozicia) {
        getPozSposobilostsId().setPozicia(pozicia);
    }

    public Sposobilost getSposobilost() {
        return getPozSposobilostsId().getSposobilost();
    }

    public void setSposobilost(Sposobilost sposobilost) {
        getPozSposobilostsId().setSposobilost(sposobilost);
    }

}
