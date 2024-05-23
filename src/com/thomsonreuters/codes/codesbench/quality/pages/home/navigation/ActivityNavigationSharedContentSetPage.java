package com.thomsonreuters.codes.codesbench.quality.pages.home.navigation;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.activity.ActivityUserLogElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ActivityNavigationSharedContentSetPage extends BasePage
{

	private WebDriver driver;

	@Autowired
    public ActivityNavigationSharedContentSetPage(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
    }

	@PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ActivityUserLogElements.class);
    }
}
