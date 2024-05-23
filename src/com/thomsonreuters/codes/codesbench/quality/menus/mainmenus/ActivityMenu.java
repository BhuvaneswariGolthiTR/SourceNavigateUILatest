package com.thomsonreuters.codes.codesbench.quality.menus.mainmenus;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.ActivityMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.activity.ActivityUserLogElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ActivityMenu extends Menu
{
	private WebDriver driver;

    @Autowired
    public ActivityMenu(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ActivityMenuElements.class);
    }
    
	public boolean goToUserLog()
	{
	    openMenu(ActivityMenuElements.activity);
	    sendEnterToElement(ActivityMenuElements.userLog);
		return switchToWindow(ActivityUserLogElements.ACTIVITY_USER_LOG_PAGE_TITLE);
	}
}
