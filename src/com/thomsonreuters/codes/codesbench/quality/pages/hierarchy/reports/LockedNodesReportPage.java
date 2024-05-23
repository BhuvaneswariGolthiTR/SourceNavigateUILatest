package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.reports;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyLockedNodesReportElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LockedNodesReportPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public LockedNodesReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HierarchyLockedNodesReportElements.class);
    }

    public void selectNodeByGivenUuid(String uuid)
    {
        click(String.format(HierarchyLockedNodesReportElements.SELECT_NODE_OF_GIVEN_UUID,uuid));
    }

    public void rightClickSelectedNode()
    {
    rightClick(HierarchyLockedNodesReportElements.selectedNode);
    }

    public boolean isNodeDisplayedByGivenUuid(String uuid)
    {
        return doesElementExist(String.format(HierarchyLockedNodesReportElements.SELECT_NODE_OF_GIVEN_UUID,uuid));
    }

    public void clickRefresh()
    {
        click(HierarchyLockedNodesReportElements.refreshButton);
        waitForGridRefresh();
    }

    public void switchToLockedNodesReportPage()
    {
        switchToWindow(HierarchyLockedNodesReportElements.HIERARCHY_LOCKED_NODES_REPORT_PAGE_TITLE);
    }

    public boolean gridExists(){
        return doesElementExist(HierarchyLockedNodesReportElements.GRID_TABLE);
    }

    public void selectNodeWithoutGivenUserInfo(String usernameToAvoid)
    {
        boolean differentUserLockedNodeAppears = isElementDisplayed(String.format(HierarchyLockedNodesReportElements.NODE_WITHOUT_GIVEN_USERNAME,usernameToAvoid));
        Assertions.assertTrue(differentUserLockedNodeAppears,"No node locked by another user is displayed, one must be created");
        click(String.format(HierarchyLockedNodesReportElements.NODE_WITHOUT_GIVEN_USERNAME,usernameToAvoid));
    }
}
