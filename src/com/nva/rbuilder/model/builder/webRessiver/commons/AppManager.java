package com.nva.rbuilder.model.builder.webRessiver.commons;

import com.nva.rbuilder.model.builder.core.ReportThreadData;
import org.openqa.selenium.WebDriver;

public class AppManager {

    private WebDriver driver;
    private ReportThreadData reportThreadData;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public ReportThreadData getReportThreadData() {
        return reportThreadData;
    }

    public void setReportThreadData(ReportThreadData reportThreadData) {
        this.reportThreadData = reportThreadData;
    }

}
