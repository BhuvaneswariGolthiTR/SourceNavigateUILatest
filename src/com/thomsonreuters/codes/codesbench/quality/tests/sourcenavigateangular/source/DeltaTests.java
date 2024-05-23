package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.source;

import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DeltaTests extends SourceNavigateAngularAssertions
{
    /**
     * This is a start point to create automated tests for the Delta tab:
     * First we need to find the required Rendition, then select the rendition and go to the Delta tab
     * After each test we have to go back to the Rendition tab
     * BeforeEach and AfterEach are implemented in order to make the test creation easier; they contain the necessary steps.
     */
    private static final String PREP_RENDITION_UUID = "I0699F57081D011E2ABA2F3FC0A502E12";

    @BeforeEach
    public void locateRenditionAndGoToDeltaTab()
    {
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", PREP_RENDITION_UUID);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        assertThat(sourceNavigateAngularTabsPage().isTabDisabled(DELTA_TAB_NAME))
                .as("'%s' tab should be enabled", DELTA_TAB_NAME)
                .isFalse();
        sourceNavigateAngularPage().click(DELTA_TAB);
    }

    @AfterEach
    public void goBackToRenditionTab()
    {
        sourceNavigateAngularPage().click(RENDITION_TAB);
    }

}
