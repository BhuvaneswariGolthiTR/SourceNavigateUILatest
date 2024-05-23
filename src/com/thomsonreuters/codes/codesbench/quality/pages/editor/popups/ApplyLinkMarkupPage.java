package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ApplyLinkMarkupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplyLinkMarkupPage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public ApplyLinkMarkupPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, ApplyLinkMarkupPageElements.class);
        }

        public void clickApplyAll()
        {
                sendEnterToElement(CommonPageElements.APPLY_ALL_BUTTON);
        }

        public void clickCancel()
        {
                sendEnterToElement(CommonPageElements.CANCEL_BUTTON);
        }
}
