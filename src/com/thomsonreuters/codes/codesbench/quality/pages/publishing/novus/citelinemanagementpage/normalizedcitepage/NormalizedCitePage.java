package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.normalizedcitepage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.NormalizedCiteReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.CiteLineManagementPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NormalizedCitePage extends CiteLineManagementPage
{
    private WebDriver driver;

    @Autowired
    public NormalizedCitePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, NormalizedCiteReferencesPageElements.class);
    }

    public boolean clickAddNewNormalizedCite()
    {
        return genericGoTo(NormalizedCiteReferencesPageElements.addNewNormalizedCite, AddNormalizedCitePageElements.NORMALIZED_CITE_ADD);
    }

    public boolean clickEdit(String rowID)
    {
        return genericGoTo(String.format(CiteLineManagementsCommonPageElements.EDIT, rowID), AddNormalizedCitePageElements.NORMALIZED_CITE_EDIT);
    }

    public void openFilterMenuForCitationPrefix()
    {
        clickOnInvisibleElement(NormalizedCiteReferencesPageElements.CITATION_PREFIX_MENU_XPATH);
    }

    public void openFilterMenuForCondensedPrefix()
    {
        clickOnInvisibleElement(NormalizedCiteReferencesPageElements.CONDENSED_PREFIX_MENU_XPATH);
    }

    public void clickFirstCitationPrefix()
    {
        click(NormalizedCiteReferencesPageElements.FIRST_CITE_IN_GRID);
    }

    public boolean verifySelectedNormalizedCiteCitationPrefixByRowIndex(String citationPrefixExpected, String rowIndex)
    {
        return getElementsText(String.format(NormalizedCiteReferencesPageElements.CITATION_PREFIX_BY_ROW_INDEX, rowIndex)).equals(citationPrefixExpected);
    }

    public boolean verifySelectedNormalizedCiteCitationPrefix(String citationPrefixExpected, String rowID)
    {
        return getElementsText(String.format(NormalizedCiteReferencesPageElements.CITATION_PREFIX_BY_ROW_ID, rowID)).equals(citationPrefixExpected);
    }

    public boolean verifySelectedNormalizedCiteCondensedPrefix(String condensedPrefixExpected, String rowID)
    {
        return getElementsText(String.format(NormalizedCiteReferencesPageElements.CONDENSED_PREFIX_BY_ROW_ID, rowID)).equals(condensedPrefixExpected);
    }

    public boolean verifySelectedNormalizedCiteModifiedBy(String modifiedByExpected, String rowID)
    {
        return getElementsText(String.format(NormalizedCiteReferencesPageElements.MODIFIED_BY_BY_ROW_ID, rowID)).equals(modifiedByExpected);
    }

    public boolean verifySelectedNormalizedCiteModifiedDate(String modifiedDateExpected, String rowID)
    {
        return getElementsText(String.format(NormalizedCiteReferencesPageElements.MODIFIED_DATE_BY_ROW_ID, rowID)).equals(modifiedDateExpected);
    }

    public boolean verifySelectedNormalizedCiteComments(String commentsExpected, String rowID)
    {
        return getElementsText(String.format(NormalizedCiteReferencesPageElements.COMMENTS_BY_ROW_ID, rowID)).equals(commentsExpected);
    }

    public boolean isCitationPrefixInGrid(String citationPrefixText)
    {
        return doesElementExist(String.format(NormalizedCiteReferencesPageElements.NORMALIZED_CITE_USING_CITATION_PREFIX, citationPrefixText));
    }

    public boolean clickDeleteAndVerifyAlertAppears(String rowId)
    {
        return genericGoTo(String.format(NormalizedCiteReferencesPageElements.DELETE, rowId), NormalizedCiteReferencesPageElements.DELETE_ALERT_TEXT);
    }

    public String getRowID(String citationPrefix)
    {
        return getElementsAttribute(String.format(NormalizedCiteReferencesPageElements.CITATION_PREFIX_PARENT_XPATH, citationPrefix) , "row-id");
    }

    public void rightClickElementByRowId(String rowId)
    {
        rightClick(String.format(NormalizedCiteReferencesPageElements.CITATION_PREFIX_BY_ROW_ID, rowId));
    }

    public String getColumnHeaderNextSibling(WebElement element)
    {
        return getElementsNextSibling(element).getAttribute("col-id");
    }
}