package com.nva.rbuilder.model.builder.webRessiver.commons;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageBase {

    protected AppManager app;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final Wait<WebDriver> wait2;

    public PageBase(AppManager app){
        this.app = app;
        this.driver = app.getDriver();
        this.wait = new WebDriverWait(this.driver, 2);
        wait2 = new WebDriverWait(driver, 3).ignoring(ElementNotVisibleException.class);
        PageFactory.initElements(driver,this);
    }

}