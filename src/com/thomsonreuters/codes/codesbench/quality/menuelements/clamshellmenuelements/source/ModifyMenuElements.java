package com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ModifyMenuElements 
{
	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Split Rendition by Section')]")
	public static WebElement splitRenditionBySection;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Omit Rendition')]")
	public static WebElement omitRendition;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Mark as Non-Duplicate')]")
	public static WebElement markAsNonDuplicate;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Veto Rendition')]")
	public static WebElement vetoRendition;
	
	@FindBy(how = How.XPATH, using ="//div[contains(@onclick, 'Delete Rendition')]")
	public static WebElement deleteRendition;

	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Undelete Rendition')]")
	public static WebElement undeleteRendition;

	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Integrate')]")
	public static WebElement integrate;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Assign User')]")
	public static WebElement assignUser;

	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Modify Cite Locate')]")
	public static WebElement modifyCiteLocate;

	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Resolve Cite Locate')]")
	public static WebElement resolveCiteLocate;

	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Insert Into Hierarchy Wizard')]")
	public static WebElement insertIntoHierarchyWizard;

	@FindBy(how = How.XPATH, using = "//div[contains(@onclick, 'Reset Integration Status')]")
	public static WebElement resetIntegrationStatus;
}
