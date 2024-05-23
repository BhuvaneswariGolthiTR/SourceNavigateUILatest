package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ClamshellPageElements
{
	public static final String SIDEBAR = "//div[@id='sidebar']/div[contains(@id,'clamshell_toggle')]";

	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;
	
	@FindBy(how = How.XPATH, using = SIDEBAR + "/span[contains(text(),'View')]")
	public static WebElement sidebarView;
	
	@FindBy(how = How.XPATH, using = SIDEBAR + "/span[contains(text(),'View')]")
	public static WebElement sidebarViewRenditionXml;
	
	@FindBy(how = How.XPATH, using = SIDEBAR + "/span[contains(text(),'Track')]")
	public static WebElement sidebarTrack;
	
	@FindBy(how = How.XPATH, using = SIDEBAR + "/span[contains(text(),'Report')]")
	public static WebElement sidebarReport;
	
	@FindBy(how = How.XPATH, using = SIDEBAR + "/span[contains(text(),'Modify')]")
	public static WebElement sidebarModify;
	
	@FindBy(how = How.XPATH, using = SIDEBAR + "/span[contains(text(),'Validate')]")
	public static WebElement sidebarValidate;

	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'sync') and text()='Sync']")
	public static WebElement sidebarSyncSync;
	
	@FindBy(how = How.XPATH, using = SIDEBAR + "/span[contains(text(),'Create')]")
	public static WebElement sidebarCreate;
	
	@FindBy(how = How.XPATH, using = "//div[@id='side_content_container']")
	public static WebElement sideContentContainer;
	
	@FindBy(how = How.XPATH, using = "//div[@id='Multiples and Duplicates']")
	public static WebElement sidebarMultiplesAndDuplicates;
	
	@FindBy(how = How.XPATH, using = SIDEBAR + "/span[contains(text(),'Sync')]")
	public static WebElement sync;
	
	@FindBy(how = How.XPATH, using = SIDEBAR + "/span[contains(text(),'Edit')]")
	public static WebElement sidebarEdit;
	
	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'viewSourceLockReportText') and text()='Lock Report']")
	public static WebElement SIDEBAR_VIEW_SOURCE_LOCKED_REPORT;
	
	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'view') and text()='Delta(s)']")
	public static WebElement delta;

	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'create') and text()='Add PDF/Metadata (New)']")
	public static WebElement sidebarAddPDFMetadata;

	public static final String SIDEBAR_ADD_PDF_METADATA_XPATH = "//div/span[contains(@id,'create') and text()='Add PDF/Metadata (New)']";

	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'edit') and text()='Edit PDF/Metadata']")
	public static WebElement sidebarEditPDFMetadata;
}
