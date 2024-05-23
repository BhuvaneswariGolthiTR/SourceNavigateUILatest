package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.NodMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.HomePageAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.TableHeaders;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

public class CasesTests extends TestService
{

    /**
     * HALCYONST-9503 + HALCYONST-9087 - NOD Codes menu option & cases table layout
     * 1. Log onto Iowa (Development) content set
     * 2. VERIFY: Confirm that NOD menu has the following entries:
     * Subscribe Cases
     * Subscribe Cases - Angular
     * Cases
     * Cases - Angular
     * 3. Go to NOD -> Cases - angular
     * 4. The UI will come up, VERIFY: that "NO Team Cases" is selected
     * 5. It shouldn't matter what is selected in the drop down, the columns will all be the same.
     * 6. They should be in the following order:  Loaded Date, Case Serial #, Westlaw #, Reporter Cite, Court, R/U, Reloaded, Headnotes, Title, Subscriptions
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void casesUSTableLayout()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.IOWA_DEVELOPMENT.getName());
        nodMenu().openMenu();
        boolean casesOptionExists = NodMenuElements.cases.isDisplayed();
        boolean subscribedCasesOptionExists = NodMenuElements.subscribedCases.isDisplayed();
        boolean casesWindowAppeared = nodMenu().goToCases();
        Assertions.assertTrue(casesWindowAppeared, "NOD Cases Window didn't open");
        boolean casesPageIsOpened = casesPageAngular().isPageOpened();
        Assertions.assertTrue(casesPageIsOpened, "NOD Cases page didn't open");
        casesPageAngular().maximizeCurrentWindow();
        boolean noTeamCasesAreOpened = casesPageAngular().getContentSetTeamMessage().equals("No Team Cases");
        HashMap<String, Integer> expectedColumnOrder = casesPageAngular().getExpectedColumnOrder();
        HashMap<String, Integer> actualColumnOrder = casesTablePage().getActualColumnOrder();
        boolean orderOfColumnsIsEqual = expectedColumnOrder.equals(actualColumnOrder);

        Assertions.assertAll
          (
              () -> Assertions.assertTrue(casesOptionExists, "Cases option is not shown"),
              () -> Assertions.assertTrue(subscribedCasesOptionExists, "Subscribed Cases option is not shown"),
              () -> Assertions.assertTrue(noTeamCasesAreOpened, "No Team Cases were not opened by default"),
              () -> Assertions.assertTrue(orderOfColumnsIsEqual, "Column order is not expected")
          );
    }

    /**
     * HALCYONST-10740 - Table layout for Canada
     * 1. Log onto Canada Ontario (Development) content set
     * 2. Click the NOD button
     * 3. Select the Cases option on the page
     * 4.When UI will come up, VERIFY: that "NO Team Cases" is selected
     * 5. It shouldn't matter what is selected in the drop down, the columns will all be the same.
     * 6. They should be in the following order:  Loaded Date, Case serial #, Westlaw #, Report Cite, Court, R/U, Reloaded, Headnotes, Title, Subscriptions
     */
    @Test
    @EDGE
    @CARSWELL
    @LOG
    public void casesCanadaTableLayout()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.CANADA_ONTARIO_DEVELOPMENT.getName());
        boolean nodWindowAppeared = nodMenu().goToCanadianNOD();
        Assertions.assertTrue(nodWindowAppeared, "NOD Window did not appear");
        boolean homePageIsOpened = homePageAngular().verifyPageIsOpened();
        Assertions.assertTrue(homePageIsOpened, "NOD Home page did not appear");
        homePageAngular().clickHomePageMenuOption(HomePageAngular.NodHomePageListOptions.CASES);
        boolean casesPageIsOpened = casesPageAngular().isPageOpened();
        Assertions.assertTrue(casesPageIsOpened, "NOD Cases page didn't open");
        casesPageAngular().maximizeCurrentWindow();
        boolean noTeamCasesAreOpened = casesPageAngular().getContentSetTeamMessage().equals("No Team Cases");
        TableHeaders headers = casesTablePage().parseHeaders();
        HashMap<String, Integer> expectedColumnOrder = casesPageAngular().getExpectedColumnOrder();
        HashMap<String, Integer> actualColumnOrder = headers.getActualColumnOrder();
        boolean orderOfColumnsIsEqual = expectedColumnOrder.equals(actualColumnOrder);

        Assertions.assertAll
          (
              () -> Assertions.assertTrue(noTeamCasesAreOpened, "No Team Cases were not opened by default"),
              () -> Assertions.assertTrue(orderOfColumnsIsEqual, "Order of columns is not expected")
          );
    }
}