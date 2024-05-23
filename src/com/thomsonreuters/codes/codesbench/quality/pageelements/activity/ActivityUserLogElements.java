package com.thomsonreuters.codes.codesbench.quality.pageelements.activity;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ActivityUserLogElements
{
	public static final String ACTIVITY_USER_LOG_PAGE_TITLE = "Activity Log";
	
	public static final String ACTIVITY_LOG_ENTRY = "//tbody[@class='yui-dt-data']//tr//td[contains(@class,'NodeTitle')]//div[contains(text(),'%s')]";
	
	@FindBy(how = How.ID, using = "pageForm:activityLogGrid:HierarchyRefreshAndClose")
	public static WebElement closeAndRefreshHierarchy;
}
