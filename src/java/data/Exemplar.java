/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Thomas Papke
 */
@Entity
@Table(name = "exemplar", catalog = "bibliothek", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exemplar.findAll", query = "SELECT e FROM Exemplar e"),
    @NamedQuery(name = "Exemplar.findByExemplarId", query = "SELECT e FROM Exemplar e WHERE e.exemplarId = :exemplarId"),
    @NamedQuery(name = "Exemplar.findByExemplarNr", query = "SELECT e FROM Exemplar e WHERE e.exemplarNr = :exemplarNr")})
public class Exemplar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "exemplar_id", nullable = false)
    private Integer exemplarId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "exemplar_nr", nullable = false, length = 255)
    private String exemplarNr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exemplarId")
    private Collection<Leih> leihCollection;
    @JoinColumn(name = "verlagbuch_id", referencedColumnName = "verlagbuch_id", nullable = false)
    @ManyToOne(optional = false)
    private Verlagbuch verlagbuchId;

    public Exemplar() {
    }

    public Exemplar(Integer exemplarId) {
        this.exemplarId = exemplarId;
    }

    public Exemplar(Integer exemplarId, String exemplarNr) {
        this.exemplarId = exemplarId;
        this.exemplarNr = exemplarNr;
    }

    public Integer getExemplarId() {
        return exemplarId;
    }

    public void setExemplarId(Integer exemplarId) {
        this.exemplarId = exemplarId;
    }

    public String getExemplarNr() {
        return exemplarNr;
    }

    public void setExemplarNr(String exemplarNr) {
        this.exemplarNr = exemplarNr;
    }

    @XmlTransient
    public Collection<Leih> getLeihCollection() {
        return leihCollection;
    }

    public void setLeihCollection(Collection<Leih> leihCollection) {
        this.leihCollection = leihCollection;
    }

    public Verlagbuch getVerlagbuchId() {
        return verlagbuchId;
    }

    public void setVerlagbuchId(Verlagbuch verlagbuchId) {
        this.verlagbuchId = verlagbuchId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exemplarId != null ? exemplarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exemplar)) {
            return false;
        }
        Exemplar other = (Exemplar) object;
        if ((this.exemplarId == null && other.exemplarId != null) || (this.exemplarId != null && !this.exemplarId.equals(other.exemplarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Exemplar[ exemplarId=" + exemplarId + " ]";
    }
    
}
