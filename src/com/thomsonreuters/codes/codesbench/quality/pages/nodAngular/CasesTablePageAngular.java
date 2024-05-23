package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.CasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.TablePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.tableAngular.TablePageAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CasesTablePageAngular extends TablePageAngular
{

    @Autowired
    public CasesTablePageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TablePageElementsAngular.class);
    }

    public void openHeadnotesPageByCaseSerial(String caseSerial)
    {
        filterByCaseSerialWaitForTableReload(caseSerial);
        Table table = casesTablePage().parseCertainRows(0);
        TableCell cell = table.getCellByRowAndColumn(0, TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        cell.click();
    }

    public void openNodCasesPage(ContentSets contentSet)
    {
        String urlWithContentSet = String.format(urls().getNodPageUrl(), environmentTag,
                CasesPageElementsAngular.URL_MODIFIER, contentSet.getCode());
        openPageWithUrl(urlWithContentSet, CasesPageElementsAngular.PAGE_TITLE);
        waitForPageLoaded();
    }

    public void filterByCaseSerialWaitForTableReload(String caseSerial)
    {
        filterByTableColumnWaitForTableReload(TableColumns.CASE_SERIAL_SUBSCRIBED_CASES, caseSerial);
    }
}