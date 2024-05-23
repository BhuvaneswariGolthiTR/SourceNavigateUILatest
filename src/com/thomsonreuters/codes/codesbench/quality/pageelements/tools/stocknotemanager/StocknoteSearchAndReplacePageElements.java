package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StocknoteSearchAndReplacePageElements
{
	public static final String STOCKNOTE_SEARCH_AND_REPLACE_PAGE_TITLE = "Stocknote Search and Replace";
	
	@FindBy(how = How.ID, using = "j_idt6:searchValue")
	public static WebElement searchValueTextBox;
	
	@FindBy(how = How.ID, using = "j_idt6:replaceValue")
	public static WebElement replaceValueTextBox;

	@FindBy(how = How.ID, using = "j_idt6:j_idt16")
	public static WebElement previewChangesButton;

	@FindBy(how = How.XPATH, using = "//tbody/tr[@class='report-table-even-row'][2]/td[1]/div")
	public static WebElement beforeChangeText;
	
	@FindBy(how = How.XPATH, using = "//tbody/tr[@class='report-table-even-row'][2]/td[2]/div")
	public static WebElement afterChangeText;

	@FindBy(how = How.ID, using = "j_idt6:j_idt26")
	public static WebElement applyChangesButton;
}
