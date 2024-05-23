package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdvancedSearchAndReplaceScanElements 
{  
    @FindBy(how = How.XPATH, using = "//input[@value='< Back']")
	public static WebElement backButton;
    
    @FindBy(how = How.XPATH, using = "//tr[contains(@class,'difference')]")
	public static List<WebElement> scanMatches;
}
