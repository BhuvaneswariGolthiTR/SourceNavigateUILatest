package com.thomsonreuters.codes.codesbench.quality.pageelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DateSearchFilterPageElements
{
	public static final String DATE_SEARCH_FILTER_PAGE_TITLE = "Search Filter";
	
	@FindBy(how = How.ID, using = "singleDate")
    public static WebElement dateFilter;

	@FindBy(how = How.ID, using = "showSingleDateCal")
	public static WebElement searchFilterCalendar;

	@FindBy(how = How.ID, using = "singleDate")
	public static WebElement dateTextBox;

	public static final String FLAG_MENU_DISPLAY_BUTTON_XPATH = "//span[@id='multipleDuplicateViewableToggle']//button";
	public static final String FILTER_DROPDOWN = "//div/ul/li[contains(@class, 'highlight') and text()='%s']";

	@FindBy(how = How.ID, using = "dateBefore")
	public static WebElement dateBeforeInput;

	@FindBy(how = How.XPATH, using = "//div[@id='dateContent']//input[@value='DateBefore']")
	public static WebElement dateBeforeRadioButton;

	@FindBy(how = How.XPATH, using = "//div[@id='dateContent']//input[@value='None']")
	public static WebElement noneRadioButton;

	public static final String MISMATCHED_REPORTS_CALENDAR = "//div[@class='calheader']";
}
