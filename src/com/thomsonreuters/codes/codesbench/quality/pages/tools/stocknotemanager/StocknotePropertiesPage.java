package com.thomsonreuters.codes.codesbench.quality.pages.tools.stocknotemanager;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.popups.RawXmlEditorPageElements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknotePropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class StocknotePropertiesPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public StocknotePropertiesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, StocknotePropertiesPageElements.class);
	}
	
	/**
	 * Clears the current name of the note and sets it to the given one. Must be in the inner frame for
	 * this method to work.
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		clearAndSendKeysToElement(StocknotePropertiesPageElements.nameTextBox, name);
	}
	
	/**
	 * Checks the On Context Menu check box.
	 */
	public void checkOnContextMenu()
	{
		checkCheckbox(StocknotePropertiesPageElements.onContextMenuCheckbox);
	}
	
	/**
	 * Checks the state rules check box.
	 */
	public void checkStateRules()
	{
		checkCheckbox(StocknotePropertiesPageElements.stateRulesCheckbox);
	}
	
	/**
	 * Checks the local rules check box.
	 */
	public void checkLocalRules()
	{
		checkCheckbox(StocknotePropertiesPageElements.localRulesCheckbox);
	}
	
	/**
	 * Sets the category to the given one.
	 * 
	 * @param category - Desired category
	 */
	public void setUserAssignedCategory(String category)
	{
		selectDropdownOptionUsingJavascript(StocknotePropertiesPageElements.USER_ASSIGNED_CATEGORY_DROPDOWN_ID, category);
	}
	
	/**
	 * Clicks the save button and switches back to the stocknote manager. Waits for the properties
	 * window to close.
	 *
	 * @throws 
	 */
	public void save()  
	{
		click(StocknotePropertiesPageElements.saveButton);
		waitForWindowGoneByTitle(StocknotePropertiesPageElements.STOCKNOTE_PROPERTIES_PAGE_TITLE);
		switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
	/**
	 * Clicks the save button and switches back to the stocknote manager.
	 *
	 * @throws 
	 */
	public void cancel()  
	{
		click(StocknotePropertiesPageElements.cancelButton);
		waitForWindowGoneByTitle(StocknotePropertiesPageElements.STOCKNOTE_PROPERTIES_PAGE_TITLE);
		switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
	}
	
	/**
	 * Checks the pin to top check box.
	 */
	public void checkPinToTop()
	{
		checkCheckbox(StocknotePropertiesPageElements.pinToTopCheckbox);
	}
	
	/**
	 * Unchecks the target documents select all check box.
	 */
	public void uncheckTargetDocumentsSelectAll()
	{
		uncheckCheckbox(StocknotePropertiesPageElements.targetDocumentsSelectAllCheckbox);
	}
	
	/**
	 * Checks the source documents select all check box.
	 */
	public void checkSourceDocumentsSelectAll()
	{
		checkCheckbox(StocknotePropertiesPageElements.sourceDocumentsSelectAllCheckbox);
	}
	
	/**
	 * Unchecks the source documents select all check box.
	 */
	public void uncheckSourceDocumentsSelectAll()
	{
		uncheckCheckbox(StocknotePropertiesPageElements.sourceDocumentsSelectAllCheckbox);
	}
	
	/**
	 * Sets the contents to the given string.
	 *
	 * @param content
	 */
	public void setContent(String content)
	{
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		javascriptExecutor.executeScript(String.format("arguments[0].value='%s'",content), StocknotePropertiesPageElements.contentTextArea);
		click(StocknotePropertiesPageElements.contentTextArea);
	}
	
	/**
	 * Clicks the validate button.
	 */
	public void validate()
	{
		click(StocknotePropertiesPageElements.validateButton);
		waitForPageLoaded();
		waitForElement(StocknotePropertiesPageElements.validateMessage);
	}
	
	/**
	 * Returns the value inside the name text box.
	 *
	 * @return
	 */
	public String getStocknoteName()
	{
		return getElementsAttribute(StocknotePropertiesPageElements.nameTextBox, "value");
	}
	
	/**
	 * Returns true if the on context menu check box is checked.
	 *
	 * @return
	 */
	public boolean isOnContextMenuChecked()
	{
		return isElementSelected(StocknotePropertiesPageElements.onContextMenuCheckbox);
	}
	
	/**
	 * Returns true if the state rules check box is checked.
	 *
	 * @return
	 */
	public boolean isStateRulesChecked()
	{
		return isElementSelected(StocknotePropertiesPageElements.stateRulesCheckbox);
	}
	
	/**
	 * Returns true if the local rules check box is checked.
	 *
	 * @return
	 */
	public boolean isLocalRulesChecked()
	{
		return isElementSelected(StocknotePropertiesPageElements.stateRulesCheckbox);
	}
	
	/**
	 * Returns true if the pin to top check box is checked.
	 *
	 * @return
	 */
	public boolean isPinToTopChecked()
	{
		return isElementSelected(StocknotePropertiesPageElements.pinToTopCheckbox);
	}
	
	/**
	 * Returns the values currently selected in the user assigned category.
	 *
	 * @return
	 */
	public String getUserAssignedCategory()
	{
		return getSelectedDropdownOptionText(StocknotePropertiesPageElements.userAssignedCategoryDropdown);
	}
	
	/**
	 * Returns true if the target documents select all check box is checked.
	 *
	 * @return
	 */
	public boolean isTargetDocumentsSelectAllChecked()
	{
		return isElementSelected(StocknotePropertiesPageElements.targetDocumentsSelectAllCheckbox);
	}
	
	/**
	 * Returns true if the source documents select all check box is checked.
	 *
	 * @return
	 */
	public boolean isSourceDocumentsSelectAllChecked()
	{
		return isElementSelected(StocknotePropertiesPageElements.sourceDocumentsSelectAllCheckbox);
	}
	
	/**
	 * Unchecks the state rules check box.
	 */
	public void uncheckStateRules()
	{
		uncheckCheckbox(StocknotePropertiesPageElements.stateRulesCheckbox);
	}
	
	/**
	 * Unchecks the local rules check box.
	 */
	public void uncheckLocalRules()
	{
		uncheckCheckbox(StocknotePropertiesPageElements.localRulesCheckbox);
	}
	
	/**
	 * Checks the target documents select all check box.
	 */
	public void checkTargetDocumentsSelectAll()
	{
		checkCheckbox(StocknotePropertiesPageElements.targetDocumentsSelectAllCheckbox);
	}
	
	/**
	 * Verifies the content text area equals the given text. Spaces are ignored.
	 *
	 * @param content
	 * @return
	 */
	public boolean contentEquals(String content)
	{
		return getElementsAttribute(StocknotePropertiesPageElements.contentTextArea, "value").replace(" ", "").equals(content.replace(" ", ""));
	}
	
	/**
	 * Verifies the validate message contains the given message.
	 *
	 * @param message
	 * @return
	 */
	public boolean validateMessageContains(String message)
	{
		return getElementsText(StocknotePropertiesPageElements.validateMessage).contains(message);
	}
	
	/**
	 * Unchecks the pin to top option.
	 */
	public void uncheckPinToTop()
	{
		uncheckCheckbox(StocknotePropertiesPageElements.pinToTopCheckbox);
	}

	/**
	 * Checks the content area contains the inputed value.
	 *
	 * @param value
	 * @return
	 */
	public boolean contentContains(String value)
	{
		return getElementsText(StocknotePropertiesPageElements.contentTextArea).contains(value);
	}
}
