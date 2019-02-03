package com.nva.rbuilder.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ItemOfTree {

    private StringProperty nameSection = new SimpleStringProperty();
    private StringProperty labelSection = new SimpleStringProperty();
    private StringProperty nameUnit = new SimpleStringProperty();
    private StringProperty labelUnit = new SimpleStringProperty();
    private StringProperty percent = new SimpleStringProperty();

    public ItemOfTree(String nameSection){
        setNameSection(nameSection);
        setLabelSection("");
        setNameUnit("");
        setLabelUnit("");
        setPercent("");
    }

    public ItemOfTree(String nameSection, String labelSection){
        setNameSection(nameSection);
        setLabelSection(labelSection);
        setNameUnit("");
        setLabelUnit("");
        setPercent("");
    }

    public ItemOfTree(String nameUnit, String labelUnit, String percent){
        setNameSection("");
        setLabelSection("");
        setNameUnit(nameUnit);
        setLabelUnit(labelUnit);
        setPercent(percent);
    }


    public void setNameSection(String nameSection) {
        this.nameSection.set(nameSection);
    }
    public String getNameSection() {
        return nameSection.get();
    }
    public StringProperty nameSectionProperty() {
        return nameSection;
    }


    public void setLabelSection(String labelSection) {
        this.labelSection.set(labelSection);
    }
    public String getLabelSection() {
        return labelSection.get();
    }
    public StringProperty labelSectionProperty() {
        return labelSection;
    }


    public void setNameUnit(String nameUnit) {
        this.nameUnit.set(nameUnit);
    }
    public String getNameUnit() {
        return nameUnit.get();
    }
    public StringProperty nameUnitProperty() {
        return nameUnit;
    }


    public void setLabelUnit(String labelUnit) {
        this.labelUnit.set(labelUnit);
    }
    public String getLabelUnit() {
        return labelUnit.get();
    }
    public StringProperty labelUnitProperty() {
        return labelUnit;
    }


    public void setPercent(String percent) {
        this.percent.set(percent);
    }
    public String getPercent() {
        return percent.get();
    }
    public StringProperty percentProperty() {
        return percent;
    }

}
