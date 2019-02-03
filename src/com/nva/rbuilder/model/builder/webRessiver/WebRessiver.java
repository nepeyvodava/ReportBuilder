package com.nva.rbuilder.model.builder.webRessiver;

import com.nva.rbuilder.model.builder.core.ReportThreadData;
import com.nva.rbuilder.utils.Assets;
import com.nva.rbuilder.model.builder.webRessiver.commons.TestBase;
import com.nva.rbuilder.model.builder.webRessiver.pages.LoginPage;
import com.nva.rbuilder.model.builder.webRessiver.pages.ProjectPage;

public class WebRessiver extends TestBase {

    public WebRessiver(ReportThreadData reportThreadData) {
        this.setUp(reportThreadData);
    }

    public WebRessiver loginToServer() {
        try {
            LoginPage loginPage = new LoginPage(this.appManager);
            loginPage.login();
            return this;
        }catch (Exception ex){
            Assets.logPrint(ex);
            Assets.println("UNKNOWN ERROR: Login to JIRA filed!", this.appManager.getReportThreadData());
            this.appManager.getReportThreadData().addError("Login to JIRA filed!");
            this.closeDriver();
            return this;
        }
    }

    public void getBugs() {
        if(!this.appManager.getReportThreadData().isAuthorithationStatus())return;

        try {
            ProjectPage projectPage = new ProjectPage(this.appManager);
            projectPage.openProjectPage().getDataBugs();
        }catch (Exception ex){
            Assets.println("UNKNOWN ERROR: Get Bugs Data filed!", this.appManager.getReportThreadData());
            this.appManager.getReportThreadData().addError("Get Bugs Data filed!");
            this.closeDriver();
        }
    }

    public void closeDriver() {
        try {
            this.driver.getTitle();
            Assets.print("Closing phantom...", this.appManager.getReportThreadData());
            this.tearDown();
            Assets.println("Done!", this.appManager.getReportThreadData());
        }catch (Exception ex){
            Assets.println("Phantom already closed!", this.appManager.getReportThreadData());
        }
    }

}
