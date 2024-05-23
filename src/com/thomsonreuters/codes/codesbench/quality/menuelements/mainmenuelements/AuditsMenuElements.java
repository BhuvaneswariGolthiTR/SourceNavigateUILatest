package com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AuditsMenuElements extends CommonMenuElements
{
	public static final String AUDITS_MENU = MAIN_NAVIGATOR_MENU_XPATH + "//a[text()='Audits']";

	@FindBy(how = How.XPATH, using = AUDITS_MENU)
	public static WebElement auditsMenu;

	@FindBy(how = How.LINK_TEXT, using = "Audit by Document")
	public static WebElement auditByDocument;

	@FindBy(how = How.LINK_TEXT, using = "Audit by Source")
	public static WebElement auditBySource;

	@FindBy(how = How.XPATH, using = "//a[text()='Report Central']")
	public static WebElement reportCentral;

	public static final String TWIRL_INVERTED = "//a[text()='Twirl - Inverted']";

	@FindBy(how = How.XPATH, using = TWIRL_INVERTED)
	public static WebElement twirlInverted;

	public static final String TWIRL_PUBLICATION = "//a[text()='Twirl - Publication']";

	@FindBy(how = How.XPATH, using = TWIRL_PUBLICATION)
	public static WebElement twirlPublication;
}
