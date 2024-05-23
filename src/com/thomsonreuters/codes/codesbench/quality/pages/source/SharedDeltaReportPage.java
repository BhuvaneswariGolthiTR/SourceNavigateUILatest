package com.thomsonreuters.codes.codesbench.quality.pages.source;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SharedDeltaReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SharedDeltaReportPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SharedDeltaReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SharedDeltaReportPageElements.class);
    }

    public void setFilterContentSet(String contentSet)
    {
        selectDropdownOption(SharedDeltaReportPageElements.contentSet, contentSet);
    }

    public void selectMaterialFromAvailableMaterials(String material)
    {
        click(String.format(SharedDeltaReportPageElements.NON_SELECTED_MATERIALS,material));
    }

    public void addMaterialToTheSelectedMaterialsList()
    {
        click(SharedDeltaReportPageElements.addVolsButton);
    }

    public int getNumberOfSelectedMaterials()
    {
        return getElements(SharedDeltaReportPageElements.LIST_OF_SELECTED_MATERIALS_OPTIONS).size();
    }

    public String getFirstSelectedMaterialsName()
    {
        return getElementsText(SharedDeltaReportPageElements.LIST_OF_SELECTED_MATERIALS_OPTIONS);
    }

    public void unCheckAllColumns()
    {
        uncheckCheckbox(SharedDeltaReportPageElements.allColumnsCheckBox);
    }

    public void checkAllColumns()
    {
        checkCheckbox(SharedDeltaReportPageElements.allColumnsCheckBox);
    }

    public boolean clickSubmitNoName()
    {
        click(SharedDeltaReportPageElements.submitButton);
        return AutoITUtils.verifyAlertTextAndAccept(true, "Report identifier needs to be provided");
    }

    public void clickSubmitWithName()
    {
        click(SharedDeltaReportPageElements.submitButton);
    }

    public void addReportName(String reportName)
    {
        sendTextToTextbox(SharedDeltaReportPageElements.reportNameField, reportName);
    }
}
