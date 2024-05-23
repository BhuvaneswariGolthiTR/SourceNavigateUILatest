package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.page;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptVersionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceErrorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScriptMaintenanceErrorPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ScriptMaintenanceErrorPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ScriptMaintenanceErrorPageElements.class);
    }

    public boolean clickOk()
    {
        sendEnterToElement(ScriptMaintenanceErrorPageElements.OK_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        waitForPageLoaded();
        return isElementDisplayed(ScriptMaintenanceErrorPageElements.PAGE_XPATH);
    }
}
