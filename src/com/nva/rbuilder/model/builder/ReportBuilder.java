package com.nva.rbuilder.model.builder;

import com.nva.rbuilder.model.ReportConfig;
import com.nva.rbuilder.model.builder.core.ConfigLoader;
import com.nva.rbuilder.model.builder.core.ExcelBuilder;
import com.nva.rbuilder.model.builder.core.ReportThreadData;
import com.nva.rbuilder.model.builder.webRessiver.WebRessiver;
import com.nva.rbuilder.utils.Assets;

public class ReportBuilder {

    private ReportConfig config;
    private ConfigLoader configLoader;
    private WebRessiver webRessiver;
    private ReportThreadData reportThreadData;
    private String[] info;

    public ReportBuilder(ReportConfig config) {
        this.config = config;
        reportThreadData = new ReportThreadData();
        reportThreadData.setConfig(config);
        configLoader = new ConfigLoader(config, reportThreadData);
        configLoader.load();
    }

    public void build(){
        Assets.initializeLog();
        reportThreadData.setProgress(2.5);
        webRessiver = new WebRessiver(reportThreadData);
        reportThreadData.setProgress(5);

        try {
            webRessiver.loginToServer().getBugs();
            ExcelBuilder excelBuilder = new ExcelBuilder(reportThreadData);
            excelBuilder.buildTable();
            webRessiver.closeDriver();
            reportThreadData.setProgress(100);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assets.logPrint(ex);
            webRessiver.closeDriver();
        }
    }

    public String[] getInfo(){
        info = new String[] {config.getReport().getGameName(), reportThreadData.getErrors()};
        return info;
    }

}
