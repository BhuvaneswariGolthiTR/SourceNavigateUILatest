package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditAttributesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditAttributesPage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public EditAttributesPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, EditAttributesPageElements.class);
        }

        public void setFirstInput(String inputString)
        {
                sendKeysToElement(EditAttributesPageElements.FIRST_INPUT, inputString);
        }

        public void clickSave()
        {
                click(CommonPageElements.SAVE_BUTTON);
                waitForWindowGoneByTitle(EditAttributesPageElements.PAGE_TITLE);
                editorPage().switchToEditor();
        }
}

