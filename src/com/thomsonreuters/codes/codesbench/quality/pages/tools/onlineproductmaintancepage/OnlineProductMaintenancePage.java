package com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.OnlineProductMaintenanceContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.openqa.selenium.Keys.ENTER;

@Component
public class OnlineProductMaintenancePage extends BasePage
{

    private WebDriver driver;

    @Autowired
    public OnlineProductMaintenancePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, OnlineProductMaintenancePageElements.class);
        PageFactory.initElements(driver, OnlineProductMaintenanceContextMenuElements.class);
    }

    public void selectProduct(ProductType productType)
    {
        selectDropdownOption(OnlineProductMaintenancePageElements.selectProductDropdown, productType.getType());
    }

    public String getSelectedProduct()
    {
        return getSelectedDropdownOptionText(OnlineProductMaintenancePageElements.selectProductDropdown);
    }

    public boolean isShowDeletedSelected()
    {
        return OnlineProductMaintenancePageElements.showDeletedCheckbox.isSelected();
    }

    public void selectShowDeleted()
    {
        if (!isShowDeletedSelected())
        {
            OnlineProductMaintenancePageElements.showDeletedCheckbox.click();
        }
    }

    public void clickRefresh()
    {
        OnlineProductMaintenancePageElements.refreshButton.sendKeys(ENTER);
        waitForGridRefresh();
        waitForPageLoaded();
        waitForGridRefresh();
    }

    public boolean selectContentSetByName(String name)
    {
        if (doesElementExist(String.format(OnlineProductMaintenancePageElements.CONTENT_SET, name)))
        {
            getElement(String.format(OnlineProductMaintenancePageElements.CONTENT_SET, name)).click();
            return true;
        }
        return false;
    }


}
