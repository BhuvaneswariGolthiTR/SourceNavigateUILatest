package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.courts.AddCourtRoutingPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.SortingOrder;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class CourtsTests extends TestService
{
    /**
     * HALCYONST-10663 - Courts layout
     * 1. Log onto the Iowa (Development) content set
     * 2. Go to NOD -> Courts
     * 3. VERIFY: Courts page has appeared
     * 4. VERIFY: the following columns are present and in this order:  Court, Display Name, Long Name and Court Line Style
     * 5. Select the Court heading and it should filter from ascending to descending format
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void courtsLayoutTest()
    {
        TableColumns column = TableColumns.COURT;

        // 1. Log onto the Iowa (Development) content set
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> Courts
        homePageAngular().clickCourts();
        //3. Courts page has appeared
        boolean courtsWindowAppeared = courtsPageAngular().isPageOpened();
        Assertions.assertTrue(courtsWindowAppeared, "NOD Courts page didn't open");
        //4. The following columns are present and in this order:  Court, Display Name, Long Name and Court Line Style
        HashMap<String, Integer> expectedColumnOrder = courtsPageAngular().getExpectedUSColumnOrder();
        HashMap<String, Integer> actualColumnOrder = courtsTablePageAngular().getActualColumnOrder();
        boolean orderOfColumnsIsEqual = expectedColumnOrder.equals(actualColumnOrder);
        //5. Select the Court heading
        boolean columnIsUnsortedByDefault = courtsTablePageAngular().isColumnUnsorted(column);
        Assertions.assertTrue(columnIsUnsortedByDefault, "Column is not unsorted by default.");
        courtsTablePageAngular().clickToSort(column);
        //data was filtered in ascending format
        SortingOrder newSortingOrder = courtsTablePageAngular().defineSortingOrder(column);
        boolean columnIsShownAsSorted = newSortingOrder.equals(SortingOrder.ASCENDING);
        List<String> columnValues = courtsTablePageAngular().getAllValuesInGivenColumn(column);
        SortingOrder actualColumnValuesSorting = courtsTablePageAngular().getColumnValuesSorting(column, columnValues);
        boolean columnIsSorted = actualColumnValuesSorting.equals(SortingOrder.ASCENDING);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(columnIsShownAsSorted, String.format("Column is not shown as sorted. " +
                                "Sorting order is: %s", newSortingOrder)),
                        () -> Assertions.assertTrue(columnIsSorted, String.format("Column values are not sorted as expected. \n" +
                                        "Expected order: %s; \nActual order: %s; \nActual column values: %s",
                                SortingOrder.ASCENDING.getSortingOrderAsString(), actualColumnValuesSorting.getSortingOrderAsString(),
                                columnValues)),
                        () -> Assertions.assertTrue(orderOfColumnsIsEqual, "Order of columns is not expected")
                );

    }

    /**
     * HALCYONST-10839 - Courts layout
     * 1. Log onto the Iowa (Development) content set
     * 2. Go to NOD -> Courts
     * 3. VERIFY: Courts page has appeared
     * 4. Locate 3630, by filtering the "Court" column
     * 5. VERIFY: Court 3630 ("Supreme Court of the United States") exists
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void supremeCourtUSContentSetsTest()
    {
        String court = "3630";
        String longName = "Supreme Court of the United States";

        // 1. Log onto the Iowa (Development) content set
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> Courts
        homePageAngular().clickCourts();
        //3. Courts page has appeared
        boolean courtsWindowAppeared = courtsPageAngular().isPageOpened();
        Assertions.assertTrue(courtsWindowAppeared, "NOD Courts page didn't open");
        //4. Locate 3630, by filtering the "Court" column
        courtsTablePageAngular().openFilterOptionsForColumn(TableColumns.COURT);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, court);
        //5. VERIFY: Court 3630 ("Supreme Court of the United States") exists
        courtsTablePageAngular().waitForTableToReload();
        boolean isCourtExist = !courtsTablePageAngular().isTableEmpty();
        String columnValues = courtsTablePageAngular().getAllValuesInGivenColumn(TableColumns.LONG_NAME).get(0);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isCourtExist, String.format("Court %s doesn't exist",court)),
                        () -> Assertions.assertEquals(longName, columnValues, "Court 'Supreme Court of the United States' doesn't exist")
                );

    }

    /**
     * HALCYONST-10839 - Courts layout
     * 1. Log onto Canada Ontario (Development) content set
     * 2. Click the NOD button
     * 3. Select the Courts option on the page
     * 4. VERIFY: Courts page has appeared
     * 5. Locate 3630, by filtering the "Court" column
     * 6. VERIFY: Court 3630 ("Supreme Court of the United States") doesn't exist
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void supremeCourtNotUSContentSetsTest()
    {
        String court = "3630"; //"Supreme Court of the United States"

        // 1. Log onto the Iowa (Development) content set
        homePageAngular().openNodHomePage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> Courts
        homePageAngular().clickCourts();
        //3. Courts page has appeared
        boolean courtsWindowAppeared = courtsPageAngular().isPageOpened();
        Assertions.assertTrue(courtsWindowAppeared, "NOD Courts page didn't open");
        //4. Locate 3630, by filtering the "Court" column
        courtsTablePageAngular().openFilterOptionsForColumn(TableColumns.COURT);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, court);
        //5. VERIFY: Court 3630 ("Supreme Court of the United States") exists
        courtsTablePageAngular().waitForTableToReload();
        boolean isCourtExist  = !courtsTablePageAngular().isTableEmpty();

        Assertions.assertAll
                (
                        () -> Assertions.assertFalse(isCourtExist, String.format("Court %s exists for not US Content sets",court))
                );

    }

    /**
     * HALCYONST-13711 / HALCYONST-13344 / HALCYONST-13425 - Add new court
     * 1. Log onto the Canada Ontario (Development) content set
     * 2. Go to NOD -> Courts
     * 3. VERIFY: Courts page has appeared
     * 4. Click button 'Add court'
     * 5. VERIFY: Add Court Ruting popup is appeared
     * 6. Put 5668 and click on the court
     * 7. Click submit
     * 8. VERIFY: court 5668 is appeared on the Court Table
     */
    @Test
    @EDGE
    @CARSWELL
    @LOG
    public void addNewCourtTest() {

        String courtShortNumber = "5668";
        String expectedCourtOption = "Florida Circuit Court, Fifteenth Judicial Circuit, Appellate Division";

        // 1. Log onto the Canada Ontario (Development) content set
        homePageAngular().openNodHomePage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> Courts
        homePageAngular().clickCourts();
        //3. Courts page has appeared
        Assertions.assertTrue(courtsPageAngular().isPageOpened(), "NOD Courts page didn't open");
        //4. Click button 'Add court'
        courtsPageAngular().clickAddCourtRouting();
        //5. VERIFY: Add Court Routing popup is appeared
        Assertions.assertTrue(addCourtRoutingPopupAngular().doesElementExist(AddCourtRoutingPopupElementsAngular.PAGE_TITLE), "Add Court Routing page didn't open");
        //6. Put 5668 and click on the court
        addCourtRoutingPopupAngular().inputCourtNumber(courtShortNumber, expectedCourtOption);
        //7. Click submit
        addCourtRoutingPopupAngular().clickSubmit();
        //8. VERIFY: court 5668 is appeared on the Court Table
        courtsTablePageAngular().openFilterOptionsForColumn(TableColumns.COURT);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, courtShortNumber);
        courtsTablePageAngular().waitForTableToReload();
        boolean isCourtExistAfterAdding = courtsTablePageAngular().isTableEmpty();
        courtsPageAngular().deleteCourtByNumber(courtShortNumber);
        courtsPageAngular().acceptAlert();

        Assertions.assertAll
                (
                        () -> Assertions.assertFalse(isCourtExistAfterAdding, String.format("Court %s does NOT exist", courtShortNumber))
                );

    }

}
