package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.EditAdminPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditAdminPageAngular extends BasePage
{
    @Autowired
    public EditAdminPageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EditAdminPageElementsAngular.class);
    }

    public boolean isPageOpened()
    {
        try
        {
            waitForVisibleElement(EditAdminPageElementsAngular.CASE_TITLE, 30);
        } catch (Exception ignored)
        {
        }
        return doesElementExist(EditAdminPageElementsAngular.CASE_TITLE);
    }

    public void clickClassify()
    {
        EditAdminPageElementsAngular.classify.click();
    }

    public boolean doesTableWithClassificationInformationExist()
    {
        try
        {
            waitForVisibleElement(EditAdminPageElementsAngular.CLASSIFICATION_INFORMATION_TABLE, 30);
        } catch (Exception ignored)
        {
        }return doesElementExist(EditAdminPageElementsAngular.CLASSIFICATION_INFORMATION_TABLE);
    }

    public String getStatuteShortCiteByRow(int index)
    {
        return getElementsText(String.format(EditAdminPageElementsAngular.STATUTE_SHORT_CITE_BY_ROW, index));
    }

    public String getBlueLineInformationByRow(int index)
    {
        return getElementsText(String.format(EditAdminPageElementsAngular.BLUE_LINE_INFORMATION_BY_ROW, index));
    }

    public void clickRemoveClassificationByRow(int index)
    {
        getElement(String.format(EditAdminPageElementsAngular.REMOVE_CLASSIFICATION_BY_ROW, index)).click();
    }

}
