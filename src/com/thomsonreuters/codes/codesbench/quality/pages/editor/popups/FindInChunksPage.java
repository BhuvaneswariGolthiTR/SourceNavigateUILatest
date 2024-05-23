package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.FindAndReplacePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.popups.base.BaseFindPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.FindInChunksPageElements;

@Component
public class FindInChunksPage extends BaseFindPage
{
	private WebDriver driver;
	
	@Autowired
	public FindInChunksPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, FindInChunksPageElements.class);
    }
	public void close()
	{
		switchToWindow(FindInChunksPageElements.PAGE_TITLE);
		sendEnterToElement(FindInChunksPageElements.closeButton);
	}

}
