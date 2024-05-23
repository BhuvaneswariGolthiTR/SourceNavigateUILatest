package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SectionPropertiesPageElements
{
	public static final String SECTION_PROPERTIES_PAGE_TITLE = "Section Properties";

	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement saveButton;

	//Section Properties Elements
	@FindBy(how = How.ID, using  = "SectionPropsBlind_header")
	public static WebElement sectionPropertiesTab;

	@FindBy(how = How.ID, using  = "pageForm:assignedToid")
	public static WebElement assignedUserInput;

	@FindBy(how = How.ID,  using = "pageForm:assignedToDate")
	public static WebElement assignedDateInput;

	@FindBy(how = How.ID, using = "pageForm:sectionEffectiveDate")
	public static WebElement effectiveDateInput;

	//Proposed/Approved Tracking Information
	@FindBy(how = How.ID, using  = "ProposedTrackingInfoBlind_header")
	public static WebElement proposedTrackingInfoTab;

	@FindBy(how = How.ID, using  = "pageForm:imagesSentOut")
	public static WebElement imagesSentOut;

	@FindBy(how = How.ID, using  = "pageForm:imagesCompleted")
	public static WebElement imagesCompleted;

	@FindBy(how = How.ID, using  = "pageForm:tabularRequestedDate")
	public static WebElement tabularRequested;

	@FindBy(how = How.ID, using  = "pageForm:tabularStartedDate")
	public static WebElement tabularStarted;

	@FindBy(how = How.ID, using  = "pageForm:tabularCompletedDate")
	public static WebElement tabularCompleted;

	//PREP Tracking Information
	@FindBy(how = How.ID, using  = "TrackingInfoBlind_header")
	public static WebElement trackingInfoTab;

	@FindBy(how = How.ID, using  = "pageForm:auditRequestedDate")
	public static WebElement auditRequestedDate;

	@FindBy(how = How.ID, using  = "pageForm:auditStartedDate")
	public static WebElement auditReviewStartedDate;

	@FindBy(how = How.ID, using  = "pageForm:auditCompletedDate")
	public static WebElement auditReviewCompletedDate;

	@FindBy(how = How.ID, using  = "pageForm:worksheetStartedDate")
	public static WebElement PREPStartedDate;

	@FindBy(how = How.ID, using  = "pageForm:worksheetCompletedDate")
	public static WebElement PREPCompletedDate;

	@FindBy(how = How.ID, using  = "pageForm:readyForIntegrationDate")
	public static WebElement readyForIntegrationDate;

	@FindBy(how = How.ID, using  = "pageForm:correctionsAuditor")
	public static WebElement correctionsAuditor;

	@FindBy(how = How.ID, using  = "pageForm:integrationStartedDate")
	public static WebElement integrationStartedDate;

	@FindBy(how = How.ID, using  = "pageForm:integrationCompletedDate")
	public static WebElement integrationCompletedDate;

	@FindBy(how = How.ID, using  = "pageForm:attorneyWorkStartedDate")
	public static WebElement attorneyWorkStartedDate;

	@FindBy(how = How.ID, using  = "pageForm:attorneyWorkCompletedDate")
	public static WebElement attorneyWorkCompletedDate;

	@FindBy(how = How.ID, using  = "pageForm:versioningWorkStartedDate")
	public static WebElement versioningWorkStartedDate;

	@FindBy(how = How.ID, using  = "pageForm:versioningWorkCompletedDate")
	public static WebElement versioningWorkCompletedDate;

	@FindBy(how = How.ID, using  = "pageForm:auditCorrectionsStartedDate")
	public static WebElement auditCorrectionsStartedDate;

	@FindBy(how = How.ID, using  = "pageForm:auditCorrectionsCompletedDate")
	public static WebElement auditCorrectionsCompletedDate;

	@FindBy(how = How.ID, using  = "pageForm:integration2StartedDate")
	public static WebElement integration2StartedDate;

	@FindBy(how = How.ID, using  = "pageForm:integration2CompletedDate")
	public static WebElement integration2CompletedDate;

	@FindBy(how = How.XPATH, using = "//span[contains(text(), 'Integration Query Started')]/../following-sibling::td/input[@type='checkbox']")
	public static WebElement integrationQueryStartedCheckbox;

	@FindBy(how = How.XPATH, using = "//span[contains(text(), 'Integration Query Started')]/../following-sibling::td[2]/input[@type='text']")
	public static WebElement integrationQueryStartedOwner;

	@FindBy(how = How.XPATH, using = "//span[contains(text(), 'Integration Query Completed')]/../following-sibling::td/input[@type='checkbox']")
	public static WebElement integrationQueryCompletedCheckbox;

	@FindBy(how = How.XPATH, using = "//span[contains(text(), 'Integration Query Completed')]/../following-sibling::td[2]/input[@type='text']")
	public static WebElement integrationQueryCompletedOwner;

	@FindBy(how = How.ID, using  = "pageForm:correctionsAuditStartedDate")
	public static WebElement correctionsAuditStartedDate;

	@FindBy(how = How.ID, using  = "pageForm:correctionsAuditCompletedDate")
	public static WebElement correctionsAuditCompletedDate;

	@FindBy(how = How.ID, using  = "pageForm:correctionsAuditRequestedDate")
	public static WebElement correctionsAuditRequestedDate;

	@FindBy(how = How.ID, using  = "pageForm:cleanDate")
	public static WebElement cleanDate;

	@FindBy(how = How.ID, using  = "pageForm:releasedToWestlawDate")
	public static WebElement releasedToWestLawDate;

}
