package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractEditorCopyAndPasteTests;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class EditorTreeCopyAndPasteTests extends AbstractEditorCopyAndPasteTests
{
    private static final String IOWA_TARGET_NODE_UUID = "IDA001260FFCC11DA9C1BA4F3A96229FB";

    @BeforeEach
    public void openDocument()
    {
        //1. Open document (target)
        nodesToExpand = new String[]{"body", "subsection \"1\""};
        firstSourceNode = "subsection \"1 a\"";
        secondSourceNode = "subsection \"1 b\"";
        destinationNode = "subsection \"2\"";
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(IOWA_TARGET_NODE_UUID);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }
}
