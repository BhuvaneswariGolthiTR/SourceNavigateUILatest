package com.thomsonreuters.codes.codesbench.quality.pages.source.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.popups.TaxTypeAddPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TaxTypeAddPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public TaxTypeAddPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TaxTypeAddPageElements.class);
    }

    public boolean isAvailableTaxTypeAddsEmpty()
    {
       return TaxTypeAddPageElements.availableTaxTypeAdds.isEmpty();
    }

    public void selectAndMoveAvailableTaxTagAddByName(String name)
    {
       click(String.format(TaxTypeAddPageElements.AVAILABLE_TAX_TYPE_BY_NAME, name));
       click(TaxTypeAddPageElements.moveToSelected);
       waitForElementExists(String.format(TaxTypeAddPageElements.SELECTED_TAX_TYPE_BY_NAME, name));
    }

    public void selectAndMoveSelectedTaxTagAddByName(String name)
    {
       click(String.format(TaxTypeAddPageElements.SELECTED_TAX_TYPE_BY_NAME, name));
       click(TaxTypeAddPageElements.moveToAvailable);
    }

    public void selectAndMoveAllSelectedTaxTagAdd()
    {
        if (!getElements(TaxTypeAddPageElements.SELECTED_TAX_TYPE_ADDS).isEmpty())
        {
            String name = TaxTypeAddPageElements.selectedTaxTypeAdds.get(0).getText();
            click(TaxTypeAddPageElements.moveAllToAvailable);
            waitForElementGone(String.format(TaxTypeAddPageElements.SELECTED_TAX_TYPE_BY_NAME, name));
        }
    }

    public void selectAndMoveAllSelectedTaxTagAddWithoutWaiting()
    {
            click(TaxTypeAddPageElements.moveAllToAvailable);
    }

    public boolean clickSave()
    {
        click(CommonPageElements.SAVE_BUTTON);
        waitForPageLoaded();
        return doesWindowExistByTitle(TaxTypeAddPageElements.PAGE_TITLE);
    }


}
