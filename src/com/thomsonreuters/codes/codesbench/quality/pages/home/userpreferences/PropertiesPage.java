package com.thomsonreuters.codes.codesbench.quality.pages.home.userpreferences;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences.UserPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class PropertiesPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public PropertiesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, UserPropertiesPageElements.class);
	}
	
	public boolean switchToHomePropertiesPage()
	{
		return switchToWindow(UserPropertiesPageElements.USER_PROPERTIES_PAGE_TITLE);
	}
	
	public boolean sourceTagsFound()
	{
		return isElementDisplayed(UserPropertiesPageElements.NOD_SOURCE_TAG_SETTINGS);
	}
	
	public boolean mergeFound()
	{
		return isElementDisplayed(UserPropertiesPageElements.NOD_MERGE_SETTINGS);
	}
	
	public boolean gridSettingsFound()
	{
		return isElementDisplayed(UserPropertiesPageElements.NOD_UNREPORTED_FILTER);
	}
	
	public void returnToHomePage()
	{
		click(UserPropertiesPageElements.breadcrumbHome);
	}
	
	public void setDefaultRowsPerPage(String defaultRowsPerPage)
	{
		selectDropdownOption(UserPropertiesPageElements.defaultRowsPerPageDropDown, defaultRowsPerPage);
	}
	
	public void setDefaultRowsPerPageTo25()
	{
		setDefaultRowsPerPage("25");
	}
	
	public void setDefaultRowsPerPageTo500()
	{
		setDefaultRowsPerPage("500");
	}
	
	public void clickSave()
	{
		click(UserPropertiesPageElements.saveButton);
		waitForPageLoaded();
	}
	
	public void clickCancel()
	{
		click(UserPropertiesPageElements.cancelButton);
		waitForPageLoaded();
	}
}
