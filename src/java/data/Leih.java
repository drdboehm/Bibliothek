/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author KLC
 */
@Entity
@Table(name = "leih", catalog = "bibliothek", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leih.findAll", query = "SELECT l FROM Leih l"),
    @NamedQuery(name = "Leih.findByLeihId", query = "SELECT l FROM Leih l WHERE l.leihId = :leihId"),
    @NamedQuery(name = "Leih.findAllByKundeId", query = "SELECT l FROM Leih l WHERE l.kundeId.kundeId = :kundeId"),
    @NamedQuery(name = "Leih.findByLeihDatum", query = "SELECT l FROM Leih l WHERE l.leihDatum = :leihDatum"),
    @NamedQuery(name = "Leih.findByLeihRueckgabedatum", query = "SELECT l FROM Leih l WHERE l.leihRueckgabedatum = :leihRueckgabedatum"),
    @NamedQuery(name = "Leih.findByLeihRueckgabedatumIsNULL", query = "SELECT l FROM Leih l WHERE l.leihRueckgabedatum is null")})
public class Leih implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "leih_id", nullable = false)
    private Integer leihId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "leih_datum", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date leihDatum;
    @Column(name = "leih_rueckgabedatum")
    @Temporal(TemporalType.DATE)
    private Date leihRueckgabedatum;
    @JoinColumn(name = "kunde_id", referencedColumnName = "kunde_id", nullable = false)
    @ManyToOne(optional = false)
    private Kunde kundeId;
    @JoinColumn(name = "exemplar_id", referencedColumnName = "exemplar_id", nullable = false)
    @ManyToOne(optional = false)
    private Exemplar exemplarId;

    public Leih() {
    }

    public Leih(Integer leihId) {
        this.leihId = leihId;
    }

    public Leih(Integer leihId, Date leihDatum) {
        this.leihId = leihId;
        this.leihDatum = leihDatum;
    }

    public Integer getLeihId() {
        return leihId;
    }

    public void setLeihId(Integer leihId) {
        this.leihId = leihId;
    }

    public Date getLeihDatum() {
        return leihDatum;
    }

    public void setLeihDatum(Date leihDatum) {
        this.leihDatum = leihDatum;
    }

    public Date getLeihRueckgabedatum() {
        return leihRueckgabedatum;
    }

    public void setLeihRueckgabedatum(Date leihRueckgabedatum) {
        this.leihRueckgabedatum = leihRueckgabedatum;
    }

    public Kunde getKundeId() {
        return kundeId;
    }

    public void setKundeId(Kunde kundeId) {
        this.kundeId = kundeId;
    }

    public Exemplar getExemplarId() {
        return exemplarId;
    }

    public void setExemplarId(Exemplar exemplarId) {
        this.exemplarId = exemplarId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (leihId != null ? leihId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leih)) {
            return false;
        }
        Leih other = (Leih) object;
        if ((this.leihId == null && other.leihId != null) || (this.leihId != null && !this.leihId.equals(other.leihId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Leih[ leihId=" + leihId + " ]";
    }
    
}
