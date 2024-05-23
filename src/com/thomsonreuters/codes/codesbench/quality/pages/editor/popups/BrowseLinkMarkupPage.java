package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ApplyLinkMarkupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.BrowseLinkMarkupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BrowseLinkMarkupPage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public BrowseLinkMarkupPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, BrowseLinkMarkupPageElements.class);
        }

        public void clickClose()
        {
                switchToWindow(BrowseLinkMarkupPageElements.PAGE_TITLE);
                click(CommonPageElements.CLOSE_BUTTON);
                switchToWindow(ApplyLinkMarkupPageElements.PAGE_TITLE);
        }
}
