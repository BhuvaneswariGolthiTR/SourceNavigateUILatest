package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.base;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BaseFindPageElements
{
	@FindBy(how = How.XPATH, using = "//input[@id='find']")
	public static WebElement findInputField;
	
	@FindBy(how = How.XPATH, using = "//input[@id='case']")
	public static WebElement caseSensitiveCheckBox;
	
	@FindBy(how = How.XPATH, using = "//button[@id='b_find']")
	public static WebElement findButton;

	@FindBy(how = How.XPATH, using = "//button[@id='b_cancel']")
	public static WebElement closeButton;

	@FindBy(how = How.XPATH, using = "//button[@id='b_markup']")
	public static WebElement addMarkupButton;

	@FindBy(how = How.XPATH, using = "//input[@id='replace']")
	public static WebElement replaceInputField;
}
