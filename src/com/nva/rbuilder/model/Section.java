package com.nva.rbuilder.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;

@XmlType(propOrder = {"name", "label", "units"})
public class Section {

    private String name;
    private String label;
    private LinkedList<Unit> units = new LinkedList<>();

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void addUnit(String name, String label, String percent){
        units.add(new Unit(name, label, percent));
    }

    public void setUnits(LinkedList<Unit> units) {
        this.units = units;
    }

    @XmlElement(name = "unit")
    public LinkedList<Unit> getUnits() {
        return units;
    }

    public Section(){
        this("EmptyName", "");
    }

    public Section(String name, String label){
        setName(name); setLabel(label);
    }

}

