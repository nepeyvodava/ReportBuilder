package com.nva.rbuilder.utils;

import com.nva.rbuilder.model.ItemOfTree;
import com.nva.rbuilder.model.ReportConfig;
import com.nva.rbuilder.model.Section;
import com.nva.rbuilder.model.Unit;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.LinkedList;

public class TreeTableUtil {

    public static TreeItem<ItemOfTree> getRoot(ReportConfig config){
        TreeItem<ItemOfTree> root = new TreeItem<>(new ItemOfTree("Sections"));

        for(Section section: config.getReport().getSections()){
            ItemOfTree sectionItem = new ItemOfTree(section.getName(), section.getLabel());
            TreeItem<ItemOfTree> sectionTree = new TreeItem<>(sectionItem);

            for(Unit unit: section.getUnits()){
                ItemOfTree unitItem = new ItemOfTree(unit.getName(), unit.getLabel(), unit.getPercent());
                sectionTree.getChildren().add(new TreeItem<>(unitItem));
            }

            root.getChildren().add(sectionTree);
        }

        return root;
    }

    public static void saveChangesToConfig(TreeItem<ItemOfTree> root, ReportConfig config){
        ObservableList<TreeItem<ItemOfTree>> sectionsTree = root.getChildren();
        LinkedList<Section> sections = new LinkedList<>();

        for (TreeItem<ItemOfTree> sectionItem: sectionsTree){
            ObservableList<TreeItem<ItemOfTree>> unitsTree = sectionItem.getChildren();
            LinkedList<Unit> units = new LinkedList<>();

            for (TreeItem<ItemOfTree> unitItem: unitsTree){
                String name = unitItem.getValue().getNameUnit();
                String label = unitItem.getValue().getLabelUnit();
                String percent = unitItem.getValue().getPercent();
                Unit unit = new Unit(name, label, percent);
                units.add(unit);
            }

            String name = sectionItem.getValue().getNameSection();
            String label = sectionItem.getValue().getLabelSection();
            Section section = new Section(name, label);
            section.setUnits(units);
            sections.add(section);
        }

        config.getReport().setSections(sections);
    }

}
