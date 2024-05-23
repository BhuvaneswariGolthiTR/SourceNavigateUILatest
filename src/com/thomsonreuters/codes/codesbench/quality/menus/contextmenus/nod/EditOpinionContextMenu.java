package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.nod;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.nod.EditOpinionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditOpinionContextMenu extends ContextMenu
{
	WebDriver driver;
	
	@Autowired
	public EditOpinionContextMenu(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditOpinionContextMenuElements.class);
	}
	
	public boolean viewContent()
	{
		click(EditOpinionContextMenuElements.viewContent);
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}
	
	public boolean editContent()
	{
		click(EditOpinionContextMenuElements.editContent);
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}
}
