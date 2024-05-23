package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.UpdateCodeNameIDPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class UpdateCodeNameIDPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public UpdateCodeNameIDPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UpdateCodeNameIDPageElements.class);
    }

    public void setCreateNewCodeNameIDTextField(String newCodeNameID)
    {
        click(UpdateCodeNameIDPageElements.createNewCodeNameIDEntryField);
        sendKeysToElement(UpdateCodeNameIDPageElements.createNewCodeNameIDEntryField, newCodeNameID);
    }

    public void checkAssignCodeNameIDToMetadata()
    {
        checkCheckbox(UpdateCodeNameIDPageElements.assignCodeNameIDToMetadataCheckBox);
    }

    public void clickCreateNewCodeNameIDRadioButton()
    {
        checkCheckbox(UpdateCodeNameIDPageElements.createNewCodeNameIDRadioButton);
    }

    public void clickChangeExistingCodeNameIDRadioButton()
    {
        checkCheckbox(UpdateCodeNameIDPageElements.changeExistingCodeNameIDRadioButton);
    }

    public void clickSubmit()
    {
        click(UpdateCodeNameIDPageElements.submitButton);
        waitForWindowGoneByTitle(UpdateCodeNameIDPageElements.UPDATE_CODE_NAME_ID_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public void clickCancel()
    {
        click(UpdateCodeNameIDPageElements.cancelButton);
        waitForWindowGoneByTitle(UpdateCodeNameIDPageElements.UPDATE_CODE_NAME_ID_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }


    public String getCodeNameID()
    {
        String CodeNameID = getElementsText(UpdateCodeNameIDPageElements.CURRENT_CODE_NAME_ID);
        return CodeNameID;
    }

    public void changeExistingCodeNameID(String CodeName)
    {
        waitForElementToBeEnabled(UpdateCodeNameIDPageElements.EXISTING_CODE_NAME_ID_DROPDOWN_MENU);
        selectDropdownOption(UpdateCodeNameIDPageElements.EXISTING_CODE_NAME_ID_DROPDOWN_MENU, CodeName);
    }
}