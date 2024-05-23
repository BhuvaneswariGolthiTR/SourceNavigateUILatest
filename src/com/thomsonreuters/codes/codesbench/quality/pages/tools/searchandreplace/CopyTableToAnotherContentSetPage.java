package com.thomsonreuters.codes.codesbench.quality.pages.tools.searchandreplace;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.SiblingMetadataElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace.CopyTableToAnotherContentSetPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import java.awt.*;
import java.awt.event.KeyEvent;

@Component
public class CopyTableToAnotherContentSetPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public CopyTableToAnotherContentSetPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		
	}
	
	public void multiselectContentSets(String ...contentSets)
	{
		click(String.format(CopyTableToAnotherContentSetPageElements.CONTENT_SET_TO_SELECT, contentSets[0]));
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
			for (int i = 0; i < contentSets.length; i++)
			{
				if(i == 0)
				{
					continue;
				}
				click(String.format(CopyTableToAnotherContentSetPageElements.CONTENT_SET_TO_SELECT, contentSets[i]));
			}
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
	}
	
	public void enterTableName(String tableName)
	{
		sendKeysToElement(CopyTableToAnotherContentSetPageElements.TABLE_NAME_TEXTBOX, tableName);
	}
	
	public void enterTableDescription(String tableDescription)
	{
		sendKeysToElement(CopyTableToAnotherContentSetPageElements.TABLE_DESCRIPTION_TEXTBOX, tableDescription);
	}	
}
