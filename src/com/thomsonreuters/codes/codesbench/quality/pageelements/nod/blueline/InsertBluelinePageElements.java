package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InsertBluelinePageElements 
{
	public static final String INSERT_BLUELINE_PAGE_TITLE = "Insert Blueline";

	public static final String SPELLCHECK_ERRORS_MESSAGE = "//span[@id='title' and contains(., 'The following words were flagged as possible spelling errors:')]";

	@FindBy(how = How.XPATH, using = "//input[@value='Finish']")
	public static WebElement finishButton;

	@FindBy(how = How.ID, using = "pageForm:blNumber")
	public static WebElement bluelineNumberInputField;

	private static final String BLUELINE_TYPE_TABLE = "//table[@id='pageForm:bluelineType']";

	@FindBy(how = How.XPATH, using = BLUELINE_TYPE_TABLE + "//input[@value='Flush']")
	public static WebElement bluelineTypeFlush; 

	@FindBy(how = How.XPATH, using = BLUELINE_TYPE_TABLE + "//input[@value='Start of Indent']")
	public static WebElement bluelineTypeStartOfIndent; 

	@FindBy(how = How.XPATH, using = BLUELINE_TYPE_TABLE + "//input[@value='Indent']")
	public static WebElement bluelineTypeIndent;

	public static final String BLUELINE_TYPE_INDENT = BLUELINE_TYPE_TABLE + "//input[@value='Indent']";

	@FindBy(how = How.ID, using = "pageForm:fbtText")
	public static WebElement bluelineTextField; 

	@FindBy(how = How.ID, using = "pageForm:indexAlso")
	public static WebElement bluelineIndexAlsoButton; 

	@FindBy(how = How.ID, using = "pageForm:ffaText")
	public static WebElement bluelineFlushAnalysisTextField; 

	public static final String BLUE_LINE_WITH_NAME_GIVEN = "//table[@id='pageForm:NAL_Table:1:indentedEntries']//tr//td//a[text()='%s']";

	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:data']//td[contains(text(),'Flush Analysis (NAL):')]/..//input")
	public static WebElement bluelineAdditonalFlushAnalysisTextFields;

	public static final String BLUELINE_ADDITIONAL_FLUSH_ANALYSIS_TEXT_FIELD = "//table[@id='pageForm:data']//td[contains(text(),'Flush Analysis (NAL):')]/..//input";

	public static final String BLUELINE_TEXT_HINTS = "//div[@class='yui-ac-content']/div/ul/li[@class]"; 

	public static final String BLUELINE_TEXT_HINT_WITH_TEXT_GIVEN = "//div[@class='yui-ac-content']/div/ul/li[text()='%s']"; 

	@FindBy(how = How.ID, using = "pageForm:soibtPreDash")
	public static WebElement bluelineStartOfIndentFirstTextField; 

	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:soibtToggle']//button")
	public static WebElement bluelineStartOfIndentFirstTextFieldDropdownButton;

	public static final String BLUELINE_START_OF_INDENT_FIRST_TEXT_FIELD_DROPDOWN_BUTTON = "//span[@id='pageForm:soibtToggle']//button";

	@FindBy(how = How.ID, using = "pageForm:soibtPostDash")
	public static WebElement bluelineStartOfIndentSecondTextField; 

	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:soibtPostToggle']//button")
	public static WebElement bluelineStartOfIndentSecondTextFieldDropdownButton;

	public static final String BLUELINE_START_OF_INDENT_SECOND_TEXT_FIELD_DROPDOWN_BUTTON = "//span[@id='pageForm:soibtPostToggle']//button";

	@FindBy(how = How.ID, using = "pageForm:soifaText")
	public static WebElement bluelineStartOfIndentFirstFlushAnalysisTextField;

	@FindBy(how = How.ID, using = "pageForm:indentAnalysisText")
	public static WebElement bluelineStartOfIndentSecondFlushAnalysisTextField;

	@FindBy(how = How.ID, using = "pageForm:parentBlueline")
	public static WebElement bluelineIndentParentBlValue;

	@FindBy(how = How.ID, using = "pageForm:ibtIndentedText")
	public static WebElement bluelineIndentTextField;

	public static final String BLUELINE_INDENT_TEXT_FIELD = "//*[@id='pageForm:ibtIndentedText']";

	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:indentToggle']//button")
	public static WebElement bluelineIndentTextFieldDropdownButton;

	public static final String BLUELINE_INDENT_TEXT_FIELD_DROPDOWN_BUTTON = "//span[@id='pageForm:indentToggle']//button";

	@FindBy(how = How.ID, using = "pageForm:btpb")
	public static WebElement bluelineIndentSecondTextField;

	@FindBy(how = How.ID, using = "pageForm:ifaText")
	public static WebElement bluelineIndentFirstFlushAnalysis;

	@FindBy(how = How.ID, using = "pageForm:ifaParentBlueline")
	public static WebElement bluelineIndentSecondFlushAnalysis;

	@FindBy(how = How.ID, using = "pageForm:indentAnalysisText")
	public static WebElement bluelineIndentIndentAnalysis;

	public static final String BLUELINE_INDENT_ANALYSIS = "//*[@id='pageForm:indentAnalysisText']";
}
