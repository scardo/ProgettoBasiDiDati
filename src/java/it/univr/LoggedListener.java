package it.univr;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.application.ConfigurableNavigationHandler;
import java.io.IOException;
import javax.faces.bean.SessionScoped;

import javax.faces.bean.ManagedBean;


@ManagedBean( name = "logge" )
@SessionScoped
public class LoggedListener {
   private FacesContext fc;
   private ConfigurableNavigationHandler nav;
   private boolean logged;
   private String page;

   public LoggedListener(){
   	fc = FacesContext.getCurrentInstance();
    nav = (ConfigurableNavigationHandler)fc.getApplication().getNavigationHandler();
    logged=false;
    page=null;
    }

    public void setLogged(boolean log){
    	this.logged=log;
    } 

    public void setPage(String p){
    	this.page=p;
    }
    
    public void controlloLog(String page, boolean logged) throws IOException {
        if(logged){
        nav.performNavigation(page);//add your URL here, instead of list.do
    	}else{
    		nav.performNavigation("/login.jsf");
    	}
    }
}