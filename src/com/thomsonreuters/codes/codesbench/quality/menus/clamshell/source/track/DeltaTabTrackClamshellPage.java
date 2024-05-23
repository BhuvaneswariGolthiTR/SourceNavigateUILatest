package com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.track;

import com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source.TrackMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractTrackClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaTabTrackClamshellPage extends AbstractTrackClamshellPage
{
	private WebDriver driver;
	
	@Autowired
	public DeltaTabTrackClamshellPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, TrackMenuElements.class);
	}

	public boolean clickImagesSentOut(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.imagesSentOut);

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

	public boolean clickImagesCompleted(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.imagesCompleted);

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

	public boolean clickTabularRequested(Boolean expectedEnabled, Boolean closeWindow)
	{
		boolean expectedWindowAppeared;
		click(TrackMenuElements.sideBarHeaderImage);
		click(TrackMenuElements.tabularRequested);

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
}
