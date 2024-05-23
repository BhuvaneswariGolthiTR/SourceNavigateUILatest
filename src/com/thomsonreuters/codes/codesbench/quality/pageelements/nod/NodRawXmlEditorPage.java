package com.thomsonreuters.codes.codesbench.quality.pageelements.nod;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.RawXmlEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractRawXmlEditorPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NodRawXmlEditorPage extends AbstractRawXmlEditorPage
{
    private WebDriver driver;

    @Autowired
    public NodRawXmlEditorPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RawXmlEditorPageElements.class);
    }

}
