package com.thomsonreuters.codes.codesbench.quality.pageelements.nod;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ListOfCasesPageElements 
{
	public static final String LIST_OF_CASES_PAGE_TITLE = "List of Cases";

	@FindBy(how = How.ID, using = "contentSetForm:OpinionTypeMenu")
	public static WebElement contentSetTeamDropdown;

	@FindBy(how = How.ID, using = "contentSetForm:GoButton")
	public static WebElement goButton;
	
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'casesView')]")
	public static WebElement currentlyViewingCasesForSpan;
}
