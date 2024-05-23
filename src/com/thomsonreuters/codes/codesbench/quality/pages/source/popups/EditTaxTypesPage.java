package com.thomsonreuters.codes.codesbench.quality.pages.source.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditTaxTypesElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditTaxTypesPage  extends BasePage
{
    private WebDriver driver;

    @Autowired
    public EditTaxTypesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EditTaxTypesElements.class);
    }

    public void selectAndMoveAllSelectedTaxTagAdd()
    {
        if (!getElements(EditTaxTypesElements.SELECTED_TAX_TYPE_ADDS).isEmpty())
        {
            String name = EditTaxTypesElements.selectedTaxTypeAdds.get(0).getText();
            click(EditTaxTypesElements.moveAllToAvailable);
            waitForElementGone(String.format(EditTaxTypesElements.SELECTED_TAX_TYPE_BY_NAME, name));
        }
    }

    public void selectAndMoveAvailableTaxTagAddByName(String name)
    {
        selectDropdownOptionUsingJavascript(EditTaxTypesElements.AVAILABLE_TAX_TYPE_BY_NAME_ID, name);
        sendEnterToElement("//input[contains(@id,'addOneButton')]");
        waitForElementExists(String.format(EditTaxTypesElements.SELECTED_TAX_TYPE_BY_NAME, name));
    }

    public void clickOk()
    {
        sendEnterToElement(CommonPageElements.OK_BUTTON);
    }
}
