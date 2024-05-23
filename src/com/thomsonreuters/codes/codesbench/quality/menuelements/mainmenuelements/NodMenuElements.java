package com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class NodMenuElements extends CommonMenuElements
{
	public static final String GENERIC_INITIATE_REPORT_XPATH = "//ul[@class='first-of-type']//a[contains(text(),'%s')]";
    public static final String GENERIC_NOD_MENU_XPATH = MAIN_NAVIGATOR_MENU_XPATH + "//div[@id='baseNavBarNOD']";
    public static final String GENERIC_NOD_REPORTS_XPATH = GENERIC_NOD_MENU_XPATH + "//div[@id='NODNODreports']";
    public static final String GENERIC_NOD_TOOLS_XPATH = GENERIC_NOD_MENU_XPATH + "//div[@id='NODNODTools']";

	public static final String NOD_MENU_XPATH = "//div[@id='codesNavigatorMenu']//a[text()='NOD']";
    
    @FindBy(how = How.XPATH, using = NOD_MENU_XPATH)
	public static WebElement nod;
    
	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[text()='Reports']")
	public static WebElement reports;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[text()='Subscribed Cases']")
	public static WebElement subscribedCases;

	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[text()='Subscribed Cases - angular']")
	public static WebElement subscribedCasesAngular;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[text()='Cases']")
	public static WebElement cases;

	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[text()='Cases - angular']")
	public static WebElement casesAngular;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[text()='Administrative Opinions']")
	public static WebElement administrativeOpinions;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[text()='Courts']")
	public static WebElement courts;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[text()='Tools']")
	public static WebElement tools;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_TOOLS_XPATH + "//a[text()='Initiate NOD Update']")
	public static WebElement initiateNODUpdate;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_REPORTS_XPATH + "//a[text()='Browse Reports']")
	public static WebElement browseReports;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_REPORTS_XPATH + "//a[text()='No Team']")
	public static WebElement noTeam;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_REPORTS_XPATH + "//a[text()='Summary']")
	public static WebElement summary;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_REPORTS_XPATH + "//a[text()='Detail']")
	public static WebElement detail;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[text()='TOPULA']")
	public static WebElement topula;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_TOOLS_XPATH + "//a[text()='Initiate NOD Batch Merge']")
	public static WebElement initiateNODBatchMerge;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_TOOLS_XPATH + "//a[text()='Initiate NOD Unmerged Report']")
	public static WebElement initiateNODUnmergedReport;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_TOOLS_XPATH + "//a[text()='Import Court/Team Routing']")
	public static WebElement importCourtTeamRouting;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_TOOLS_XPATH + "//a[text()='Initiate NOD Data Validation']")
	public static WebElement initiateNODDataValidation;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_TOOLS_XPATH + "//a[text()='Initiate XUSSC Update']")
	public static WebElement initiateXUSSCUpdate;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_TOOLS_XPATH + "//a[text()='Initiate NOD Batch Reorder']")
	public static WebElement initiateNODBatchReorder;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOD_MENU_XPATH + "//a[contains(text(),'Blueline Spellcheck Dictionary')]")
	public static WebElement bluelineSpellcheckDictionary;
}
