package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.headnotesPageFragments;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.HeadnotesContextMenuElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HeadnotesContextMenuAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public HeadnotesContextMenuAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HeadnotesContextMenuElementsAngular.class);
    }

    public List<String> getContextMenuOptionsTexts()
    {
        List<WebElement> contextMenuOptions = headnotesPageAngular().getElements(HeadnotesContextMenuElementsAngular.CONTEXT_MENU_OPTION);
        return contextMenuOptions.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void selectOptionByText(String contextMenuOption)
    {
        click(String.format(HeadnotesContextMenuElementsAngular.CONTEXT_MENU_OPTION_WITH_TEXT, contextMenuOption));
    }

    public boolean isOptionPresent(String contextMenuOption)
    {
        return doesElementExist(String.format(HeadnotesContextMenuElementsAngular.CONTEXT_MENU_OPTION_WITH_TEXT, contextMenuOption));
    }

    public void selectEditContent()
    {
        selectOptionByText("Edit Content");
    }

    public void selectViewContent()
    {
        selectOptionByText("View Content");
    }

    public void selectViewContentRawXml()
    {
        selectOptionByText("View Content Raw Xml");
    }

    public void selectEditContentRawXml()
    {
        selectOptionByText("View Content Raw Xml ");
    }

    public void selectFindInHierarchy()
    {
        selectOptionByText("Find In Hierarchy");
    }

    public void selectInsertBlueline()
    {
        selectOptionByText("Insert Blueline");
    }

    public void selectDeleteBlueline() { selectOptionByText("Delete Blueline"); }

    public void selectEditBlueline() { selectOptionByText("Edit Blueline"); }

    public void selectUpdateMetadata() { selectOptionByText("Update Metadata"); }

    public boolean isUpdateMetadataPresent()
    {
        return isOptionPresent("Update Metadata");
    }

}
