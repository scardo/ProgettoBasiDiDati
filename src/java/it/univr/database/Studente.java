package it.univr.database;

public class Studente{

	// === Properties ============================================================

	private String codice;
	private String nome;
	private String cognome;
	private String cittaRes;
	private String email;

	// === Methods ===============================================================
	
	public Studente(){
		codice=null;
		nome=null;
		cognome=null;
		cittaRes=null;
		email=null;
	}

	public Studente(String codice,String nome,String cognome,String citta,String email){
		this.codice=codice;
		this.nome=nome;
		this.cognome=cognome;
		this.cittaRes=citta;
		this.email=email;
	}

	public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCittaRes() {
        return cittaRes;
    }

    public void setCittaRes(String cittaRes) {
        this.cittaRes = cittaRes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}