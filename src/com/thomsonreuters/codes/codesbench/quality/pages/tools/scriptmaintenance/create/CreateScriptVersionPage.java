package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.create;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptVersionPageElements;
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
public class CreateScriptVersionPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CreateScriptVersionPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CreateScriptVersionPageElements.class);
    }

    public void setVersionDescription(String versionDescription)
    {
        clear(CreateScriptVersionPageElements.versionDescription);
        clearAndSendKeysToElement(CreateScriptVersionPageElements.versionDescription, versionDescription);
    }

    public List<String> getSelectedContentSets()
    {
        return getElementsTextList(CreateScriptVersionPageElements.SELECTED_CONTENT_SETS);
    }

    public boolean clickSubmit()
    {
        sendEnterToElement(CreateScriptVersionPageElements.SUBMIT_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(CreateScriptVersionPageElements.PAGE_XPATH);
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CreateScriptVersionPageElements.CANCEL_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(CreateScriptVersionPageElements.PAGE_XPATH);
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
