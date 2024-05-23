package com.thomsonreuters.codes.codesbench.quality.menus.mainmenus;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.HierarchyMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class HierarchyMenu extends Menu
{
	private WebDriver driver;

	@Autowired
	public HierarchyMenu(WebDriver driver)
	{
		super(driver);
	    this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
	    PageFactory.initElements(driver, HierarchyMenuElements.class);
		PageFactory.initElements(driver, HierarchyTreePageElements.class);
	}

	public boolean goToLockedNodesReport()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		waitForElement(HierarchyMenuElements.HIERARCHY_MENU_OPTIONS_XPATH);
    	openSubMenu(HierarchyMenuElements.reports);
		waitForElement(HierarchyMenuElements.HIERARCHY_REPORTS_MENU_OPTIONS_XPATH);
    	sendEnterToElement(HierarchyMenuElements.lockedNodesReport);
		return switchToWindow(HierarchyLockedNodesReportElements.HIERARCHY_LOCKED_NODES_REPORT_PAGE_TITLE);
	}

	public boolean goToNavigate()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		waitForElement(HierarchyMenuElements.HIERARCHY_MENU_OPTIONS_XPATH);
		sendEnterToElement(HierarchyMenuElements.navigate);
		boolean hierarchyNavigatePageAppeared = switchToWindow(HierarchyPageElements.PAGE_TITLE);
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		return hierarchyNavigatePageAppeared;
	}
	
	public boolean goToPubNavigate()
	{
		openMenu(HierarchyMenuElements.hierarchy);
    	sendEnterToElement(HierarchyMenuElements.pubNavigate);
		boolean hierarchyPubNavigatePageAppeared = switchToWindow(HierarchyPageElements.HIERARCHY_PUB_NAVIGATE_PAGE_TITLE);
		waitForGridRefresh();
		waitForElementToBeClickable(HierarchyTreePageElements.selectedNode);
		return hierarchyPubNavigatePageAppeared;
	}

	public boolean areCarswellHierarchyMenuOptionsCorrectlyDisplayed()
	{
		List<String> expectedReportsOptionsUnavailableToCarswellUser = Arrays.asList("Keycite Dates Report", "Script Listing Report", "Target Tags Report", "New Nodes Report", "Source Documents In Hierarchy Report");
		List<String> expectedOptionsUnavailableToCarswellUser = Arrays.asList("Recomp Navigation");
		List<String> expectedReportsOptionsAvailableToCarswellUser = Arrays.asList("Locked Nodes Report");
		List<String> expectedOptionsAvailableToCarswellUser = Arrays.asList("Navigate", "Pub Navigate", "Reports");

		openMenu(HierarchyMenuElements.hierarchy);
		List<String> actualOptionsAvailableToCarswellUser = new ArrayList<>();
		getElements(HierarchyMenuElements.HIERARCHY_MENU_OPTIONS_XPATH).stream().forEach(option -> actualOptionsAvailableToCarswellUser.add(option.getText()));

		openSubMenu(HierarchyMenuElements.reports);
		List<String> actualReportsOptionsAvailableToCarswellUser = new ArrayList<>();
		getElements(HierarchyMenuElements.HIERARCHY_REPORTS_MENU_OPTIONS_XPATH).stream().forEach(option -> actualReportsOptionsAvailableToCarswellUser.add(option.getText()));

		return actualOptionsAvailableToCarswellUser.containsAll(expectedOptionsAvailableToCarswellUser) &&
				!actualOptionsAvailableToCarswellUser.containsAll(expectedOptionsUnavailableToCarswellUser) &&
				actualReportsOptionsAvailableToCarswellUser.containsAll(expectedReportsOptionsAvailableToCarswellUser) &&
				!actualReportsOptionsAvailableToCarswellUser.containsAll(expectedReportsOptionsUnavailableToCarswellUser);
	}

	public boolean goToHierarchyReportsTargetTagsReport()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		openSubMenu(HierarchyMenuElements.reports);
		sendEnterToElement(HierarchyMenuElements.hierarchyMenuTargetTagsReportXpath);
		return switchToWindow(HierarchyTargetTagsReportPageElements.HIERARCHY_MENU_TARGET_TAGS_REPORT_PAGE_TITLE);
	}

	public boolean goToHierarchyReportsDateSearchMultipleDocuments()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		openSubMenu(HierarchyMenuElements.reports);
		sendEnterToElement(HierarchyMenuElements.hierarchyDateSearchMultipleDocumentsReportPage);
		return switchToWindowWithNoTitle(2);
	}

	public boolean goToHierarchyRecompNavigation()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		sendEnterToElement(HierarchyMenuElements.hierarchyMenuRecompNavigationXpath);
		return switchToWindow(hierarchyRecompNavigationPageElements.HIERARCHY_MENU_RECOMP_NAVIGATION_PAGE_TITLE);
	}

	public boolean goToHierarchyReportsScriptListingReport()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		openSubMenu(HierarchyMenuElements.reports);
		sendEnterToElement(HierarchyMenuElements.hierarchyMenuScriptListingReportXpath);
		return switchToWindow(HierarchyScriptListingReportsPageElements.HIERARCHY_MENU_SCRIPT_LISTING_REPORT_PAGE_TITLE);
	}

	public boolean goToHierarchyReportsNewNodesReport()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		openSubMenu(HierarchyMenuElements.reports);
		sendEnterToElement(HierarchyMenuElements.newNodesReport);
		return switchToWindow(HierarchyMenuNewNodesReportPageElements.HIERARCHY_MENU_NEW_NODES_REPORT_PAGE_TITLE);
	}

	public boolean goToReportsSourceDocumentsInHierarchyReport()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		openSubMenu(HierarchyMenuElements.reports);
		sendEnterToElement(HierarchyMenuElements.hierarchyMenuSourceDocumentsXpath);
		return switchToWindow(HierarchySourceDocumentsPageElements.HIERARCHY_MENU_SOURCE_DOCUMENTS_PAGE_TITLE);
	}

	public boolean goToHierarchyRelatedRuleBooks()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		sendEnterToElement(HierarchyMenuElements.hierarchyMenuNavigateToRelatedRuleBookXpath);
		boolean relatedRuleBookWindowAppeared = switchToWindow(RelatedRuleBooksPageElements.RELATED_RULE_BOOK_PAGE_TITLE);
		enterTheInnerFrame();
		return relatedRuleBookWindowAppeared;
	}

	public boolean goToHierarchyReportsVirtualRuleBookReport()
	{
		openMenu(HierarchyMenuElements.hierarchy);
		openSubMenu(HierarchyMenuElements.reports);
		sendEnterToElement(HierarchyMenuElements.hierarchyMenuReportsVirtualRulebookReportXpath);
		return switchToWindow(hierarchyReportsVirtualRulebookReportPageElements.HIERARCHY_MENU_REPORTS_VIRTUAL_RULEBOOK_REPORT_PAGE_TITLE);
	}

	public void openMenu()
	{
		sendKeyToElement(HierarchyMenuElements.hierarchy, Keys.ARROW_DOWN);
	}

	public void openReportsSubMenu()
	{
		sendKeyToElement(HierarchyMenuElements.reports, Keys.ARROW_DOWN);
	}
}
