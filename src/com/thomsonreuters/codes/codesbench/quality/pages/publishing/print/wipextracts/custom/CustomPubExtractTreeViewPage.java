package com.thomsonreuters.codes.codesbench.quality.pages.publishing.print.wipextracts.custom;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom.CustomPubExtractTreeViewPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import java.awt.*;
import java.awt.event.KeyEvent;

import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomPubExtractTreeViewPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CustomPubExtractTreeViewPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CustomPubExtractTreeViewPageElements.class);
    }

    public void clickExpandButtonNextToValueInTreeViewPane(String valueToExpand)
    {
        scrollToView(String.format(CustomPubExtractTreeViewPageElements.COLLAPSED_BUTTON_NEXT_TO_VALUE_IN_TREE_VIEW,valueToExpand));
        click(String.format(CustomPubExtractTreeViewPageElements.COLLAPSED_BUTTON_NEXT_TO_VALUE_IN_TREE_VIEW,valueToExpand));
        waitForElement(String.format(CustomPubExtractTreeViewPageElements.EXPANDED_BUTTON_NEXT_TO_VALUE_IN_TREE_VIEW,valueToExpand));
        waitForPageLoaded();
    }

    public void clickValueInSelectedRecordsPane(String value)
    {
        scrollToView(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE,value));
        click(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, value));
    }

    public void clickRightMultipleArrowButton()
    {
        click(CustomPubExtractTreeViewPageElements.rightMultipleArrowButton);
        waitForGridRefresh();
    }

    public void clickRightSingleArrowButton()
    {
        click(CustomPubExtractTreeViewPageElements.rightSingleArrowButton);
        waitForGridRefresh();
    }

    public void clickLeftArrowButton()
    {
        click(CustomPubExtractTreeViewPageElements.leftArrowButton);
        waitForGridRefresh();
    }

    public boolean isValueInSelectedRecordsView(String value)
    {
        return isElementDisplayed(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, value));
    }

    public void selectMultipleValuesInTreeViewPaneValues(String... values)
    {
        scrollToView(String.format(CustomPubExtractTreeViewPageElements.TREE_VIEW_PANE_VALUE, values[0]));
        click(String.format(CustomPubExtractTreeViewPageElements.TREE_VIEW_PANE_VALUE, values[0]));
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            for (int i = 0; i < values.length; i++)
            {
                if(i == 0)
                {
                    continue;
                }
                scrollToView(String.format(CustomPubExtractTreeViewPageElements.TREE_VIEW_PANE_VALUE, values[i]));
                click(String.format(CustomPubExtractTreeViewPageElements.TREE_VIEW_PANE_VALUE, values[i]));
            }
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public void selectMultipleValuesInSelectedRecordsPaneValues(String... values)
    {
        scrollToView(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[0]));
        click(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[0]));
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            for (int i = 0; i < values.length; i++)
            {
                if(i == 0)
                {
                    continue;
                }
                scrollToView(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[0]));
                click(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[i]));
            }
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
        }
    }
}
