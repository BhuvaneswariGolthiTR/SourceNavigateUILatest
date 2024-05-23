package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.SpellcheckManagerContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpellcheckManagerContextMenu extends ContextMenu
{
	private WebDriver driver;
	
	@Autowired
	public SpellcheckManagerContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SpellcheckManagerContextMenuElements.class);
	}

	public void removeWord()
	{
		click(SpellcheckManagerContextMenuElements.removeWord);
	}

	public void restoreWord()
	{
		click(SpellcheckManagerContextMenuElements.restoreWord);
	}
}
