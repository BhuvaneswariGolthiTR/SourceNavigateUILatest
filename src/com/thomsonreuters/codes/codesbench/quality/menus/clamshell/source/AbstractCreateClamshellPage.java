package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.CreateMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.AddPdfMetadataPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractCreateClamshellPage  extends BasePage
{
	private WebDriver driver;

	@Autowired
	public AbstractCreateClamshellPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, CreateMenuElements.class);  
	}
	
	public boolean clickCreatePrepDocument(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(CreateMenuElements.sideBarHeaderImage);
		click(CreateMenuElements.createPrepDocument);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			AutoITUtils.verifyAlertTextAndAccept(true, "PREP Rendition(s) have been previously created for this Rendition. Do you want to proceed with the creation of PREP document(s)?");
			expectedWindowAppeared = waitForGridRefresh();
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickAddContent(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(CreateMenuElements.sideBarHeaderImage);
		click(CreateMenuElements.addContent);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = manualDataEntryPage().switchToManualDataEntryWindow();
		}
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAddPDFMetadata(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(CreateMenuElements.sideBarHeaderImage);
		click(CreateMenuElements.addPDFMetadata);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = switchToWindow(AddPdfMetadataPageElements.ADD_PDF_METADATA_PAGE_TITLE);
			enterTheInnerFrame();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}
