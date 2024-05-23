package com.thomsonreuters.codes.codesbench.quality.pages.nod.blueline;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline.BluelineAnalysisPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class BluelineAnalysisPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public BluelineAnalysisPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HeadnotesPageElements.class);
	}
	
	public boolean switchToBluelineAnalysisWindow()
	{
		return switchToWindow(BluelineAnalysisPageElements.BLUELINE_ANALYSIS_PAGE_TITLE);
	}

	public boolean clickBluelineWithGivenText(String blueLine)
    {
    	click(String.format(HeadnotesPageElements.BLUE_LINE_WITH_NAME_GIVEN, blueLine));
    	return switchToWindow(HeadnotesPageElements.HEADNOTES_PAGE_TITLE);
    }
}
