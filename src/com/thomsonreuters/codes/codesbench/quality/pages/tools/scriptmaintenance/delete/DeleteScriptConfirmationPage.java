package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.delete;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.delete.DeleteScriptConfirmationPageElements;
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
public class DeleteScriptConfirmationPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeleteScriptConfirmationPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeleteScriptConfirmationPageElements.class);
    }

    public boolean clickCancel()
    {
        sendEnterToElement(DeleteScriptConfirmationPageElements.cancelButton);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(DeleteScriptConfirmationPageElements.PAGE_XPATH);
    }

    public boolean clickSubmit()
    {
        click(DeleteScriptConfirmationPageElements.submitButton);
        waitForPageLoaded();
        waitForProgressBarGone();
        return isElementDisplayed(DeleteScriptConfirmationPageElements.PAGE_XPATH);
    }

    public String getConfirmationQuestion()
    {
        return getElementsText(DeleteScriptConfirmationPageElements.confirmationQuestion);
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
