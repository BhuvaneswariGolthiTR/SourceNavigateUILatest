package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularExportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QueryNoteReportAngularExportPage extends QueryNoteReportAngularEditDeleteResolveCommonPage
{
    private WebDriver driver;

    @Autowired
    public QueryNoteReportAngularExportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, QueryNoteReportAngularExportPageElements.class);
    }

    public void clickWorkflowLink()
    {
        click(QueryNoteReportAngularExportPageElements.EXPORT_REPORT_LINK);
        switchToWindow(WorkflowDetailsPageElements.PAGE_TITLE);
        maximizeCurrentWindow();
    }

}
