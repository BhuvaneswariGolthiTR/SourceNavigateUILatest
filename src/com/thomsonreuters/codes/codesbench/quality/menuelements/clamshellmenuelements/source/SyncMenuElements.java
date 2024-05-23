package com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SyncMenuElements 
{
	@FindBy(how = How.XPATH, using = "//div[contains(@id,'syncClamshellDiv')]//div[contains(@onclick, 'Sync')]")
	public static WebElement sync;

	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;
}
