package com.thomsonreuters.codes.codesbench.quality.pages.publishing.print.wipextracts.custom;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom.CustomPubExtractManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom.CustomPubExtractSavePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomPubExtractSavePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CustomPubExtractSavePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CustomPubExtractSavePageElements.class);
    }

}
