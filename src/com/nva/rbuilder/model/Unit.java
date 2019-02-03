package com.nva.rbuilder.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(propOrder = {"name" , "label", "percent"})
public class Unit {

    private String name;
    private String label;
    private String percent;

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;    }

    @XmlAttribute
    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @XmlValue
    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getPercent() {
        return percent;
    }

    public Unit(){
        this("Empty Name","","0");
    }

    public Unit(String name, String label, String percent){
        setName(name);
        setLabel(label);
        setPercent(percent);
    }

}
