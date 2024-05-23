package com.thomsonreuters.codes.codesbench.quality.pages.nod.headnotes;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.HeadnoteDetailsPopupElementsAngular;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class HeadnotesDetailsPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public HeadnotesDetailsPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		
	}
	
	public boolean switchToHeadnotesDetailsPage()
	{
		return switchToWindow(HeadnotesDetailsPageElements.HEADNOTE_DETAIL_PAGE_TITLE);
	}

	public boolean clickCancel()  
    {
    	click(CommonPageElements.CANCEL_BUTTON);
    	return switchToWindow(HeadnotesPageElements.HEADNOTES_PAGE_TITLE);
    }

    public String getHeader()
	{
		return HeadnoteDetailsPopupElementsAngular.header.getText();
	}


}
