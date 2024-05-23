package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdvancedSearchAndReplaceSearchAndReplaceElements 
{
	@FindBy(how = How.XPATH, using = "//input[@value='Commit']")
	public static WebElement commitButton;
	
	@FindBy(how = How.XPATH, using = "//tr[contains(@class,'difference')]/td[1]//span[@class='hit']")
	public static List<WebElement> beforeChanges;
	
	@FindBy(how = How.XPATH, using = "//tr[contains(@class,'difference')]/td[2]//span[@class='hit']")
	public static List<WebElement> afterChanges;

}
