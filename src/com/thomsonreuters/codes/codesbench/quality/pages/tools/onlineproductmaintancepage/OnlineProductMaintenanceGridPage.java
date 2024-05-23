package com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.onlineproductmaintenance.OnlineProductMaintenanceGridPageElement;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class OnlineProductMaintenanceGridPage extends BasePage
{
    WebDriver driver;

    @Autowired
    public OnlineProductMaintenanceGridPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, OnlineProductMaintenanceGridPageElement.class);
    }

    public boolean isOnlineProductInGrid(String viewTag, String productName, String productShortName)
    {
        boolean result = false;
        List<String> table = getElementsTextList(OnlineProductMaintenanceGridPageElement.ROWS);
        for (String onlineProduct : table)
        {
            if (onlineProduct.contains(viewTag)
                    && onlineProduct.contains(productName)
                    && onlineProduct.contains("VRB." + productShortName))
            {
                result = true;
                break;
            }
        }
        return result;
    }

    public String getProductIdByViewTag(String viewTag)
    {
        String result = "There is NO such View/Tag";
        if (doesElementExist(String.format(OnlineProductMaintenanceGridPageElement.ROW_WITH_VIEW_TAG_GIVEN, viewTag)))
        {
            result = getElement(String.format(OnlineProductMaintenanceGridPageElement.ROW_WITH_VIEW_TAG_GIVEN + OnlineProductMaintenanceGridPageElement.PRODUCT_ID, viewTag)).getText();
        }
        return result;
    }

    public Integer getProductIdByViewTagAsInt(String viewTag)
    {
        Integer result = 0;
        if (doesElementExist(String.format(OnlineProductMaintenanceGridPageElement.ROW_WITH_VIEW_TAG_GIVEN, viewTag)))
        {
            result = Integer.valueOf(getElement(String.format(OnlineProductMaintenanceGridPageElement.ROW_WITH_VIEW_TAG_GIVEN + OnlineProductMaintenanceGridPageElement.PRODUCT_ID, viewTag)).getText().split("-")[1]);
        }
        return result;
    }

    public String getProductNodeAssignmentByViewTag(String viewTag)
    {
        String result = "There is NO such View/Tag";
        if (doesElementExist(String.format(OnlineProductMaintenanceGridPageElement.ROW_WITH_VIEW_TAG_GIVEN, viewTag)))
        {
            result = getElement(String.format(OnlineProductMaintenanceGridPageElement.ROW_WITH_VIEW_TAG_GIVEN + OnlineProductMaintenanceGridPageElement.NODE_ASSIGNMENT, viewTag)).getText();
        }
        return result;
    }

    public void rightClickProductTag(String viewTag)
    {
        scrollToElement(getElement(String.format(OnlineProductMaintenanceGridPageElement.VIEW_TAG_WITH_TEXT, viewTag)));
        rightClick(String.format(OnlineProductMaintenanceGridPageElement.VIEW_TAG_WITH_TEXT, viewTag));
    }

    public void rightClickStrikeProductTag(String viewTag)
    {
        scrollToElement(getElement(String.format(OnlineProductMaintenanceGridPageElement.VIEW_TAG_WITH_STRIKE_TEXT, viewTag)));
        rightClick(String.format(OnlineProductMaintenanceGridPageElement.VIEW_TAG_WITH_STRIKE_TEXT, viewTag));
    }

}
