package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HeadnotesPageElements
{
	@FindBy(how = How.XPATH, using = "//table//tr/th[text()='Case Serial #']//ancestor::table//tr[2]/td[count(//table//tr/th[text()='Case Serial #']/preceding-sibling::th)+1]")
	public static WebElement caseNumberField;

	public static final String BLUE_LINE_WITH_NAME_GIVEN = "//table[@id='pageForm:NAL_Table:1:indentedEntries']//tr//td//a[text()='%s']";

	public static final String CASE_INFO_FIELD = "//table//tr/th[text()='%s']//ancestor::table//tr[2]/td[count(//table//tr/th[text()='%s']/preceding-sibling::th)+1]";

	@FindBy(how = How.ID, using = "//table[@id='pageForm:headnotesTable']")
	public static WebElement headnotesTable;

	public static final String HIGHLIGHTED_HEADNOTE_IN_TREE_WITH_GIVEN_TEXT = "//table[contains(@class,'highlight1')]//td[contains(text(),'%s')]";

	public static final String HEADNOTES_TABLE_ROWS = "//table[@id='pageForm:headnotesTable']" + "/tbody/tr";

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:headnotesTable']//tr[td/input[@value='Ignore']][1]")
	public static WebElement firstUnignoredHeadnote;

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:headnotesTable']//tr[td/input[@value='Ignore']][2]")
	public static WebElement secondUnignoredHeadnote;

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:headnotesTable']//tr[td/input[@value='Ignore']][3]")
	public static WebElement thirdUnignoredHeadnote;
	
	public static final String IGNORE_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER =  "//table[@id='pageForm:headnotesTable']//tr[%s]//td//input[@value='Ignore']";
	
	public static final String UNIGNORE_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER = "//table[@id='pageForm:headnotesTable']//tr[%s]//td//input[@value='Unignore']";

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:headnotesTable']//tr[%s]//td//input[@value='Classify']")
	public static WebElement classifyHeadnoteInARowWithGivenNumber;

	public static final String CLASSIFY_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER = "//table[@id='pageForm:headnotesTable']//tr[%s]//td//input[@value='Classify']";

	public static final String UNCLASSIFY_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER = "//table[@id='pageForm:headnotesTable']//tr[%s]//td//img[@title='Remove Classification']";
	
	@FindBy(how = How.ID, using = "pageForm:headnotesTable:0:j_idt143:0:unClassifyHeadnote")
	public static WebElement firstClassificationDeletionId;

	public static final String FIRST_CLASSIFICATION_DELETION_ID = "//*[@id='pageForm:headnotesTable:0:j_idt143:0:unClassifyHeadnote']";

	@FindBy(how = How.ID, using = "pageForm:headnotesTable:0:j_idt143:1:unClassifyHeadnote")
	public static WebElement secondClassificationDeletionId;
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:headnotesTable']//tr[td/input[@value='Classify']][1]")
	public static WebElement firstUnclassifiedHeadnote;

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:headnotesTable']//tr[td/input[@value='Unignore']][1]")
	public static WebElement firstIgnoredHeadnote;

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:headnotesTable']//tr[td/img[@title='Remove Classification']][1]")
	public static WebElement firstClassifiedHeadnote;

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:headnotesTable']//tr[td/img[@title='Remove Classification']][2]")
	public static WebElement secondClassifiedHeadnote;

	public static final String HEADNOTES_PAGE_TITLE = "Headnotes";

	@FindBy(how = How.XPATH, using = "//span[@id='topPageForm:selectedBlueline']")
	public static WebElement selectedBluelineField;

   	public static final String BLUELINE_TEXT_HINTS = "//div[@class='yui-ac-content']/div/ul/li[@class]"; 
	
	@FindBy(how = How.XPATH, using = "//span[@id='topPageForm:selectedBlueline']")
	public static WebElement bluelineAnalysisCloseButton;

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:headnotesTable']//tr[td/input[@value='Ignore']][1]//td[2]//a[2]")
	public static WebElement firstHeadnoteNumber;

	public static final String HEADNOTE_WITH_NUMBER_GIVEN =  "//table[@id='pageForm:headnotesTable']//tr//td//a[text()='%s']";

	@FindBy(how = How.XPATH, using = "//form[@id='pageForm']//table[2]//tr[2]//td[10]")
	public static WebElement completedByField;

	@FindBy(how = How.XPATH, using = "//a[text()='Blueline Analysis View']")
	public static WebElement bluelineAnalysisViewLink;

	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:completedDate']")
	public static WebElement completedDateField;

	@FindBy(how = How.XPATH, using = "//input[@value='Completed By and Date']")
	public static WebElement completedByAndDateField;

	@FindBy(how = How.XPATH, using = "//table[contains(@class,'highlight1')]")
	public static WebElement highlightedItemInSearchResult;

	public static final String HIGHLIGHTED_ITEM_IN_SEARCH_RESULT = "//table[contains(@class,'highlight1')]";

	@FindBy(how = How.XPATH, using = "//input[@value='Unignore All']")
	public static WebElement unignoreAllButton;

	public static final String UNIGNORE_ALL_BUTTON_XPATH = "//input[@value='Unignore All']";
	
	public static final String UNIGNORE_BUTTON_XPATH = "//input[@value='Unignore']";

	@FindBy(how = How.XPATH, using = "//input[@value='Ignore All']")
	public static WebElement ignoreAllButton;

	public static final String IGNORE_ALL_BUTTON_XPATH = "//input[@value='Ignore All']";

	public static final String IGNORE_BUTTON_XPATH = "//input[@value='Ignore']";

	@FindBy(how = How.XPATH, using = "//input[@value='Previous Case']")
	public static WebElement previousCaseButton;

	public static final String PREVIOUS_CASE_BUTTON = "//input[@value='Previous Case']";

	@FindBy(how = How.XPATH, using = "//input[@value='Next Case']")
	public static WebElement nextCaseButton;

	public static final String NEXT_CASE_BUTTON = "//input[@value='Next Case']";

	@FindBy(how = How.XPATH, using = "//input[@value='Completed By and Date']")
	public static WebElement completedByAndDateButton;

	public static final String COMPLETED_BY_AND_DATE_BUTTON = "//input[@value='Completed By and Date']";

	@FindBy(how = How.XPATH, using = "//input[@value='Sign Out Case']")
	public static WebElement signOutCaseButton;

	public static final String SIGN_OUT_CASE_BUTTON = "//input[@value='Sign Out Case']";

	@FindBy(how = How.XPATH, using = "//input[@value='Clear Sign Out']")
	public static WebElement clearSignOutCaseButton;

	@FindBy(how = How.XPATH, using = "//tr[contains(@class,'yui-dt-')]/td[a or not(*)]")
	public static WebElement quickSearchTableContents;

	@FindBy(how = How.XPATH, using = "//input[contains(@value,'Refresh Reporter')]")
	public static WebElement refreshReporterButton;

	public static final String REFRESH_REPORTER_BUTTON = "//input[contains(@value,'Refresh Reporter')]";

	@FindBy(how = How.XPATH, using = "//input[@value = 'Completed By']")
	public static WebElement completedByButton;

	public static final String COMPLETED_BY_BUTTON = "//input[@value = 'Completed By']";

	@FindBy(how = How.ID, using = "pageForm:collapsible-trigger-3")
	public static WebElement notesCollapsibleDiv;

	public static final String NOTES_COLLAPSIBLE_DIV = "//div[@id='pageForm:collapsible-trigger-3']";

	@FindBy(how = How.ID, using = "pageForm:uneditableCaseNoteButton")
	public static WebElement editNoteButton;

	public static String EDIT_NOTE_BUTTON = "//input[@id='pageForm:uneditableCaseNoteButton']";

	@FindBy(how = How.ID, using = "pageForm:editableCaseNote")
	public static WebElement editNoteTextarea;

	@FindBy(how = How.XPATH, using = "//a[@href='subscribedCases.jsf']")
	public static WebElement subscribedCasesBreadcrumb;

	@FindBy(how = How.ID, using = "collapsible-trigger-2")
	public static WebElement synopsisHoldingCollapsibleDiv;

	public static String SYNOPSIS_HOLDING_COLLAPSIBLE_DIV = "//*[@id='collapsible-trigger-2']";
	
	@FindBy(how = How.ID, using = "collapsible-trigger-2")
	public static WebElement synopsisBackgroundCollapsibleDiv;

	public static String SYNOPSIS_BACKGROUND_COLLAPSIBLE_DIV = "//*[@id='collapsible-trigger-2']";

	@FindBy(how = How.ID, using = "collapsible-trigger-1-body")
	public static WebElement synopsisBackgroundCollapsibleBodyDiv; 

	@FindBy(how = How.ID, using = "collapsible-trigger-2-body")
	public static WebElement synopsisHoldingsCollapsibleBodyDiv; 

	@FindBy(how = How.ID, using = "pageForm:collapsible-trigger-3-body")
	public static WebElement notesCollapsibleBodyDiv; 

	public static String findResultUuidByHid = "//table//tr[td[contains(text(), '%s')]]/td[count(//table//th[text()='Node UUID']/preceding-sibling::tr) + 1]/a";
	
	public static String repealedWithGivenText = "//td[contains(@id, 'ygtvcontente') and contains(@class, 'repealed') and contains(text(), '%s')]";

	public static final String BLUELINE_CLASSIFICATION_XPATH = "//table[@id='pageForm:headnotesTable']//tr[%s]//table[@class='classificationTable']//a[contains(text(),'%s')]";

	public static final String SIGNED_OUT_BY_XPATH = "//span[@id='pageForm:signedOutBy']";

	public static final String COMPLETED_DATE_XPATH = "//span[@id='pageForm:completedDate']";
}
