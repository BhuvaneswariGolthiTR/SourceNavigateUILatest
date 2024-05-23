package com.thomsonreuters.codes.codesbench.quality.pages.tools.stocknotemanager;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteHotKeysPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class StocknoteHotKeysPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public StocknoteHotKeysPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, StocknoteHotKeysPageElements.class);
	}
	
	/**
	 * Clears current key then inputs the given one.
	 *
	 * @param key
	 */
	public void setKey(String key)
	{
		clearAndSendTextToTextbox(StocknoteHotKeysPageElements.keyInputBox, key);
		waitForPageLoaded();
	}
	
	/**
	 * Selects shift-alt option.
	 */
	public void selectShiftAlt()
	{
		click(StocknoteHotKeysPageElements.shiftAltSelector);
		waitForPageLoaded();
	}
	
	/**
	 * Selects alt option.
	 */
	public void selectAlt()
	{
		click(StocknoteHotKeysPageElements.altSelector);
		waitForPageLoaded();
	}
	
	/**
	 * Sets shift + alt + given char hotkey.
	 *
	 * @param key
	 */
	public void setShiftAltAndChar(String key)
	{
		selectShiftAlt();
		setKey(key);
	}
	
	/**
	 * Sets alt + given char hotkey.
	 *
	 * @param key
	 */
	public void setAltAndChar(String key)
	{
		selectAlt();
		setKey(key);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
	}
	
	/**
	 * Clicks save and accepts the alert if there is one.
	 *
	 * @throws 
	 */
	public void saveAndIgnoreAlert()  
	{
		sendEnterToElement(StocknoteHotKeysPageElements.saveButton);
		acceptAlertNoFail();
		waitForWindowGoneByTitle(StocknoteHotKeysPageElements.STOCKNOTE_HOT_KEY_SETUP_PAGE_TITLE);
		switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
		waitForGridRefresh();
	}
	
	/**
	 * Saves and checks if the alert text matches the given text.
	 *
	 * @param expectedText
	 * @return
	 */
	public boolean saveAndCheckAlertValidHotKey(String expectedText)  
	{
		click(StocknoteHotKeysPageElements.saveButton);
		boolean correctAlert = checkAlertTextMatchesGivenText(expectedText);
		switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
		waitForGridRefresh();
		return correctAlert;
	}
	
	/**
	 * Saves and checks if the alert text matches the given text.
	 *
	 * @param expectedText
	 * @return
	 */
	public boolean saveAndCheckAlertInvalidHotKey(String expectedText)  
	{
		click(StocknoteHotKeysPageElements.saveButton);
		boolean correctAlert = checkAlertTextMatchesGivenText(expectedText);
		switchToWindow(StocknoteHotKeysPageElements.STOCKNOTE_HOT_KEY_SETUP_PAGE_TITLE, false);
		return correctAlert;
	}
	
	/**
	 * Saves and accepts the alert. Fails if there is not an alert.
	 *
	 * @throws 
	 */
	public void saveAndAcceptAlert()  
	{
		click(StocknoteHotKeysPageElements.saveButton);
		acceptAlert();
		switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
		waitForGridRefresh();
	}
	
	/**
	 * Clicks cancel button.
	 *
	 * @throws 
	 */
	public void cancel()  
	{
		click(StocknoteHotKeysPageElements.cancelButton);
		switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
		waitForGridRefresh();
	}
	
	/**
	 * Checks if the given hot key matches in the window.
	 *
	 * @param hotKey
	 * @return True if it matches, false if not.
	 */
	public boolean doesHotKeyMatch(String hotKey)
	{
		waitForElementExists(StocknoteHotKeysPageElements.hotKeyCombination);
		return getElementsText(StocknoteHotKeysPageElements.hotKeyCombination).equals(hotKey);
	}
	
	public boolean switchToStocknoteHotKeySetupPage()
	{
		return switchToWindow(StocknoteHotKeysPageElements.STOCKNOTE_HOT_KEY_SETUP_PAGE_TITLE);
	}
	
	/**
	 * Sets the hot key from the given string. The following are the valid formats: Shift-Alt-Z and
	 * Alt-Z. 'Z' can be changed to the desired key.
	 *
	 * @param hotKeyAsString
	 */
	public void setHotkeyFromString(String hotKeyAsString)
	{
		String keys[] = hotKeyAsString.split("-");
		
		if(keys.length == 3 && keys[0].contentEquals("Shift") && keys[1].equals("Alt"))
		{
			selectShiftAlt();
			setKey(keys[2]);
		}
		else if (keys.length == 2 && keys[0].equals("Alt"))
		{
			selectAlt();
			setKey(keys[1]);
		}
		else
		{
			Assertions.fail(hotKeyAsString + " is not in the valid hot key as string form");
		}
	}
}
