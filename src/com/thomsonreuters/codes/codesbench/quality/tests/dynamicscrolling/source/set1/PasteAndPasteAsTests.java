package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractPasteAndPasteAsTests;
import org.junit.jupiter.api.BeforeEach;

public class PasteAndPasteAsTests extends AbstractPasteAndPasteAsTests
{
    private static final String UUID = "IB6FA198060D011EBBE6FA8AD556CB520";

    @BeforeEach
    protected void beforeEachTestMethod()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
    }
}
