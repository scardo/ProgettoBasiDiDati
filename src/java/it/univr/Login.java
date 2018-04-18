package it.univr;


import it.univr.database.DataSource;
 
import java.io.Serializable;
 
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Collections;



 
@ManagedBean( name = "login" )
@SessionScoped
public class Login implements Serializable {
 
    private static final long serialVersionUID = 1094801825228386363L;
     
    private String user;
    private String pwd;
    private boolean loggedIN;
    private DataSource ds;
    private String page;

    public Login(){
        this.user=null;
        this.pwd = null;
        this.ds=null;
        this.page=null;
        this.loggedIN=false;
    }


    @PostConstruct
    public void initialize() {
        try {
            this.ds = new DataSource();
        } catch (ClassNotFoundException e) {
            this.ds = null;
        }
    }
 	
    public String getPwd() {
        return pwd;
    }
 
    public void setPwd(String pwd) {
        this.pwd=pwd;
    }
 
    public boolean isLoggedIn(){
        return this.loggedIN;
    }
 
    public String getUser() {
        return this.user;
    }
 
    public void setUser(String user) {
        this.user=user;
    }
    
    public void setPage(String url){
        this.page=url;
    }

    //validate login
    public String validateUsernamePassword() {
            if(this.ds != null){
        	    boolean valid = this.ds.doLogin(user, pwd);
              
        	    if (valid) {          
                    loggedIN=true;
                    return "/"+this.page+".jsf";
        	    }else{ 
                 
                    loggedIN=false;
                    return "/login.jsf";
        	    }
    	    }
            loggedIN=false;
   		    return "/login.jsf";
    }

    public String getStringa(){

        if (this.loggedIN){
            return "logout";
        }
        return "";
    }


    public String logout(){
        this.user=null;
        this.pwd=null;
        this.loggedIN=false;

        return "/index.jsf";
    }
 
    //logout event, invalidate session

}