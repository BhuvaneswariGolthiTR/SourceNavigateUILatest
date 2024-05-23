package com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewMenuElements 
{
	public static final String VIEW_CLAMSHELL_DIV = "//div[contains(@id, 'viewClamshellDiv')]";

	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;  

	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'View Section')]")
	public static WebElement viewSections;

	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'View Rendition')]")
	public static WebElement renditions;
	
	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'Delta')]")
	public static WebElement Deltas;
	
	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick,'Notes')]")
	public static WebElement notes;
	
	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'Baselines')]")
	public static WebElement baselines;
	
	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'Deltas Affecting Same Target')]")
	public static WebElement deltasAffectingSameTarget;

	@FindBy(how = How.XPATH, using =  VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'MultiplesAndDuplicates')]")
	public static WebElement multiplesAndDuplicates;

	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'Rendition XML')]")
	public static WebElement renditionXml;
	
	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'XML Extract')]")
	public static WebElement xmlExtract;

	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'Rendition Print Preview')]")
	public static WebElement renditionPrintPreview;
	
	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'Tabular Print Preview')]")
	public static WebElement tabularPrintPreview;

	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'View Target')]")
	public static WebElement viewTarget;

	@FindBy(how = How.XPATH, using = VIEW_CLAMSHELL_DIV + "/div[contains(@onclick, 'View Target In Hierarchy')]")
	public static WebElement viewTargetInHierarchy;
}



