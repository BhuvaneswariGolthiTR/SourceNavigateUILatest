package com.thomsonreuters.codes.codesbench.quality.pages.nod.administrativeopinions;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.AdministrativeOpinionsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.DeleteOpinionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class DeleteOpinionPage extends BasePage
{
	private WebDriver driver; 

	@Autowired
	public DeleteOpinionPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, DeleteOpinionPageElements.class);
	}

	public boolean clickDelete()
	{
		click(DeleteOpinionPageElements.deleteButton);
		waitForWindowGoneByTitle(DeleteOpinionPageElements.DELETE_OPINION_PAGE_TITLE);
		return switchToWindow(AdministrativeOpinionsPageElements.ADMINISTRATIVE_OPINIONS_PAGE_TITLE);
	}

	public boolean opinionAppearedInDelete(String opinionType, String opinionNumber, String opinionDate, String editor, String opinionText, String opinionCitation, String wlNumber)
	{
		String opinionXpath = String.format(DeleteOpinionPageElements.opinionWithGivenValuesInGridXpath, opinionType, opinionNumber, opinionDate, editor, opinionText, opinionCitation, wlNumber);
		return doesElementExist(opinionXpath);
	}
}
