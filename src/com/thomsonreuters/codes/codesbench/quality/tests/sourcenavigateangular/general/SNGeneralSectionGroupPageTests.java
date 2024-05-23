package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.sql.Connection;

import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularFiltersAndSortsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionGroupPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static java.lang.String.format;

public class SNGeneralSectionGroupPageTests extends SourceNavigateAngularAssertions
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;
    @BeforeEach
    public void mockData(TestInfo testInfo) {
        if (!(testInfo.getDisplayName().equals("verifySectionGroupTabClearFilterTest()"))) {
            datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
            renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();
            connection = CommonDataMocking.connectToDatabase(environmentTag);
        }
    }
    @AfterEach
    public void deleteMockedData() {
        if (datapodObject != null) {
            // sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }
    /*
      User Story 722130: [HALCYONST-15806] Source Navigation: Clear filter
        */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifySectionGroupTabClearFilterTest() {

        sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 0)), (format(RENDITION_ROW_PATTERN, 1)));
        sourceNavigateAngularPage().click(SECTION_GROUP_TAB);
        String totalNumberOfSectionGroups = sourceNavigateAngularSectionGroupPage().getTotalNumberOfSectionGroups();
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeaderForTabs(COLUMN_FILTER_BUTTON_PATTERN_SECTION_GROUP_TAB,RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(BLANKS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verify current section numbers less than to the total section numbers
        assertThatFilteredRenditionsNumberLessThanTotalRenditions(sourceNavigateAngularSectionGroupPage().getTotalNumberOfSectionGroups(), totalNumberOfSectionGroups);

        //Clearing Filter
        sourceNavigateAngularSectionGroupPage().isElementDisplayed(CLEAR_FILTERS_SECTION_GROUP_TAB);
        sourceNavigateAngularSectionGroupPage().clickSectionGroupTabClearFiltersButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //verify current section numbers is equal to the total section numbers
        assertThatTotalRenditionsNumberMatchingToExpected(sourceNavigateAngularSectionGroupPage().getTotalNumberOfSectionGroups(), totalNumberOfSectionGroups);
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
}
