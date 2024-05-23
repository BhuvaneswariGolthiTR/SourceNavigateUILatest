package com.thomsonreuters.codes.codesbench.quality.pages.nod.administrativeopinions;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.AdministrativeOpinionsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class AdministrativeOpinionsGridPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public AdministrativeOpinionsGridPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, AdministrativeOpinionsGridPage.class);
	}

	public boolean opinionAppearedOnGrid(String opinionType, String opinionNumber, String opinionDate, String editor, String opinionText, String opinionCitation, String wlNumber)
	{
		String opinionXpath = String.format(AdministrativeOpinionsPageElements.opinionWithGivenValuesInGridXpath,
				opinionType, opinionNumber, opinionDate, editor, opinionText, opinionCitation, wlNumber);
		return doesElementExist(opinionXpath);
	}

	public void rightClickOpinionByOpinionNumber(String opinionNumber)  
	{
		rightClick(String.format(AdministrativeOpinionsPageElements.opinionTypeCell, opinionNumber));
	}
}
