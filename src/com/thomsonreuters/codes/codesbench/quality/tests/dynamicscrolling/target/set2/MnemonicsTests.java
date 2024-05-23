package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractMnemonicsTests;
import org.junit.jupiter.api.BeforeEach;

public class MnemonicsTests extends AbstractMnemonicsTests
{
    @BeforeEach
    public void beforeEachTestMethod()
    {
        openTargetNodeInDsEditorAndSwitchToEditorTextFrame();
        //Select multiple Text Paragraphs on the chunk boundary
        selectSubsectionElementsUnder(10);
    }
}
