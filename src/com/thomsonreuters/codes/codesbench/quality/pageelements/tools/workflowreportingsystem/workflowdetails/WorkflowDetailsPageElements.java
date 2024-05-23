package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WorkflowDetailsPageElements
{
        public static final String PAGE_TITLE = "Workflow Details";
        public static final String WORKFLOW_ID_INPUT = "//input[@id='pageForm:workflowIdInput']";
        public static final String STEP_COMPLETED_XPATH = "//table[@class='availableActionContent']//span[contains(text(), '%s')]";

        public static final int DEFAULT_VERIFY_WORKFLOW_FINISHED_INTERVAL_IN_SECONDS = 60;
        public static final int MAX_VERIFY_WORKFLOW_FINISHED_INTERVAL_IN_SECONDS = 300;

        public static final String CHECK_SKIP_MMR_ACTION_XPATH = "//span[contains(text(),'Check Skip (MMRImage)')]";

        @FindBy(how = How.XPATH, using = "//tbody/tr/td[@class='right-align']/table/tbody/tr/td/fieldset/table[@class='statusFieldset']/tbody/tr/td/span")
        public static WebElement currentStepStatusTextXpath;

        @FindBy(how = How.XPATH, using = "//span[@id='headerForm:breadcrumbArea']/a[text()='Sign In']")
        public static WebElement signInXpath;

        @FindBy(how = How.ID, using = "pageForm:workflowIdInput")
        public static WebElement workflowIdTextBox;

        @FindBy(how = How.XPATH, using = "//span[contains(text(),'Check Skip (MMRImage)')]")
        public static WebElement checkSkipMmrimageActionXpath;

        @FindBy(how = How.ID, using = "pageForm:i6")
        public static WebElement goButton;

        @FindBy(how = How.XPATH, using = "//table[@class='availableActionContent']//span[text()='PublishingLock Completed')]")
        public static WebElement publishingLockCompleted;

        @FindBy(how = How.XPATH, using = "//table[5]//img[@id='workflow_icon']")
        public static WebElement workflowProperiesButtonXpath;

        @FindBy(how = How.ID, using = "detailed_history_icon")
        public static WebElement workflowHistoryStepsDetailXpath;


}
