package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertSourceCiteReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.PendingRenditionNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertSourceCiteReferencePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public InsertSourceCiteReferencePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InsertSourceCiteReferencePageElements.class);
    }

    public void clickLocateSource()
    {
        sendEnterToElement(InsertSourceCiteReferencePageElements.SOURCE_LOCATE_BUTTON);
        insertSourceCiteReferencePage().waitForPageLoaded();
        waitForWindowByUrl(PendingRenditionNavigatePageElements.PAGE_TITLE_URL);
        switchToWindowByUrl(PendingRenditionNavigatePageElements.PAGE_TITLE_URL);
    }

    public void clickSave()
    {
        sendEnterToElement(CommonPageElements.SAVE_BUTTON);
    }

    public void selectReferenceType(InsertSourceCiteReferencePageElements.ReferenceType referenceType)
    {
        selectDropdownOption(InsertSourceCiteReferencePageElements.REFERENCE_TYPE, referenceType.getName());
    }

    public void selectContentSet(ContentSets contentSet)
    {
        selectDropdownOption(InsertSourceCiteReferencePageElements.CONTENT_SET, contentSet.getName());
    }
}
