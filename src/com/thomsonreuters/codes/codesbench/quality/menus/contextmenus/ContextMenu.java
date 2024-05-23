package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class ContextMenu extends BasePage 
{
	private WebDriver driver;
	
	public ContextMenu(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	public void openContextMenuSubMenu(WebElement element)
	{
		sendKeyToElement(element, Keys.ARROW_RIGHT);
	}
	
	public boolean isContextMenuElementDisabled(WebElement element)
	{
		return isElementDisabled(element);
	}
}
