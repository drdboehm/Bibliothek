/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mappe;

import data.Buchauthor;
import data.Exemplar;
import data.Kunde;
import data.Leih;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Thomas Papke
 */
@ManagedBean
@RequestScoped
@Named(value = "leihBean")
public class LeihBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /* 
     the Kunden - Map for the  <h:selectOneMenu ... 
     */
    private final Map<String, Integer> alleKundenMap = new HashMap();
    /* 
     the ExemplarMap - Map for the  <h:selectOneMenu ... 
     */
    private final Map<String, Integer> alleExemplarMap = new HashMap();
    /* 
     the alleVerfuegbarBuecher - Map for the  <h:selectOneMenu ... 
     */
    private Map<String, Integer> alleVerfuegbarBuecher = new HashMap<>();

    /*
     what is it ?
     */
    List<Object[]> anzeigeList = new ArrayList<>();
    /* 
     primary keyIds from r elevant Entities
     */
    private Integer kundeId;
    private Integer exemplarId;
    private Integer leihId;
  
    /*
     injects the session bean
     */
    @EJB
    MySessionBean ms;

    /**
     * Creates a new instance of LeihBean
     */
    public LeihBean() {
    }

    /**
     * create and returns a Map with concatenated String of all Kunde -Objects
     * to show in the <h:selectOneMenu ...>
     * and the kundeId as key
     *
     * @return
     */
    public Map<String, Integer> getAlleKundenMap() {
        List<Kunde> tempList = new ArrayList();
        tempList = ms.getAlleKunden();
        for (Kunde next : tempList) {
            this.alleKundenMap.put(next.getKundeName() + ", "
                    + next.getKundeFname() + ", " + next.getKundeOrt() + ", "
                    + next.getKundePlz() + ", " + next.getKundeStr(), next.getKundeId());
        }
        return alleKundenMap;
    }

    /**
     * create and returns a Map with getExemplar as String of all Exemplare
     * -Objects to show in the <h:selectOneMenu ...>
     * and the exemplarID as key
     *
     * @return
     */
    public Map<String, Integer> getAlleExemplareMap() {
        for (Exemplar next : ms.getAlleExemplare()) {
            this.alleExemplarMap.put(next.getExemplarNr(), next.getExemplarId());
        }
        return alleExemplarMap;
    }

    /**
     * create and returns a Map with getExemplar as String of all Exemplare
     * -Objects to show in the <h:selectOneMenu ...>
     * and the exemplarID as key
     *
     * @return
     */
    public Map<String, Integer> getAlleVerfuegbarBuecher() {
        return ms.getKatalog();
    }

    public void setAlleVerfuegbarBuecher(Map<String, Integer> alleVerfuegbarBuecher) {
        this.alleVerfuegbarBuecher = alleVerfuegbarBuecher;
    }

    public String confirmLeihe() {
        System.out.println("KundeId: " + kundeId + " ExemplarId: " + exemplarId);
        ms.setLeihe(kundeId, exemplarId);
        return "./index.xhtml";
    }

    public String zurLeihIDPage() {
        return "./leihID.xhtml";
    }

    public List<LeihTable> showLeihTable() {
        return createLeihtable(ms.getAlleLeih());
    }

    public List<LeihTable> getLeihschein() {
        List<Leih> leih = new ArrayList();
        leih.add(ms.getLeihById(kundeId));
        return createLeihtable(leih);
    }

    public List<LeihTable> getLeihscheinByKunde() {
//        List<Leih> leih = new ArrayList();
        System.out.println("!!!!!!!!!!!! KundeId = !!!!!!!!!!!!!!!!!!!!!" + kundeId);
        return createLeihtable(ms.getLeihByKundeId(kundeId));
    }

    private List<LeihTable> createLeihtable(List<Leih> leih) {
        List<LeihTable> leihTableList = new ArrayList();

        for (Leih l : leih) {
            LeihTable leihtable = new LeihTable();
            leihtable.leihId = l.getLeihId();
            leihtable.kundeFname = l.getKundeId().getKundeFname();
            leihtable.kundeNname = l.getKundeId().getKundeName();
            leihtable.exemplarnummer = l.getExemplarId().getExemplarNr();
            //obj[1] = next.getLeihDatum();
            leihtable.verlag = l.getExemplarId().getVerlagbuchId().getVerlagId().getVerlagName();
            leihtable.isbn = l.getExemplarId().getVerlagbuchId().getVerlagbuchIsbn();
            leihtable.buchtitel = l.getExemplarId().getVerlagbuchId().getBuchId().getBuchTitel();

            String h = "";
            for (int i = 0; i < l.getExemplarId().getVerlagbuchId().getBuchId().getBuchauthorCollection().size(); i++) {
                Buchauthor ba = (Buchauthor) (l.getExemplarId().getVerlagbuchId().getBuchId().getBuchauthorCollection().toArray()[i]);
                if (i > 0) {
                    h += " / ";
                }
                h += ba.getAuthorId().getAuthorFname() + " " + ba.getAuthorId().getAuthorLname();
            }
            leihtable.author = h;

            leihTableList.add(leihtable);
        }
        return leihTableList;
    }

    public Integer getKundeId() {
        return kundeId;
    }

    public void setKundeId(Integer kundeId) {
        this.kundeId = kundeId;
    }

    public Integer getExemplarId() {
        return exemplarId;
    }

    public void setExemplarId(Integer exemplarId) {
        this.exemplarId = exemplarId;
    }

    public List<Object[]> getAnzeigeList() {
        return anzeigeList;
    }

    public void setAnzeigeList(List<Object[]> anzeigeList) {
        this.anzeigeList = anzeigeList;
    }

    public Integer getLeihId() {
        return leihId;
    }

    public void setLeihId(Integer leihId) {
        this.leihId = leihId;
    }

    public class LeihTable implements Serializable {

        private Integer leihId;
        private String kundeFname;
        private String kundeNname;
        private String buchtitel;
        private String verlag;
        private String isbn;
        private String exemplarnummer;
        private String author;

        public LeihTable() {
        }

        public LeihTable(Integer leihId) {
            this.leihId = leihId;
        }

        public Integer getLeihId() {
            return leihId;
        }

        public void setLeihId(Integer leihId) {
            this.leihId = leihId;
        }

        public String getKundeFname() {
            return kundeFname;
        }

        public void setKundeFname(String kundeFname) {
            this.kundeFname = kundeFname;
        }

        public String getKundeNname() {
            return kundeNname;
        }

        public void setKundeNname(String kundeNname) {
            this.kundeNname = kundeNname;
        }

        public String getBuchtitel() {
            return buchtitel;
        }

        public void setBuchtitel(String buchtitel) {
            this.buchtitel = buchtitel;
        }

        public String getVerlag() {
            return verlag;
        }

        public void setVerlag(String verlag) {
            this.verlag = verlag;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getExemplarnummer() {
            return exemplarnummer;
        }

        public void setExemplarnummer(String exemplarnummer) {
            this.exemplarnummer = exemplarnummer;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

    }

}
/*
 Kunde wählt aus buchliste aus (per select)
 im Katolog stehen nur die verfügbaren Bücher
 Kunde wählt nur den Titel -> Exemplar wird automatisch generiert
 wo ist die Lösung (Warenkorb)
 1. Lösung:  Generieren einer Liste aller verfügbaren Bücher
 mit dem ersten verfügbaren Exemplar
 2. Lösung:  Liste aller verfügbaren Bücher mit Dropdown-verfügbarer Exemplare




 */
