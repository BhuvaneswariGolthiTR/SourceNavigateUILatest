package com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditMenuElements 
{
	public static final String TOP_CONTENT = "//div[contains(@id, 'top_content')]//";

	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;
	
	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Section')]")
	public static WebElement sections;
	
	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Rendition')]")
	public static WebElement renditions;
	
	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Delta')]")
	public static WebElement deltas;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Notes')]")
	public static WebElement notes;
	
	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Integration Properties')]")
	public static WebElement integrationProperties;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Target Content')]")
	public static WebElement targetContent;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Difficulty Level')]")
	public static WebElement difficultyLevel;
	
	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Effective Date')]")
	public static WebElement effectiveDate;
	
	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Rendition XML')]")
	public static WebElement renditionXml;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[@id='propertiesDiv']")
	public static WebElement properties;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Integration Properties')]")
	public static WebElement IntegrationProperties;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Rendition Properties')]")
	public static WebElement renditionAndLineageProperties;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Section Properties')]")
	public static WebElement sectionProperties;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Delta Properties')]")
	public static WebElement deltaProperties;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[@id='propertiesDiv']")
	public static WebElement propertiesDiv;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Edit PDF / Additional Metadata')]")
	public static WebElement editPdfMetadata;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Class Number Wizard')]")
	public static WebElement classNumberWizard;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'West ID Wizard')]")
	public static WebElement westIdWizard;

	@FindBy(how = How.XPATH, using = TOP_CONTENT + "div[contains(@onclick, 'Approval Date Wizard')]")
	public static WebElement approvalDateWizard;
}

