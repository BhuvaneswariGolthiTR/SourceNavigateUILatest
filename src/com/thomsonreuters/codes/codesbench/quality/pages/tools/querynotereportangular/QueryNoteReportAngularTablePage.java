package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.SubscribedCasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.TablePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.tableAngular.TablePageAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.Table;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.TableCell;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class QueryNoteReportAngularTablePage extends TablePageAngular
{
    private WebDriver driver;

    @Autowired
    public QueryNoteReportAngularTablePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TablePageElementsAngular.class);
    }

    public void openContextMenuForRow(int row)
    {
        click(String.format(QueryNoteReportAngularPageElements.ROW_BY_ROW_ID, row));
        rightClick(String.format(QueryNoteReportAngularPageElements.ROW_BY_ROW_ID, row));
    }

    public List<String> getValuesForPageByRow(int row)
    {
        List<String> rowValues = new ArrayList<String>();
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_TYPE));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_STATUS));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_VOLS));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_CODE));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_HIERARCHY_BREADCRUMB));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_KEYWORD));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_VALUE));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_CREATED_DATE));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_ACTION_DATE));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_START_DATE));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_END_DATE));
        scrollToRight("1500");
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_PRODUCT_TYPE));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_VIEW_TAG));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_PRODUCT_NAME));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_QUERY_TEXT));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_RESOLVED_DATE));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_RESOLVED_BY));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_RESOLVED_COMMENT));
        rowValues.add(getCellTextByRowAndColumn(row, TableColumns.QUERY_NOTE_REPORT_PUBTAG_INFORMATION));
        scrollToRight("-1500");
        return rowValues;
    }

    public void scrollToRight(String offset)
    {
        ((JavascriptExecutor) driver).executeScript(String.format("arguments[0].scrollLeft += %s",offset), getElement(SubscribedCasesPageElementsAngular.HORIZONTAL_SCROLL));
    }

    @Override
    public void selectGivenRowsInTable(TableColumns columnToClick, int... rows)
    {
        Actions highlighter = new Actions(driver);
        highlighter.keyDown(Keys.SHIFT);

        for (int row : rows)
        {
            Table table = parseCertainRows(row);
            TableCell cell = table.getCellByRowAndColumn(row, columnToClick);
            highlighter.click(cell.getWebElement());
        }

        highlighter.keyUp(Keys.SHIFT);
        highlighter.build().perform();
    }
}
