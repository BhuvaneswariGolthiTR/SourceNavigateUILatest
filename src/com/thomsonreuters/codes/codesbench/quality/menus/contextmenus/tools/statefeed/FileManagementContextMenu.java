package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.statefeed;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.statefeed.FileManagementContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed.FileManagementPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FileManagementContextMenu extends ContextMenu
{
	private final WebDriver driver;

	@Autowired
	public FileManagementContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, FileManagementContextMenuElements.class);
	}

	public void delete()
	{
		click(FileManagementContextMenuElements.DELETE_XPATH);
		waitForElementExists(FileManagementPageElements.DELETE_FILE_CONFIRMATION_POPUP_XPATH);
	}
}
