package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.copy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.copy.CopyScriptPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CopyScriptPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CopyScriptPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CopyScriptPageElements.class);
    }

    public void setScriptName(String name)
    {
        clearAndSendKeysToElement(CopyScriptPageElements.scriptName, name);
        waitForPageLoaded();
    }

    public void setVersionDescription(String versionDescription)
    {
        clearAndSendKeysToElement(CopyScriptPageElements.versionDescription, versionDescription);
        waitForPageLoaded();
    }

    public void setPubTag(String pubTag)
    {
        clearAndSendKeysToElement(CopyScriptPageElements.pubTag, pubTag);
        waitForPageLoaded();
    }

    public List<String> getSelectedContentSets()
    {
        return getElementsTextList(CopyScriptPageElements.SELECTED_CONTENT_SETS);
    }

    public boolean clickSubmit()
    {
        waitForElementToBeEnabled(CopyScriptPageElements.SUBMIT_BUTTON);
        sendEnterToElement(CopyScriptPageElements.SUBMIT_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        return isElementDisplayed(CopyScriptPageElements.PAGE_XPATH);
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CopyScriptPageElements.CANCEL_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(CopyScriptPageElements.PAGE_XPATH);
    }

    private void waitForProgressBarGone()
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        int counter = 0;
        while(isElementDisplayed(ScriptMaintenancePageElements.PROGRESS_BAR) && counter < 30)
        {
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            counter++;
        }
        Assertions.assertTrue(counter <= 30, "Progress bar disappeared in 30 seconds");
    }
}
