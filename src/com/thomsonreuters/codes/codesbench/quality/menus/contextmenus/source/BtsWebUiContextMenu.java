package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.BtsWebUiContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BtsWebUiContextMenu extends ContextMenu
{
private WebDriver driver;
	
	@Autowired
	public BtsWebUiContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, BtsWebUiContextMenuElements.class);
	}
	
	
	public void delete()
	{
//		Actions action = new Actions(driver());
//    	action.moveToElement(getElement(BtsWebUiContextMenuElements.delete));
//    	action.click()
//    	.build().perform();	
		click(BtsWebUiContextMenuElements.delete);
    	waitForPageLoaded();

	}
	
}
