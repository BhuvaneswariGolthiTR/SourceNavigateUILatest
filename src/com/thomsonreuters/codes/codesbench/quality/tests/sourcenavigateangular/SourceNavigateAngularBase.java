package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.RENDITIONS_ACTIVE_VIEW_NAME;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularViewManagerPageElements.DEFAULT_VIEW_BOTTOM;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularViewManagerPageElements.DEFAULT_VIEW_DROPDOWN;

public class SourceNavigateAngularBase extends TestService
{
    @BeforeEach
    public void accessSourceNavigate()
    {
        sourceNavigateAngularPage().goToSourcePage();
        loginPage().logIn();
    }

    @AfterEach
    public void setDefaultView()
    {
        if (!sourceNavigateAngularPage().getActiveViewName(RENDITIONS_ACTIVE_VIEW_NAME).contains(DEFAULT_VIEW_BOTTOM))
        {
            sourceNavigateAngularViewManagerPage().applyExistingView(DEFAULT_VIEW_DROPDOWN);
        }
    }
}
