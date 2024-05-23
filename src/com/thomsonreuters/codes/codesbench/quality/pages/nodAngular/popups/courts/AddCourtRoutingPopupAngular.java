package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.courts;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.courts.AddCourtRoutingPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddCourtRoutingPopupAngular extends BasePage {

    private final WebDriver driver;

    @Autowired
    public AddCourtRoutingPopupAngular(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, AddCourtRoutingPopupElementsAngular.class);
    }

    
    public void inputCourtNumber(String number, String expectedCourtOption)
    {
        click(AddCourtRoutingPopupElementsAngular.inputField);
        sendKeysToElement(AddCourtRoutingPopupElementsAngular.inputField, number);
        click(AddCourtRoutingPopupElementsAngular.inputField);
        waitForElement(String.format(AddCourtRoutingPopupElementsAngular.FIRST_OPTION, expectedCourtOption));
        //click(String.format(AddCourtRoutingPopupElementsAngular.FIRST_OPTION, expectedCourtOption));
        sendEnterToElement(AddCourtRoutingPopupElementsAngular.inputField);
    }
    
    public void clickSubmit()
    {
        click(AddCourtRoutingPopupElementsAngular.submit);
    }
}
