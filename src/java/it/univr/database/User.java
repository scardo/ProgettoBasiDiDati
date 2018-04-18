package it.univr.database;

import it.univr.database.DataSource;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;


public class User {
	
	private String name;
	private String password;
	private boolean loggedIn;



	public User(){
		this.name="";
		this.password="";
		this.loggedIn=false;
	}

	public User(String name, String password){
		this.name=name;
		this.password=password;
		this.loggedIn=false;
	}

	

	public void setName(String newValue) { 
		this.name = newValue; 
	}
	
	public boolean getLoggedIn(){
		return this.loggedIn;
	}

	
	public void setLoggedIn(boolean logged){
		this.loggedIn=logged;
	}

	public String getName(){
		return this.name;
	}

	public void setPassword(String newValue) { 
		this.password = newValue;
	}

	public String getPassword(){
		return this.password;
	}

}