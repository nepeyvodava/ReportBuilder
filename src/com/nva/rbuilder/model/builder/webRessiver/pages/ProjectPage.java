package com.nva.rbuilder.model.builder.webRessiver.pages;

import com.nva.rbuilder.utils.Assets;
import com.nva.rbuilder.model.builder.webRessiver.commons.AppManager;
import com.nva.rbuilder.model.builder.webRessiver.commons.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ProjectPage extends PageBase {

    public ProjectPage(AppManager app) {
        super(app);
    }
    @FindBy(id = "assignee-val")
    private WebElement assigneeUser;
    @FindBy(id = "linkingmodule")
    private WebElement linksBlock;
    @FindBy(id = "show-more-links-link")
    private WebElement showMoreLinksButton;

    private List<String> tempDBBugs = new ArrayList<String>();
    private String otherSectionName;

    public ProjectPage openProjectPage(){
        if(this.app.getReportThreadData().isConferenceMode()){
            otherSectionName = "Issues";
        }else {otherSectionName = "Others Issues";}
        driver.get(this.app.getReportThreadData().getIssueURL(this.app.getReportThreadData().getIssueCode()));
        return this;
    }

    public void getDataBugs() {
        List<String> unitListNames = new ArrayList<String>();
        int otherIssueNum = 0;
        Assets.print("Get Bugs Data...", this.app.getReportThreadData());
        getBugsLinks();
        double progressPersent;
        try {
            progressPersent = (90 / this.app.getReportThreadData().getBugQuantity());
        }catch (Exception e){
            progressPersent = 0;
        }
        int bugcount = 0;
        for(String bugTitle : tempDBBugs){
            BugPage bugPage = new BugPage(app);
            Assets.printrep("Get Bugs Data..." + "[" + ++bugcount + "/" + this.app.getReportThreadData().getBugQuantity() + "]", this.app.getReportThreadData());
            String bugCode = bugTitle.split(":", 2)[0];
            String bugName = bugTitle.split(":", 2)[1];
            //open page and get label and username
            bugPage.openBugPage(bugCode);
            String assigneeUser = bugPage.getAssigneeUser();
            String label = bugPage.getLabel();
            String oldLabel = label;
            //progress add
            this.app.getReportThreadData().setProgress(this.app.getReportThreadData().getProgress() + progressPersent);
            try {
                label = Assets.cutBugLabel(label, 0)
                        + Assets.SPECIALSYMBOL + Assets.cutBugLabel(label, 1);
            }catch (Exception ex){}
             if(this.app.getReportThreadData().getUnitMap().keySet().contains(label)){
                 //add ticket
                 if(this.app.getReportThreadData().getUnitMap().get(label).get(2).equals("")){
                     this.app.getReportThreadData().getUnitMap().get(label).set(2, this.app.getReportThreadData().getUnitMap().get(label).get(2) + bugCode);
                 }else {
                     this.app.getReportThreadData().getUnitMap().get(label).set(2, this.app.getReportThreadData().getUnitMap().get(label).get(2) + "\n" + bugCode);}

                 //add comments
                 if(this.app.getReportThreadData().getUnitMap().get(label).get(3).equals("")){
                     this.app.getReportThreadData().getUnitMap().get(label).set(3, this.app.getReportThreadData().getUnitMap().get(label).get(3) + bugName);
                 }else {
                     this.app.getReportThreadData().getUnitMap().get(label).set(3, this.app.getReportThreadData().getUnitMap().get(label).get(3) + "\n" + bugName);}

                 //add assigned to
                 if(this.app.getReportThreadData().getUnitMap().get(label).get(4).equals("")){
                     this.app.getReportThreadData().getUnitMap().get(label).set(4, this.app.getReportThreadData().getUnitMap().get(label).get(4) + assigneeUser);
                 }else {
                     this.app.getReportThreadData().getUnitMap().get(label).set(4, this.app.getReportThreadData().getUnitMap().get(label).get(4) + "\n" + assigneeUser);}
             }else {
                label = oldLabel;
//                 if(label.equals("None"))label = " ";
                 List<String> unitListData = new ArrayList<String>();
                 //add unit name
                 if(this.app.getReportThreadData().isConferenceMode()){
                     unitListData.add(" ");
                 }else {unitListData.add(label);}

                 label = ++otherIssueNum + "." + label;

                 //add percent value
                 if(!this.app.getReportThreadData().isConferenceMode()) unitListData.add("");

                 //add ticket
                 unitListData.add(bugCode);

                 //add comments
                 if(this.app.getReportThreadData().isConferenceMode()){unitListData.add("• " + bugName + bugPage.getDescription());
                 }else {unitListData.add(bugName);}
                 //add assigned to
                 unitListData.add(assigneeUser);
                 //add date end value
                 if(!this.app.getReportThreadData().isConferenceMode()) unitListData.add("");

                 //put unit's data
                 this.app.getReportThreadData().getUnitMap().put(otherSectionName + Assets.SPECIALSYMBOL + label,unitListData);
                 unitListNames.add(label);
             }
        }
        // put section's data
        if(!unitListNames.isEmpty()){
            this.app.getReportThreadData()
                    .getSectionMap()
                    .put(otherSectionName + Assets.SPECIALSYMBOL + otherSectionName,unitListNames);}

        Assets.printrep("Get Bugs Data...Done!", this.app.getReportThreadData());
        Assets.print("\n", this.app.getReportThreadData());
    }

    private void getBugsLinks() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("linkingmodule")));
        }catch (TimeoutException ex){
            Assets.println("This Issue don't contain links!", this.app.getReportThreadData());
            return;
        }

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-more-links-link")));
            showMoreLinksButton.click();
        }catch (TimeoutException ex){
            //TODO
        }

        List<WebElement> bugs = linksBlock.findElements(By.xpath("./div/div/dl/dd[contains(@id,'internal')]"));
        //Set All Bug Quantity
        this.app.getReportThreadData().setAllBugQuantity(bugs.size());

        for(WebElement bug : bugs){
            String status = bug.findElement(By.xpath("./div/ul/li/span")).getText();
            String bugName = bug.findElement(By.xpath("./div/p/span")).getAttribute("title");
            if(!status.equals("CLOSED")){tempDBBugs.add(bugName);}
        }
        //Set Not Closed Bugs
        this.app.getReportThreadData().setBugQuantity(tempDBBugs.size());
    }

}
