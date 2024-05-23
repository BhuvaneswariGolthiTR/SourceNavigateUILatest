package com.thomsonreuters.codes.codesbench.quality.pages.tools.statefeed;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed.StateFeedBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StateFeedBasePage extends BasePage
{
	private final WebDriver driver;

	@Autowired
	public StateFeedBasePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, StateFeedBasePageElements.class);
	}

	public String getContentSetValue()
	{
		//Trim in case of leading/trailing spaces in content set
		return getElementsText(StateFeedBasePageElements.pageContentSet).trim();
	}
}
