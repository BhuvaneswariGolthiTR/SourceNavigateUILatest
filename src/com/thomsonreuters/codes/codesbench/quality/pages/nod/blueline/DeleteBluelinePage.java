package com.thomsonreuters.codes.codesbench.quality.pages.nod.blueline;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline.DeleteBluelinePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class DeleteBluelinePage extends BasePage
{
	WebDriver driver;

	@Autowired
	public DeleteBluelinePage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, DeleteBluelinePageElements.class);
	}

	public boolean switchToDeleteBluelineWindow()
	{
		return switchToWindow(DeleteBluelinePageElements.DELETE_BLUELINE_PAGE_TITLE);
	}
	
	public boolean clickOk()
	{
		click(DeleteBluelinePageElements.okButton);
		return switchToWindow(HeadnotesPageElements.HEADNOTES_PAGE_TITLE);
	}
}
