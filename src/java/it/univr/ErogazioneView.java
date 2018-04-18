package it.univr;

import it.univr.database.Erogazione;
import it.univr.database.Docente;
import it.univr.database.DataSource;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;


@ManagedBean(name = "er")
@SessionScoped
public class ErogazioneView implements Serializable {

    private DataSource ds;
    private List<Erogazione> listaErogazione;
    private Erogazione erogazioneSelezionato;
    private Docente doc;
    private List<Docente> listaDoc;

    public ErogazioneView() {
        this.ds = null;
        this.listaErogazione = null;
        this.erogazioneSelezionato = null;
        this.doc=null;
        this.listaDoc=null;
    }

    @PostConstruct
    public void initialize() {
        try {
            this.ds = new DataSource();
        } catch (ClassNotFoundException e) {
            this.ds = null;
        }
    }

    public List<Erogazione> getErogazioni(String nome) {
        if (this.ds != null) {
            listaErogazione = ds.getListaErogazioni(nome);

        }
        return listaErogazione;

    }

    public String recuperaErogazione(String codice) {
        if (this.ds != null) {
            erogazioneSelezionato = ds.getErogazione(codice);
        } else {
            if (listaErogazione != null && !listaErogazione.isEmpty()) {

                for (Erogazione erogazione : listaErogazione) {
                    if (erogazione.getCodice().equalsIgnoreCase(codice)) {
                        erogazioneSelezionato = erogazione;
                    }
                }
            }
        }
        return "/Template/dettaglioErogazione";
    }

    public List<Docente> getDocenti(String codice) {
        List<Docente> listaDoc=null;
        if (this.ds != null) {
            listaDoc = ds.getListaDocente(codice);

        }
        return listaDoc;

    }    

    public List<Erogazione> getListaErogazione() {
        return listaErogazione;
    }

    public void setListaErogazione(List<Erogazione> listaErogazione) {
        this.listaErogazione = listaErogazione;
    }

    public Erogazione getErogazioneSelezionato() {
        return erogazioneSelezionato;
    }

    public void setErogazioneSelezionato(Erogazione erogazioneSelezionato) {
        this.erogazioneSelezionato = erogazioneSelezionato;
    }

}