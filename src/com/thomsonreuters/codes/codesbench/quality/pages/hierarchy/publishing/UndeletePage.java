package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.publishing;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.UndeleteNodePageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class UndeletePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public UndeletePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, UndeleteNodePageElements.class);
	}
	
	public void pressQuickLoad()
	{
		click(UndeleteNodePageElements.quickLoadTrackingButton);
		waitForPageLoaded();
		waitForElement(UndeleteNodePageElements.submitButton);
	}
	
	public void pressSubmit()
	{
		sendEnterToElement(UndeleteNodePageElements.submitButton);
		waitForWindowGoneByTitle(UndeleteNodePageElements.UNDELETE_PAGE_TITLE);
		switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}
}
