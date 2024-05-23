package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPOM;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.InsertNewNodesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InsertNewNodesPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public InsertNewNodesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InsertNewNodesPageElements.class);
    }

    @Deprecated
    public void insertNewNodeSetDepth(String depth)//Use new method below for specific row
    {
        getElement(HierarchyPOM.DEPTH_TEXTBOX).clear();
        getElement(HierarchyPOM.DEPTH_TEXTBOX).sendKeys(depth);
    }

    public void setWestlawFormatStatus(String wlFormatStatus)//Use new method below for specific row
    {
        click(String.format("//select[@name='pageForm:userEnteredData:0:j_idt47']/option[@value='%s']", wlFormatStatus));
    }

    @Deprecated
    public void setNodeType(String nodeType)//Use new method below for specific row
    {
        click(String.format("//select[@name='pageForm:userEnteredData:0:j_idt50']/option[@value='%s']", nodeType));
    }

    @Deprecated
    public void setKeyword(String keyword)//Use new method below for specific row
    {
        click(String.format("//select[@name='pageForm:userEnteredData:0:j_idt53']/option[@value='%s']", keyword));
    }

    @Deprecated
    public void setValue(String value)//Use new method below for specific row
    {
        getElement(HierarchyPOM.VALUE_TEXTBOX).clear();
        getElement(HierarchyPOM.VALUE_TEXTBOX).sendKeys(value);
    }

    public void setValueByRowNumber(String value, int rowNumber)
    {
        getElement(String.format(HierarchyPOM.VALUE_TEXTBOX_BY_NUMBER,rowNumber-1)).clear();
        getElement(String.format(HierarchyPOM.VALUE_TEXTBOX_BY_NUMBER,rowNumber-1)).sendKeys(value);
    }

    public void setNewNodeDepthByRowNumber(String depth, int rowNumber)
    {
        getElement(String.format(HierarchyPOM.DEPTH_TEXTBOX_BY_NUMBER,rowNumber-1)).clear();
        getElement(String.format(HierarchyPOM.DEPTH_TEXTBOX_BY_NUMBER,rowNumber-1)).sendKeys(depth);
    }

    @Deprecated
    public void setEffectiveStartDate(String date)//Use new method below for specific row
    {
        getElement(HierarchyPOM.EFFECTIVE_START_DATE_TEXTBOX).clear();
        getElement(HierarchyPOM.EFFECTIVE_START_DATE_TEXTBOX).sendKeys(date);
    }

    @Deprecated
    public void setEffectiveEndDate(String date)//Use new method below for specific row
    {
        getElement(HierarchyPOM.EFFECTIVE_END_DATE_TEXTBOX).clear();
        getElement(HierarchyPOM.EFFECTIVE_END_DATE_TEXTBOX).sendKeys(date);
    }

    public void clickCancel()
    {
        click(CommonPageElements.CANCEL_BUTTON);
    }

    public void clickOk()
    {
        click(CommonPageElements.OK_BUTTON);
        waitForWindowGoneByTitle(InsertNewNodesPageElements.INSERT_NEW_NODES_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public void clickQuickLoad()
    {
        click(InsertNewNodesPageElements.quickLoadButton);
    }

    public void setValueToGivenRow(String value,int rowIndex)
    {
        clearAndSendKeysToElement(getElement(String.format(InsertNewNodesPageElements.VALUE_TEXTBOX_OF_GIVEN_ROW,rowIndex)),value);
    }
    public void setEffectiveStartDateOfGivenRow(String date,int rowIndex)
    {
        clearAndSendKeysToElement(getElement(String.format(InsertNewNodesPageElements.EFFECTIVE_START_DATE_TEXTBOX_OF_GIVEN_ROW,rowIndex-1)),date);
    }
    public void setEffectiveEndDateOfGivenRow(String date,int rowIndex)
    {
        clearAndSendKeysToElement(getElement(String.format(InsertNewNodesPageElements.EFFECTIVE_END_DATE_TEXTBOX_OF_GIVEN_ROW,rowIndex)),date);
    }
    public void setNodeTypeofGivenRow(String nodeType,int rowIndex)
    {
        click(String.format(InsertNewNodesPageElements.NODE_TYPE_TEXTBOX_OF_GIVEN_ROW,rowIndex, nodeType));
    }
    public void setKeywordOfGivenRow(String keyword,int rowIndex)
    {
        click(String.format(InsertNewNodesPageElements.KEYWORD_TEXTBOX_OF_GIVEN_ROW,rowIndex, keyword));
    }
    public void setDepthOfGivenRow(String depth, int rowIndex)
    {
        clearAndSendKeysToElement(getElement(String.format(InsertNewNodesPageElements.DEPTH_TEXTBOX_OF_GIVEN_ROW,rowIndex)),depth);
    }

    public void createNewRowBelowCurrentRow()
    {
        getElement(HierarchyPOM.VALUE_TEXTBOX).sendKeys(Keys.ENTER);
    }
}
