package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.AddAssignedScriptsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.AddHighAssignedScriptsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddHighAssignedScriptsPage extends BasePage {

    private WebDriver driver;

    @Autowired
    public AddHighAssignedScriptsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, AddAssignedScriptsPageElements.class);
    }

    public void rightClickScriptWithGivenPubTagValue(String pubTagValue) {
        click(String.format(AddHighAssignedScriptsPageElements.SCRIPT_WITH_GIVEN_PUBTAG_VALUE, pubTagValue));
        rightClick(String.format(AddHighAssignedScriptsPageElements.SCRIPT_WITH_GIVEN_PUBTAG_VALUE, pubTagValue));
    }

}
