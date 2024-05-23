package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.audits;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SetLawTrackingContextMenuElements 
{	
	@FindBy(how = How.LINK_TEXT, using = "Set Law Tracking")
	public static WebElement setLawTracking;
}
