package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.PreviousWipVersionsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.*;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PreviousWipVersionsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public PreviousWipVersionsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PreviousWipVersionsPageElements.class);
        PageFactory.initElements(driver, PreviousWipVersionsContextMenuElements.class);
    }

    public void restoreSpecificWIPVersionByIndex(String versionIndex)
    {
        rightClick(String.format(PreviousWipVersionsPageElements.WIP_VERSION_BY_INDEX_XPATH, versionIndex));
        clickRestoreWipContent();
    }

    public void restoreOriginalWIPVersion()
    {
        scrollToView(PreviousWipVersionsPageElements.originalVersion);
        rightClick(PreviousWipVersionsPageElements.originalVersion);
        clickRestoreWipContent();
    }

    public void clickRestoreWipContent()
    {
        sendEnterToElement(PreviousWipVersionsContextMenuElements.RESTORE_WIP_CONTENT);
        switchToWindow(SetLawTrackingPageElements.SET_LAW_TRACKING_TITLE);
        enterTheInnerFrame();
    }

    public void clickClose()
    {
        click(PreviousWipVersionsPageElements.closeButton);
        waitForWindowGoneByTitle(PreviousWipVersionsPageElements.PREVIOUS_WIP_VERSIONS_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }

    public void clickWipVersionByIndex(String versionIndex)
    {
        click(String.format(PreviousWipVersionsPageElements.WIP_VERSION_BY_INDEX_XPATH, versionIndex));
    }

    public void rightClickWipVersionByIndex(String versionIndex)
    {
        rightClick(String.format(PreviousWipVersionsPageElements.WIP_VERSION_BY_INDEX_XPATH, versionIndex));
    }

    public String getLawTrackinStatusByIndex(String versionIndex)
    {
       return getElementsText(String.format(PreviousWipVersionsPageElements.LAW_TRACKING_STATUS_BY_INDEX_XPATH, versionIndex));
    }

    public void clickMostRecentWipVersion()
    {
        click(PreviousWipVersionsPageElements.MOST_RECENT_WIP_VERSION_XPATH);
    }

    public String getSelectedWipVersionsCreatedByText()
    {
        return getElementsText(PreviousWipVersionsPageElements.SELECTED_WIP_VERSION_CREATED_BY_XPATH);
    }

    public String getSelectedWipVersionsLawTrackingText()
    {
        return getElementsText(PreviousWipVersionsPageElements.SELECTED_WIP_VERSION_LAW_TRACKING_XPATH);
    }

    public void clickCloseButton()
    {
        waitForGridRefresh();
        click(PreviousWipVersionsPageElements.closeButton);
        waitForWindowGoneByTitle(PreviousWipVersionsPageElements.PREVIOUS_WIP_VERSIONS_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForGridRefresh();
    }
    
    public String getWipVersionOfSelectedWip()
    {
        return getElementsText(PreviousWipVersionsPageElements.SELECTED_WIP_VERSION_WIP_VERSION_NUM_XPATH);
    }

    public String getSelectedWipVersionsCreatedDate()
    {
        return getElementsText(PreviousWipVersionsPageElements.SELECTED_WIP_VERSION_CREATED_DATE_XPATH);
    }

    public void switchToPreviousWipVersionsPage()
    {
        switchToWindow(PreviousWipVersionsPageElements.PREVIOUS_WIP_VERSIONS_PAGE_TITLE);
        waitForGridRefresh();
    }
}
