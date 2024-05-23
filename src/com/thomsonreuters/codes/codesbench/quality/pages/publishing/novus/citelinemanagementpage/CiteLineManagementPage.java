package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddOrEditNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CiteLineManagementPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CiteLineManagementPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CiteLineManagementsCommonPageElements.class);
    }

    public void goToNormalizedCiteTab()
    {
        click(CiteLineManagementsCommonPageElements.normalizedCite);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        waitForPageLoaded();
    }

    public void goToCiteLineReferencesTab()
    {
        click(CiteLineManagementsCommonPageElements.citeLineReferences);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        waitForPageLoaded();
    }

    /**
     * This is to be used for going clickable elements, that pull up a new page, in the Cite Line Management
     */
    protected boolean genericGoTo(WebElement buttonXpath, String pageExpected)
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sendEnterToElement(buttonXpath);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        return isElementDisplayed(pageExpected);
    }

    protected boolean genericGoTo(String buttonXpath, String pageExpected)
    {
        sendEnterToElement(buttonXpath);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        return isElementDisplayed(pageExpected);
    }

    /**
    * This is to be used for the inputs on the add and edit pages in Cite Line Management
     */
    protected void genericInput(String setValue, WebElement inputField)
    {
        clearAndSendKeysToElement(inputField, setValue);
        waitForPageLoaded();
    }

    /**
     * This is to be used as a clearing method for the Cite Line Management page inputs
     */
    protected void genericClear(String valueToClear, WebElement inputField)
    {
        int stringLength = valueToClear.length();
        click(inputField);
        inputField.sendKeys(Keys.END);
        for(int i=0; i<stringLength; i++)
        {
            inputField.sendKeys(Keys.BACK_SPACE);
        }
        waitForPageLoaded();
    }

    /**
     * The methods below are used in both CiteLine Add/Edit Cite Tabs. As such they are here in a common page
     * If you need to use them their respective pages extend This page. So please use the appropriate page call.
     */

    public void clickYes()
    {
        click(CiteLineManagementsCommonPageElements.yes);
        waitForGridRefresh();
    }

    public void clickNo()
    {
        click(CiteLineManagementsCommonPageElements.no);
    }
}
