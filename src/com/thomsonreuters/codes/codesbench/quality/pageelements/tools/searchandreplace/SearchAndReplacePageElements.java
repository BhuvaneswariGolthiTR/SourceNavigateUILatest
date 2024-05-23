package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SearchAndReplacePageElements
{
	public static final String SAR_PAGE_TITLE_UPPERCASE = "SAR";
	public static final String SAR_PAGE_TITLE_LOWERCASE = "sar";
	public static final String VIEW_REPORT_LINK_XPATH = "//table[@id='pageForm:runDetailsView']/tbody/tr/td/input[@value='View Report']";


	@FindBy(how = How.XPATH, using = "//a[contains(text(),'magellan3.int.westgroup.com') or contains(@href, 'magellan3.int.westgroup.com')]")
	public static WebElement workflowLink;


	public static final String COMMITTING_CHANGES_LINK_XPATH = "//table[@id='pageForm:runDetailsView']/tbody/tr/td/a[contains(text(), 'Committing Changes')]";
	public static final String COMMITTED_LINK_XPATH = "//table[@id='pageForm:runDetailsView']/tbody/tr/td/a[contains(text(), 'Committed')]";
	public static final String SAVE_TABLE_BUTTON = "//*[@id='pageForm:saveTable']";
	public static final String TABEL_WITH_GIVEN_NAME = "//div[@class='ygtvchildren']//span[contains(text(),'%s')]";

	@FindBy(how = How.ID, using = "ygtvt2")
    public static WebElement tableTabExpander;

	@FindBy(how = How.ID, using = "ygtvlabelel2")
    public static WebElement tableTab;

	@FindBy(how = How.ID, using = "ygtvcontentel2")
	public static WebElement tables;

	@FindBy(how = How.ID, using = "pageForm:tableName")
    public static WebElement tableName;

	@FindBy(how = How.ID, using = "pageForm:tableDescription")
    public static WebElement tableDescription;

	@FindBy(how = How.ID, using = "pageForm:table")
	public static WebElement searchAndReplaceTableDropdown;

	@FindBy(how = How.ID, using = "pageForm:createTable")
    public static WebElement createTableButton;

	@FindBy(how = How.ID, using = "pageForm:clearTable")
    public static WebElement clearTableButton;

	@FindBy(how = How.ID, using = "pageForm:newTableEntry")
    public static WebElement newEntryTableButton;

	public static final String NEW_ENTRY_TABLE_BUTTON = "//*[@id='pageForm:newTableEntry']";

	@FindBy(how = How.ID, using = "pageForm:copyTable")
    public static WebElement copyTableButton;

	public static final String COPY_TABLE_BUTTON = "//*[@id='pageForm:copyTable']";

	@FindBy(how = How.ID, using = "pageForm:deleteTableButton")
    public static WebElement deleteTableButton;

	public static final String DELETE_TABLE_BUTTON = "//*[@id='pageForm:deleteTableButton']";

	@FindBy(how = How.ID, using = "pageForm:copyTabletoAnotherJuris")
    public static WebElement copyToAnotherContentSetTableButton;

	public static final String COPY_TO_ANOTHER_CONTENT_SET_TABLE_BUTTON = "//*[@id='pageForm:copyTabletoAnotherJuris']";

	@FindBy(how = How.ID, using = "pageForm:FirstEntry")
    public static WebElement firstEntryTableButton;

	public static final String FIRST_ENTRY_TABLE_BUTTON = "//*[@id='pageForm:FirstEntry']";

	@FindBy(how = How.ID, using = "pageForm:LastEntry")
    public static WebElement lastEntryTableButton;

	public static final String LAST_ENTRY_TABLE_BUTTON = "//*[@id='pageForm:LastEntry']";

	@FindBy(how = How.ID, using = "pageForm:unlockTable")
    public static WebElement forceUnlockTableButton;

	public static final String FORCE_UNLOCK_TABLE_BUTTON = "//*[@id='pageForm:unlockTable']";

	@FindBy(how = How.ID, using = "pageForm:searchPhrase")
	public static WebElement searchPhraseTextbox;

	@FindBy(how = How.ID, using = "pageForm:replacePhrase")
	public static WebElement replacePhraseTextbox;

	@FindBy(how = How.XPATH, using = "//input[@value='Save']")
	public static WebElement saveButton;

	@FindBy(how = How.ID, using = "pageForm:runButton")
	public static WebElement runButton;

	@FindBy(how = How.ID, using = "pageForm:tableEntryName")
	public static WebElement entryNameTextbox;

	@FindBy(how = How.ID, using = "pageForm:j_idt63:2")
	public static WebElement searchAndReplaceRadioButton;

	@FindBy(how = How.XPATH, using = "//input[@name='pageForm:j_idt69']")
	public static WebElement textFilesCheckbox;

	@FindBy(how = How.XPATH, using = "//input[@value='Refresh']")
	public static WebElement refreshButton;

}
