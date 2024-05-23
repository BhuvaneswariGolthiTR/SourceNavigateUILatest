package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WorkflowSearchPageElements
{
	public static final String WORKFLOW_REPORTING_SYSTEM_PAGE_TITLE = "Workflow Search";

	public static final String WORKFLOW_REPORTING_STATUS_COLUMN = "//tbody/tr[@class='grid-row1']/td[9]/span";

	private static final String TABLE_ID = "//table[@id='pageForm:workflowGrid']";

	// Sorting Tabs
	@FindBy(how = How.XPATH, using = TABLE_ID + "/thead/tr[@class='grid-header'][1]/th[2]")
	public static WebElement workflowIdSort;

	@FindBy(how = How.XPATH, using = TABLE_ID + "/thead/tr[@class='grid-header'][1]/th[3]")
	public static WebElement workflowTypeSort;

	@FindBy(how = How.XPATH, using = TABLE_ID + "/thead/tr[@class='grid-header'][1]/th[4]")
	public static WebElement contentSetSort;

	@FindBy(how = How.XPATH, using = TABLE_ID + "/thead/tr[@class='grid-header'][1]/th[5]")
	public static WebElement descriptionSort;

	@FindBy(how = How.XPATH, using = TABLE_ID + "/thead/tr[@class='grid-header'][1]/th[6]")
	public static WebElement stepNameSort;

	@FindBy(how = How.XPATH, using = TABLE_ID + "/thead/tr[@class='grid-header'][1]/th[7]")
	public static WebElement startTimeSort;

	@FindBy(how = How.XPATH, using = TABLE_ID + "/thead/tr[@class='grid-header'][1]/th[8]")
	public static WebElement userSort;

	@FindBy(how = How.XPATH, using = TABLE_ID + "/thead/tr[@class='grid-header'][1]/th[9]")
	public static WebElement statusSort;

    // Filter Headers
	@FindBy(how = How.ID, using = "pageForm:workflowGrid:Filter")
	public static WebElement filterButton;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id17")
	public static WebElement workflowIdField;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id25")
	public static WebElement workflowTypeFilter;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id32")
	public static WebElement contentSetFilter;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id39")
	public static WebElement descriptionFilter;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id45")
	public static WebElement stepNameFilter;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id54")
	public static WebElement startTimeFromFilter;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id55")
	public static WebElement startTimeToFilter;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id61")
	public static WebElement userFilter;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id68")
	public static WebElement statusFilter;

    // Columns
	@FindBy(how = How.ID, using = "pageForm:workflowGrid:0:id56")
	public static WebElement startTimeText1Xpath;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:0:id19")
	public static WebElement workflowIdLink1Xpath;

    // Per Page / Page Selector
	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:workflowGrid:id72']/select[@id='id75']")
	public static WebElement rowsPerPageDropdownXpath;

	@FindBy(how = How.ID, using = "pageForm:workflowGrid:id73")
	public static WebElement numberOfRowsDisplayed;

    // Workflow Details
	@FindBy(how = How.ID, using = "pageForm:jobIdInput")
	public static WebElement jobIdTextboxXpath;

	@FindBy(how = How.XPATH, using = "//tbody/tr/td[@class='right-align']/table/tbody/tr/td/fieldset/table[@class='statusFieldset']/tbody/tr/td/span[@id='pageForm:wfi16']")
	public static WebElement currentStepStatusTextXpath;

	@FindBy(how = How.XPATH, using = "//tbody/tr[@class='grid-row1']/td[9]/span")
	public static WebElement workflowStatusXpath;

	@FindBy(how = How.XPATH, using = "//tr//span[text()='idSetUpdatedHistoricalNodesAdded']/../..//td[@class='wrapping-grid-text']//pre")
	public static WebElement updatedHistoricalNodesAddedIdSetXpath;

	@FindBy(how = How.XPATH, using = "//tbody/tr[@class='grid-row1']/td[7]/span")
	public static WebElement workflowStartTimeXpath;

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:workflowGrid']/tbody//td[7]/span")
	public static WebElement workflowModifiedDateXpath;

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:workflowGrid']//tr[@class='grid-row1'][1]//td[@class='grid-date']//span")
	public static WebElement workflowUserName;

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:workflowGrid']//tr[@class='grid-row1'][1]//td[@class='grid-image']//a//span")
	public static WebElement firstWorkflowIdXpath;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'ClassificationNorm')]")
	public static WebElement classificationnormActionXpath;

	public static final String CLASSIFICATION_NORM_ACTION_XPATH = "//span[contains(text(),'ClassificationNorm')]";

  // Workflow Properties
  public static final String PROPERTY_NAME_XPATH = "//span[text()='%s']";

  public static final String PROPERTY_LOG_VALUE_XPATH = "/../..//td[2]//span";

  public static final String PROPERTY_GENERIC_VALUE_XPATH = "/../..//td//span//pre";

  public static final String XML_EXTRACT_VALUE_XPATH =  "/../..//td//span//a";

	public static final String WORKFLOW_START_TIME_XPATH = "//tbody/tr[@class='grid-row1']/td[7]/span";//used for validating if the workflow has started in the correct cycle.

  @FindBy(how = How.XPATH, using = "//span[@id='headerForm:breadcrumbArea']/a[text()='Workflow Search']")
  public static WebElement workflowSearchBreadcrumbXpath;

	public static final String WORKFLOW_REPORTING_SYSTEM =  "(//span[text()='Workflow Reporting System'])[1]"; // Used for Source Navigate Angular

	public static final String WORKFLOW_PROPERTIES =  "//*[text()='Workflow Properties']";
	public static final String WORKFLOW_PROPERTIES_RENDITION_COUNT =  "//*[@class='grid-text']//span[text()='uuids']//following::*//pre";	
	public static final String WORKFLOW_PROPERTIES_TARGET_LOCATION_UUIDS_COUNT = "(//*[@class='grid-text']//span[text()='targetLocationUuids']//following::*//pre)[1]";
}
