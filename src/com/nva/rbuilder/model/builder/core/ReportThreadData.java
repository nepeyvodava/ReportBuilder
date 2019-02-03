package com.nva.rbuilder.model.builder.core;

import com.nva.rbuilder.model.ReportConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ReportThreadData {

    private HashMap<String,List<String>> sectionMap;
    private HashMap<String,List<String>> unitMap;
    private String errors;
    private String gameName;
    private String gameDir;
    private String login;
    private String password;
    private String jiraURL;
    private String issueCode;
    private String dateEnd;
    private String[] columName;
    private int bugQuantity;
    private int allBugQuantity;
    private boolean conferenceMode = false;
    private boolean autoAuthorisationMode = false;
    private boolean authorithationStatus = false;
    private ReportConfig config;

    public ReportThreadData(){
        sectionMap = new LinkedHashMap<String, List<String>>();
        unitMap = new LinkedHashMap<String, List<String>>();
        errors = new String("");
    }

    //Get's
    public synchronized HashMap<String,List<String>> getSectionMap(){return sectionMap;}
    public synchronized HashMap<String,List<String>> getUnitMap(){return unitMap;}

    public String getGameName(){return gameName;}
    public String getGameDir() {
        return gameDir;
    }
    public String getLogin(){return login;}
    public String getPassword(){return password;}
    public String getIssueCode(){return issueCode;}
    public String getIssueURL(String issueCode){
        return getJiraURL() + "/browse/" + issueCode;}
    public String getJiraURL(){return jiraURL;}
    public String getDateEnd(){return dateEnd;}
    public String getReportsName(){
        String result;
        Date date = new Date();
        SimpleDateFormat dateNow = new SimpleDateFormat("dd.MM.yy");
        SimpleDateFormat timeNow = new SimpleDateFormat("HHmmss");
        if(conferenceMode){result = getGameName() + "-" + dateNow.format(date) + "_RID";
//        }else{result = getGameName() + "-" + dateNow.format(date) + "(" + getLogin() + ")" + timeNow.format(date);}
        }else{result = getGameName() + "-" + dateNow.format(date) + "(" + getLogin() + ")";}
        if(!isAuthorithationStatus()){result+="Empty";}
        return result;
    }
    public String[] getColumnNameList(){
        if(conferenceMode){
            columName = new String[]{
                    gameName,
                    "Ticket",
                    "Comments",
                    "Assignee"};
        }else{
            columName = new String[]{
                    gameName,
                    "%",
                    "Ticket",
                    "Comments",
                    "Assignee",
                    "Date End"};
        }
        return columName;
    }

    public int getBugQuantity(){return bugQuantity;}
    public int getAllBugQuantity(){return allBugQuantity;}
    public boolean isConferenceMode(){return conferenceMode;}
    public boolean isAutoAuthorisationMode(){return autoAuthorisationMode;}
    public boolean isAuthorithationStatus(){return authorithationStatus;}
    public String getErrors() {
        return errors;
    }
    public double getProgress(){
       return config.progress().get()*100;
    }


    //Set's
    public void setGameName(String name){gameName = name;}

    public void setGameDir(String gameDir) {
        this.gameDir = gameDir;
    }

    public void setLogin(String lgn){login = lgn;}
    public void setPassword(String psw){password = psw;}
    public void setIssueCode(String iss){ issueCode = iss;}
    public void setJiraURL(String url){ jiraURL = url;}
    public void setDateEnd(String dEnd){ dateEnd = dEnd;}
    public void setBugQuantity(int quantity){bugQuantity = quantity;}
    public void setAllBugQuantity(int quantity){allBugQuantity = quantity;}
    public void setConferenceMode(boolean mode){conferenceMode = mode;}
    public void setAutoAuthorisationMode(boolean mode){autoAuthorisationMode = mode;}
    public void setAutorithationStatus(boolean status){ authorithationStatus = status;}

    public void addError(String error) {
        this.errors += error+"\n";
    }

    public void setConfig(ReportConfig config) {
        this.config = config;
    }
    public void setProgress(double percents){
        config.setProgress(percents/100);
    }
    public void setProgressLabel(String label){
        config.setProgressLabel(label);
    }

}
