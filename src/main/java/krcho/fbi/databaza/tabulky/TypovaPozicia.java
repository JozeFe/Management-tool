/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.databaza.tabulky;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jozef Krcho
 */
@Entity
@Table(name = "typova_pozicia")
public class TypovaPozicia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pozicia_id")
    private long poziciaId;

    @Column(name = "nazov")
    private String nazov;

    @Column(name = "charakteristika")
    private String charakteristika;

    @ManyToOne
    @JoinColumn(name = "vzdelanie_id")
    private StupenVzdelania stupenVzdelania;

    @ManyToOne
    @JoinColumn(name = "certifikat_id")
    private Certifikat certifikat;

    @ManyToOne
    @JoinColumn(name = "prax_id")
    private OdbornaPrax odbornaPrax;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "podrobna_char",
            joinColumns = @JoinColumn(name = "pozicia_id"),
            inverseJoinColumns = @JoinColumn(name = "cinnost_id"))
    private Set<PopisCinnosti> popisyCinnosti = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "klasifikacia_zamestnania",
            joinColumns = @JoinColumn(name = "pozicia_id"),
            inverseJoinColumns = @JoinColumn(name = "ISCO_kod"))
    private Set<Isco> Isco = new HashSet<>();

    @OneToMany(mappedBy = "pozSposobilostsId.pozicia", targetEntity=PozadovanaSposobilost.class)
    private List<PozadovanaSposobilost> pozadovaneSposobilosti = new ArrayList<>();

    @OneToMany(mappedBy = "pozZrucnostId.pozicia", targetEntity=PozadovanaZrucnost.class)
    private List<PozadovanaZrucnost> pozadovaneZrucnosti = new ArrayList<>();

    @OneToMany(mappedBy = "pozVedomostId.pozicia")
    private List<PozadovanaVedomost> pozadovaneVedomosti = new ArrayList<>();

    public TypovaPozicia() {

    }

    public TypovaPozicia(String nazov, String charakteristika) {
        this.nazov = nazov;
        this.charakteristika = charakteristika;
    }

    public TypovaPozicia(long poziciaId, String nazov, String charakteristika) {
        this.poziciaId = poziciaId;
        this.nazov = nazov;
        this.charakteristika = charakteristika;
    }

    public long getPoziciaId() {
        return poziciaId;
    }

    public void setPoziciaId(long poziciaId) {
        this.poziciaId = poziciaId;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getCharakteristika() {
        return charakteristika;
    }

    public void setCharakteristika(String charakteristika) {
        this.charakteristika = charakteristika;
    }

    public StupenVzdelania getStupenVzdelania() {
        return stupenVzdelania;
    }

    public void setStupenVzdelania(StupenVzdelania stupenVzdelania) {
        this.stupenVzdelania = stupenVzdelania;
    }

    public Certifikat getCertifikat() {
        return certifikat;
    }

    public void setCertifikat(Certifikat certifikat) {
        this.certifikat = certifikat;
    }

    public OdbornaPrax getPrax() {
        return odbornaPrax;
    }

    public void setPrax(OdbornaPrax odbornaPrax) {
        this.odbornaPrax = odbornaPrax;
    }

    public Set<PopisCinnosti> getPopisyCinnosti() {
        return popisyCinnosti;
    }

    public void setPopisyCinnosti(Set<PopisCinnosti> popisyCinnosti) {
        this.popisyCinnosti = popisyCinnosti;
    }

    public OdbornaPrax getOdbornaPrax() {
        return odbornaPrax;
    }

    public void setOdbornaPrax(OdbornaPrax odbornaPrax) {
        this.odbornaPrax = odbornaPrax;
    }

    public Set<Isco> getIsco() {
        return Isco;
    }

    public void setIsco(Set<Isco> Isco) {
        this.Isco = Isco;
    }

    public List<PozadovanaSposobilost> getPozadovaneSposobilosti() {
        return pozadovaneSposobilosti;
    }

    public void setPozadovaneSposobilosti(List<PozadovanaSposobilost> pozadovaneSposobilosti) {
        this.pozadovaneSposobilosti = pozadovaneSposobilosti;
    }
    
    public List<PozadovanaZrucnost> getPozadovaneZrucnosti() {
        return pozadovaneZrucnosti;
    }

    public void setPozadovaneZrucnosti(List<PozadovanaZrucnost> pozadovaneZrucnosti) {
        this.pozadovaneZrucnosti = pozadovaneZrucnosti;
    }

    public List<PozadovanaVedomost> getPozadovaneVedomosti() {
        return pozadovaneVedomosti;
    }

    public void setPozadovaneVedomosti(List<PozadovanaVedomost> pozadovaneVedomosti) {
        this.pozadovaneVedomosti = pozadovaneVedomosti;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.poziciaId ^ (this.poziciaId >>> 32));
        hash = 37 * hash + Objects.hashCode(this.nazov);
        hash = 37 * hash + Objects.hashCode(this.charakteristika);
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
        final TypovaPozicia other = (TypovaPozicia) obj;
        if (this.poziciaId != other.poziciaId) {
            return false;
        }
        if (!Objects.equals(this.nazov, other.nazov)) {
            return false;
        }
        if (!Objects.equals(this.charakteristika, other.charakteristika)) {
            return false;
        }
        return true;
    }
    
    

}
