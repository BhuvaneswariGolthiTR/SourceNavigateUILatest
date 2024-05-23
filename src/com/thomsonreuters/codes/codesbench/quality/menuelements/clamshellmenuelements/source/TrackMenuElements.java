package com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class TrackMenuElements 
{
	public static final String TRACK_CLAMSHELL_DIV = "//div[contains(@id,'trackClamshellDiv')]//"; 
	
	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;
  
	@FindBy(how = How.XPATH, using = TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Sync to Westlaw Completed')]")
	public static WebElement syncToWestlawCompleted;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Chapter/Approval Received')]")
	public static WebElement chapterApprovalReceived;

	@FindBy(how = How.XPATH, using = TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'APV Started')]")
	public static WebElement apvStarted;

	@FindBy(how = How.XPATH, using = TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'APV Completed')]")
	public static WebElement apvCompleted;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Topical Heading Needed')]")
	public static WebElement topicalHeadingNeeded;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Topical Heading Completed')]")
	public static WebElement topicalHeadingCompleted;

	@FindBy(how = How.XPATH, using = TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Images Sent Out')]")
	public static WebElement imagesSentOut;

	@FindBy(how = How.XPATH, using = TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Images Completed')]")
	public static WebElement imagesCompleted;

	@FindBy(how = How.XPATH, using = TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Tabular Requested')]")
	public static WebElement tabularRequested;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Tabular Received Hardcopy')]")
	public static WebElement tabularReceivedHardcopy;

	@FindBy(how = How.XPATH, using = TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Tabular Started')]")
	public static WebElement tabularStarted;

	@FindBy(how = How.XPATH, using = TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Tabular Completed')]")
	public static WebElement tabularCompleted;

	@FindBy(how = How.XPATH, using = TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'APV Review Started')]")
	public static WebElement apvReviewStarted;

	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'APV Review Completed')]")
	public static WebElement apvReviewCompleted;

	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Ready for Advance Sheet')]")
	public static WebElement readyForAdvanceSheet;

	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Published in Advance Sheet')]")
	public static WebElement publishedInAdvanceSheet;
	
	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick,'Attorney Work Started')]")
	public static WebElement attorneyWorkStarted;
	
	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Attorney Work Completed')]")
	public static WebElement attorneyWorkCompleted;
	
	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Versioning Work Started')]")
	public static WebElement versioningWorkStarted;
	
	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Versioning Work Completed')]")
	public static WebElement versioningWorkCompleted;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'PREP Started')]")
	public static WebElement prepStarted;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'PREP Completed')]")
	public static WebElement prepCompleted;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Ready for Integration')]")
	public static WebElement readyForIntegration;
	
	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Integration Started')]")
	public static WebElement integrationStarted;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Integration Completed')]")
	public static WebElement integrationCompleted;
	
	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Integration 2 Started')]")
	public static WebElement integration2Started;

	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Integration 2 Completed')]")
	public static WebElement integration2Completed;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Integration Query Started')]")
	public static WebElement integrationQueryStarted;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Integration Query Completed')]")
	public static WebElement integrationQueryCompleted;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Cut and Paste Completed')]")
	public static WebElement cutAndPasteCompleted;

	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Run Auto-Integrate & Audit')]")
	public static WebElement runAutoIntegrateAndAudit;
	
	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Ready to Resolve Auto-Integrate Errors')]")
	public static WebElement readyToResolveAutoIntegrateErrors;
	
	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Resolve Auto-Integrate Errors Started')]")
	public static WebElement resolveAutoIntegrateErrorsStarted;
	
	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Resolve Auto-Integrate Errors Completed')]")
	public static WebElement resolveAutoIntegrateErrorsCompleted;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Audit Requested')]")
	public static WebElement auditRequested;

	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Audit Review Started')]")
	public static WebElement auditReviewStarted;
	
	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Audit Review Completed')]")
	public static WebElement auditReviewCompleted;
	
	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Audit Corrections to be Done by')]")
	public static WebElement auditCorrectionsToBeDoneBy;

	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick,'Audit Corrections Started')]")
	public static WebElement auditCorrectionsStarted;

	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick,'Audit Corrections Completed')]")
	public static WebElement auditCorrectionsCompleted;
	
	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Corrections Audit Requested')]")
	public static WebElement correctionsAuditRequested;

	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Corrections Audit Started')]")
	public static WebElement correctionsAuditStarted;

	@FindBy(how = How.XPATH, using =   TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Corrections Audit Completed')]")
	public static WebElement correctionsAuditCompleted;
	
	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Clean')]")
	public static WebElement clean;
	
	@FindBy(how = How.XPATH, using =  TRACK_CLAMSHELL_DIV + "div[contains(@onclick, 'Released to Westlaw')]")
	public static WebElement releasedToWestlaw;
}
