package com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbydocument;

import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.CommonAuditsPageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AuditByDocumentPageElements extends CommonAuditsPageElements
{
	public static final String AUDIT_BY_DOCUMENT_PAGE_TITLE = "Audit By Document";
	public static final String NON_SELECTED_DOCUMENTS_LIST_XPATH = "//select[@id='pageForm:nonSelectedDocsList']";
	public static final String NON_SELECTED_VOLUME_LIST_OPTIONS_XPATH = "//select[@id='pageForm:nonSelectedVolsList']";

	public static final String LABEL_VOLUMES_XPATH = "//span[text()='Volume(s)']";

	public static final String LABEL_SELECTED_VOLUMES_XPATH = "//span[text()='Selected Volume(s)']";

	public static final String LABEL_SEARCH_OPTIONS_XPATH = "//span[text()='Search Options']";

	public static final String USER_TO_REPORT_ON_DROPDOWN_XPATH = "//select[@name='pageForm:userName']";
	public static final String VOLUME_ITEM = "//option[@value='%s']";

	@FindBy(how = How.XPATH, using = "//select[@id='pageForm:nonSelectedVolsList']")
	public static WebElement nonSelectedVolsList;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:addAllVolsButton']")
	public static WebElement addAllButton;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:reportName']")
	public static WebElement auditIdentifierTextBox;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:next']")
	public static WebElement nextButton;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:processAudit']")
	public static WebElement processAudit;

	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:addOneVolsButton']")
	public static WebElement addOneVolumeButton;

	@FindBy(how = How.ID, using = "pageForm:researchReferences")
	public static WebElement researchReferencesCheckbox;

	@FindBy(how = How.XPATH, using = "//input[@value='PREVIOUS_WIP']")
	public static WebElement mostRecentWIPDocumentRadioButton;

	@FindBy(how = How.ID, using = "pageForm:cutoffDate")
	public static WebElement cutOffDateEntry;
}
