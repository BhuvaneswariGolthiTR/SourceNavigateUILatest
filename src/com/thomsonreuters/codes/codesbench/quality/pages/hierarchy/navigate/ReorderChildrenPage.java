package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ReorderChildrenPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ReorderChildrenPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ReorderChildrenPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ReorderChildrenPageElements.class);
    }

    public void clickAndDragFirstChildDownOne()
    {
        WebElement elementToClickAndDrag = getElements(ReorderChildrenPageElements.REORDER_DRAG_LIST).get(0);
        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(elementToClickAndDrag).moveByOffset(0, 15).release().build();
        dragAndDrop.perform();
    }

    public void clickQuickLoadSave()
    {
        sendEnterToElement(ReorderChildrenPageElements.quickLoadButton);
        waitForElement(ReorderChildrenPageElements.saveButton);
        sendEnterToElement(ReorderChildrenPageElements.saveButton);
        waitForWindowGoneByTitle(ReorderChildrenPageElements.REORDER_CHILDREN_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
