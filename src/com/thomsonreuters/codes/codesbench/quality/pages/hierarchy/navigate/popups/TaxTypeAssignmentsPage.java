package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.TaxTypeAssignmentsPageElements;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxTypeAssignmentsPage extends AssignmentsPage
{
    WebDriver driver;

    @Autowired
    public TaxTypeAssignmentsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public void switchToTaxTypeAssignmentsPage()
    {
        switchToWindow(TaxTypeAssignmentsPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }



}
