package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ParentageGraphPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ParentageGraphPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public ParentageGraphPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ParentageGraphPageElements.class);
	}

	public void clickExpandButton()
	{
		click(ParentageGraphPageElements.expandButton);
		waitForPageLoaded();
	}

	public void rightClickSelectedParentageGraphNodeImage()
	{
		rightClick(ParentageGraphPageElements.parentageGraphImage);
	}
}
