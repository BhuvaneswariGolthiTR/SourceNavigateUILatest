package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.nod;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.nod.SubscribedCasesContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.SubscribedCasesPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SubscribedCasesContextMenu extends ContextMenu
{
	WebDriver driver;

	@Autowired
	public SubscribedCasesContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SubscribedCasesContextMenuElements.class);
	}

	public void signOutCase()
	{
		click(SubscribedCasesContextMenuElements.signOut);
		waitForGridRefresh();
		waitForPageLoaded();
	}

	public void clearCaseSignOut(String initials)
	{
		String expectedMessage = String.format("Are you sure you want to undo the sign out by '%s'.", initials);
		click(SubscribedCasesContextMenuElements.clearSignOut);
		boolean expectedMessageAppeared = AutoITUtils.verifyAlertTextContainsAndAccept(true, expectedMessage);
		Assertions.assertTrue(expectedMessageAppeared, "The expected clear case sign out message appeared.");
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
    public boolean openPropertiesOnCaseSerialNumber()
    {
    	sendEnterToElement(SubscribedCasesContextMenuElements.properties);
    	boolean reportedCasePropertiesWindowOpened = switchToWindow(SubscribedCasesPropertiesPageElements.SUBSCRIBED_CASES_PROPERTIES_PAGE_TITLE);
		enterTheInnerFrame();
		return reportedCasePropertiesWindowOpened;
    }
}
