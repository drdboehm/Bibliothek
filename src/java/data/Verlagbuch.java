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
@Table(name = "verlagbuch", catalog = "bibliothek", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Verlagbuch.findAll", query = "SELECT v FROM Verlagbuch v"),
    @NamedQuery(name = "Verlagbuch.findByVerlagbuchId", query = "SELECT v FROM Verlagbuch v WHERE v.verlagbuchId = :verlagbuchId"),
    @NamedQuery(name = "Verlagbuch.findByVerlagbuchIsbn", query = "SELECT v FROM Verlagbuch v WHERE v.verlagbuchIsbn = :verlagbuchIsbn")})
public class Verlagbuch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "verlagbuch_id", nullable = false)
    private Integer verlagbuchId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "verlagbuch_isbn", nullable = false, length = 255)
    private String verlagbuchIsbn;
    @JoinColumn(name = "verlag_id", referencedColumnName = "verlag_id", nullable = false)
    @ManyToOne(optional = false)
    private Verlag verlagId;
    @JoinColumn(name = "buch_id", referencedColumnName = "buch_id", nullable = false)
    @ManyToOne(optional = false)
    private Buch buchId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "verlagbuchId")
    private Collection<Exemplar> exemplarCollection;

    public Verlagbuch() {
    }

    public Verlagbuch(Integer verlagbuchId) {
        this.verlagbuchId = verlagbuchId;
    }

    public Verlagbuch(Integer verlagbuchId, String verlagbuchIsbn) {
        this.verlagbuchId = verlagbuchId;
        this.verlagbuchIsbn = verlagbuchIsbn;
    }

    public Integer getVerlagbuchId() {
        return verlagbuchId;
    }

    public void setVerlagbuchId(Integer verlagbuchId) {
        this.verlagbuchId = verlagbuchId;
    }

    public String getVerlagbuchIsbn() {
        return verlagbuchIsbn;
    }

    public void setVerlagbuchIsbn(String verlagbuchIsbn) {
        this.verlagbuchIsbn = verlagbuchIsbn;
    }

    public Verlag getVerlagId() {
        return verlagId;
    }

    public void setVerlagId(Verlag verlagId) {
        this.verlagId = verlagId;
    }

    public Buch getBuchId() {
        return buchId;
    }

    public void setBuchId(Buch buchId) {
        this.buchId = buchId;
    }

    @XmlTransient
    public Collection<Exemplar> getExemplarCollection() {
        return exemplarCollection;
    }

    public void setExemplarCollection(Collection<Exemplar> exemplarCollection) {
        this.exemplarCollection = exemplarCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (verlagbuchId != null ? verlagbuchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Verlagbuch)) {
            return false;
        }
        Verlagbuch other = (Verlagbuch) object;
        if ((this.verlagbuchId == null && other.verlagbuchId != null) || (this.verlagbuchId != null && !this.verlagbuchId.equals(other.verlagbuchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Verlagbuch[ verlagbuchId=" + verlagbuchId + " ]";
    }
    
}
