package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.statefeed;

import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;

public class ExtractedDataContextMenu extends ContextMenu
{
	private final WebDriver driver;

	public ExtractedDataContextMenu(WebDriver driver, WebDriver driver1)
	{
		super(driver);
		this.driver = driver1;
	}
}
