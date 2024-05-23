package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.BillGapCountContentSetPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BillGapCountContentSetPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public BillGapCountContentSetPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, BillGapCountContentSetPageElements.class);
    }

    public void setDropdownNotSelected(String setValue)
    {
        //selectDropdownOption(BillGapCountContentSetPageElements.notSelectedContentSetDropdown, setValue);
        setValue = contentSetMapper(setValue);
        selectDropdownOptionUsingJavascript(BillGapCountContentSetPageElements.NOT_SELECTED_CONTENT_SET_DROPDOWN_ID, setValue);
    }
    public void pressMoveOneFromNonSelectedToSelected()
    {
        sendEnterToElement(BillGapCountContentSetPageElements.moveOneNonSelectedToSelectedButton);
    }
    public void setDropdownSelected(String setValue)
    {
        //selectDropdownOption(BillGapCountContentSetPageElements.selectedContentSetDropdown, setValue);
        setValue = contentSetMapper(setValue);
        selectDropdownOptionUsingJavascript(BillGapCountContentSetPageElements.SELECTED_CONTENT_SET_DROPDOWN_ID, setValue);
    }
    public void pressMoveOneFromSelectedToNonSelected()
    {
        click(BillGapCountContentSetPageElements.moveOneSelectedToNonSelectedButton);
    }

    private String contentSetMapper(String contentSet)
    {
        switch(contentSet)
        {
            case "Iowa (Development)": return "SOS.IAT";
            case "USCA(Development)": return "SOS.UST";
            case "Alabama (Development)": return "SOS.ALT";
            default: logger.information("Unsupported setValue.  Please check to see if we have a map for your content set.");
            return "";
        }
    }
}
