package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractToolbarUndoButtonTests;
import org.junit.jupiter.api.BeforeEach;

public class ToolbarUndoButtonTests extends AbstractToolbarUndoButtonTests
{
    private static final String RENDITION_UUID = "IB6FA198060D011EBBE6FA8AD556CB520";

    @BeforeEach
    protected void beforeEachTestMethod()
    {
        sourcePage().goToSourcePageWithRenditionUuids(RENDITION_UUID);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
    }
}
