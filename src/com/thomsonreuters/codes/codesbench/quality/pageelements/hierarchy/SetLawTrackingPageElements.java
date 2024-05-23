package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SetLawTrackingPageElements
{
	public static final String SET_LAW_TRACKING_TITLE = "Set Law Tracking";

	// Law Track Text
	@FindBy(how = How.ID, using = "pageForm:lawTrackingText")
	public static WebElement lawTrackingTextXpath;

	// Buttons
	@FindBy(how = How.ID, using = "pageForm:C2012SetLawTracking")
	public static WebElement c2012LawTrackingButtonXpath;

	@FindBy(how = How.ID, using = "pageForm:setLawTracking")
	public static WebElement legoLawTrackingButtonXpath;

	@FindBy(how = How.ID, using = "pageForm:setLawTrackingFull")
	public static WebElement fullVolsTrackingButtonXpath;

	@FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
	public static WebElement quickLoadTrackingButtonXpath;

	@FindBy(how = How.ID, using = "pageForm:setLawTrackingOk")
	public static WebElement okayButtonXpath;

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingCancel")
    public static WebElement cancelButton;

	@FindBy(how = How.ID, using = "yearFilter")
	public static WebElement setLawtrackingYearInput;

	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr")
	public static WebElement lawTrackingItem;
	public static final String LAW_TRACKING_ITEM = "//tbody[@class='yui-dt-data']/tr";
}
