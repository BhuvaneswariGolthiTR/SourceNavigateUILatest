package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InitiateNodReportPageElements
{
	@FindBy(how = How.ID, using = "pageForm:fileName")
	public static WebElement fileNameTextbox;

	@FindBy(how = How.XPATH, using = "//td[contains(text(),'Non-Selected Volumes')]/select")
	public static WebElement volumeSelector; 

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'move1to2')]")
	public static WebElement moveSelectedVolumeToTheRight; 
	
	@FindBy(how = How.XPATH, using = "//input[@value='Create Workflow']")
	public static WebElement createWorkflowButton;

	@FindBy(how = How.XPATH, using = "//input[@value='Create CWB Workflow']")
	public static WebElement createCwbWorkflowButton;
}
