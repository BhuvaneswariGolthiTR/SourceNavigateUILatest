package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.audits;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReportCentralContextMenuElements 
{
	@FindBy(how = How.XPATH, using = "//a[text()='Delete']")
	public static WebElement delete;
}
