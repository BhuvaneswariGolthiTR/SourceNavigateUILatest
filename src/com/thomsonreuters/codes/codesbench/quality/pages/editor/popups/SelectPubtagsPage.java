package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SelectPubtagsElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class SelectPubtagsPage  extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public SelectPubtagsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, SelectPubtagsElements.class);
    }
	
}