package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.audits;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.audits.ReportCentralContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ReportCentralContextMenu extends ContextMenu
{
	private WebDriver driver;
	
	@Autowired
	public ReportCentralContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ReportCentralContextMenuElements.class);
	}
	
	public void delete()
	{
		sendEnterToElement(ReportCentralContextMenuElements.delete);
	}
}
