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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import krcho.fbi.databaza.tabulky.enumy.Poziadavka;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "pozadovane_zrucnosti")
@AssociationOverrides({
    @AssociationOverride(name = "pozZrucnostId.pozicia", joinColumns = @JoinColumn(name = "pozicia_id")),
    @AssociationOverride(name = "pozZrucnostId.zrucnost", joinColumns = @JoinColumn(name = "zrucnost_id"))})
public class PozadovanaZrucnost {

    @EmbeddedId
    private PozadovanaZrucnostId pozZrucnostId = new PozadovanaZrucnostId();

    @Enumerated(EnumType.STRING)
    @Column(name = "poziadavka")
    private Poziadavka poziadavka;

    @ManyToOne
    @JoinColumn(name = "uroven")
    private NKRZrucnost uroven;

    public PozadovanaZrucnost() {
    }

    public PozadovanaZrucnost(Poziadavka poziadavka) {
        this.poziadavka = poziadavka;
    }

    public PozadovanaZrucnostId getPozZrucnostId() {
        return pozZrucnostId;
    }

    public void setPozZrucnostId(PozadovanaZrucnostId pozZrucnostId) {
        this.pozZrucnostId = pozZrucnostId;
    }

    public Poziadavka getPoziadavka() {
        return poziadavka;
    }

    public void setPoziadavka(Poziadavka poziadavka) {
        this.poziadavka = poziadavka;
    }

    public TypovaPozicia getPozicia() {
        return getPozZrucnostId().getPozicia();
    }

    public void setPozicia(TypovaPozicia pozicia) {
        getPozZrucnostId().setPozicia(pozicia);
    }

    public OdbornaZrucnost getZrucnost() {
        return getPozZrucnostId().getZrucnost();
    }

    public void setZrucnost(OdbornaZrucnost zrucnost) {
        getPozZrucnostId().setZrucnost(zrucnost);
    }

    public NKRZrucnost getUroven() {
        return uroven;
    }

    public void setUroven(NKRZrucnost uroven) {
        this.uroven = uroven;
    }

}
