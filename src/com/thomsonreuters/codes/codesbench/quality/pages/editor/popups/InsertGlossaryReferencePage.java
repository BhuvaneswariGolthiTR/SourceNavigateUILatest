package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertGlossaryReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertGlossaryReferencePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public InsertGlossaryReferencePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InsertGlossaryReferencePageElements.class);
    }

    public void clickCancel()
    {
        click(CommonPageElements.CANCEL_BUTTON);
        acceptAlert();
    }
}
