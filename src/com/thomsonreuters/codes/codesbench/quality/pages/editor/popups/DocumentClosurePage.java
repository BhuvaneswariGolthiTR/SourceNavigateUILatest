package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SpellcheckPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SetLawTrackingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;


@Component
public class DocumentClosurePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public DocumentClosurePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, DocumentClosurePageElements.class);
	}
	
	public boolean switchToEditorClosureWindow()
	{
		return switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
	}

	public void clickDiscardChanges()
	{
		click(DocumentClosurePageElements.discardButton);
		acceptAlert();
	}

	public boolean clickCheckInChanges()
	{
		click(DocumentClosurePageElements.checkInButton);
		editorPage().waitForEditorToClose();
		return switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}

	public void clickCheckInChangesAndHandleAlert(String alertMessage)
	{
		click(DocumentClosurePageElements.checkInButton);
		switchToWindow(EditorPageElements.PAGE_TITLE);
		AutoITUtils.verifyAlertTextAndAccept(true, alertMessage);
		waitForPageLoaded();
		while(true)
		{
			DateAndTimeUtils.takeNap(DateAndTimeUtils.HALF_SECOND);
			if(!isElementDisplayed(EditorToolbarPageElements.CLOSE_DOC))
			{
				break;
			}
		}
		switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}
	
	public void clickC2012LawTracking()
	{
		click(DocumentClosurePageElements.c2012LawTrackingButton);
		switchToWindow(SetLawTrackingPageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void selectQuickLoad()
	{
		click(DocumentClosurePageElements.quickloadRadioButton);
	}

	public void selectFullVols()
	{
		click(DocumentClosurePageElements.fullVolsRadioButton);
	}

	public void selectFullVolsCompare()
	{
		click(DocumentClosurePageElements.fullVolsCompareRadioButton);
	}

	public void selectFullVolsRecomp()
	{
		click(DocumentClosurePageElements.fullVolsRecompRadioButton);
	}

	@Deprecated
	public boolean isFullVolsCompareDisplayed()
	{
		return isElementDisplayed(DocumentClosurePageElements.FULL_VOLS_COMPARE_RADIO_BUTTON);
	}

	@Deprecated
	public boolean isFullVolsRecompDisplayed()
	{
		return isElementDisplayed(DocumentClosurePageElements.FULL_VOLS_RECOMP_RADIO_BUTTON);
	}

	public void setAssignEffectiveDate(String date)
	{
		clearAndSendKeysToElement(DocumentClosurePageElements.effectiveDate, date);
	}

	public boolean switchToHierarchyPage()
	{
		 return switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}

	public void closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared()
	{
		click(DocumentClosurePageElements.checkInButton);
		if(switchToWindow(SpellcheckPageElements.PAGE_TITLE))
		{
			waitForPageLoaded();
			click(SpellcheckPageElements.COMMIT_CHANGES_AND_CLOSE_BUTTON);
		}
	}

	public boolean checkIfEffectiveDateFieldIsEmpty()
	{
		return doesElementExist(DocumentClosurePageElements.EFFECTIVE_DATE) && getElement(DocumentClosurePageElements.EFFECTIVE_DATE).getText().isEmpty();
	}

	public boolean checkWestlawDateSuppressIsOff()
	{
		return doesElementExist(DocumentClosurePageElements.WESTLAW_DATE_SUPPRESS_OFF)
				&& getElement(DocumentClosurePageElements.WESTLAW_DATE_SUPPRESS_OFF).isSelected()
				&& !(getElement(DocumentClosurePageElements.WESTLAW_DATE_SUPPRESS_ON).isSelected());
	}

	public boolean checkLawTrackingFieldIsEmpty()
	{
		return doesElementExist(DocumentClosurePageElements.LAW_TRACKING_TEXT_AREA)
				&& getElement(DocumentClosurePageElements.LAW_TRACKING_TEXT_AREA).getText().isEmpty();
	}

	public boolean checkQuickLoadIsSelected()
	{
		return doesElementExist(DocumentClosurePageElements.QUICK_LOAD_RADIO)
				&& getElement(DocumentClosurePageElements.QUICK_LOAD_RADIO).isSelected();
	}

	public boolean checkFullVolIsNotSelected()
	{
		return doesElementExist(DocumentClosurePageElements.FULL_VOLS_RADIO_BUTTON)
				&& !getElement(DocumentClosurePageElements.FULL_VOLS_RADIO_BUTTON).isSelected();
	}

	public boolean checkFullVolRecompIsNotSelected()
	{
		return doesElementExist(DocumentClosurePageElements.FULL_VOLS_RECOMP_RADIO_BUTTON)
				&& !getElement(DocumentClosurePageElements.FULL_VOLS_RECOMP_RADIO_BUTTON).isSelected();
	}

	public boolean checkFullVolCompareIsNotSelected()
	{
		return doesElementExist(DocumentClosurePageElements.FULL_VOLS_COMPARE_RADIO_BUTTON)
				&& !getElement(DocumentClosurePageElements.FULL_VOLS_COMPARE_RADIO_BUTTON).isSelected();
	}

	public boolean checkQuickLoadIsNotSelected()
	{
		return doesElementExist(DocumentClosurePageElements.QUICK_LOAD_RADIO)
				&& !getElement(DocumentClosurePageElements.QUICK_LOAD_RADIO).isSelected();
	}

	public boolean checkLawTrackingFieldHasRequiredLoad(String load)
	{
		return doesElementExist(DocumentClosurePageElements.LAW_TRACKING_TEXT_AREA)
				&& getElement(DocumentClosurePageElements.LAW_TRACKING_TEXT_AREA).getAttribute("value").contentEquals(load);
	}

}
