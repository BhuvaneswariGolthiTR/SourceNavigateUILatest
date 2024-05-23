package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.source;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularViewManagerPageElements.FIND_TEST_VIEW_DROPDOWN;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils.assignUser;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * User Story 289183: [HALCYONST-16151] Source Navigate (Angular) - Find Function
 */
public class FindDocumentSourceLegalTests extends SourceNavigateAngularAssertions
{
    private static final String CORRELATION_ID = "ID32E496140E511E18BA4C1B1D27F8FCC";
    private static final String BILL_ID = "2011_2012_15_2_1_0SB500_0_1_1_20120117_1";
    private static final String RENDITION_UUID = "I27977A8040E611E1959DCB3E93D939B8";
    private static final String DOCUMENT_UUID = "IFDBFF61140E511E187EEA0EC202A9E09";

    private static final String FIRST_RENDITION_ASSIGNED_USER = "I27977A8040E611E1959DCB3E93D939B8";
    private static final String SECOND_RENDITION_ASSIGNED_USER = "IDEB04D1040E511E1959DCB3E93D939B8";

    private Connection connection;

    @BeforeEach
    public void applyViewAndGoToFindInterface()
    {
        sourceNavigateAngularViewManagerPage().applyExistingView(FIND_TEST_VIEW_DROPDOWN);

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
    }

    static Stream<Arguments> getFindTestData()
    {
        return Stream.of(
                arguments("Correlation ID", CORRELATION_ID, SourceNavigateAngularPageElements.CORRELATION_ID),
                arguments("Bill ID", BILL_ID, SourceNavigateAngularPageElements.BILL_ID),
                arguments("Rendition UUID", RENDITION_UUID, SourceNavigateAngularPageElements.RENDITION_UUID),
                arguments("Document UUID", DOCUMENT_UUID, SourceNavigateAngularPageElements.DOCUMENT_UUID)
        );
    }

    @ParameterizedTest(name = "#{index} - Find Rendition using {0}")
    @MethodSource("getFindTestData")
    @DisplayName("Find functionality: Correlation ID, Bill ID, Rendition UUID, Document UUID")
    @EDGE
    @LEGAL
    @LOG
    public void findDocumentByUuidLegalTest(String columnHeader, String uuid, String columnId)
    {
        sourceNavigateAngularLeftSidePanePage().setFindValue(columnHeader, uuid);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        assertThatTotalRenditionsNumberIsEqualToNumberOfDisplayedRenditions();
        assertThatFilterIconAppearsInColumnHeader(columnId);
        assertThatAllFoundRenditionsHaveSameId(columnId, uuid, columnHeader);
    }

    @Test
    @DisplayName("Find functionality: Assigned To User")
    @EDGE
    @LEGAL
    @LOG
    public void findDocumentByAssignedUserLegalTest()
    {
        String username = user().getFirstname() + " " + user().getLastname();
        String userId = user().getUsername();

        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        assignUserToRenditions(userId);

        sourceNavigateAngularLeftSidePanePage().selectUserFromDropdown(username);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        assignUserToRenditions("");
        BaseDatabaseUtils.disconnect(connection);

        assertThatTotalRenditionsNumberIsEqualToNumberOfDisplayedRenditions();
        assertThatFilterIconAppearsInColumnHeader(SourceNavigateAngularPageElements.ASSIGNED_TO);
        assertThatRequiredRenditionsAreDisplayed(SourceNavigateAngularPageElements.RENDITION_UUID, FIRST_RENDITION_ASSIGNED_USER, SECOND_RENDITION_ASSIGNED_USER);
    }

    private void assignUserToRenditions(String userId)
    {
        assignUser(connection, userId, FIRST_RENDITION_ASSIGNED_USER);
        assignUser(connection, userId, SECOND_RENDITION_ASSIGNED_USER);
    }
}
