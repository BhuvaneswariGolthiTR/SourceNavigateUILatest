package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.RepealPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RepealPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public RepealPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RepealPageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(RepealPageElements.quickLoadTrackingButton);
        waitForPageLoaded();
        click(RepealPageElements.okButton);
        waitForWindowGoneByTitle(RepealPageElements.REPEAL_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForPageLoaded();
        waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
    }

    public void setCurrentDateAsEffectiveStartDate()
    {
        enterTheInnerFrame();
        String date = DateAndTimeUtils.getCurrentDateMMddyyyy();
        sendKeysToElement(RepealPageElements.effectiveStartDateTextBox,date);
    }

    public void clickShareNodsForward()
    {
        click(RepealPageElements.shareNodsForwardRadioButton);
    }

    public void clickProcessAsRepealedRange()
    {
        click(RepealPageElements.processAdRepleasedRangeRadioButton);
    }

    public void clickRunPubTagRefreshNow()
    {
        click(RepealPageElements.runPubTagRefreshNowRadioButton);
    }

    public void clickQuickLoad()
    {
        click(RepealPageElements.quickLoadTrackingButton);
        waitForPageLoaded();
    }

    @Override
    public void clickOK()
    {
        sendEnterToElement(CommonPageElements.OK_BUTTON);
    }
}