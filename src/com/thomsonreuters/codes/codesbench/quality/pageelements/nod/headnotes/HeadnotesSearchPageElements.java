package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HeadnotesSearchPageElements 
{
	@FindBy(how = How.ID, using = "quickFindForm:quickFindQuery")
	public static WebElement quickFindField;

	@FindBy(how = How.XPATH, using = "//input[@value='Quick Find']")
	public static WebElement quickFindButton;
	
	@FindBy(how = How.XPATH, using = "//input[@value='Keyword Find']")
	public static WebElement keywordFindButton;
}
