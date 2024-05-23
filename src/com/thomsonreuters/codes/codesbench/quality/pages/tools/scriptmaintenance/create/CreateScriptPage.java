package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.create;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptPageElements;
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

@Component
public class CreateScriptPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CreateScriptPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CreateScriptPageElements.class);
    }

    public void setScriptName(String name)
    {
        clearAndSendKeysToElement(CreateScriptPageElements.scriptName, name);
    }

    public void setVersionDescription(String versionDescription)
    {
        clearAndSendKeysToElement(CreateScriptPageElements.versionDescription, versionDescription);
    }

    public void setPubTag(String pubTag)
    {
        clearAndSendKeysToElement(CreateScriptPageElements.pubTag, pubTag);
    }

    public void setContentSets(String... contentSets)
    {
        for (String contentSet : contentSets)
        {
            clearAndSendKeysToElement(CreateScriptPageElements.contentSetFilter, contentSet);
            waitForElement(String.format(CreateScriptPageElements.AVAILABLE_CONTENT_SET_SPECIFIC_CHECKBOX, contentSet));
            checkCheckbox(String.format(CreateScriptPageElements.AVAILABLE_CONTENT_SET_SPECIFIC_CHECKBOX, contentSet));
            click(CreateScriptPageElements.MOVE_RIGHT_ARROW);
            waitForElement(String.format(CreateScriptPageElements.SELECTED_CONTENT_SET, contentSet));
        }
    }

    public boolean clickSave()
    {
        sendEnterToElement(CreateScriptPageElements.SAVE_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(CreateScriptPageElements.PAGE_XPATH);
    }

    public boolean clickSaveErrorExpected()
    {
        sendEnterToElement(CreateScriptPageElements.SAVE_BUTTON);
        waitForPageLoaded();
        waitForElement(ScriptMaintenanceErrorPageElements.PAGE_XPATH);
        return isElementDisplayed(ScriptMaintenanceErrorPageElements.PAGE_XPATH);
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CreateScriptPageElements.CANCEL_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(CreateScriptPageElements.PAGE_XPATH);
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
