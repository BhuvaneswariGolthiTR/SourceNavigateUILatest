package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.PropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.RenditionPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractPropertiesPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RenditionPropertiesPage extends AbstractPropertiesPage
{
    WebDriver driver;

    @Autowired
    public RenditionPropertiesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PropertiesPageElements.class);
    }

    public boolean switchToRenditionPropertiesWindow()
    {
        boolean windowAppears = switchToWindow(PropertiesPageElements.RENDITION_PROPERTIES_TITLE);
        enterTheInnerFrame();
        return windowAppears;
    }

    public void clickCancel()
    {
        super.clickCancel();
        waitForWindowGoneByTitle(PropertiesPageElements.RENDITION_PROPERTIES_TITLE);
        sourcePage().switchToSourceNavigatePage();
        waitForGridRefresh();
    }

    public boolean openApprovalDateCalendar()
    {
        return openCalendar("showApprovalDateCalendar");
    }

    public boolean openAssignedToDateCalendar()
    {
        return openCalendar("showAssignedToDateCalendar");
    }

    public boolean openCompareDateCalendar()
    {
        return openCalendar("showCompareDateCalendar");
    }

    public boolean openTabularStartedDateCalendar()
    {
        return openCalendar("showTabularStartedDateCalendar");
    }

    private boolean openCalendar(String calendarType)
    {
        click(String.format(RenditionPropertiesPageElements.RENDITION_PROPERTIES_CALENDAR,calendarType));
        return isElementDisplayed(RenditionPropertiesPageElements.CALCONTAINER_BLOCK);
    }

    public String getPageHeader()
    {
        return getElementsText(RenditionPropertiesPageElements.PAGE_HEADER);
    }

    public String getEarliestEffectiveDate()
    {
        return getElement(RenditionPropertiesPageElements.EARLIEST_EFFECTIVE_DATE_INPUT).getAttribute("value");
    }

    public String getEffectiveDate()
    {
        return getElement(RenditionPropertiesPageElements.EFFECTIVE_DATE_INPUT).getAttribute("value");
    }

    //-------------------------------------------------------//
    //---------Proposed/Approved Tracking Information--------//
    //-------------------------------------------------------//

    public void openProposedApprovedTrackingInfoTab()
    {
        click(PropertiesPageElements.proposedTrackingInfoTab);
    }

    public String getAPVStarted()
    {
        return PropertiesPageElements.apvStartedDate.getAttribute("value");
    }


    public String getTopicalHeadingNeeded()
    {
        return PropertiesPageElements.topicalHeadingNeededDate.getAttribute("value");
    }

    public String getTabularRequested()
    {
        return PropertiesPageElements.tabularRequested.getAttribute("value");
    }

    public String getTabularStarted()
    {
        return PropertiesPageElements.tabularStarted.getAttribute("value");
    }

    public String getAPVReviewStarted()
    {
        return PropertiesPageElements.apvReviewStarted.getAttribute("value");
    }

    public String getReadyForAdvancedSheet()
    {
        return PropertiesPageElements.readyForAdvancedSheet.getAttribute("value");
    }

    public String getAPVCompleted()
    {
        return PropertiesPageElements.apvCompleted.getAttribute("value");
    }

    public String getChapterApprovalReceived()
    {
        return PropertiesPageElements.chapterApprovalReceived.getAttribute("value");
    }

    public String getTopicalHeadingCompleted()
    {
        return PropertiesPageElements.topicalHeadingCompleted.getAttribute("value");
    }

    public String getImagesCompleted()
    {
        return PropertiesPageElements.imagesCompleted.getAttribute("value");
    }

    public String getTabularReceivedHardcopy()
    {
        return PropertiesPageElements.tabularReceivedHardcopy.getAttribute("value");
    }

    public String getTabularCompleted()
    {
        return PropertiesPageElements.tabularCompleted.getAttribute("value");
    }

    public String getAPVReviewCompleted()
    {
        return PropertiesPageElements.apvReviewCompleted.getAttribute("value");
    }

    public String getPublishedInAdvancedSheetDate()
    {
        return PropertiesPageElements.publishedInAdvancedSheet.getAttribute("value");
    }
}
