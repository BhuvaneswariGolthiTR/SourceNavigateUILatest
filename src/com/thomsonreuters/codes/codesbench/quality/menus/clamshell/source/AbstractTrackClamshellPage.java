package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.TrackMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractTrackClamshellPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public AbstractTrackClamshellPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, TrackMenuElements.class);
	}
	
	public boolean clickSyncToWestlawCompleted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.syncToWestlawCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickChapterApprovalReceived(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.chapterApprovalReceived);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickApvStarted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.apvStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickApvCompleted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.apvCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickTopicalHeadingNeeded(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.topicalHeadingNeeded);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickTopicalHeadingCompleted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.topicalHeadingCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickImagesSentOut(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.imagesSentOut);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickImagesCompleted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.imagesCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickTabularRequested(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.tabularRequested);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickTabularReceivedHardcopy(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.tabularReceivedHardcopy);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickTabularStarted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.tabularStarted);

		if(expectedEnabled ==false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickTabularCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.tabularCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickApvReviewStarted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.apvReviewStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickApvReviewCompleted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.apvReviewCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickReadyForAdvanceSheet(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.readyForAdvanceSheet);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared =  AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickPublishedInAdvanceSheet(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.publishedInAdvanceSheet);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAttorneyWorkStarted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.attorneyWorkStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAttorneyWorkCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.attorneyWorkCompleted);

		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickVersioningWorkStarted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.versioningWorkStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared =  AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickVersioningWorkCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.versioningWorkCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickPrepStarted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.prepStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickPrepCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.prepCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickReadyForIntegration(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.readyForIntegration);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickIntegrationStarted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.integrationStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickIntegrationCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.integrationCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickIntegration2Started(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.integration2Started);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickIntegration2Completed(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.integration2Completed);

		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickIntegrationQueryStarted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.integrationQueryStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickIntegrationQueryCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.integrationQueryCompleted);

		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}

	public boolean clickCutAndPasteCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.cutAndPasteCompleted);

		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
		}
		else
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}

		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickRunAutoIntegrateAudit(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.runAutoIntegrateAndAudit);

		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickReadyToResolveAutoIntegrateErrors(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.readyToResolveAutoIntegrateErrors);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickResolveAutoIntegrateErrorsStarted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.resolveAutoIntegrateErrorsStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickResolveAutoIntegrateErrorsCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.resolveAutoIntegrateErrorsCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAuditRequested(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.auditRequested);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAuditReviewStarted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.auditReviewStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAuditReviewCompleted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.auditReviewCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAuditCorrectionsToBeDoneBy(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.auditCorrectionsToBeDoneBy);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = auditCorrectionsPage().switchToAuditCorrectionsWindow();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAuditCorrectionStarted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.auditCorrectionsStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickAuditCorrectionsCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.auditCorrectionsCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickCorrectionsAuditRequested(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.correctionsAuditRequested);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickCorrectionsAuditStarted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.correctionsAuditStarted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickCorrectionsAuditCompleted(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.correctionsAuditCompleted);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = waitForGridRefresh();
			waitForPageLoaded();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickClean(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.clean);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared =  cleanPage().switchToCleanWindow();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
	
	public boolean clickReleasedToWestlaw(Boolean expectedEnabled, Boolean closeWindow) 
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.releasedToWestlaw);
    	
		if(expectedEnabled == false)
		{
			expectedWindowAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Selected rows do not support this function.");
     	}
		else 
		{
			expectedWindowAppeared = releasedToWestlawPage().switchToReleasedToWestlawWindow();
		}	
		
		sourcePage().closeDocument(expectedEnabled, expectedWindowAppeared, closeWindow);
		return expectedWindowAppeared;
	}
}