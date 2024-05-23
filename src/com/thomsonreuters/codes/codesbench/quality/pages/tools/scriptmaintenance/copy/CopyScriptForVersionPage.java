package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.copy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.copy.CopyScriptForVersionPageElements;
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
public class CopyScriptForVersionPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CopyScriptForVersionPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CopyScriptForVersionPageElements.class);
    }

    public void setVersionDescription(String versionDescription)
    {
        clearAndSendKeysToElement(CopyScriptForVersionPageElements.versionDescription, versionDescription);
    }

    public List<String> getSelectedContentSets()
    {
        waitForElementExists((CopyScriptForVersionPageElements.SELECTED_CONTENT_SETS));
        return getElementsTextList(CopyScriptForVersionPageElements.SELECTED_CONTENT_SETS);
    }

    public boolean clickSubmit()
    {
        waitForElementToBeEnabled(CopyScriptForVersionPageElements.SUBMIT_BUTTON);
        sendEnterToElement(CopyScriptForVersionPageElements.SUBMIT_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        return isElementDisplayed(CopyScriptForVersionPageElements.PAGE_XPATH);
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CopyScriptForVersionPageElements.CANCEL_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(CopyScriptForVersionPageElements.PAGE_XPATH);
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
