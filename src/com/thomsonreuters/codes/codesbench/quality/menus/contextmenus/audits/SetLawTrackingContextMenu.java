package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.audits;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.audits.SetLawTrackingContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentClosurePageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SetLawTrackingContextMenu extends ContextMenu
{
	private WebDriver driver;
	
	@Autowired
	public SetLawTrackingContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SetLawTrackingContextMenuElements.class);
	}
	
	public void setLawTracking()
	{
		click(SetLawTrackingContextMenuElements.setLawTracking);
		switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
	}
}
