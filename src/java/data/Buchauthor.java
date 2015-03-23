/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thomas Papke
 */
@Entity
@Table(name = "buchauthor", catalog = "bibliothek", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buchauthor.findAll", query = "SELECT b FROM Buchauthor b"),
    @NamedQuery(name = "Buchauthor.findByBuchId", query = "SELECT b FROM Buchauthor b WHERE b.buchauthorId = :buchId"),
    @NamedQuery(name = "Buchauthor.findByBuchauthorId", query = "SELECT b FROM Buchauthor b WHERE b.buchauthorId = :buchauthorId")})
public class Buchauthor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "buchauthor_id", nullable = false)
    private Integer buchauthorId;
    @JoinColumn(name = "author_id", referencedColumnName = "author_id", nullable = false)
    @ManyToOne(optional = false)
    private Author authorId;
    @JoinColumn(name = "buch_id", referencedColumnName = "buch_id", nullable = false)
    @ManyToOne(optional = false)
    private Buch buchId;

    public Buchauthor() {
    }

    public Buchauthor(Integer buchauthorId) {
        this.buchauthorId = buchauthorId;
    }

    public Integer getBuchauthorId() {
        return buchauthorId;
    }

    public void setBuchauthorId(Integer buchauthorId) {
        this.buchauthorId = buchauthorId;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

    public Buch getBuchId() {
        return buchId;
    }

    public void setBuchId(Buch buchId) {
        this.buchId = buchId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buchauthorId != null ? buchauthorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buchauthor)) {
            return false;
        }
        Buchauthor other = (Buchauthor) object;
        if ((this.buchauthorId == null && other.buchauthorId != null) || (this.buchauthorId != null && !this.buchauthorId.equals(other.buchauthorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Buchauthor[ buchauthorId=" + buchauthorId + " ]";
    }
    
}
