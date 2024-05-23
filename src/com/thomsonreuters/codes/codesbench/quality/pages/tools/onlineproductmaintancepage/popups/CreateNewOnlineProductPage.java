package com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups.CreateNewOnlineProductPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CreateNewOnlineProductPage extends BasePage
{

    private WebDriver driver;

    @Autowired
    public CreateNewOnlineProductPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CreateNewOnlineProductPageElements.class);
    }

    public void selectProductType(ProductType optionToSelect)
    {
        selectDropdownOption(CreateNewOnlineProductPageElements.productTypeDropdown, optionToSelect.getType());
    }

    public void setViewTag(String setValue)
    {
        clearAndSendKeysToElement(CreateNewOnlineProductPageElements.viewTagTextField, setValue);
    }

    public void setProductName(String setValue)
    {
        clearAndSendKeysToElement(CreateNewOnlineProductPageElements.productNameTextField, setValue);
    }

    public void setProductShortName(String setValue)
    {
        clearAndSendKeysToElement(CreateNewOnlineProductPageElements.productShortNameTextField, setValue);
    }

    public List<String> getSelectedContentSet()
    {
        return getElementsTextList(CreateNewOnlineProductPageElements.SELECTED_CONTENT_SET_LIST);
    }

    public void addAssignments(List<String> setList)
    {
        for (String set : setList)
        {
            if (doesElementExist(String.format(CreateNewOnlineProductPageElements.NON_SELECTED_CONTENT_SET_BY_NAME, set)))
            {
                getElement(String.format(CreateNewOnlineProductPageElements.NON_SELECTED_CONTENT_SET_BY_NAME, set)).click();
                sendEnterToElement(CreateNewOnlineProductPageElements.addContentSet);
            }
        }
    }

    public boolean clickCancel()
    {
        sendEnterToElement(CommonPageElements.CANCEL_BUTTON);
        waitForWindowGoneByTitle(CreateNewOnlineProductPageElements.PAGE_TITLE);
        boolean windowClosed = !doesWindowExistByTitle(CreateNewOnlineProductPageElements.PAGE_TITLE);
        switchToWindow(OnlineProductMaintenancePageElements.PAGE_TITLE);
        return windowClosed;
    }

    public boolean clickSave()
    {
        sendEnterToElement(CommonPageElements.SAVE_BUTTON);
        waitForWindowGoneByTitle(CreateNewOnlineProductPageElements.PAGE_TITLE);
        boolean windowClosed = !doesWindowExistByTitle(CreateNewOnlineProductPageElements.PAGE_TITLE);
        switchToWindow(OnlineProductMaintenancePageElements.PAGE_TITLE);
        return windowClosed;
    }
}
