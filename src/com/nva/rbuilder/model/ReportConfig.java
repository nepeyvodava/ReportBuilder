package com.nva.rbuilder.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"settings", "report"})
@XmlRootElement(name = "root")
public class ReportConfig {

    private Settings settings = new Settings();
    private Report report = new Report();
    private StringProperty configName = new SimpleStringProperty();
    private DoubleProperty progress = new SimpleDoubleProperty();
    private StringProperty progressLabel = new SimpleStringProperty();

    public StringProperty configName() {
        return configName;
    }

    public void setName(String name){
        this.configName.set(name);
    }

    public DoubleProperty progress(){return progress;}

    public void setProgress(double progress) {
        if(progress > 1) progress = 1;
        if(progress < 0) progress = 0;
        this.progress.setValue(progress);
    }

    public StringProperty progressLabel(){return progressLabel;}

    public void setProgressLabel(String progressLabel) {
        this.progressLabel.set(progressLabel);
    }

    @XmlElement(name = "settings")
    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @XmlElement(name = "report")
    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
