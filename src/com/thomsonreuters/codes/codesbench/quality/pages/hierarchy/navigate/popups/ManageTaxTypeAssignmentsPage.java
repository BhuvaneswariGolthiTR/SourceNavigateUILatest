package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ManageTaxTypeAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.TaxTypeAssignmentsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ManageTaxTypeAssignmentsPage extends ManageAssignmentsPage
{
    WebDriver driver;

    @Autowired
    public ManageTaxTypeAssignmentsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ManageTaxTypeAssignmentsPageElements.class);
        super.init();
    }

    public boolean clickSubmit()
    {
        sendEnterToElement(CommonPageElements.SUBMIT_BUTTON);
        waitForWindowGoneByTitle(ManageTaxTypeAssignmentsPageElements.PAGE_TITLE);
        boolean windowClosed = !doesWindowExistByTitle(ManageTaxTypeAssignmentsPageElements.PAGE_TITLE);
        switchToWindow(TaxTypeAssignmentsPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return windowClosed;
    }

    public String getSelectedTaxTypes()
    {
        StringBuilder taxTypes= new StringBuilder();
        for (WebElement taxType: getElements(ManageTaxTypeAssignmentsPageElements.SELECTED_TAX_TYPES))
        {
            taxTypes.append(taxType.getText());}
        return taxTypes.toString();
    }

    public String getSelectedDocuments()
    {
        return getElement(ManageTaxTypeAssignmentsPageElements.SELECTED_DOCUMENTS).getText();
    }

    public String getSelectedHierarchyScope()
    {
        return getElement(ManageTaxTypeAssignmentsPageElements.SELECTED_HIERARCHY_SCOPE).getText();
    }


}
