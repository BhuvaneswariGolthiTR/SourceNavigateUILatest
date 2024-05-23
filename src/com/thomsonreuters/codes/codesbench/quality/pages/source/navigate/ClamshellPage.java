package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.DeltaContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.LineageContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.ClamshellPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClamshellPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public ClamshellPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ClamshellPageElements.class);
	}
	
	public void openSideBarEdit()
	{
		click(ClamshellPageElements.sidebarEdit);
    	waitForPageLoaded();
	}
	
	public void openSideBarSync()
	{
		click(ClamshellPageElements.sync);
    	waitForPageLoaded();
	}

	public void clickSideBarSyncSync()
	{
		waitForElement(ClamshellPageElements.sidebarSyncSync);
		click(ClamshellPageElements.sidebarSyncSync);
	}

	public void openSideBarModify()
	{
		click(ClamshellPageElements.sidebarModify);
    	waitForPageLoaded();
	}
	
	public void openSideBarTrack()
	{
		click(ClamshellPageElements.sidebarTrack);
    	waitForPageLoaded();
	}

	public void openSideBarView()
	{
		click(ClamshellPageElements.sidebarView);
		waitForPageLoaded();
	}

	public void openViewSubmenu()
	{
		click(ClamshellPageElements.sidebarView);
		waitForPageLoaded();
	}
	
	public void openSideBarViewMulitipleandDuplicate()
	{
		click(ClamshellPageElements.sidebarMultiplesAndDuplicates);
    	waitForPageLoaded();
	}

	public boolean openviewSourcelockReport()
	{
		click(ClamshellPageElements.SIDEBAR_VIEW_SOURCE_LOCKED_REPORT);
    	return switchToWindow("Lock Report"); 
	}
	
	public void openSideBarCreate()
	{
		click(ClamshellPageElements.sidebarCreate);
    	waitForPageLoaded();
	}
	
	public void openCreatePdfMetadata()
	 {
		 click(ClamshellPageElements.sidebarCreate);
		 waitForElement(ClamshellPageElements.SIDEBAR_ADD_PDF_METADATA_XPATH);
		 click(ClamshellPageElements.SIDEBAR_ADD_PDF_METADATA_XPATH);
		 switchToWindow("Add Shell");
		 waitForPageLoaded();
		 enterTheInnerFrame();
	 }

	 public void openEditPdfMetadata()
	 {
	 	click(ClamshellPageElements.sideBarHeaderImage);
	 	click(ClamshellPageElements.sidebarEditPDFMetadata);
	 	switchToWindow("Edit Shell");
	 	waitForPageLoaded();
	 	enterTheInnerFrame();
	 }
	
	public void openSideBarValidate()
	{
		click(ClamshellPageElements.sidebarValidate);
    	waitForPageLoaded();
	}
	
	public void openSideBarReport()
	{
		click(ClamshellPageElements.sidebarReport);
	}
	
	public void getSideBarViewDelta()
	{
		click(ClamshellPageElements.delta);
		waitForPageLoaded();
	}
	
	public void goToViewTargetHierarchy()
	 {
		 click(DeltaContextMenuElements.headerImagine);
		 click("//div/span[contains(@id,'viewTargetInHierarchyText')]");
	 }
	
	public void goToViewTarget()
	 {
		 click(DeltaContextMenuElements.headerImagine);
		 click("//div/span[contains(@id,'view') and text()='View Target']");
	 }
	
	public boolean switchToEditor()
	{
		waitForWindowByTitle(EditorPageElements.PAGE_TITLE);
		return switchToWindow(EditorPageElements.PAGE_TITLE);
	}

	public List<String> enabledOptions()
	{
		List<String> fails = new ArrayList<String>();
		
		waitForPageLoaded();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
    	
		for (WebElement ele : SourceNavigatePageElements.TOP_CONTENT)
    	{
    		String id = ele.getAttribute("id");
    		String optionText = ele.getText();
			boolean optionNotSupported = verifyClamshellOptionNotSupported(id, optionText);
    		if(optionNotSupported == false)
    		{
    			if(optionText.contains("\n"))
    			{
    				optionText = optionText.replace("\n", " ");
    			}
    			fails.add(optionText);
    		}
		}
		return fails;
	}
	
	public List<String> enableOptionsTrackClamshell()
	 {
		 List<String> fails = new ArrayList<String>();
	 
		 waitForPageLoaded();
	    	try {
				Thread.sleep(DateAndTimeUtils.ONE_SECOND);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	for (WebElement ele : getElements(SourceNavigateGridPageElements.TRACK_CLAMSHELL_DIV))
	    	{
	    		String id = ele.getAttribute("id");
	    		String optionText = ele.getText();
	    		if(optionText.contains("     "))
	    		{
	    			optionText = optionText.replace("     ", "\n");
	    		}
				boolean optionNotSupported = verifyClamshellOptionNotSupported(id, optionText);
	    		if(optionNotSupported == false)
	    		{
	    			if(optionText.contains("\n"))
	    			{
	    				optionText = optionText.replace("\n", " ");
	    			}
	    			fails.add(optionText);
	    		}
			
	    	}
	    	return fails;
	 }
	
	public boolean verifyClamshellOptionNotSupported(String id, String clamshellOption) 
    {
    	boolean optionSupported = false;
    	if(clamshellOption.contains("\n"))
    	{
    		try
        	{
    			click(ClamshellPageElements.sideBarHeaderImage);
    			click("//div/span[contains(@id,'" + id + "')]");
        		optionSupported = getAlertText().equals("Selected rows do not support this function.");
            	acceptAlert();
        	}
        	catch (Exception e)
        	{
        		//This means that an alert didn't appear saying: "Selected rows do not support this function."
        	}
    	}
    	else
    	{
    		try
        	{
    			getElement("//img[@class='headerImage']").click();
    			getElement("//div/span[contains(@id,'" + id + "') and text()='" + clamshellOption + "']").click();
        		optionSupported = getAlertText().equals("Selected rows do not support this function.");
            	acceptAlert();
        	}
        	catch (Exception e)
    		{
        		//This means that an alert didn't appear saying: "Selected rows do not support this function."
        	}
    	}
    	return optionSupported;
    }
	

	public boolean viewRenditionXML()
	{
		getElement("//img[@class='headerImage']").click();
    	click(ClamshellPageElements.sidebarViewRenditionXml);
    	boolean viewRenditionXMLEnabled = switchToWindow(LineageContextMenuElements.RAW_XML_EDITOR);
    	getElement(CommonPageElements.CLOSE_BUTTON).click();
    	return viewRenditionXMLEnabled;
	}
	
	public boolean viewXMLExtract()
	{
		getElement(LineageContextMenuElements.HEADER_IMAGE).click();
    	getElement(LineageContextMenuElements.VIEW_XML_EXTRACT).click();
    	boolean viewXmlExtractEnabled = switchToWindow(LineageContextMenuElements.XML_EXTRACT_TITLE);
    	enterTheInnerFrame();
    	getElement(CommonPageElements.CLOSE_BUTTON).click();
    	return viewXmlExtractEnabled;
	}
	
	public boolean viewPrintPrevew()
	{
		getElement(LineageContextMenuElements.HEADER_IMAGE).click();
    	getElement("//div/span[contains(@id,'view8Text')]").click();
    	boolean viewPrintPreviewEnabled = switchToWindow("Print Preview");
    	enterTheInnerFrame();
    	getElement(CommonPageElements.OK_BUTTON).click();
		return viewPrintPreviewEnabled;
	}
	
	public boolean TublarPrintPrevew()
	{
		getElement(LineageContextMenuElements.HEADER_IMAGE).click();
		getElement("//div/span[contains(@id,'view9Text')]").click();
    	boolean viewTabularPrintPreviewEnabled = switchToWindow("Tabular Print Preview");
    	enterTheInnerFrame();
    	getElement(CommonPageElements.OK_BUTTON).click();
		return viewTabularPrintPreviewEnabled;
	}
}
