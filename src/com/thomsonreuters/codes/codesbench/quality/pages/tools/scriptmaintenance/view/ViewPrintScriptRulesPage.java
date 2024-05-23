package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.view;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view.ViewDescriptionContentSetsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view.ViewPrintScriptRulesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ViewPrintScriptRulesPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ViewPrintScriptRulesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ViewPrintScriptRulesPageElements.class);
    }

    public String getScriptId()
    {
        return getElementsText(ViewPrintScriptRulesPageElements.scriptId);
    }

    public String getScriptName()
    {
        return getElementsText(ViewPrintScriptRulesPageElements.scriptName);
    }

    public String getVersionDescription()
    {
        return getElementsText(ViewPrintScriptRulesPageElements.versionDescription);
    }

    public String getPubTag()
    {
        return getElementsText(ViewPrintScriptRulesPageElements.pubTag);
    }

    public List<String> getValidContentSetsForScript()
    {
        return getElementsTextList(ViewPrintScriptRulesPageElements.EFFECTED_CONTENT_SETS);
    }

    public boolean clickClose()
    {
        click(ViewDescriptionContentSetsPageElements.CLOSE);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        return isElementDisplayed(ViewPrintScriptRulesPageElements.PAGE_XPATH);
    }

}
