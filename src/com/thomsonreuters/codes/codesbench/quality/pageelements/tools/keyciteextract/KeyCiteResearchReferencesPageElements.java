package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.keyciteextract;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class KeyCiteResearchReferencesPageElements
{
	public static final String KEYCITE_RESEARCH_REFERENCES_PAGE_URL = "kcResearchRefs";
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:nonSelectedTitles")
	public static WebElement availableAnalyticalTitlesSelect;

	@FindBy(how = How.ID, using = "pageForm:pageForm:addOneTitleButton")
	public static WebElement addOneAnalyticalTitleButton;

	@FindBy(how = How.ID, using = "pageForm:pageForm:selectedTitles")
	public static WebElement selectedAnalyticalTitlesSelect;

	@FindBy(how = How.ID, using = "pageForm:pageForm:updateDefaultTitlesButton")
	public static WebElement saveDefaultTitlesButton;

	@FindBy(how = How.NAME, using = "pageForm:pageForm:nonSelectedVols")
	public static WebElement availableVolumesSelect;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:volTitles")
	public static WebElement selectedVolumesSelect;
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:addOneVolButton")
	public static WebElement addOneVolumeButton;

	@FindBy(how = How.NAME, using = "pageForm:pageForm:j_idt55")
	public static WebElement submitButton;

	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:pageForm:workflowMessage' and contains(text(),'The workflow has been queued. The workflow ID')]")
	public static WebElement workflowStartedElement;

	public static final String WORKFLOW_STARTED_ELEMENT = "//span[@id='pageForm:pageForm:workflowMessage' and contains(text(),'The workflow has been queued. The workflow ID')]";
	
	@FindBy(how = How.ID, using = "pageForm:pageForm:removeAllTitleButton")
	public static WebElement removeAllAnalyticalTitlesButton;

	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:pageForm:workflowErrorMessage' and contains(text(),'Workflow submission error! Please select at least one Volume.')]")
	public static WebElement workflowVolumeError;

	public static final String WORKFLOW_VOLUME_ERROR = "//span[@id='pageForm:pageForm:workflowErrorMessage' and contains(text(),'Workflow submission error! Please select at least one Volume.')]";

	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:pageForm:workflowMessage']")
	public static WebElement workflowMessage;

	public static final String WORKFLOW_ERROR_MESSAGE = "//span[@id='pageForm:pageForm:workflowErrorMessage']";

	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:pageForm:workflowErrorMessage' and contains(text(),'Workflow submission error! Please select at least one Analytical Title.')]")
	public static WebElement workflowAnalyticalTitleError;

	public static final String WORKFLOW_ANALYTICAL_TITLE_ERROR = "//span[@id='pageForm:pageForm:workflowErrorMessage' and contains(text(),'Workflow submission error! Please select at least one Analytical Title.')]";
}
