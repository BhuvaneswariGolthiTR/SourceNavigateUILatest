package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import javax.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AdvancedSearchAndReplaceScanElements;

@Component
public class AdvancedSearchAndReplaceScanPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public AdvancedSearchAndReplaceScanPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, AdvancedSearchAndReplaceScanElements.class);
    }
	
	public void clickBack()
	{
		click(AdvancedSearchAndReplaceScanElements.backButton);
	}
	
	public int getNumberOfMatches()
	{
		return AdvancedSearchAndReplaceScanElements.scanMatches.size();
	}
}
