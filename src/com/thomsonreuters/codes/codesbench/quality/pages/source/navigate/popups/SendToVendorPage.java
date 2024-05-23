package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.SectionPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.SendToVendorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SendToVendorPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SendToVendorPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionPropertiesPageElements.class);
    }

    public boolean clickOk()
    {
        sendEnterToElement(SendToVendorPageElements.CONFIRM_BUTTON);
        return AutoITUtils.verifyAlertTextAndAccept(true, "Data sent to vendor for capture.");
    }
}
