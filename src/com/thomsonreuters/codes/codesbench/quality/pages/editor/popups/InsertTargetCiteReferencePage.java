package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TargetLocatorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertTargetCiteReferencePage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public InsertTargetCiteReferencePage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, InsertTargetCiteReferencePageElements.class);
        }

        public void clickLocateTarget()
        {
                click(InsertTargetCiteReferencePageElements.LOCATE_TARGET_BUTTON);
                switchToWindow(TargetLocatorPageElements.PAGE_TITLE);
                waitForPageLoaded();
        }

        public void sendTextToSubsection(String text)
        {
          sendKeysToElement(InsertTargetCiteReferencePageElements.SUBSECTION, text);
        }

        public void clickSave()
        {
                click(CommonPageElements.SAVE_BUTTON);
        }

        public void enterUrl(String urlToEnter)
        {
                sendKeysToElement(InsertTargetCiteReferencePageElements.URL_INPUT, urlToEnter);
        }

        public void selectReferenceType(InsertTargetCiteReferencePageElements.ReferenceType referenceType)
        {
                selectDropdownOptionUsingJavascript(InsertTargetCiteReferencePageElements.REFERENCE_TYPE_ID, referenceType.getName());
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
                javascriptExecutor.executeScript("arguments[0].onchange()", getElement(InsertTargetCiteReferencePageElements.REFERENCE_TYPE));
                waitForPageLoaded();
        }

        public void selectContentSet(ContentSets contentSet)
        {
                selectDropdownOptionUsingJavascript(InsertTargetCiteReferencePageElements.CONTENT_SET_ID,contentSet.getCode());
        }

        public void enterDocFamilyId(String docFamilyId)
        {
                clearAndSendKeysToElement(getElement(InsertTargetCiteReferencePageElements.DOC_FAMILY_ID_INPUT), docFamilyId);
        }

        public void enterEmail(String email)
        {
                clearAndSendKeysToElement(getElement(InsertTargetCiteReferencePageElements.EMAIL_INPUT), email);
        }

        public void switchToInsertTargetCiteReferencePage()
        {
                insertTargetCiteReferencePage().switchToWindow(InsertTargetCiteReferencePageElements.PAGE_TITLE);
                insertTargetCiteReferencePage().enterTheInnerFrame();
                waitForPageLoaded();
        }

        public void clickAdditionalContentSetsCheckbox()
        {
                click(InsertTargetCiteReferencePageElements.ADDITIONAL_CONTENT_SETS_CHECKBOX);
                waitForPageLoaded();
        }
}
