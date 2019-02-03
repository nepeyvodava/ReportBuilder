package com.nva.rbuilder.model.builder.webRessiver.pages;

import com.nva.rbuilder.model.builder.webRessiver.commons.AppManager;
import com.nva.rbuilder.model.builder.webRessiver.commons.PageBase;
import com.nva.rbuilder.utils.Assets;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends PageBase {

    public LoginPage(AppManager app) {
        super(app);
    }

    @FindBy (id = "login-form-username")
    private static WebElement loginField;
    @FindBy (id = "login-form-password")
    private static WebElement passwordField;
    @FindBy (id = "login")
    private static WebElement btnSubmit;
    @FindBy (id = "login-form-captcha")
    private static WebElement captchaField;
    @FindBy (id = "usernameerror")
    private static WebElement errorMesseage;
    @FindBy (id = "captchaimg")
    private static WebElement captchaImg;

    private String captchaText;
    private String beforeTitle;
    private String afterTitle;

    public void login() {
        Assets.println("Login to server...", this.app.getReportThreadData());
        driver.get(this.app.getReportThreadData().getJiraURL() + "/secure/Dashboard.jspa");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
        beforeTitle = driver.getTitle();
        //login
        loginField.clear();
        if(this.app.getReportThreadData().isAutoAuthorisationMode()){
            loginField.sendKeys(Assets.LOGIN);
        }else {loginField.sendKeys(this.app.getReportThreadData().getLogin());}
        //password
        passwordField.clear();
        if(this.app.getReportThreadData().isAutoAuthorisationMode()){
            passwordField.sendKeys(Assets.PASSWORD);
        }else{passwordField.sendKeys(this.app.getReportThreadData().getPassword());}
        //submit
        submitForm();
    }

    private void submitForm() {
        btnSubmit.submit();
        try {
            wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("login-form-username")));
        }catch (TimeoutException ex){
            Assets.println("Authorisation Filed!", this.app.getReportThreadData());
            app.getReportThreadData().addError("Authorisation Filed!");}

        afterTitle = driver.getTitle();
        if(!afterTitle.equals(beforeTitle)){
            Assets.println("Authorisation Done!", this.app.getReportThreadData());
            this.app.getReportThreadData().setAutorithationStatus(true);
            return;}
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usernameerror")));
        if(beforeTitle.equals(afterTitle) && errorMesseage.getText().equals(
                "Sorry, your username and password are incorrect - please try again.")){
            Assets.println(errorMesseage.getText(), this.app.getReportThreadData());
            app.getReportThreadData().addError("your username and password are incorrect");}
        if(beforeTitle.equals(afterTitle) && errorMesseage.getText().equals(
                "Sorry, your userid is required to answer a CAPTCHA question correctly.")){
            Assets.println(errorMesseage.getText(), this.app.getReportThreadData());
            app.getReportThreadData().addError("your username and password are incorrect");}
    }

}
