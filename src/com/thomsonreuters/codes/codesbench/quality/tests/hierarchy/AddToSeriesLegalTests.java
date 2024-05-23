package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

public class AddToSeriesLegalTests extends TestService
{
    /**
     * STORY/BUG - JETS-23127 <br>
     * SUMMARY - This test logs in as the legal user and verifies that Add to Series is not an option in the hierarchy navigate context menu <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void verifyAddToSeriesIsNotPresentForLegalUser()
    {
        String nodeUuid = "IDD518CB0157F11DA8AC5CD53670E6B4E";
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedNode();

        boolean isAddToSeriesPresent = siblingMetadataContextMenu().doesElementExist(SiblingMetadataContextMenuElements.ADD_TO_SERIES);
        Assertions.assertFalse(isAddToSeriesPresent, "Add To Series was present in the context menu when it should not have been");
    }
}
