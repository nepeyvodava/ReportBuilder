package com.nva.rbuilder.model.builder.webRessiver.pages;

import com.nva.rbuilder.utils.Assets;
import com.nva.rbuilder.model.builder.webRessiver.commons.AppManager;
import com.nva.rbuilder.model.builder.webRessiver.commons.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BugPage extends PageBase {

    public BugPage(AppManager app) {
        super(app);
    }

    @FindBy(id = "assignee-val")
    private WebElement assigneeUser;
    @FindBy(id = "reporter-val")
    private  WebElement reporterUser;
    @FindBy(id = "type-val")
    private  WebElement typeIssue;
    @FindBy(xpath = "//span[contains(@id,'labels-')]")
    private WebElement labelNoneValue;
    @FindBy(xpath = "//ul[contains(@id,'labels-')]/li/a/span")
    private WebElement labelValue;
    @FindBy(id = "description-val")
    private WebElement descriptionBlock;
    @FindBy(id = "aui-uid-1")
    private WebElement textButton;

    private String bugCode;

    public BugPage openBugPage(String issueCode){
        this.bugCode = issueCode;
        try {
            String url = this.app.getReportThreadData().getIssueURL(bugCode);
            driver.get(url);
        }catch (Exception ex){
            Assets.logPrint(bugCode + " - not available");
        }

        return this;
    }

    public String getLabel() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@id,'labels-')]/li/a/span")));
            return labelValue.getText();
        }catch (TimeoutException ex){
            return getNoneLabel();
        }
    }

    private String getNoneLabel() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@id,'labels-')]")));
            return labelNoneValue.getText();
        }catch (TimeoutException ex){
            Assets.println("In Issue " + bugCode + " Label - NOT FOUND!", this.app.getReportThreadData());
            return "EmptyLabel";
        }

    }

    public String getAssigneeUser(){

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("assignee-val")));
            return assigneeUser.getText();
        }catch (TimeoutException ex){
            Assets.println("In Issue " + bugCode + " AssigneeUser - NOT FOUND!", this.app.getReportThreadData());
            return "EmptyAssigneeUser";
        }

    }

    public String getReporterUser(){

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reporter-val")));
            return reporterUser.getText();
        }catch (TimeoutException ex){
            Assets.println("In Issue " + bugCode + " ReporterUser - NOT FOUND!", this.app.getReportThreadData());
            return "EmptyReporterUser";
        }

    }

    public String getTypeIssue(){

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("type-val")));
            return typeIssue.getText();
        }catch (TimeoutException ex){
            Assets.println("In Issue " + bugCode + " Type - NOT FOUND!", this.app.getReportThreadData());
            return " ";
        }

    }

    public String getDescription(){
        String result;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-val")));
//            descriptionBlock.findElement(By.xpath("./span")).click();
            driver.findElement(By.id("description-val")).click();
        }catch (Exception ex){}
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("aui-uid-1")));
//            descriptionBlock.findElement(By.xpath("./form/div/div/div/div/div/ul/li/a[contains(text(),'Text')]")).click();
            textButton.click();
        }catch (Exception e){}
        try {
            result = descriptionBlock.findElement(By.xpath("./form/div/div/div/div/div/textarea")).getText();
        }catch (Exception ex){Assets.println("Description not found!", this.app.getReportThreadData()); return "";}
        return "\n" + result;
    }

}
