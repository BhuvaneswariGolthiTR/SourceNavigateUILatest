package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTablePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertTablePage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public InsertTablePage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, InsertTablePageElements.class);
        }

        public void setNumberOfColumns(String columnAmount)
        {
                sendKeysToElement(CommonPageElements.VALUE_INPUT, columnAmount);
        }

        public void clickSave()
        {
                sendEnterToElement(CommonPageElements.SAVE_BUTTON);
                waitForWindowGoneByTitle(InsertTablePageElements.PAGE_TITLE);
                editorPage().switchToEditor();
        }
}
