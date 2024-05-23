package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.smoke;

import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DisabledTabsTests extends SourceNavigateAngularAssertions
{
    @Test
    @DisplayName("Disabled tabs test")
    @EDGE
    @LEGAL
    @LOG
    public void disabledLineageSectionDeltaTabsTest()
    {
        List<String> tabsNames = Arrays
                .asList(LINEAGE_TAB_NAME, SECTION_GROUP_TAB_NAME, SECTION_TAB_NAME, DELTA_GROUP_TAB_NAME, DELTA_TAB_NAME);

        tabsNames
                .forEach(
                        s -> assertThat(sourceNavigateAngularTabsPage().isTabDisabled(s))
                                .as("'%s' tab should be disabled", s)
                                .isTrue()
                );
    }
}
