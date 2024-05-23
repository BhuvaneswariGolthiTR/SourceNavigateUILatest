package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SectionEffectiveDatePageElements
{
	public static final String SECTION_EFFECTIVE_DATE_PAGE_TITLE = "Section Effective Date";

	@FindBy(how = How.ID, using = "pageForm:effectiveDate")
	public static WebElement effectiveDateInput;

	@FindBy(how = How.XPATH, using = "//div[@id='pageContentScroll']/table//tr[3]//input[@type='checkbox']")
	public static WebElement runCiteLocateCheckbox;

	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement saveButton;
}
