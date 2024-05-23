package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ManageAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ManageTaxTypeAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ManageAssignmentsPage extends BasePage
{
    WebDriver driver;

    @Autowired
    public ManageAssignmentsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ManageAssignmentsPageElements.class);
        PageFactory.initElements(driver, ManageTaxTypeAssignmentsPageElements.class);
    }

    public String getSelectedDocument()
    {
        return getElement(ManageAssignmentsPageElements.SELECTED_DOCUMENT).getText();
    }

    public String getSelectedProductViewTag()
    {
        return getElement(ManageAssignmentsPageElements.SELECTED_PRODUCT_VIEW_TAG).getText();
    }

    public String getSelectedProductType()
    {
        return getElement(ManageAssignmentsPageElements.SELECTED_PRODUCT_TYPE).getText();
    }

    public String getSelectedProductName()
    {
        return getElement(ManageAssignmentsPageElements.SELECTED_PRODUCT_NAME).getText();
    }

    public String getSelectedProductCurrentAssignmentStatus()
    {
        return getElement(ManageAssignmentsPageElements.SELECTED_PRODUCT_CURRENT_ASSIGNMENT_STATUS).getText();
    }

    public void selectAddTag()
    {
        if (!getElement(ManageAssignmentsPageElements.ADD_TAG).isSelected())
        {
            getElement(ManageAssignmentsPageElements.ADD_TAG).click();
        }
    }

    public void selectRemoveTag()
    {
        if (!getElement(ManageAssignmentsPageElements.REMOVE_TAG).isSelected())
        {
            getElement(ManageAssignmentsPageElements.REMOVE_TAG).click();
        }
    }

    public void selectSingleNodeOnly()
    {
        if (!getElement(ManageAssignmentsPageElements.SINGLE_NODE_ONLY).isSelected())
        {
            getElement(ManageAssignmentsPageElements.SINGLE_NODE_ONLY).click();
        }
    }

    public void selectWithAncestorsAndDescendants()
    {
        if (!getElement(ManageAssignmentsPageElements.WITH_ANCESTORS_AND_DESCENDANTS).isSelected())
        {
            getElement(ManageAssignmentsPageElements.WITH_ANCESTORS_AND_DESCENDANTS).click();
        }
    }

    public void selectWithDescendantsOnly()
    {
        if (!getElement(ManageAssignmentsPageElements.WITH_DESCENDANTS_ONLY).isSelected())
        {
            getElement(ManageAssignmentsPageElements.WITH_DESCENDANTS_ONLY).click();
        }
    }

    public void selectAllVersions()
    {
        if (!getElement(ManageAssignmentsPageElements.ALL_VERSIONS).isSelected())
        {
            getElement(ManageAssignmentsPageElements.ALL_VERSIONS).click();
        }
    }

    public void selectCurrentAndProspectiveVersionsOnly()
    {
        if (!getElement(ManageAssignmentsPageElements.CURRENT_AND_PROSPECTIVE_VERSIONS_ONLY).isSelected())
        {
            getElement(ManageAssignmentsPageElements.CURRENT_AND_PROSPECTIVE_VERSIONS_ONLY).click();
        }
    }


}
