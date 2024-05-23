package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SpellcheckManagerGridPageElements
{
	private static final String GENERIC_GRID_CONTAINER = "//div[@id='basic']/div[@class='yui-dt-bd']//tbody[@class='yui-dt-data']";

	public static final String WORD_VALUES_IN_GRID = GENERIC_GRID_CONTAINER + "//td[@headers='yui-dt0-th-word ']/div[text()='%s']";
	public static final String ALL_WORDS_IN_DICTIONARY ="//div[@id='basic']/div[@class='yui-dt-bd']//tbody[@class='yui-dt-data']//td[@headers='yui-dt0-th-word ']/div";
	public static final String MINUS_BUTTON = GENERIC_GRID_CONTAINER + "//td[.='%s']/..//input[@value='-']";
	public static final String PLUS_BUTTON = GENERIC_GRID_CONTAINER + "//td[.='%s']/..//input[@value='+']";
	public static final String BUTTON_VALUE= GENERIC_GRID_CONTAINER + "//td[.='%s']/..//input";
	public static final String REMOVED_WORD_STATUS = GENERIC_GRID_CONTAINER + "//td[.='%s']/..//td[@headers='yui-dt0-th-state ']/div[text()='removed']";
	public static final String NEW_WORD_STATUS = GENERIC_GRID_CONTAINER + "//td[.='%s']/..//td[@headers='yui-dt0-th-state ']/div[text()='new']";
	public static final String GIVEN_WORD_USER_XPATH = GENERIC_GRID_CONTAINER + "//td[.='%s']/..//td[@headers='yui-dt0-th-user ']/div";
	public static final String GIVEN_WORD_DATE_XPATH = GENERIC_GRID_CONTAINER + "//td[.='%s']/..//td[@headers='yui-dt0-th-date ']/div";
	public static final String SELECTED_WORD_VALUE_IN_GRID = "//tr[contains(@class, 'selected')]//td[@headers='yui-dt0-th-word ']/div[text()='%s']";
	public static final String REMOVE_WORD_ALERT = "\"%s\" will be removed from the jurisdictional Spellcheck list. Click ok to continue or Cancel to back out of request";
    public static final String NO_RECORDS_FOUND = "//div[@id = 'basic']//div[text()='No records found.']";

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt0-th-word ']/div")
	public static List<WebElement> wordElementsInGrid;
	
	public static final String WORD_STATES_IN_GRID = "//td[contains(@class,'yui-dt-col-state')]/div";
}
