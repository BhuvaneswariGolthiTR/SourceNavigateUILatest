package com.thomsonreuters.codes.codesbench.quality.menus.mainmenus;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.HomeMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.ContentPreferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.GroupRelatedRuleBooksPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.HomePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.MyHomePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences.UserContentSetsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences.UserPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences.UserSecuritySettingsPageElements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HomeMenu extends Menu
{
	private WebDriver driver;

	@Autowired
	public HomeMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HomeMenuElements.class);
		PageFactory.initElements(driver, HomePageElements.class);
	}
	
	public boolean goToMyHomePage() 
	{
		openMenu(HomeMenuElements.home);
		sendEnterToElement(HomeMenuElements.myHomePage);
		return switchToWindow(MyHomePageElements.HOME_PAGE_TITLE);
	}

	public boolean goToUserPreferencesContentSets()
	{
		openMenu(HomeMenuElements.home);
		openSubMenu(HomeMenuElements.userPreferences);
		sendEnterToElement(HomeMenuElements.contentSets);
		return switchToWindow(UserContentSetsPageElements.USER_CONTENT_SET_PREFERENCES_PAGE_TITLE);
	}

	public boolean goToContentPreferences()
	{
		openMenu(HomeMenuElements.home);
		sendEnterToElement(HomeMenuElements.contentPreferences);
		return switchToWindow(ContentPreferencesPageElements.CONTENT_PREFERENCES_PAGE_TITLE);
	}

	public boolean goToUserPreferencesProperties()
	{
		openMenu(HomeMenuElements.home);
		openSubMenu(HomeMenuElements.userPreferences);
		sendEnterToElement(HomeMenuElements.properties);
		return switchToWindow(UserPropertiesPageElements.USER_PROPERTIES_PAGE_TITLE);
	}

	public boolean goToUserPreferencesSecurity()
	{
		openMenu(HomeMenuElements.home);
		openSubMenu(HomeMenuElements.userPreferences);
		sendEnterToElement(HomeMenuElements.security);
		return switchToWindow(UserSecuritySettingsPageElements.USER_SECURITY_SETTINGS_PAGE_TITLE);
	}
	
	public boolean goToHomeRelatedRuleBookPreferences()
	{
		openMenu(HomeMenuElements.home);
		sendEnterToElement(HomeMenuElements.relatedRuleBookPreferences);
		return switchToWindow(GroupRelatedRuleBooksPageElements.HOME_RELATED_RULE_BOOK_PAGE_TITLE);
	}

	public void openMenu()
	{
		sendKeyToElement(HomeMenuElements.home, Keys.ARROW_DOWN);
	}
}
