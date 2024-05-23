package com.thomsonreuters.codes.codesbench.quality.pages.home;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.home.ContentPreferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.stream.Collectors;
import java.util.List;



@Component
public class ContentPreferencesPage extends BasePage
{
	private WebDriver driver;
		
	@Autowired
	public ContentPreferencesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
		
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ContentPreferencesPageElements.class);
	}

	public boolean isUserInPublishApproversList(String username)
	{
		return isElementDisplayed(String.format(ContentPreferencesPageElements.PUBLISH_APPROVERS_TABLE_USERNAME_XPATH, username)) ||
				isElementDisplayed(String.format(ContentPreferencesPageElements.PUBLISH_APPROVERS_TABLE_USERNAME_XPATH, username.toUpperCase()));
	}

	public void setPublishApproverUserId(String username)
	{
		clearAndSendKeysToElement(ContentPreferencesPageElements.publishApproversUserIdTextBox, username);
	}

	public void clickAddApprover()
	{
		click(ContentPreferencesPageElements.addApproverButton);
		waitForPageLoaded();
	}

	public void clickRemoveUser(String username)
	{
		if(isElementDisplayed(String.format(ContentPreferencesPageElements.PUBLISH_APPROVERS_TABLE_REMOVE_USERNAME, username)))
		{
			click(String.format(ContentPreferencesPageElements.PUBLISH_APPROVERS_TABLE_REMOVE_USERNAME, username));
		}
		else if(isElementDisplayed(String.format(ContentPreferencesPageElements.PUBLISH_APPROVERS_TABLE_REMOVE_USERNAME, username.toUpperCase())))
		{
			click(String.format(ContentPreferencesPageElements.PUBLISH_APPROVERS_TABLE_REMOVE_USERNAME, username.toUpperCase()));
		}
		acceptAlert();
		waitForPageLoaded();
	}

	public void clickSave()
	{
		click(ContentPreferencesPageElements.saveButton);
		waitForPageLoaded();
	}

	public boolean sourceTagsFound()
	{
		return isElementDisplayed(ContentPreferencesPageElements.NOD_SOURCE_TAG_SETTINGS);
	}

	public boolean gridSettingsFound()
	{
		return isElementDisplayed(ContentPreferencesPageElements.NOD_UNREPORTED_FILTER);
	}

	public String getDailyTime()
	{
		return getElementsText(ContentPreferencesPageElements.nodUploadSettingsDailyUploadTime);
	}

	public void setDailyTime(String hours, String minutes, String amOrPm)
	{
		String time = hours+":"+minutes+" "+amOrPm.toUpperCase();
		click(ContentPreferencesPageElements.nodUploadSettingsDailyUploadTime);
		click(String.format(ContentPreferencesPageElements.NOD_UPLOAD_SETTINGS_DAILY_UPLOAD_TIME_XPATH,time));
	}

	public void setDailyTime(String time)
	{
		click(ContentPreferencesPageElements.nodUploadSettingsDailyUploadTime);
		click(String.format(ContentPreferencesPageElements.NOD_UPLOAD_SETTINGS_DAILY_UPLOAD_TIME_XPATH,time));
	}

	public void clickDailyUploadRadioButton()
	{
		click(ContentPreferencesPageElements.dailyUploadRadioButton);
	}

	public void clickCancel()
	{
		click(CommonPageElements.CANCEL_BUTTON);
	}

	public void uncheckEnableVolumeChoosingCheckbox()
	{
		uncheckCheckbox(ContentPreferencesPageElements.ENABLE_VOLUME_CHOOSING_CHECKBOX_XPATH);
	}

	public void checkEnableVolumeChoosingCheckbox()
	{
		checkCheckbox(ContentPreferencesPageElements.ENABLE_VOLUME_CHOOSING_CHECKBOX_XPATH);
	}

	public void selectVolumesFromVolumesTable(String... volumes)
	{
		click(String.format(ContentPreferencesPageElements.GIVEN_VOLUME_UNDER_VOLUMES_TABLE, volumes[0]));
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
			for (int i = 0; i < volumes.length; i++)
			{
				if(i == 0)
				{
					continue;
				}
				click(String.format(ContentPreferencesPageElements.GIVEN_VOLUME_UNDER_VOLUMES_TABLE, volumes[i]));
			}
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	public void selectVolumesFromDisabledVolumesTable(String... volumes)
	{
		click(String.format(ContentPreferencesPageElements.GIVEN_VOLUME_UNDER_DISABLED_VOLUMES_TABLE, volumes[0]));
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
			for (int i = 0; i < volumes.length; i++)
			{
				if(i == 0)
				{
					continue;
				}
				click(String.format(ContentPreferencesPageElements.GIVEN_VOLUME_UNDER_DISABLED_VOLUMES_TABLE, volumes[i]));
			}
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	public void clickSingleRightArrowButton()
	{
		click(ContentPreferencesPageElements.rightArrowButton);
		waitForPageLoaded();
	}

	public void clickSingleLeftArrowButton()
	{
		click(ContentPreferencesPageElements.leftArrowButton);
		waitForPageLoaded();
	}

	public List<String> getListOfAllVolumesUnderVolumesTable()
	{
		return getElements(ContentPreferencesPageElements.ALL_VOLUMES_UNDER_VOLUMES_TABLE).stream().map(WebElement::getText).collect(Collectors.toList());
	}

	public List<String> getListOfAllVolumesUnderDisabledVolumesTable()
	{
		return getElements(ContentPreferencesPageElements.ALL_VOLUMES_UNDER_DISABLED_VOLUMES_TABLE).stream().map(WebElement::getText).collect(Collectors.toList());
	}


	public boolean isContentSelected(String contentSet)
	{
		return doesElementExist(String.format(ContentPreferencesPageElements.SOURCE_SELECTED_CONTENT_GROUP,contentSet));
	}

	public boolean isContentUnSelected(String contentSet)
	{
		return doesElementExist(String.format(ContentPreferencesPageElements.SOURCE_HIDDEN_CONTENT_GROUP,contentSet));
	}

	public void selectHiddenContentOption(String contentSet)
	{
		click(String.format(ContentPreferencesPageElements.SOURCE_HIDDEN_CONTENT_GROUP,contentSet));
	}

	public void addSourceTargetContentSet()
	{
		click(ContentPreferencesPageElements.addSourceTargetContentSet);
	}

	public void selectSelectedContentOption(String contentSet)
	{
		click(String.format(ContentPreferencesPageElements.SOURCE_SELECTED_CONTENT_GROUP,contentSet));
	}

	public void removeSourceTargetContentSet()
	{
		click(ContentPreferencesPageElements.removeSourceTargetContentSet);
	}
}