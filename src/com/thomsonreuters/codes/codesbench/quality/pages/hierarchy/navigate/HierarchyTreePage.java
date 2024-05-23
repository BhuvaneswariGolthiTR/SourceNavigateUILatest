package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.util.ArrayList;

@Component
public class HierarchyTreePage extends BasePage
{
	WebDriver driver;

	@Autowired
	public HierarchyTreePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HierarchyTreePageElements.class);
	}

	public void uncheckHideDeletedForNavigationTree()
	{
		uncheckCheckbox(HierarchyTreePageElements.hideDeletedCheckbox);
		waitForGridRefresh();
		waitForPageLoaded();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
	}

	public void checkHideDeletedForNavigationTree()
	{
		checkCheckbox(HierarchyTreePageElements.hideDeletedCheckbox);
		waitForGridRefresh();
	}

	public void rightClickSelectedNavigationTreeNode()
	{
		waitForPageLoaded();
		rightClick(HierarchyTreePageElements.selectedNode);
	}

	public String getSelectedCollapsedTreeNodeValue()
	{
		waitForPageLoaded();
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
		return getElementsText(HierarchyTreePageElements.COLLAPSED_SELECTED_NODE_TEXT);
	}

	public String getSelectedTreeNodeValue()
	{
		waitForPageLoaded();
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
		return getElementsText(HierarchyTreePageElements.selectedNode).trim();
	}

	public String getNodeValueAboveSelectedTreeNode()
	{
		waitForElement(HierarchyTreePageElements.selectedNode);
		HierarchyTreePageElements.selectedNode.sendKeys(Keys.UP);
		return getElementsText(HierarchyTreePageElements.navTreeSelectedSectionNameValue).trim();
	}

	public void getNodeValueDownSelectedTreeNode()
	{
		waitForElement(HierarchyTreePageElements.selectedNode);
		HierarchyTreePageElements.selectedNode.sendKeys(Keys.RIGHT);
		click(HierarchyTreePageElements.navTreeSelectedSectionNameValue);
	}

	public boolean doesSelectedNodeHaveChildren()
	{
		waitForPageLoaded();
		return !isElementDisplayed(HierarchyTreePageElements.NAV_TREE_SELECTED_NODE_WITHOUT_CHILDREN);
	}

	public void setNavigationTreeToCurrentDate()
	{
		sendEnterToElement(HierarchyTreePageElements.calendarButton);
		waitForElement(HierarchyTreePageElements.calendarModal);
		click(String.format(HierarchyTreePageElements.CALENDAR_MODAL_CELL, DateAndTimeUtils.getCurrentDayDWithoutLeadingZero()));
		waitForPageLoaded();
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
	}

	public void setNavigationTreeToYesterdaysDate()
	{
		sendEnterToElement(HierarchyTreePageElements.calendarButton);
		waitForElement(HierarchyTreePageElements.calendarModal);
		click(String.format(HierarchyTreePageElements.CALENDAR_MODAL_CELL, DateAndTimeUtils.getYesterdayDayDWithoutLeadingZero()));
		waitForPageLoaded();
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
	}

	public void setDateOfTree(String date)
	{
		clearAndSendKeysToElement(HierarchyTreePageElements.date, date);
		sendEnterToElement(HierarchyTreePageElements.date);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public boolean isTreeNodeWithGivenValueDeleted(String nodeValue)
	{
		waitForPageLoaded();
		return doesElementExist(String.format(HierarchyTreePageElements.DELETED_NODE_WITH_GIVEN_VALUE_XPATH,nodeValue));
	}

	public boolean isSelectedTreeNodeDisplayedByValue(String nodeValue)
	{
		waitForPageLoaded();
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
		return getSelectedTreeNodeValue().contains(nodeValue);
	}

	public void clickExpandButtonNextToGivenValue(String valueToExpand)
	{
		click(String.format(HierarchyTreePageElements.EXPAND_BUTTON_NEXT_TO_GIVEN_VALUE,valueToExpand));
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public boolean isTreeNodeDisplayedWithGivenValue(String treeNodeValue)
	{
		return doesElementExist(String.format(HierarchyTreePageElements.NAV_TREE_NODE_WITH_VALUE_GIVEN,treeNodeValue));
	}

	public void clickOnTreeNodeWithGivenValue(String treeNodeValue)
	{
		click(String.format(HierarchyTreePageElements.NAV_TREE_NODE_WITH_VALUE_GIVEN, treeNodeValue));
		waitForPageLoaded();
		waitForGridRefresh();
		//TODO: Run related tests to see if stability is an issue
		//waitForGridRefresh();
	}


}