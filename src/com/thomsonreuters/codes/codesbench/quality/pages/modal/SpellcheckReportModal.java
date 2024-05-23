package com.thomsonreuters.codes.codesbench.quality.pages.modal;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.modal.SpellcheckReportModalElements.CLOSE_BUTTON;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.modal.SpellcheckReportModalElements.SPELLCHECK_REPORT_LINK;

import com.thomsonreuters.codes.codesbench.quality.pageelements.modal.SpellcheckReportModalElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpellcheckReportModal extends BasePage
{
        private WebDriver driver;

        @Autowired
        public SpellcheckReportModal(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, SpellcheckReportModalElements.class);
        }

        public void clickWorkflowLink()
        {
                sendEnterToElement(SPELLCHECK_REPORT_LINK);
                switchToWindow(WorkflowDetailsPageElements.PAGE_TITLE);
                maximizeCurrentWindow();
        }

        public boolean clickClose()
        {
                click(CLOSE_BUTTON);
                waitForWindowGoneByTitle(SpellcheckReportModalElements.SPELLCHECK_REPORT_MODAL_TITLE);
                return !checkWindowIsPresented(SpellcheckReportModalElements.SPELLCHECK_REPORT_MODAL_TITLE);
        }

        public String getWorkflowId()
        {
                String workflowUrl = getElementsText(SPELLCHECK_REPORT_LINK);
                int index = workflowUrl.lastIndexOf('=');
                return workflowUrl.substring(index + 1);
        }
}
