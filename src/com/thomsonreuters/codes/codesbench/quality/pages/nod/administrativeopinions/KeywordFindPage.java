package com.thomsonreuters.codes.codesbench.quality.pages.nod.administrativeopinions;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.administrativeopinions.EditOpinionPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class KeywordFindPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public KeywordFindPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditOpinionPageElements.class);
	}
}
