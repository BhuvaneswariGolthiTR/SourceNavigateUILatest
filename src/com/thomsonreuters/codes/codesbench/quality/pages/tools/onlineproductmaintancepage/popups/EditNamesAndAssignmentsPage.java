package com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups.CreateNewOnlineProductPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.popups.EditNamesAndAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class EditNamesAndAssignmentsPage extends BasePage
{

    private WebDriver driver;

    @Autowired
    public EditNamesAndAssignmentsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EditNamesAndAssignmentsPageElements.class);
    }

    public void setProductName(String setValue)
    {
        clearAndSendKeysToElement(EditNamesAndAssignmentsPageElements.productNameTextField, setValue);
    }

    public void setProductShortName(String setValue)
    {
        clearAndSendKeysToElement(EditNamesAndAssignmentsPageElements.productShortNameTextField, setValue);
    }

    public boolean clickSave()
    {
        sendEnterToElement(CommonPageElements.SAVE_BUTTON);
        waitForWindowGoneByTitle(EditNamesAndAssignmentsPageElements.PAGE_TITLE);
        boolean windowClosed = !doesWindowExistByTitle(EditNamesAndAssignmentsPageElements.PAGE_TITLE);
        switchToWindow(OnlineProductMaintenancePageElements.PAGE_TITLE);
        return windowClosed;
    }

    public String getProductTypeValue()
    {
        return EditNamesAndAssignmentsPageElements.productType.getText();
    }

    public String getViewTagValue()
    {
        return EditNamesAndAssignmentsPageElements.viewTag.getText();
    }

    public String getProductId()
    {
        return EditNamesAndAssignmentsPageElements.productId.getText();
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

}
