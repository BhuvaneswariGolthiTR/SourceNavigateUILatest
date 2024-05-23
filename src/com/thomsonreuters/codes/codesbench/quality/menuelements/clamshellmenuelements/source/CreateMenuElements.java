package com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreateMenuElements
{
	public static final String CREATE_CLAMSHELL_DIV = "//div[contains(@id,'createClamshellDiv')]//"; 
	
	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;
  
	@FindBy(how = How.XPATH, using = CREATE_CLAMSHELL_DIV + "div[contains(@onclick, 'Create Preparation Document')]")
	public static WebElement createPrepDocument;

	@FindBy(how = How.XPATH, using = CREATE_CLAMSHELL_DIV + "div[contains(@onclick, 'Add Content')]")
	public static WebElement addContent;

	@FindBy(how = How.XPATH, using = CREATE_CLAMSHELL_DIV + "div[contains(@onclick, 'Add Shell')]")
	public static WebElement addPDFMetadata;
}