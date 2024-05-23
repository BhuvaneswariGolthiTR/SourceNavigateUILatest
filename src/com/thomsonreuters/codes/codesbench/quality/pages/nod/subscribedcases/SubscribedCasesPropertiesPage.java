package com.thomsonreuters.codes.codesbench.quality.pages.nod.subscribedcases;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class SubscribedCasesPropertiesPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public SubscribedCasesPropertiesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		
	}

	public void clickCancel() 
	{
		click(CommonPageElements.CANCEL_BUTTON);
	}
}
