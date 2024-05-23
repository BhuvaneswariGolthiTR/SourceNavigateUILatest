package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractHighlightingElementsTests;
import org.junit.jupiter.api.BeforeEach;

public class HighlightingElementsSourceTests extends AbstractHighlightingElementsTests
{
    private static final String RENDITION_UUID = "I689E09A02F4611E29538B629ABD39661";

    @BeforeEach
    public void openRenditionInDsEditor()
    {
        sourcePage().goToSourcePageWithRenditionUuids(RENDITION_UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
    }
}
