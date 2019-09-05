package com.nva.rbuilder.model.builder.core;

import com.nva.rbuilder.model.ReportConfig;
import com.nva.rbuilder.model.Section;
import com.nva.rbuilder.model.Unit;
import com.nva.rbuilder.utils.Assets;

import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {

    private ReportConfig config;
    private ReportThreadData reportThreadData;

    public ConfigLoader(ReportConfig config, ReportThreadData reportThreadData){
        this.config = config;
        this.reportThreadData = reportThreadData;
    }

    public void load() {
//        getDOM(filePath);
        Assets.print("Config Loading...", reportThreadData);
        //get Settings
        reportThreadData.setLogin(config.getSettings().getLogin());
        reportThreadData.setPassword(config.getSettings().getPass());
        reportThreadData.setIssueCode(config.getSettings().getIssue());
        reportThreadData.setJiraURL(config.getSettings().getJiraURL());
        reportThreadData.setDateEnd(config.getSettings().getDateEnd().equals("") ?
                Assets.getNextFriday() : config.getSettings().getDateEnd());
        reportThreadData.setAutoAuthorisationMode(config.getSettings().isAutoAuthorisationMode());
        reportThreadData.setConferenceMode(config.getSettings().isConferenceMode());

        //get reports data
        reportThreadData.setGameName(config.getReport().getGameName());
        reportThreadData.setGameDir(Assets.getCurrentPath() + this.reportThreadData.getGameName() + "/");
        if(reportThreadData.isConferenceMode()) {Assets.println("Done!", reportThreadData); return;}
        for(Section section: config.getReport().getSections()){
            String sectionNameOrLabel = (section.getLabel().equals("") ? section.getName() : section.getLabel());
            List<String> unitListNames = new ArrayList<String>();
            for(Unit unit: section.getUnits()){
                List<String> unitListData = new ArrayList<String>();
                String unitNameOrLabel =(unit.getLabel().equals("") ? unit.getName() : unit.getLabel());
                //add unit name
                unitListNames.add(unitNameOrLabel);
                unitListData.add(unit.getName());
                //add percent
                unitListData.add(unit.getPercent());
                //add ticket value
                unitListData.add("");
                //add comments value
                unitListData.add("");
                //add assignee value
                unitListData.add("");
                //add DateEnd value
                unitListData.add(unit.getPercent().equals("100") ? "Complete" : reportThreadData.getDateEnd());
                //put unit's data
                reportThreadData.getUnitMap().put(sectionNameOrLabel + Assets.SPECIALSYMBOL + unitNameOrLabel, unitListData);
            }
            // put section's data
            reportThreadData.getSectionMap().put(section.getName() + Assets.SPECIALSYMBOL + sectionNameOrLabel, unitListNames);
        }
        Assets.println("Done!", reportThreadData);
    }

}
