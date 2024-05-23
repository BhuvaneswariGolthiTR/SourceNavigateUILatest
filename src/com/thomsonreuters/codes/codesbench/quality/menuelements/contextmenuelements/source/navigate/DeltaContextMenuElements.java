package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeltaContextMenuElements 
{
	public static final String CONTEXT_MENU = "//div[@id='contextMenu']/div/ul/li";
	public static final String CONTEXT_MENU_EDIT = CONTEXT_MENU + "/div[@id='edit']/div/ul/li";
	public static final String CONTEXT_MENU_VIEW = CONTEXT_MENU + "/div[@id='view']/div/ul/li";
	public static final String CONTEXT_MENU_MODIFY = CONTEXT_MENU + "/div[@id='modify']/div/ul/li";
	public static final String CONTEXT_MENU_TRACK = CONTEXT_MENU + "/div[@id='track']/div/ul/li";
	public static final String CONTEXT_MENU_REPORT = CONTEXT_MENU + "/div[@id='report']/div/ul/li";

	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "/a[text()='Edit']")
	public static WebElement edit;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_EDIT + "/a[text()='Integration Properties']")
	public static WebElement editIntegrationProperties;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_EDIT + "/a[text()='Delta Notes']")
	public static WebElement editDeltaNotes;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_EDIT + "/a[text()='Effective Date']")
	public static WebElement editEffectiveDate;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_EDIT + "/a[text()='Difficulty Level']")
	public static WebElement editDifficultyLevel;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_EDIT + "/a[text()='Target Content']")
	public static WebElement editTargetContent;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "/a[text()='View']")
	public static WebElement view;
	
	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "/a[text()='Modify']")
	public static WebElement modify;
	
	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "/a[text()='Track']")
	public static WebElement track;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_TRACK + "/a[text()='Images Sent Out']")
	public static WebElement trackImagesSentOut;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_TRACK + "/a[text()='Images Completed']")
	public static WebElement trackImagesCompleted;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_TRACK + "/a[text()='Tabular Requested']")
	public static WebElement trackTabularRequested;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_TRACK + "/a[text()='Tabular Started']")
	public static WebElement trackTabularStarted;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_TRACK + "/a[text()='Tabular Completed']")
	public static WebElement trackTabularCompleted;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "/a[text()='Report']")
	public static WebElement report;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_REPORT + "/a[text()='Integration Results']")
	public static WebElement reportIntegrationResults;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_VIEW + "/a[text()='Target Content']")
	public static WebElement viewTargetContent;
	
	@FindBy(how = How.XPATH, using = CONTEXT_MENU_VIEW + "/a[text()='Target In Hierarchy']")
	public static WebElement viewTargetInHierarchy;
	
	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement headerImagine;
	
	@FindBy(how = How.XPATH, using = CONTEXT_MENU_VIEW + "/a[text()='Deltas Affecting Same Target']")
	public static WebElement viewDeltasAffectingSameTarget;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_MODIFY + "/a[text()='Reset Integration Status']")
	public static WebElement modifyResetIntegrationStatus;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_MODIFY + "/a[text()='Integrate']")
	public static WebElement modifyIntegrate;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_MODIFY + "/a[text()='Cite Locate']")
	public static WebElement modifyCiteLocate;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_MODIFY + "/a[text()='Assign User']")
	public static WebElement modifyAssignUser;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_MODIFY + "/a[text()='Insert Into Hierarchy Wizard']")
	public static WebElement insertIntoHierarchyWizard;

	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'view') and text()='Delta(s)']")
	public static WebElement delta;
	
	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "/a[text()='Delta Properties']")
	public static WebElement deltaProperties;
	
	@FindBy(how = How.XPATH, using = CONTEXT_MENU_VIEW + "/a[text()='Delta' or text()='Deltas']")
	public static WebElement viewDelta;
		
	@FindBy(how = How.XPATH, using = CONTEXT_MENU_VIEW + "/a[text()='Delta Notes']")
	public static WebElement viewDeltaNotes;  

	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "/a[text()='Validate']")
	public static WebElement validate;

	public static final String DELTA = CONTEXT_MENU + "//a[text()='Delta']";

	public static final String VIEW_DELTA = CONTEXT_MENU_VIEW + "//a[text()='Delta']";
}
