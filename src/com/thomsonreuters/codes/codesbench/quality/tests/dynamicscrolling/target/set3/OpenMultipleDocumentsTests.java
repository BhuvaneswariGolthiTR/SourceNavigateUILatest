package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractOpenMultipleDocumentsTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class OpenMultipleDocumentsTests extends AbstractOpenMultipleDocumentsTests
{
    private static final String FIRST_UUID = "I26C8B27014EE11DA8AC5CD53670E6B4E";
    private static final String SECOND_UUID = "I26BFD8D014EE11DA8AC5CD53670E6B4E";
    private static final String THIRD_UUID = "I26C46CB014EE11DA8AC5CD53670E6B4E";

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openMultipleTargetNodesLegalTest()
    {
        //Go to Hierarchy page and open target nodes in DS editor
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        int numberOfTheOpenedNodes = hierarchyNavigatePage().openMultipleTargetNodes(FIRST_UUID, SECOND_UUID, THIRD_UUID);

        //Assert that target nodes are opened in DS editor
        assertThatDocumentsAreOpenedInDsEditor(numberOfTheOpenedNodes);
        //Assert that each blue bar of the opened target node has different metadata
        assertThatEachBlueBarOfTheOpenedDocumentHasDifferentMetadata();
        //Assert that list with editor links is displayed in html window
        assertThatListWithEditorLinksIsDisplayedInHtmlWindow(numberOfTheOpenedNodes);
        //Assert that current position is not bounced anywhere after Copy and Paste Text Paragraph
        assertThatCurrentPositionIsNotBouncedAfterCopyPasteTextParagraphByIframeIndex(3);
    }
}
