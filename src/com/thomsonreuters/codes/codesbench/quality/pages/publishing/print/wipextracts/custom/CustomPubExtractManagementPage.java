package com.thomsonreuters.codes.codesbench.quality.pages.publishing.print.wipextracts.custom;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom.CustomPubExtractManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom.CustomPubExtractOptionsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomPubExtractManagementPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CustomPubExtractManagementPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CustomPubExtractManagementPageElements.class);
    }

    public boolean clickNew()
    {
        sendEnterToElement(CustomPubExtractManagementPageElements.newButton);
        return switchToWindow(CustomPubExtractOptionsPageElements.WIP_EXTRACTS_CUSTOM_PUB_EXTRACT_OPTIONS_PAGE_TITLE);
    }
}
