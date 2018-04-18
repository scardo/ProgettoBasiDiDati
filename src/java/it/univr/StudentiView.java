package it.univr;

import it.univr.database.DataSource;
import it.univr.database.Studente;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;


@ManagedBean(name = "st")
@SessionScoped
public class StudentiView implements Serializable {

	private DataSource ds;
	private List<Studente> studenti;

	public StudentiView() {
        this.ds = null;
        this.studenti = null;
    }

    @PostConstruct
    public void initialize() {
        try {
            this.ds = new DataSource();
        } catch (ClassNotFoundException e) {
            this.ds = null;
        }
    }

    public List<Studente> getStudenti(String codice) {
        if (this.ds != null) {
            studenti = ds.getListaStudenti(codice);

        }
        return studenti;
    }

}