package com.thomsonreuters.codes.codesbench.quality.pages.activity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.thomsonreuters.codes.codesbench.quality.pageelements.activity.ActivityUserLogElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

public class ActivityUserLogPage extends BasePage
{
	private WebDriver driver;

    public ActivityUserLogPage(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
    }

    public void init()
    {
        PageFactory.initElements(driver, ActivityUserLogElements.class);
    }
    
    public void rightClickActivityLogEntry(String selectedSiblingMetadataValue)
    {
    	click(String.format(ActivityUserLogElements.ACTIVITY_LOG_ENTRY, selectedSiblingMetadataValue));
    	rightClick(String.format(ActivityUserLogElements.ACTIVITY_LOG_ENTRY, selectedSiblingMetadataValue));
    }
    
    public boolean nodeAppearsInActivityLog(String selectedSiblingMetadataValue)
    {
    	return doesElementExist(String.format(ActivityUserLogElements.ACTIVITY_LOG_ENTRY, selectedSiblingMetadataValue));
    }
    
    public void closeActivityUserLogAndRefreshHierarchy()
    {
    	ActivityUserLogElements.closeAndRefreshHierarchy.click();
    	waitForWindowGoneByTitle(ActivityUserLogElements.ACTIVITY_USER_LOG_PAGE_TITLE);
    	switchToWindow(HierarchyPageElements.PAGE_TITLE);
    	waitForGridRefresh();
    }
}
