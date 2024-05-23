package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.view;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view.ViewDescriptionContentSetsPageElements;
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
public class ViewDescriptionContentSetsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ViewDescriptionContentSetsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ViewDescriptionContentSetsPageElements.class);
    }

    public String getScriptId()
    {
        return getElementsText(ViewDescriptionContentSetsPageElements.scriptId);
    }

    public String getScriptName()
    {
        return getElementsText(ViewDescriptionContentSetsPageElements.scriptName);
    }

    public String getVersionDescription()
    {
        return getElementsText(ViewDescriptionContentSetsPageElements.versionDescription);
    }

    public String getPubTag()
    {
        return getElementsText(ViewDescriptionContentSetsPageElements.pubTag);
    }

    public List<String> getValidContentSetsForScript()
    {
        return getElementsTextList(ViewDescriptionContentSetsPageElements.VALID_CONTENT_SETS_FOR_SCRIPT);
    }

    public boolean clickClose()
    {
        click(ViewDescriptionContentSetsPageElements.CLOSE);
        waitForProgressBarGone();
        waitForPageLoaded();
        return isElementDisplayed(ViewDescriptionContentSetsPageElements.PAGE_XPATH);
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
