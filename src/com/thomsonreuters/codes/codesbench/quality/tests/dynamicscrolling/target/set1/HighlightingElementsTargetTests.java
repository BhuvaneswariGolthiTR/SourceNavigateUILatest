package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractHighlightingElementsTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.BeforeEach;

public class HighlightingElementsTargetTests extends AbstractHighlightingElementsTests
{
    private static final String NODE_UUID = "ID117BAE014F411DA8AC5CD53670E6B4E";

    @BeforeEach
    public void openNodeInDsEditor()
    {
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(NODE_UUID);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }
}
