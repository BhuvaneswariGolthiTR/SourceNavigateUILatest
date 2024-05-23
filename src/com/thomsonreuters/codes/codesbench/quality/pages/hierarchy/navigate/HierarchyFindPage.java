package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyFindPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HierarchyFindPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public HierarchyFindPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HierarchyFindPageElements.class);
    }

    public void clickFirstNodeUuid()
    {
        waitForPageLoaded();
        waitForGridRefresh();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles)//This is needed to switch from 'Find Cite' window to 'Find' window
        {
            try
            {
                driver.switchTo().window(windowHandle);
            }
            catch (UnhandledAlertException e)
            {
                acceptAlertNoFail();
                driver.switchTo().window(windowHandle);
            }
            String currentWindowTitle = driver.getTitle();
            if(currentWindowTitle.equals("Find"))
            {
                break;
            }
        }
        enterTheInnerFrame();
        click(HierarchyFindPageElements.firstNodeUuidLink);
        waitForWindowGoneByTitle(HierarchyFindPageElements.FIND_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public boolean numberOfNodesFoundEqualsGivenValue(String numberOfNodes)
    {
        switchToWindow(HierarchyFindPageElements.FIND_PAGE_TITLE);
        enterTheInnerFrame();
        return getElementsText(HierarchyFindPageElements.searchResultSummaryInFindPage).trim().contains(numberOfNodes + " nodes matched your criteria");
    }

    public List<String> listOfAllNodeUuidsShown()
    {
        switchToWindow(HierarchyFindPageElements.FIND_PAGE_TITLE);
        enterTheInnerFrame();
        return getElements(HierarchyFindPageElements.ALL_UUIDS_IN_GRID).stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
