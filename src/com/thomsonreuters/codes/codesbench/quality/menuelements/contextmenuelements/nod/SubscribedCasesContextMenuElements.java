package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.nod;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SubscribedCasesContextMenuElements 
{
	public static final String SUBSCRIBED_CASES_CONTEXT_MENU = "//div[@id='mycontextmenu']";

    @FindBy(how = How.XPATH, using = SUBSCRIBED_CASES_CONTEXT_MENU + "//a[text()='Sign Out']")
   	public static WebElement signOut;

    @FindBy(how = How.XPATH, using = SUBSCRIBED_CASES_CONTEXT_MENU + "//a[text()='Clear Sign Out']")
   	public static WebElement clearSignOut;

    @FindBy(how = How.XPATH, using = SUBSCRIBED_CASES_CONTEXT_MENU + "//a[text()='Properties']")
   	public static WebElement properties;
}
