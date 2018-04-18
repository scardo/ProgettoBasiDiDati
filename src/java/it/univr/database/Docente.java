
package it.univr.database;

public class Docente {

	private String cF;
	private String nome;
	private String cognome;
	private String email;
	private String telefono;
	
	public Docente(){
		cF=null;
		nome=null;
		cognome=null;
		email=null;
		telefono=null;
	}

	public Docente(String cF, String nome, String cognome, String email,
			String telefono) {
		super();
		this.cF = cF;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.telefono = telefono;
	}

	public String getCf() {
		return cF;
	}

	public void setCf(String cF) {
		this.cF = cF;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}