package com.thomsonreuters.codes.codesbench.quality.pages.audits;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbydocument.AuditByDocumentPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.ConfirmAuditRequestPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbysource.AuditBySourcePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbysource.AuditBySourcePageElements.MATERIAL_ITEM_XPATH;

@Component
public class AuditBySourcePage extends BasePage 
{
	private WebDriver driver;
	
	@Autowired
	public AuditBySourcePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, AuditBySourcePageElements.class);
	}

	public boolean switchToAuditBySourceWindow()
	{
		boolean windowAppears = switchToWindow(AuditBySourcePageElements.AUDIT_BY_SOURCE_PAGE_TITLE);
		waitForPageLoaded();
		return windowAppears;
	}
	
	public String getSelectedMaterialByIndex(int index)
	{
		return getElements(AuditBySourcePageElements.SELECTED_MATERIALS_LIST_XPATH).get(index).getText();
	}
	
	public void setAdditionalIdentifier(String setValue)
	{
		clearAndSendKeysToElement(AuditBySourcePageElements.additionalIdentifierTextBox, setValue);
	}
	
	public boolean clickNextButton()
	{
		click(AuditBySourcePageElements.nextButton);
		boolean confirmAuditRequestWindowAppeared = switchToWindow(ConfirmAuditRequestPageElements.CONFIRM_AUDIT_REQUEST_PAGE_TITLE);
		enterTheInnerFrame();
		return confirmAuditRequestWindowAppeared;
	}

	public boolean closeAuditsBySourceWindow()
	{
		closeCurrentWindowIgnoreDialogue();
		return checkWindowIsClosed(AuditBySourcePageElements.AUDIT_BY_SOURCE_PAGE_TITLE);
	}
	
	public void clickMostRecentHistoricalVersionRadioButton()
	{
		click(AuditBySourcePageElements.mostRecentHistoricalVersionRadioButton);
	}
	
	public void moveMaterialFromAvailableMaterialsToSelectedMaterials(String material)
	{
		click(String.format(AuditBySourcePageElements.AVAILABLE_MATERIALS_LIST_XPATH, material));
		sendEnterToElement(AuditBySourcePageElements.addOneVolsButton);
	}
	
	public void setContentType(String contentType)
	{
		click(String.format(AuditBySourcePageElements.SEARCH_DOCUMENTS_DROP_DOWN_XPATH, contentType));
	}
	
	public boolean isContentAuditChecked()
	{
		return isCheckboxChecked(AuditBySourcePageElements.contentAuditCheckbox);
	}
	
	public boolean isHierarchyContextChecked()
	{
		return isCheckboxChecked(AuditBySourcePageElements.hierarchyContextCheckbox);
	}
	
	public boolean isVersionsAuditStartingChecked()
	{
		return isCheckboxChecked(AuditBySourcePageElements.versionsAuditStartingCheckbox);
	}
	
	public String getVersionsAuditStartingDate()
	{
		return getElementsAttribute(AuditBySourcePageElements.versionsAuditStartingTextBox, "value");
	}
	
	public boolean isValidationsChecked()
	{
		return isCheckboxChecked(AuditBySourcePageElements.validationsCheckbox);
	}
	
	public boolean isTextMergeChecked()
	{
		return isCheckboxChecked(AuditBySourcePageElements.textMergeCheckbox);
	}
	
	public boolean isUnusedReportChecked()
	{
		return isCheckboxChecked(AuditBySourcePageElements.unusedReportCheckbox);
	}
	
	public boolean isSourcesChecked()
	{
		return isCheckboxChecked(AuditBySourcePageElements.sourcesCheckbox);
	}
	
	public boolean isNodReportChecked()
	{
		return isCheckboxChecked(AuditBySourcePageElements.nodReportCheckbox);
	}
	
	public boolean isResearchReferencesChecked()
	{
		return isCheckboxChecked(AuditBySourcePageElements.researchReferencesCheckbox);
	}
	
	public boolean isMostRecentPublishedDocumentSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.mostRecentPublishedDocumentRadioButton);
	}
	
	public boolean isMostRecentHistoricalVersionSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.mostRecentHistoricalVersionRadioButton);
	}
	
	public boolean isMostRecentWipDocumentSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.mostRecentWipDocumentRadioButton);
	}
	
	public boolean isWipVsWipSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.wipVsWipRadioButton);
	}
	
	public String getAdditionalIdentifier()
	{
		return getElementsAttribute(AuditBySourcePageElements.additionalIdentifierTextBox, "value");
	}
	
	public boolean isShortSameParagraphsSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.shortSameParagraphsRadioButton);
	}
	
	public boolean isNotSameParagraphsSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.notSameParagraphsRadioButton);
	}
	
	public boolean isAllTextSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.allTextRadioButton);
	}
	
	public boolean isIgnorePubTagChangeSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.ignorePubTagChangeRadioButton);
	}
	
	public boolean isSourceSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.sourceRadioButton);
	}
	
	public boolean isClassificationSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.classificationRadioButton);
	}
	
	public boolean isDateSelected()
	{
		return isCheckboxChecked(AuditBySourcePageElements.dateRadioButton);
	}

	public void selectMaterialByTitle(String materialByTitle)
	{
		click(String.format(MATERIAL_ITEM_XPATH,materialByTitle));
	}

	public void clickAddOneDocumentButton()
	{
		sendEnterToElement(AuditByDocumentPageElements.addOneDocumentButton);
		waitForPageLoaded();
	}

	public void clickNextButtonWithoutWaiting()
	{
		sendEnterToElement(AuditBySourcePageElements.nextButton);
	}
}
