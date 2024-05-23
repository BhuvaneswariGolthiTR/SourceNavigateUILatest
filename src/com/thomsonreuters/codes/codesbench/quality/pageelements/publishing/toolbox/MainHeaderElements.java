package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MainHeaderElements
{
        public static final String PAGE_TITLE = "Toolbox";
        public static final String PAGE_TITLE_Toolbox_URL = "publishing-toolbox";
        public static final String PUBLISH_READY_TEXT_NODES_ONLY_URL = "publish-not-ready";
        public static final String PUBLISH_APPROVE_TEXT_NODES_ONLY_URL = "publish-approve-all";
        public static final String PUBLISH_APPROVE_TEXT_AND_NOD_NODES_BY_VOLUME_URL = "publishing-approve-by-volume";
        public static final String PUBLISH_APPROVE_NOD_NODES_ONLY_URL = "publish-approve-nod";
        public static final String PUB_NAVIGATE_EVALUATION_URL = "publishing-in-progress";
        public static final String WIP_TO_PUB_UPLOAD_ISSUES_URL = "approval-troubleshooting";
        public static final String ERROR_STATUSES_URL = "publishing-troubleshooting";
        public static final String NOD_ONLY_PUB_NAVIGATE_EVALUATION_URL = "publishing-in-progress-nods";
        public static final String NOD_ONLY_WIP_to_PUB_UPLOAD_ISSUES_URL = "approval-troubleshooting-nod";
        public static final String NOD_ONLY_ERROR_STATUS_URL = "publishing-troubleshooting-nod";
        public static final String WESTLAW_LOAD_CONPLETE_URL = "westlaw-load-complete";
        public static final String PUBLISHING_TOOLBOX_CONTENT_SET_XPATH = "//toolbox-header/nav[contains(@class, 'toolbox-titlebar')]/form[contains(text(), '%s')]";
        public static final String PUBLISHING_TOOLBOX_HEADER_XPATH = "//div[@class='navbar section-header']/h2[contains(text(), '%s')]";
        public static final String HEADER_TEXT_OF_TOOLBOX_PAGE_XPATH = "//div[@class='navbar section-header']/h2";

        // Main Headers
        public static final String PUBLISH_READY_TEXT_NODES_ONLY_PAGE_HEADER = "Publish Ready-Text nodes only";
        public static final String PUBLISH_APPROVE_TEXT_NODES_ONLY_PAGE_HEADER = "Publish Approve-Text nodes only";
        public static final String PUBLISH_APPROVE_NOD_NODES_ONLY_PAGE_HEADER = "Publish Approve-NOD nodes only";
        public static final String PUBLISHING_STATUS_REPORTS_WESTLAW_LOAD_COMPLETE_PAGE_HEADER = "Westlaw Load Complete";
        public static final String PUBLISHING_STATUS_REPORTS_WIP_TO_PUB_UPLOAD_ISSUES_PAGE_HEADER = "WIP to PUB Upload Issues";
        public static final String PUBLISHING_STATUS_REPORTS_ERROR_STATUS_PAGE_HEADER = "ERROR Statuses";
        public static final String PUBLISHING_STATUS_REPORTS_PUB_NAVIGATE_EVALUATION_PAGE_HEADER = "Pub Navigate Evaluation";
        public static final String PUBLISHING_STATUS_REPORTS_NOD_PUB_NAVIGATE_EVALUATION_PAGE_HEADER = "NOD-Only Pub Navigate Evaluation";
        public static final String PUBLISHING_STATUS_REPORTS_NOD_WIP_TO_PUB_UPLOAD_ISSUES_PAGE_HEADER = "NOD-Only WIP to PUB Upload Issues";
        public static final String PUBLISHING_STATUS_REPORTS_NOD_ONLY_ERROR_STATUSES_HEADER = "NOD-Only Error Statuses";

        @FindBy(how = How.XPATH, using ="//button[@class='mat-raised-button mat-primary']")
        public static WebElement nextButton;
}
