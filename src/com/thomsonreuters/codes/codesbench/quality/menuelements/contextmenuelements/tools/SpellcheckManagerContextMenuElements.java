package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SpellcheckManagerContextMenuElements
{
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'remove word')]")
	public static WebElement removeWord;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'restore word')]")
	public static WebElement restoreWord;
}
