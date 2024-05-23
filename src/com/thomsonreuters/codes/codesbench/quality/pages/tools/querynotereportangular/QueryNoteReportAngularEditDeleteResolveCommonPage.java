package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularEditDeleteResolveCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularEditPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularResolvePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.modal.SpellcheckReportModalElements.SPELLCHECK_REPORT_LINK;

@Component
public class QueryNoteReportAngularEditDeleteResolveCommonPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public QueryNoteReportAngularEditDeleteResolveCommonPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, QueryNoteReportAngularEditDeleteResolveCommonPageElements.class);
    }

    /*
		Getter methods
	 */
    public String getUuid()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.QUERY_NOTE_UUID);
    }

    public String getType()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.TYPE);
    }

    public String getVols()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.VOLS);
    }

    public String getCode()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.CODE);
    }

    public String getKeyword()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.KEYWORD);
    }

    public String getValue()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.VALUE);
    }

    public String getStartDate()
    {
        String startDate = getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.START_DATE);
        if (startDate.equals("No Date"))
        {
            return "";
        }
        return startDate;
    }

    public String getEndDate()
    {
        String endDate = getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.END_DATE);
        if (endDate.equals("No Date"))
        {
            return "";
        }
        return endDate;
    }

    public String getActionDate()
    {
        String actionDate = getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.ACTION_DATE);
        if (actionDate.equals("No Date"))
        {
            return "";
        }
        return actionDate;
    }

    public String getQueryNoteStatus()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.QUERY_NOTE_STATUS);
    }

    public String getCreatedBy()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.CREATED_BY);
    }

    public String getCreatedDate()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.CREATED_DATE);
    }

    public String getResolvedBy()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.RESOLVED_BY);
    }

    public String getResolvedDate()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.RESOLVED_DATE);
    }

    public String getResolvedComment()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.RESOLVED_COMMENT);
    }

    public String getDeletedDate()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.DELETED_DATE);
    }

    public String getProductType()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.PRODUCT_TYPE);
    }

    public String getViewTag()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.VIEW_TAG);
    }

    public String getProductName()
    {
        return getElementsText(QueryNoteReportAngularEditDeleteResolveCommonPageElements.PRODUCT_NAME);
    }

    public String getQueryNoteText()
    {
        String result = "";
        click(QueryNoteReportAngularEditDeleteResolveCommonPageElements.queryNoteText);
        try
        {
            Actions action = new Actions(driver);
            action.keyDown(Keys.CONTROL).sendKeys("a").pause(Duration.ofSeconds(2)).build().perform();
            action.keyDown(Keys.CONTROL).sendKeys("c").pause(Duration.ofSeconds(2)).build().perform();
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            result = c.getData(DataFlavor.stringFlavor).toString();
            action.sendKeys(Keys.END).pause(Duration.ofSeconds(2)).build().perform();
        }
        catch (IOException e)
        {
            Assertions.fail("IOException " + e);
        }
        catch (UnsupportedFlavorException e)
        {
            Assertions.fail("UnsupportedFlavorException " + e);
        }
        return result;
    }

    /**
     * Clicks the submit button.
     */
    public void clickSubmitButton()
    {
        click(QueryNoteReportAngularEditDeleteResolveCommonPageElements.submitButton);
        switchToWindow(QueryNoteReportAngularPageElements.QUERY_NOTE_TITLE);
        waitForGridRefresh();
    }
}
