package com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ValidateMenuElements 
{
	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;
  
	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Run Validations')]")
	public static WebElement runValidations;

	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Validate and Update Links')]")
	public static WebElement validateAndUpdateLinks;

	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'View Validations')]")
	public static WebElement viewValidations;
}
