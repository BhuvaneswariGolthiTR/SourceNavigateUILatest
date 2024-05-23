package com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.citelinereferencespage;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.citelinereferences.AddOrEditCiteLineReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddOrEditNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.CiteLineManagementPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddOrEditCiteLineReferencesPage extends CiteLineManagementPage
{
    WebDriver driver;

    @Autowired
    public AddOrEditCiteLineReferencesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }
    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AddOrEditCiteLineReferencesPageElements.class);
        PageFactory.initElements(driver, CiteLineManagementsCommonPageElements.class);
    }

    public void setCt1Field(String input, boolean errorExpected)
    {
        genericInput(input, AddOrEditCiteLineReferencesPageElements.ct1InputField);
        if(errorExpected)
        {
            waitForElement(AddOrEditCiteLineReferencesPageElements.CT1_LABEL_ERROR);
        }
    }

    public void clearCt1Field(String input)
    {
        genericClear(input, AddOrEditCiteLineReferencesPageElements.ct1InputField);
    }

    public void setOriginalFirstLineCite(String input)
    {
        genericInput(input, AddOrEditCiteLineReferencesPageElements.orignalFirstLineCiteInputField);
    }

    public void clearOriginalFirstLineCite(String input)
    {
        genericClear(input, AddOrEditCiteLineReferencesPageElements.orignalFirstLineCiteInputField);
    }

    public void setFirstLineCite(String input)
    {
        genericInput(input, AddOrEditCiteLineReferencesPageElements.firstLineCiteInputField);
    }

    public void clearFirstLineCite(String input)
    {
        genericClear(input, AddOrEditCiteLineReferencesPageElements.firstLineCiteInputField);
    }

    public void setSecondLineCitePre(String input)
    {
        genericInput(input, AddOrEditCiteLineReferencesPageElements.secondLineCitePreInputField);
    }

    public void clearSecondLineCitePre(String input)
    {
        genericClear(input, AddOrEditCiteLineReferencesPageElements.secondLineCitePreInputField);
    }

    public void setSecondLineCiteApp(String input)
    {
        genericInput(input, AddOrEditCiteLineReferencesPageElements.secondLineCiteAppInputField);
    }

    public void setExpandedCitePre(String input)
    {
        genericInput(input, AddOrEditCiteLineReferencesPageElements.expandedCitePreInputField);
    }

    public void setExpandedCiteApp(String input)
    {
        genericInput(input, AddOrEditCiteLineReferencesPageElements.expandedCiteAppInputField);
    }

    public void setFormerCite(String input)
    {
        genericInput(input, AddOrEditCiteLineReferencesPageElements.formerCiteInputField);
    }

    public void setComments(String input)
    {
        genericInput(input, AddOrEditCiteLineReferencesPageElements.commentsInputField);
    }

    public void setNewCiteFields(String ct1, String ogFirstLine, String firstLine, String secondLinePre, String secondLineApp, String expandedPre, String expandedApp, String former, String comments)
    {
        setCt1Field(ct1, false);
        setOriginalFirstLineCite(ogFirstLine);
        setFirstLineCite(firstLine);
        setSecondLineCitePre(secondLinePre);
        setSecondLineCiteApp(secondLineApp);
        setExpandedCitePre(expandedPre);
        setExpandedCiteApp(expandedApp);
        setFormerCite(former);
        setComments(comments);
    }

    public boolean commentsHasTooManyCharactersError()
    {
        return isElementDisplayed(AddOrEditCiteLineReferencesPageElements.COMMENTS_LENGTH_ERROR);
    }
}
