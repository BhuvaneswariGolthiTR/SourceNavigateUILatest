package com.thomsonreuters.codes.codesbench.quality.tests.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.OCExtractBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.PublicationsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.PublicationsTablePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashMap;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class PublicationsTests extends TestService
{
    /**
     * STORY/BUG - HALCYONST-13393 <br>
     * SUMMARY - Publications table: layout  <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void publicationsTableLayoutTest()
    {
        // 1	Log onto the Iowa (Development) content set
        homePage().goToHomePage();
        loginPage().logIn();

        // 2	Go to Tools -> OC Extract
        boolean ocExtractPageOpened = toolsMenu().goToOCExtract();
        Assertions.assertTrue(ocExtractPageOpened, "The OC Extract page opened");

        // 3	VERIFY: O'Connors Processing UI window is opened
        boolean publicationsPageOpened = publicationsPage().isPageOpened();
        Assertions.assertTrue(publicationsPageOpened, "Publications page opened");

        /* 4	VERIFY the following elements are present:
            a) "Publications" table name
            b) Tree-structured table with columns (in order below):
            1 - Year (filled by the System from the O'Connor's Workflow parameters)
            2 - Publication Name (filled by the System from O'Connor's Workflow parameters)
        */
        boolean publicationsTableNameIsDisplayed = publicationsPage().isTableNameDisplayed(PublicationsPageElements.TABLE_NAME);
        List<String> expectedColumnOrder = publicationsPage().getExpectedColumnOrder();
        List<String> actualColumnOrder = publicationsTablePage().getActualColumnOrderListOcExtract();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(publicationsTableNameIsDisplayed, "Publications table name is displayed"),
                () -> Assertions.assertEquals(expectedColumnOrder, actualColumnOrder, "Publications table actual column order equals to expected one")
        );
    }

    /**
     * STORY/BUG - HALCYONST-13354 <br>
     * SUMMARY - Go to Publication Files page by: double-click on the publication; context menu; Publication Files button  <br>
     * USER - Legal <br>
     */
    public enum GoToPublicationFilesPageMethod
    {
        DOUBLE_CLICK,
        CONTEXT_MENU,
        PUBLICATION_FILES_BUTTON
    }
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @EnumSource
    @EDGE
    @LEGAL
    @LOG
    public void goToPublicationFilesPageTest(GoToPublicationFilesPageMethod goToPublicationFilesPageMethod)
    {
        // 1	Log onto the Iowa (Development) content set
        homePage().goToHomePage();
        loginPage().logIn();

        // 2	Go to Tools -> OC Extract
        boolean ocExtractPageOpened = toolsMenu().goToOCExtract();
        Assertions.assertTrue(ocExtractPageOpened, "The OC Extract page opened");

        // 3	VERIFY: O'Connors Processing UI window is opened
        boolean publicationsPageOpened = publicationsPage().isPageOpened();
        Assertions.assertTrue(publicationsPageOpened, "Publications page opened");

        String expectedPublicationYear = publicationsTablePage().getFirstPublicationYear();
        String expectedPublicationName = publicationsTablePage().getFirstPublicationName();

        switch (goToPublicationFilesPageMethod)
        {
            case DOUBLE_CLICK:
                // 4	Double-click on the first publication in the "Publications" table
                publicationsTablePage().doubleClickFirstPublication();
                break;
            case CONTEXT_MENU:
                // 4	Right click on the first publication in the "Publications" table
                publicationsTablePage().rightClickFirstPublication();
                // 4.1  Click on 'Select Publication' context menu item
                publicationsContextMenu().selectPublication();
                break;
            case PUBLICATION_FILES_BUTTON:
                // 4	Click on the first publication in the "Publications" table to select it
                publicationsTablePage().selectFirstPublication();
                // 4.1  VERIFY: Publication is selected
                boolean firstPublicationSelected = publicationsTablePage().isTableRowSelected(PublicationsTablePageElements.FIRST_PUBLICATION_ROW_NUMBER);
                Assertions.assertTrue(firstPublicationSelected, "First publication table row is selected");
                // 4.2  Click "Publication Files" button
                publicationsPage().clickPublicationFilesButton();
                break;
            default:
                break;
        }

        // 5    VERIFY: The "Files" table is shown for the chosen publication
        boolean publicationFilesPageOpened = publicationFilesPage().isPageOpened();
        Assertions.assertTrue(publicationFilesPageOpened, "Publication files page opened");

        String actualPublicationYear = publicationFilesPage().getPublicationYear();
        String actualPublicationName = publicationFilesPage().getPublicationName();

        Assertions.assertAll
        (
                () -> Assertions.assertEquals(expectedPublicationYear, actualPublicationYear, "Actual publication year equals to expected one"),
                () -> Assertions.assertEquals(expectedPublicationName, actualPublicationName, "Actual publication name equals to expected one")
        );
    }
}
