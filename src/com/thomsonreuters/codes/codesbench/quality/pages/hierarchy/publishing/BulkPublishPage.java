package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.BulkPublishPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BulkPublishPage extends BasePage
{
	WebDriver driver;

	public BulkPublishPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, BulkPublishPageElements.class);
	}
	
	public void clickPublishButton()
	{
		sendEnterToElement(CommonPageElements.PUBLISH_BUTTON_XPATH);
		waitForWindowGoneByTitle(BulkPublishPageElements.BULK_PUBLISH_PAGE_TITLE);
		switchToWindow(YourWorkflowHasBeenCreatedPageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

//	public boolean switchToWindow(String wordsInTitle)
//	{
//		return switchToWindow(wordsInTitle, true);
//	}


	public void clickCloseButton()
	{
		click(CommonPageElements.CLOSE_BUTTON);
	}

	public void checkFileCleanupCheckBox()
	{
		click(BulkPublishPageElements.fileCleanupCheckbox);
	}

	public void checkBermudaCheckBox()
	{
		click(BulkPublishPageElements.bermudaCheckbox);
	}

	public void checkNovusDocFamilyCheckBox()
	{
		click(BulkPublishPageElements.novusDocFamilyCheckbox);
	}

	public void checkNovusNormCheckBox()
	{
		click(BulkPublishPageElements.novusNormCheckbox);
	}
}
