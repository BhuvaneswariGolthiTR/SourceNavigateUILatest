package com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MainHeaderPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public MainHeaderPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, MainHeaderElements.class);
    }

    public boolean switchToPublishingToolboxWindow()
    {
        return switchToWindowByUrl(MainHeaderElements.PAGE_TITLE_Toolbox_URL);
    }

    public boolean switchToPublishReadyTextNodesOnlyWindow()
    {
        return switchToWindowByUrl(MainHeaderElements.PUBLISH_READY_TEXT_NODES_ONLY_URL);
    }

    public boolean switchToPublishApproveTextNodesOnlyWindow()
    {
        return switchToWindowByUrl(MainHeaderElements.PUBLISH_APPROVE_TEXT_NODES_ONLY_URL);
    }

    public boolean switchToPublishApproveTextAndNodNodesByVolumeWindow()
    {
        return switchToWindowByUrl(MainHeaderElements.PUBLISH_APPROVE_TEXT_AND_NOD_NODES_BY_VOLUME_URL);
    }

    public boolean switchToPublishApproveNodNodesOnlyWindow()
    {
        return switchToWindowByUrl(MainHeaderElements.PUBLISH_APPROVE_NOD_NODES_ONLY_URL);
    }

    public boolean switchToPublishingStatusReportsWipToPubUploadIssues()
    {
        return switchToWindowByUrl(MainHeaderElements.WIP_TO_PUB_UPLOAD_ISSUES_URL);
    }

    public boolean switchToPublishingStatusReportsErrorStatuses()
    {
        return switchToWindowByUrl(MainHeaderElements.ERROR_STATUSES_URL);
    }

    public boolean switchToPublishingStatusReportsPubNavigateEvaluation()
    {
        return switchToWindowByUrl(MainHeaderElements.PUB_NAVIGATE_EVALUATION_URL);
    }

    public boolean switchToPublishingStatusReportsNodOnlyWipToPubUploadIssues()
    {
        return switchToWindowByUrl(MainHeaderElements.NOD_ONLY_WIP_to_PUB_UPLOAD_ISSUES_URL);
    }

    public boolean switchToPublishingStatusReportsNodOnlyErrorStatuses()
    {
        return switchToWindowByUrl(MainHeaderElements.NOD_ONLY_ERROR_STATUS_URL);
    }

    public boolean switchToPublishingStatusReportsNodOnlyPubNavigateEvaluation()
    {
        return switchToWindowByUrl(MainHeaderElements.NOD_ONLY_PUB_NAVIGATE_EVALUATION_URL);
    }

    public boolean switchToLoadCompleteWLReviewWindow()
    {
        return switchToWindowByUrl(MainHeaderElements.WESTLAW_LOAD_CONPLETE_URL);
    }

    public boolean isGivenTextDisplayedOnPageHeader(String headerText)
    {
        return getElementsText(MainHeaderElements.HEADER_TEXT_OF_TOOLBOX_PAGE_XPATH).contains(headerText);
    }
}
