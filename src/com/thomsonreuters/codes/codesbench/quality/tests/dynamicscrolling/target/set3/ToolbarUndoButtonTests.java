package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractToolbarUndoButtonTests;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class ToolbarUndoButtonTests extends AbstractToolbarUndoButtonTests
{
    private static final String TARGET_UUID = "I26BB1DE014EE11DA8AC5CD53670E6B4E";

    @BeforeEach
    protected void beforeEachTestMethod()
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(TARGET_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }
}
