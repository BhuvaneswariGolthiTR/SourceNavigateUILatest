package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.base.BaseFindPageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FindAndReplacePageElements extends BaseFindPageElements
{
	public static final String PAGE_TITLE = "Find And Replace";

	@FindBy(how = How.XPATH, using = "//input[@id='replace']")
	public static WebElement replaceInputField;
	
	@FindBy(how = How.XPATH, using = "//button[@id='b_replace']")
	public static WebElement replaceButton;
	
	@FindBy(how = How.XPATH, using = "//button[@id='b_replaceAll']")
	public static WebElement replaceAllButton;
}
