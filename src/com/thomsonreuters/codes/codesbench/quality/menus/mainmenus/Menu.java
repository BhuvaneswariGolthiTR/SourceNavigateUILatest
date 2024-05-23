package com.thomsonreuters.codes.codesbench.quality.menus.mainmenus;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Menu extends BasePage
{
	private WebDriver driver;
	
	public Menu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	public void openMenu(WebElement element)
	{
		sendKeyToElement(element, Keys.ARROW_DOWN);
	}
	
	public void openSubMenu(WebElement element)
	{
		sendKeyToElement(element, Keys.ARROW_RIGHT);
	}
	
	public boolean isSubMenuItemAvailable(WebElement menu, String subMenu)
	{
		openMenu(menu);
		return isElementDisplayed(subMenu);
	}
}
