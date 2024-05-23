package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.citelinereferencespage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.DeleteCitelineReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.citelinereferences.CiteLineReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.CiteLineManagementPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CiteLineReferencesPage extends CiteLineManagementPage
{
    private WebDriver driver;

    @Autowired
    public CiteLineReferencesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CiteLineReferencesPageElements.class);
    }

    public boolean clickAddNewCiteLineReferences()
    {
        return  genericGoTo(CiteLineReferencesPageElements.addNewCitelineReferences, CiteLineReferencesPageElements.CITE_LINE_ADD);
    }

    public boolean clickEdit(String rowID)
    {
        return genericGoTo(String.format(CiteLineManagementsCommonPageElements.EDIT, rowID), CiteLineReferencesPageElements.CITE_LINE_EDIT);
    }

    public void openFilterMenuForCt1()
    {
        clickOnInvisibleElement(CiteLineReferencesPageElements.CT1_MENU_BUTTON);
    }

    public void openFilterMenuForOriginalFirstLineCite()
    {
        clickOnInvisibleElement(CiteLineReferencesPageElements.ORIGINAL_FIRST_LINE_CITE_MENU_BUTTON);
    }

    public String getColumnHeaderNextSibling(WebElement element)
    {
        return getElementsNextSibling(element).getAttribute("col-id");
    }

    public void rightClickElementByCt1(String ct1)
    {
        rightClick(String.format(CiteLineReferencesPageElements.CT1, ct1));
    }

    public boolean verifySelectedCt1(String rowID, String ct1Expected)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.CT1_COLUMN_BY_ROW_INDEX, rowID)).equals(ct1Expected);
    }

    public boolean isCT1InGrid(String ct1)
    {
        return doesElementExist(String.format(CiteLineReferencesPageElements.CT1, ct1));
    }

    public String getRowID(String CT1)
    {
        return getElementsAttribute(String.format(CiteLineReferencesPageElements.CT1_DIV_PARENT_XPATH, CT1) , "row-id");
    }

    public String getCT1Field(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.CT1_FIELD, CT1));
    }

    public String getOriginalFirstLineCite(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.ORIGINAL_FIRST_LINE_CITE, CT1));
    }

    public String getFirstLineCite(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.FIRST_LINE_CITE, CT1));
    }

    public String getSecondLineCitePre(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.SECOND_LINE_CITE_PRE, CT1));
    }

    public String getSecondLineCiteApp(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.SECOND_LINE_CITE_APP, CT1));
    }

    public String getExpandedCitePre(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.EXPANDED_CITE_PRE, CT1));
    }

    public String getExpandedCiteApp(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.EXPANDED_CITE_APP, CT1));
    }

    public String getFormerCite(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.FORMER_CITE, CT1));
    }

    public String getModifiedBy(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.MODIFIED_BY, CT1));
    }

    public String getModifiedDate(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.MODIFIED_DATE, CT1));
    }

    public String getComment(String CT1)
    {
        return getElementsText(String.format(CiteLineReferencesPageElements.COMMENTS, CT1));
    }

    public boolean clickDeleteAndVerifyAlertAppears(String rowID)
    {
        return genericGoTo(String.format(CiteLineReferencesPageElements.DELETE, rowID), CiteLineReferencesPageElements.DELETE_ALERT_TEXT);
    }
}
