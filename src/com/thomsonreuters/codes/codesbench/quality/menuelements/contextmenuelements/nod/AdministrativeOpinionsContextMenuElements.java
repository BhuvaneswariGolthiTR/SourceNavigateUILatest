package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.nod;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdministrativeOpinionsContextMenuElements 
{
	private static final String ADMINISTRATIVE_OPINIONS_CONTEXT_MENU = "//div[@id='mycontextmenu']";

	@FindBy(how = How.XPATH, using = ADMINISTRATIVE_OPINIONS_CONTEXT_MENU + "//a[text()='Edit Opinion']")
	public static WebElement editOpinion;
	
	@FindBy(how = How.XPATH, using = ADMINISTRATIVE_OPINIONS_CONTEXT_MENU + "//a[text()='Delete Opinion']")
	public static WebElement deleteOpinion;
	
	@FindBy(how = How.XPATH, using = ADMINISTRATIVE_OPINIONS_CONTEXT_MENU + "//a[text()='Copy Opinion Information']")
	public static WebElement copyOpinionInformation;
	
	@FindBy(how = How.XPATH, using = ADMINISTRATIVE_OPINIONS_CONTEXT_MENU + "//a[text()='Set Completed Date']")
	public static WebElement setCompletedDate;

	@FindBy(how = How.XPATH, using = ADMINISTRATIVE_OPINIONS_CONTEXT_MENU + "//a[text()='Clear Completed Date']")
	public static WebElement clearCompletedDate;
}
