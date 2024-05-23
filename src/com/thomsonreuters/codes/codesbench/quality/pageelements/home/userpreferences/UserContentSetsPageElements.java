package com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UserContentSetsPageElements 
{
	public static final String USER_CONTENT_SET_PREFERENCES_PAGE_TITLE = "User Content Set Preferences";
	public static final String INFO_BAR = "//div[@class='infoBar']";
	public static final String HIERARCHY_HIDDEN_CONTENT_GROUP_XPATH = "//select[@id='pageForm:targetAvailableContentSets']/option";
	public static final String HIERARCHY_CURRENT_CONTENT_GROUP_XPATH = "//select[@id='pageForm:targetSelectedContentSets']/option";
	public static final String HIERARCHY_TARGET_CONTENT_GROUP = "//select[@id='pageForm:targetSelectedContentSets']/option";
	public static final String DEFUALT_TARGET_CONTENT_SET_XPATH = "//select[@id='pageForm:targetDefaultContentSet']";
	public static final String SOURCE_SELECTED_CONTENT_GROUP = "//select[@id='pageForm:sourceSelectedContentSets']/option";
	public static final String SOURCE_HIDDEN_CONTENT_GROUP = "//select[@id='pageForm:sourceAvailableContentSets']/option";
	
	@FindBy(how = How.ID, using = "pageForm:removeSourceContentSetButton")
	public static WebElement removeSourceCurrentContentSet;
	
	@FindBy(how = How.ID, using = "pageForm:addSourceContentSetButton")
	public static WebElement addSourceCurrentContentSet;
	
	@FindBy(how = How.ID, using = "pageForm:save")
	public static WebElement saveButton;
}
