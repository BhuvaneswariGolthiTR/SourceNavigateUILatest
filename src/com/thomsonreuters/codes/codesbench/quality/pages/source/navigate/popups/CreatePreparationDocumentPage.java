package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.CreatePreparationDocumentPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CreatePreparationDocumentPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CreatePreparationDocumentPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CreatePreparationDocumentPageElements.class);
    }

    public void clickSubmit()
    {
        click(CreatePreparationDocumentPageElements.submitButton);
    }
}
