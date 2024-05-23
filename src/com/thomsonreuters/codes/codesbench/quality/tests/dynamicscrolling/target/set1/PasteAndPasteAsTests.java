package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractPasteAndPasteAsTests;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class PasteAndPasteAsTests extends AbstractPasteAndPasteAsTests
{
    private static final String UUID = "I2AC2273014EE11DA8AC5CD53670E6B4E";

    @BeforeEach
    protected void beforeEachTestMethod()
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }
}
