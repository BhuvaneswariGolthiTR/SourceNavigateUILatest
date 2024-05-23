package com.thomsonreuters.codes.codesbench.quality.pages.publishing.print.wipextracts.custom;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom.CustomPubExtractOptionsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom.CustomPubExtractTreeViewPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomPubExtractOptionsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CustomPubExtractOptionsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CustomPubExtractOptionsPageElements.class);
    }

    public boolean clickNext()
    {
        sendEnterToElement(CustomPubExtractOptionsPageElements.nextButton);
        return switchToWindow(CustomPubExtractTreeViewPageElements.WIP_EXTRACTS_CUSTOM_PUB_EXTRACT_TREE_VIEW_PAGE_TITLE);
    }

    public void setExtractFileName(String fileName)
    {
        clearAndSendKeysToElement(CustomPubExtractOptionsPageElements.extractFileNameInputField, fileName);
    }

}
