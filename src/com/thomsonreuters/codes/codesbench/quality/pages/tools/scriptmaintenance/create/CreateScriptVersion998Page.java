package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.create;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptVersion998PageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceErrorPageElements;
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
public class CreateScriptVersion998Page extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CreateScriptVersion998Page(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CreateScriptVersion998PageElements.class);
    }

    public void setVersionDescription(String versionDescription)
    {
        clearAndSendKeysToElement(CreateScriptVersion998PageElements.versionDescription, versionDescription);
    }

    public List<String> getSelectedContentSets()
    {
        return getElementsTextList(CreateScriptVersion998PageElements.SELECTED_CONTENT_SETS);
    }

    public boolean clickSubmit()
    {
        sendEnterToElement(CreateScriptVersion998PageElements.SUBMIT_BUTTON);
        waitForElementGone(CreateScriptVersion998PageElements.PAGE_XPATH);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(CreateScriptVersion998PageElements.PAGE_XPATH);
    }

    public boolean clickConfirmErrorExpected()
    {
        sendEnterToElement(CreateScriptVersion998PageElements.SUBMIT_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        waitForElement(ScriptMaintenanceErrorPageElements.PAGE_XPATH);
        return isElementDisplayed(ScriptMaintenanceErrorPageElements.PAGE_XPATH);
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CreateScriptVersion998PageElements.CANCEL_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(CreateScriptVersion998PageElements.PAGE_XPATH);
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
