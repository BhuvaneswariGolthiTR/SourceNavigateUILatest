package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DocumentSearchFilterPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DocumentSearchFilterPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DocumentSearchFilterPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DocumentSearchFilterPageElements.class);
    }

    public void setCorIdFilter(String corId)
    {
        sendTextToTextbox(DocumentSearchFilterPageElements.corIdTextBox, corId);
    }

    public void clickCorIdFilterButton()
    {
        click(DocumentSearchFilterPageElements.corIdSearchButton);
        switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        waitForGridRefresh();
    }

    public void setBillIdFilter(String billId)
    {
        sendTextToTextbox(DocumentSearchFilterPageElements.billIdTextBox, billId);
    }

    public void clickBillIdFilterButton()
    {
        click(DocumentSearchFilterPageElements.billIdSearchButton);
        switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
    }

    public void setRenditionUuidFilter(String uuid)
    {
        sendTextToTextbox(DocumentSearchFilterPageElements.renditionUuidTextBox, uuid);
    }

    public void clickRenditionUuidFilterButton()
    {
        click(DocumentSearchFilterPageElements.renditionUuidSearchButton);
        switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
    }

    public void setDocUuidFilter(String uuid)
    {
        sendTextToTextbox(DocumentSearchFilterPageElements.docUuidTextBox, uuid);
    }

    public void clickDocUuidFilterButton()
    {
        click(DocumentSearchFilterPageElements.docUuidSearchButton);
        switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
    }

    public void setAssignedUserFilter(String assignedUserValue)
    {
        selectDropdownOptionByValue(DocumentSearchFilterPageElements.assignedUserDropdown,assignedUserValue);
    }

    public void clickAssignedUserFilterButton()
    {
        click(DocumentSearchFilterPageElements.assignedUserSearchButton);
        switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
    }

    public void clickCancel()
    {
        click(DocumentSearchFilterPageElements.cancelButton);
        waitForWindowGoneByTitle(DocumentSearchFilterPageElements.DOCUMENT_SEARCH_FILTER_TITLE);
    }
}
