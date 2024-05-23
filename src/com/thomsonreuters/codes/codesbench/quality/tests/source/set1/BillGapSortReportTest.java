package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BillGapSortReportTest extends TestService
{
    /**
     * STORY: na <br>
     * SUMMARY: Checks the bill gap count report sort function <br>
     * USER: Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void billGapSortReportTest()
    {
        String contentSet = "Alabama (Development)";
        String docType = "H";
        String filterType = "docType";

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared);

        clamshellPage().openSideBarReport();
        renditionTabReportClamshellPage().clickBillGapCountReport(true, false);
        billGapCountContentSetPage().setDropdownNotSelected(contentSet);
        billGapCountContentSetPage().pressMoveOneFromNonSelectedToSelected();
        billGapCountGridPage().refreshTheGrid();
        boolean documentsAppeared =  billGapCountGridPage().recordListIsEmpty();
        billGapCountGridPage().setFilterDocType(docType);
        billGapCountGridPage().refreshTheGrid();


         // Takes the Strings of each column under the DocType Filter and checks to see if they are of type H. It then asserts that that is true for
        //  each value.
        List<String> documentList = billGapCountGridPage().getFilterList(filterType);
        List<Boolean> verifiedDocumentHasFilterList = billGapCountGridPage().getVerifiedListBasedOnFilteredType(documentList,docType);
        for(int i = 0; i < verifiedDocumentHasFilterList.size(); i++)
        {
            Assertions.assertTrue(verifiedDocumentHasFilterList.get(i),String.format("File Number: %d was not of type %s",i,docType));
        }

        //  Makes sure the size of the list before the sort is the same size as the list after the sort.
        billGapCountGridPage().sortByDocType();
        boolean sizeDidNotChangeWhenSorting = billGapCountGridPage().getFilterList(filterType).size() == documentList.size();

        // makes sure the list is unfiltered by removing every element from the list of type H. it then check to make sure the list is not empty.
        billGapCountGridPage().clearAllFilters();
        List<String> listWithoutFilters = billGapCountGridPage().getFilterList(filterType);
        boolean listIsNotFiltered = billGapCountGridPage().listIsNotFilteredByX(listWithoutFilters, docType);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(documentsAppeared, "Documents appeared in the grid."),
            () -> Assertions.assertTrue(sizeDidNotChangeWhenSorting, "The size did not chang when the list was sorted."),
            () -> Assertions.assertTrue(listIsNotFiltered, "The list is not filtered.")
        );
    }
}
