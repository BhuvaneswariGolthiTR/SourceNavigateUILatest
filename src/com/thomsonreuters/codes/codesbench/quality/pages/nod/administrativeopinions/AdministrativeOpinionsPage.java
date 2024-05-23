package com.thomsonreuters.codes.codesbench.quality.pages.nod.administrativeopinions;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.AdministrativeOpinionsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.EditOpinionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.InsertOpinionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdministrativeOpinionsPage extends BasePage
{	
	private WebDriver driver;

	@Autowired
	public AdministrativeOpinionsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, AdministrativeOpinionsPageElements.class);
	}

	public boolean clickInsertOpinion()  
	{
		click(AdministrativeOpinionsPageElements.insertOpinionButton);
		return switchToWindow(InsertOpinionPageElements.INSERT_OPINION_PAGE_TITLE);
	}

	public boolean clickOpinionNumber(String opinionNumber)  
	{
		click(String.format(AdministrativeOpinionsPageElements.clickableOpinionNumber, opinionNumber));
		return switchToWindow(EditOpinionPageElements.EDIT_OPINION_PAGE_TITLE);
	}

	public boolean switchToPage()
	{
		return switchToWindow(AdministrativeOpinionsPageElements.ADMINISTRATIVE_OPINIONS_PAGE_TITLE, true);
	}
}
