package com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbysource;

import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.CommonAuditsPageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AuditBySourcePageElements extends CommonAuditsPageElements
{
	public static final String AUDIT_BY_SOURCE_PAGE_TITLE = "Audit By Source";
	public static final String SELECTED_MATERIALS_LIST_XPATH = "//select[@id='pageForm:selectedMaterialIdList']/option";
	public static final String AVAILABLE_MATERIALS_LIST_XPATH = "//select[@id='pageForm:nonSelectedMaterialIdList']/option[text()='%s']";
	public static final String SEARCH_DOCUMENTS_DROP_DOWN_XPATH = "//select[@id='pageForm:sourceType']/option[text()='%s']";

	public static final String LABEL_UNUSED_REPORT_XPATH = "//label[@for='pageForm:containsUnusedReport']";

	public static final String LABEL_INSTRUCTIONS_REPORT_XPATH = "//label[@for='pageForm:containsInstructionsReport']";

	public static final String LABEL_STACKING_AND_BLENDING_REPORT_XPATH = "//label[@for='pageForm:containsStackingAndBlendReport']";

	public static final String LABEL_TEXT_MERGE_REPORT_XPATH = "//label[@for='pageForm:containsTextMergeReport']";

	public static final String LABEL_RESEARCH_REFERENCES_XPATH = "//label[@for='pageForm:researchReferences']";

	public static final String LABEL_TEXT_PREVIEW_XPATH = "//label[@for='pageForm:includeTextPreviewReport']";

	public static final String LABEL_SOURCE_XPATH = "//label[text()='Source']";

	public static final String ADDITIONAL_IDENTIFIER_TEXTBOX_XPATH = "//input[@name='pageForm:reportName']";

	public static final String LABEL_MOST_RECENT_PUBLISHED_DOCUMENT_XPATH = "//label[@for= 'pageForm:compareFromType:0']";

	public static final String LABEL_MOST_RECENT_HISTORICAL_VERSION_XPATH = "//label[@for= 'pageForm:compareFromType:0']";

	public static final String LABEL_MOST_RECENT_WIP_DOCUMENT_XPATH = "//label[@for= 'pageForm:compareFromType:0']";

	public static final String LABEL_WIP_VS_WIP_XPATH = "//label[@for= 'pageForm:compareFromType:0']";

	public static final String MATERIAL_ITEM_XPATH = "//option[contains(text(),'%s')]";
	
	@FindBy(how = How.ID, using = "pageForm:reportName")
    public static WebElement additionalIdentifierTextBox;
	
	@FindBy(how = How.ID, using = "pageForm:next")
    public static WebElement nextButton;
	
	@FindBy(how = How.ID, using = "pageForm:addOneVolsButton")
    public static WebElement addOneVolsButton;	
	
	@FindBy(how = How.ID, using = "pageForm:sourceType")
    public static WebElement searchDocumentsDropDown;
	
	@FindBy(how = How.ID, using = "pageForm:cutoffDate")
    public static WebElement versionsAuditStartingTextBox;
	
	//CHECKBOXES
	@FindBy(how = How.ID, using = "pageForm:contentAudit")
    public static WebElement contentAuditCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:hierarchyContext")
    public static WebElement hierarchyContextCheckbox;

	@FindBy(how = How.ID, using = "pageForm:containsVersions")
    public static WebElement versionsAuditStartingCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:containsValidationReport")
    public static WebElement validationsCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:containsTextMergeReport")
    public static WebElement textMergeCheckbox;
	
	public static final String PREP_CHECKBOX = "//input[@id='pageForm:containsPrepReport']";
	
	@FindBy(how = How.ID, using = "pageForm:containsUnusedReport")
    public static WebElement unusedReportCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:containsShowOtherSourcesReport")
    public static WebElement sourcesCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:containsNODAuditReport")
    public static WebElement nodReportCheckbox;

	@FindBy(how = How.ID, using = "pageForm:containsInstructionsReport")
	public static WebElement instructionsReportCheckbox;

	@FindBy(how = How.ID, using = "pageForm:containsStackingAndBlendReport")
	public static WebElement stackingAndBlendReportCheckbox;
	
	@FindBy(how = How.ID, using = "pageForm:researchReferences")
    public static WebElement researchReferencesCheckbox;

	@FindBy(how = How.ID, using = "pageForm:includeTextPreviewReport")
	public static WebElement textPreviewCheckbox;

	
	//RADIO BUTTONS
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:compareFromType']//label[contains(text(), 'most recent published document')]/../input")
	public static WebElement mostRecentPublishedDocumentRadioButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:compareFromType']//label[contains(text(), 'most recent historical version')]/../input")
    public static WebElement mostRecentHistoricalVersionRadioButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:compareFromType']//label[contains(text(), 'most recent WIP document')]/../input")
    public static WebElement mostRecentWipDocumentRadioButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:compareFromType']//label[contains(text(), 'WIP vs WIP')]/../input")
    public static WebElement wipVsWipRadioButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:reportFormat']//label[contains(text(), 'Short Same Paragraphs')]/../input")
    public static WebElement shortSameParagraphsRadioButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:reportFormat']//label[contains(text(), 'Not Same Paragraphs')]/../input")
    public static WebElement notSameParagraphsRadioButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:reportFormat']//label[contains(text(), 'All Text')]/../input")
    public static WebElement allTextRadioButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:reportFormat']//label[contains(text(), 'Ignore PubTag Change')]/../input")
    public static WebElement ignorePubTagChangeRadioButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:sortOptions']//label[contains(text(), 'Source')]/../input")
    public static WebElement sourceRadioButton;	
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:sortOptions']//label[contains(text(), 'Classification')]/../input")
    public static WebElement classificationRadioButton;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:sortOptions']//label[contains(text(), 'Date')]/../input")
    public static WebElement dateRadioButton;
}
