package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ViewSelectedNodesBinPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ViewSelectedNodesBinPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ViewSelectedNodesBinPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ViewSelectedNodesBinPageElements.class);
    }

    public void emptyBin()
    {
        sendEnterToElement(ViewSelectedNodesBinPageElements.emptyButton);
        waitForWindowGoneByTitle(ViewSelectedNodesBinPageElements.VIEW_SELECTED_NODES_BIN_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public String getNodeValueByRow(int row)
    {
        return getElementsText("(" + ViewSelectedNodesBinPageElements.NODE_VALUE_XPATH + ")[" + row + "]");
    }

    public Boolean isBinEmpty()
    {
        return getElementsText(ViewSelectedNodesBinPageElements.firstItemValue).equals("No records found.");
    }

    public void rightClickFindInHierarchy()
    {
        rightClick(ViewSelectedNodesBinPageElements.firstItemValue);
        click(ViewSelectedNodesBinPageElements.findInHierarchy);
        waitForWindowGoneByTitle(ViewSelectedNodesBinPageElements.VIEW_SELECTED_NODES_BIN_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }
}
