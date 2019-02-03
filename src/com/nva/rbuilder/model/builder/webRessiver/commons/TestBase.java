package com.nva.rbuilder.model.builder.webRessiver.commons;

import com.nva.rbuilder.model.builder.core.ReportThreadData;
import com.nva.rbuilder.utils.Assets;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {

    protected WebDriver driver;
    protected AppManager appManager;
    protected WebDriver newDriver = null;

    private void initPhantomJS() {
        String phantomJSDriverExePath = new String();
        if(System.getProperty("os.name").contains("Windows")){phantomJSDriverExePath = Assets.getCurrentPath() + "DATA/phantomjs.exe";}
        if(System.getProperty("os.name").contains("OS X")){phantomJSDriverExePath = Assets.getCurrentPath() + "DATA/phantomjs";}
        String phantomJSLogPath = Assets.getCurrentPath() + "DATA/phantomjsdriver.log";
        System.setProperty("phantomjs.binary.path", phantomJSDriverExePath);
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
                new String[] {"--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE",
                        "--webdriver-logfile=" + phantomJSLogPath});
        this.newDriver = new PhantomJSDriver(capabilities);
    }

    public void setUp(ReportThreadData reportThreadData) {
        try {
            Assets.print("Starting PhantomJS...", reportThreadData);
            appManager = new AppManager();
            appManager.setReportThreadData(reportThreadData);
            initPhantomJS();
            this.driver = newDriver;
            appManager.setDriver(driver);
            Assets.printrep("Starting PhantomJS...Done!", reportThreadData);
            Assets.print("\n", reportThreadData);
        }catch (Exception ex){
            Assets.logPrint(ex);
            Assets.printrep("Starting PhantomJS...Failed!", reportThreadData);
            appManager.getReportThreadData().addError("Starting PhantomJS Failed!");
            Assets.print("\n", reportThreadData);
        }
    }

    public void tearDown() {
        try {
            this.driver.close();
            this.driver.quit();
        }catch (Exception e){
            Assets.println(e, appManager.getReportThreadData());
        }
    }

}