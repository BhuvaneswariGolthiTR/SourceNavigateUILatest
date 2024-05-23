package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.publishing;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.DeleteNodePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class DeletePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public DeletePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, DeleteNodePageElements.class);
	}
	
	public void clickQuickLoad()
	{
		click(DeleteNodePageElements.quickLoadButton);
		waitForGridRefresh(); //There is a processing please wait -> delete this note later
		waitForPageLoaded();
		waitForElement(DeleteNodePageElements.submitButton);
	}
	
	public void clickSubmit()
	{
		click(DeleteNodePageElements.submitButton);
		waitForWindowGoneByTitle(DeleteNodePageElements.DELETE_PAGE_TITLE);
	}

	public void clickCancel()
	{
		click(DeleteNodePageElements.cancelButton);
		waitForWindowGoneByTitle(DeleteNodePageElements.DELETE_PAGE_TITLE);
		switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}

	public void clickSubmitWhenExpectingAlert()
	{
		sendEnterToElement(DeleteNodePageElements.submitButton);
	}
}
