package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.nod;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.nod.AdministrativeOpinionsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.DeleteOpinionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.EditOpinionPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdministrativeOpinionsContextMenu extends ContextMenu
{
	WebDriver driver;
	
	@Autowired
	public AdministrativeOpinionsContextMenu(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, AdministrativeOpinionsContextMenuElements.class);
	}

	public boolean editOpinion()
	{
		sendEnterToElement(AdministrativeOpinionsContextMenuElements.editOpinion);
		return switchToWindow(EditOpinionPageElements.EDIT_OPINION_PAGE_TITLE);
	}
	
	public boolean deleteOpinion()
	{
		sendEnterToElement(AdministrativeOpinionsContextMenuElements.deleteOpinion);
		return switchToWindow(DeleteOpinionPageElements.DELETE_OPINION_PAGE_TITLE);
	}
}
