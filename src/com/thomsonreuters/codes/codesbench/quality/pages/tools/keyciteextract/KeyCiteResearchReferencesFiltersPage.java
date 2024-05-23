package com.thomsonreuters.codes.codesbench.quality.pages.tools.keyciteextract;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.keyciteextract.KeyCiteReferencesFiltersPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class KeyCiteResearchReferencesFiltersPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public KeyCiteResearchReferencesFiltersPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, KeyCiteReferencesFiltersPageElements.class);
	}
	
	/**
	 * Sets the Content Set Legacy Id to the given one.
	 *
	 * @param contentSetLegacyId the content set legacy id
	 */
	public void setContentSetLegacyId(String contentSetLegacyId)
	{
		selectDropdownOption(KeyCiteReferencesFiltersPageElements.contentSetLegacyIdSelect, contentSetLegacyId);
	}
	
	/**
	 * Checks key rules.
	 */
	public void checkKeyRules()
	{
		checkCheckbox(KeyCiteReferencesFiltersPageElements.keyRulesCheckBox);
	}
	
	/**
	 * Checks american law report annotations.
	 */
	public void checkAmericanLawReportAnnotations()
	{
		checkCheckbox(KeyCiteReferencesFiltersPageElements.americanLawReportsAnnotationsCheckBox);
	}
	
	/**
	 * Checks treaties documents.
	 */
	public void checkTreatiesDocuments()
	{
		checkCheckbox(KeyCiteReferencesFiltersPageElements.treatiesDocumentsCheckBox);
	}
	
	/**
	 * Checks legal encyclopedia documents.
	 */
	public void checkLegalEncyclopediaDocuments()
	{
		checkCheckbox(KeyCiteReferencesFiltersPageElements.legalEncyclopediaDocumentsCheckBox);
	}
	
	/**
	 * Checks other secondary sources.
	 */
	public void checkOtherSecondarySources()
	{
		checkCheckbox(KeyCiteReferencesFiltersPageElements.otherSecondarySourcesCheckBox);
	}
	
	/**
	 * Unchecks default analytical titles.
	 */
	public void uncheckDefaultAnalyticalTitles()
	{
		uncheckCheckbox(KeyCiteReferencesFiltersPageElements.defaultAnalyticalTitlesCheckBox);
	}
	
	/**
	 * Check default analytical titles.
	 */
	public void checkDefaultAnalyticalTitles()
	{
		checkCheckbox(KeyCiteReferencesFiltersPageElements.defaultAnalyticalTitlesCheckBox);
	}
	
	/**
	 * Checks if default analytical titles is checked.
	 *
	 * @return boolean of whether it is selected
	 */
	public boolean isDefaultAnalyticalTitlesCheckboxChecked()
	{
		return isElementSelected(KeyCiteReferencesFiltersPageElements.defaultAnalyticalTitlesCheckBox);
	}
}
