package com.nva.rbuilder.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"issue", "conferenceMode", "dateEnd",
        "login", "pass", "jiraURL", "autoAuthorisationMode"})
public class Settings {

    private String issue;
    private boolean conferenceMode;
    private String dateEnd;
    private String login;
    private String pass;
    private String jiraURL;
    private boolean autoAuthorisationMode;

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getIssue() {
        return issue;
    }

    public void setConferenceMode(boolean conferenceMode) {
        this.conferenceMode = conferenceMode;
    }

    public boolean isConferenceMode() {
        return conferenceMode;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setJiraURL(String jiraURL) {
        this.jiraURL = jiraURL;
    }

    public String getJiraURL() {
        return jiraURL;
    }

    public void setAutoAuthorisationMode(boolean autoAuthorisationMode) {
        this.autoAuthorisationMode = autoAuthorisationMode;
    }

    public boolean isAutoAuthorisationMode() {
        return autoAuthorisationMode;
    }

    public Settings(){
        issue = "";
        conferenceMode = false;
        dateEnd = "";
        login = "";
        pass = "";
        jiraURL = "https://jira-srv.octavian.ru";
        autoAuthorisationMode = true;
    }

}
