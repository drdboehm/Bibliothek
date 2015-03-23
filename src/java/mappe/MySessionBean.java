
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mappe;

import data.Author;
import data.Buch;
import data.Buchauthor;
import data.Exemplar;
import data.Kunde;
import data.Leih;
import data.Verlag;
import data.Verlagbuch;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Thomas Papke
 */
@Stateless
@LocalBean
public class MySessionBean {

    @PersistenceContext(unitName = "Bibliothek-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public List<Buchauthor> getAlleBuecher() {
        return em.createNamedQuery("Buchauthor.findAll").getResultList();
    }

    public List<Kunde> getAlleKunden() {
        return em.createNamedQuery("Kunde.findAll").getResultList();
    }

    public Kunde getKundeById(Integer kundeId) {
        Query q = em.createNamedQuery("Kunde.findByKundeId");
        q.setParameter("kundeId", kundeId);
        return (Kunde) q.getSingleResult();
    }

    public List<Exemplar> getAlleExemplare() {
        return em.createNamedQuery("Exemplar.findAll").getResultList();
    }

    public Exemplar getExemplarById(Integer exemplarId) {
        Query q = em.createNamedQuery("Exemplar.findByExemplarId");
        q.setParameter("exemplarId", exemplarId);
        return (Exemplar) q.getSingleResult();
    }

    public List<Leih> getAlleLeih() {
        return em.createNamedQuery("Leih.findAll").getResultList();
    }

    public Leih getLeihById(Integer leihId) {
        Query q = em.createNamedQuery("Leih.findByLeihId");
        q.setParameter("leihId", leihId);
        return (Leih) q.getSingleResult();
    }

    public List<Leih> getLeihByKundeId(Integer kundeId) {
        Query q = em.createNamedQuery("Leih.findAllByKundeId");
        q.setParameter("kundeId", kundeId);
        return q.getResultList();
    }

    public List<Buchauthor> getBuchauthorById(Integer buchId) {
        Query q = em.createNamedQuery("Buchauthor.findByBuchId");
        q.setParameter("buchId", buchId);
        return q.getResultList();
    }

    public Author getAuthorById(Integer authorId) {
        Author temp = new Author();
        temp = (Author) em.createNamedQuery("Author.findByAuthorId").setParameter("authorId", authorId).getResultList().get(0);
        return temp;
    }

    public Leih setLeihe(Integer kundeId, Integer exemplarId) {
        Date d = new Date();

        System.out.println("in session: kid= " + kundeId + "    exid= " + exemplarId);
        Leih newLeih = new Leih();

        Kunde k = getKundeById(kundeId);
        Exemplar ex = getExemplarById(exemplarId);

        newLeih.setKundeId(k);
        newLeih.setExemplarId(ex);

        newLeih.setLeihDatum(d);
        newLeih.setLeihRueckgabedatum(null);

        try {
            em.persist(newLeih);
            return newLeih;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Leih> getLeihscheinById() {
        return em.createNamedQuery("Leih.findByLeihId").getResultList();
    }

    /*
     Mustermethode für Abfrage mit Joins
     diese Abfragen enthalten ein "Objecttyp-Gemisch"(Author,buch,buchautor)
     -> deshalb ein Arryay vom Typ Objekt für die Atrribute aus den "alten" Klassen
    
     Klasse Exemplar
     Wenn einer Buch ausleiht hat er buch mit exemplarnummer
     wenn wir in die Datenbank gucken
     wollen wir eine Methode für den Leser
     Methode: getLeihschein(Integer leihId) von Leihe ist Zugriffsschlüssel
     Sie haben folgendes Buch ausgeliehen: 
     Autor, Vorname, Nachname, 
     Buchtitel, 
     Verlag, Isbn, 
     Exemplarnummer, 
     Leihdatum
    
     Methode: setLeihe()
     Dazu wird zuerst eine Methode benötigt, die eine neue Leihe anlegt!!!
    
    
    
     */
    public List<Object[]> getBuchliste() {
        List buchListe = new ArrayList();
        List<Buchauthor> alleBuchListe = getAlleBuecher();
        Object[] h = new Object[4];
        for (Iterator it = alleBuchListe.iterator(); it.hasNext();) {
            Buchauthor next = (Buchauthor) it.next();
            h[0] = next.getAuthorId().getAuthorFname();
            h[1] = next.getAuthorId().getAuthorLname();
            h[2] = next.getBuchId().getBuchTitel();
            h[3] = next.getAuthorId().getAuthorGeb();
            buchListe.add(h);
        }
        return buchListe;
    }
    /*
     Liste aller verfügbaren Bücher
     */

    public Map<String, Integer> getKatalog() {

//        Date nichtAbgegebenDate = new Date();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String leer = "0000-00-00";
//        try {
//            nichtAbgegebenDate = df.parse(leer);
//        } catch (ParseException ex) {
//            Logger.getLogger(MySessionBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
        /*
         Dieses Datum ist kein Sql-Datum, sonder ein util-Date
         WORKAROUND is zeroDateTimeBehavior=convertToNull
         */
        Query q = em.createNamedQuery("Leih.findByLeihRueckgabedatumIsNULL");
        /* without Parameter        
         q.setParameter("leihRueckgabedatum", nichtAbgegebenDate);
         */
        List<Leih> alleLeih = q.getResultList();
        System.out.println("Anzahl aller jemals verliehenden Exemplare, die nicht zurückgegeben sind:  "
                + alleLeih.size());
        /*
         The List<Exemplar> alleExem enthält zunächst alle Exemplare der Bibliothek 
         */
        List<Exemplar> alleExem = em.createNamedQuery("Exemplar.findAll").getResultList();
        System.out.println("Anzahl aller Exemplare der Bibliothek:  "
                + alleExem.size());
        /*
         iterate through  List<Leih> alleLeih 
         =  Liste aller verliehenden Exemplare, d.h exemplarId,
         and remove this exemplar from the   List<Exemplar> 
         */
        for (Leih leih : alleLeih) {
            if (alleExem.contains(leih.getExemplarId())) {
                alleExem.remove(leih.getExemplarId());
            }
        }
        System.out.println("Anzahl allerverfügbaren  Exemplare der Bibliothek:  "
                + alleExem.size());
        /*
         iterate through  List<Exemplar> alleExem (reduced by ausgeliehen)
         =  Liste aller noch verfügbaren Exemplare, d.h exemplarIds,
         and take the BuchTitel to show as Key in @return Map<String, Integer> 
         and ExemplarID as Value, there will be only one Key for different Exemplars
         should not return a book title with every exemplar borrowed - works
         */
        Map<String, Integer> verfuegbarBucher = new HashMap<>();
        for (Exemplar exemplar : alleExem) {
            verfuegbarBucher.put(exemplar.getVerlagbuchId().getBuchId().getBuchTitel(), exemplar.getExemplarId());
        }

        return verfuegbarBucher;
        //return em.createNamedQuery("Exemplar.findAll").getResultList();
    }

    public Author setNewAuthor(String fname, String lname, Date geb) {
        // leeren Author anlegen
        Author a = new Author();
        a.setAuthorFname(fname);
        a.setAuthorGeb(geb);
        a.setAuthorLname(lname);
        Query q = em.createNamedQuery("Author.findByAuthorLname");
        q.setParameter("authorLname", lname);
        if (q.getResultList().size() > 0) {
            a = (Author) q.getResultList().get(0);
            return a;
        } else {
            try {
//                em.getTransaction().begin();
                em.persist(a);
//                em.getTransaction().commit();
                return a; // Object enthält die auto-inkrementierte authorId
            } catch (Exception e) {
//                em.getTransaction().rollback();
                return null;
            }
        }
    }

    public Verlag setNewVerlag(String vname) {
        // leeren Verlag anlegen
        Verlag v = new Verlag();
        v.setVerlagName(vname);
        Query q = em.createNamedQuery("Verlag.findByVerlagName");
        q.setParameter("verlagName", vname);
        if (q.getResultList().size() > 0) {
            v = (Verlag) q.getResultList().get(0);
            return v;
        } else {
            try {
//                em.getTransaction().begin();
                em.persist(v);
//                em.getTransaction().commit();
                return v; // Object enthält die auto-inkrementierte authorId
            } catch (Exception e) {
//                em.getTransaction().rollback();
                return null;
            }
        }
    }

    public Buch setNewBuch(String btitel) {
        // leeres Buch anlegen
        Buch b = new Buch();
        b.setBuchTitel(btitel);
        Query q = em.createNamedQuery("Buch.findByBuchTitel");
        q.setParameter("buchTitel", btitel);
        if (q.getResultList().size() > 0) {
            b = (Buch) q.getResultList().get(0);
            return b;
        } else {
            try {
//                em.getTransaction().begin();
                em.persist(b);
//                em.getTransaction().commit();
                return b; // Object enthält die auto-inkrementierte authorId
            } catch (Exception e) {
//                em.getTransaction().rollback();
                return null;
            }
        }
    }

    public Verlagbuch setNewVerlagBuch(Verlag v, Buch b, String vbISBN) {
        // leeres Buch anlegen
        Verlagbuch vb = new Verlagbuch();
        vb.setVerlagId(v);
        vb.setBuchId(b);
        vb.setVerlagbuchIsbn(vbISBN);
        Query q = em.createNamedQuery("Verlagbuch.findByVerlagbuchIsbn");
        q.setParameter("verlagbuchIsbn", vbISBN);
        if (q.getResultList().size() > 0) {
            vb = (Verlagbuch) q.getResultList().get(0);
            return vb;
        } else {
            try {
//                em.getTransaction().begin();
                em.persist(vb);
//                em.getTransaction().commit();
                return vb; // Object enthält die auto-inkrementierte authorId
            } catch (Exception e) {
//                em.getTransaction().rollback();
                return null;
            }
        }
    }

    public Buchauthor setNewBuchAuthor(Author a, Buch b) {
        // leeren Author anlegen
        Buchauthor ba = new Buchauthor();
        ba.setAuthorId(a);
        ba.setBuchId(b);
        try {
//            em.getTransaction().begin();
            em.persist(ba);
//            em.getTransaction().commit();
            return ba; // Object enthält die auto-inkrementierte authorId
        } catch (Exception e) {
//            em.getTransaction().rollback();
            return null;
        }
    }

//    public Buch sucheBuchNachTitel(String suche){
//        em.createNamedQuery("Buch.findByBuchTitel").setParameter("buchTitel", suche);
//    }
    public List<Author> getAllAuthors() {
        return em.createNamedQuery("Author.findAll").getResultList();
    }
//     public List<Author> alleAutoren() {
//        return em.createNamedQuery("Author.findAll").getResultList();
//    }

    public Exemplar setNewExemplar(Verlagbuch verlagBuch, String exampleNr) {
        // leeres Exemplar anlegen
        Exemplar e = new Exemplar();
        e.setExemplarNr(exampleNr);
        e.setVerlagbuchId(verlagBuch);

        Query q = em.createNamedQuery("Exemplar.findByExemplarNr");
        q.setParameter("exemplarNr", exampleNr);
        if (q.getResultList().size() > 0) {
            e = (Exemplar) q.getResultList().get(0);
            return e;
        } else {
            try {
//                em.getTransaction().begin();
                em.persist(e);
//                em.getTransaction().commit();
                return e; // Object enthält die auto-inkrementierte authorId
            } catch (Exception ex) {
//                em.getTransaction().rollback();
                return null;
            }
        }
    }
}
