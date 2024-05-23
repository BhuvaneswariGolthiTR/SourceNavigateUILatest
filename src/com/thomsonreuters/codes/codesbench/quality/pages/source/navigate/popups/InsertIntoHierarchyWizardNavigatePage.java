package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.GroupingPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.InsertIntoHierarchyWizardPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.popups.InsertIntoHierarchyWizardElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertIntoHierarchyWizardNavigatePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public InsertIntoHierarchyWizardNavigatePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InsertIntoHierarchyWizardPageElements.class);
    }

    public void clickFindButton()
    {
        click(InsertIntoHierarchyWizardPageElements.findButton);
    }

    public String getSelectedNodeText()
    {
        return getElementsText(InsertIntoHierarchyWizardPageElements.SELECTED_NODE_ON_THE_NODE_TREE);
    }

    public void addBelowSiblingButton()
    {
        click(InsertIntoHierarchyWizardPageElements.addBelowAsSiblingButton);
    }

    public void clickCloseButton()
    {
        click(InsertIntoHierarchyWizardPageElements.CLOSE_BUTTON);
    }

    public void clickAddBelowAsSibling()
    {
        click(InsertIntoHierarchyWizardElements.ADD_BELOW_AS_SIBLING);
        waitForGridRefresh();
    }

    public void clickAddBelowAsChild()
    {
        click(InsertIntoHierarchyWizardElements.ADD_BELOW_AS_CHILD);
    }

    public void clickSelectAsTarget()
    {
        click(InsertIntoHierarchyWizardElements.SELECT_AS_TARGET);
        waitForGridRefresh();
    }

    public void clickClose()
    {
        click(InsertIntoHierarchyWizardElements.CLOSE);
    }
}
