package com.thomsonreuters.codes.codesbench.quality.tests.smoke.section.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class SectionContextMenuModifyLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, it enters the Section Properties pop up using the Context Menu and clears the Assigned User and Assigned Date
     *           It then enters the Assign User pop up using the Context Menu and sets the Assigned User and Assigned Date.
     *           After entering the values, it checks to see if the values were set in the Section Properties.
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignUserLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String assignedUser = user().getFirstname() + " " + user().getLastname();;

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and open Section Properties page
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();

        //Clears the assigned user and assigned date
        boolean assignedEmpty = sectionPropertiesPage().clearAssignedUserAndDate();
        sectionPropertiesPage().clickSave();
        Assertions.assertTrue(assignedEmpty, "Failed to clear assigned user and assigned date");

        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openAssignUser();
        assignUserPage().setAssignedUser(assignedUser); //two methods do the same thing
        assignUserPage().setAssignedDate(currentDate);
        assignUserPage().clickSave();

        //Click first section and open Section Properties page
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();

        //Set the assigned date and assigned user
        boolean dateSet = sectionPropertiesPage().getAssignedDate().equals(currentDate);
        boolean userSet =  sectionPropertiesPage().getAssignedUser().equals(assignedUser);

        sectionPropertiesPage().clickSave();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(dateSet, "Assigned date was incorrect"),
            () -> Assertions.assertTrue(userSet, "Assigned user was incorrect")
        );
    }

    @BeforeEach
    public void mockData()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
        renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(datapodObject != null)
        {
            sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
