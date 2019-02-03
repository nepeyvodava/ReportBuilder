package com.nva.rbuilder.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.LinkedList;

public class Report {

    private String gameName;
    private LinkedList<Section> sections = new LinkedList<>();

    @XmlAttribute
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void addSection(String name, String label){
        sections.add(new Section(name, label));
    }

    public void setSections(LinkedList<Section> sections){
        this.sections = sections;
    }

    @XmlElement(name = "section")
    public LinkedList<Section> getSections() {
        return sections;
    }

    public Report(){
        this("Empty Name");
    }

    public Report(String gameName){
        setGameName(gameName);
    }

}

