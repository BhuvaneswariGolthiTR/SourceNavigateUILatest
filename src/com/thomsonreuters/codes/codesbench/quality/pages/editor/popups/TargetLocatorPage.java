package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TargetLocatorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TargetLocatorPage  extends BasePage
{
        private WebDriver driver;

        @Autowired
        public TargetLocatorPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, TargetLocatorPageElements.class);
        }

        public void selectNodeForTargetLinkMarkup()
        {
                targetLocatorContextMenu().selectTargetLinkMarkup();
                switchToWindow(InsertTargetCiteReferencePageElements.PAGE_TITLE);
                enterTheInnerFrame();
        }

        public void switchToTargetLocatorPage()
        {
                targetLocatorPage().switchToWindow(TargetLocatorPageElements.PAGE_TITLE);
                waitForPageLoaded();
        }
}
