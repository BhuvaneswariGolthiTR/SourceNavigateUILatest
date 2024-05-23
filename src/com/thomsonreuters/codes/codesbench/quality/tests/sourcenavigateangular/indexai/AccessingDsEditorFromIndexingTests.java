package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.PAGE_TITLE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Feature 725256: CWB Index UI Redesign - Session Law Box [HALCYONST-17482]
 * User Story 723604: Implement button to open editor (dependent on IE/Edge changes)
 */
public class AccessingDsEditorFromIndexingTests extends IndexingUiBase
{
    private Connection connection;
    private List<String> lockUuid;

    @BeforeEach
    public void accessIndexingUi()
    {
        findSingleApvRenditionForIndexing(UUID_NO_SUGGESTIONS);
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        lockUuid = SourceDatabaseUtils.getLockUuids(connection, UUID_NO_SUGGESTIONS);
        if (!lockUuid.isEmpty())
        {
            SourceDatabaseUtils.deleteLocks(connection, UUID_NO_SUGGESTIONS);
        }
        BaseDatabaseUtils.disconnect(connection);
        accessIndexingUiViaContextMenu();
    }

    @Test
    @DisplayName("verifyDocButtonOpensRenditionInEditorInViewModeTest - Indexing UI: Accessing Dynamic Scrolling Editor")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDocButtonOpensRenditionInEditorInViewModeTest()
    {
        assertThatSeeMoreLinkIsPresent();
        indexingPage().openFullRenditionInEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();
        assertThatRenditionIsNotLockedByDsEditor();
        indexingPage().switchToOpenedWindowByTitle(PAGE_TITLE);
    }

    @Test
    @DisplayName("verifyClickSeeMoreLinkOpensRenditionInEditorInViewModeTest - Indexing UI: Accessing Dynamic Scrolling Editor")
    @EDGE
    @LEGAL
    @LOG
    public void verifyClickSeeMoreLinkOpensRenditionInEditorInViewModeTest()
    {
        assertThatSeeMoreLinkIsPresent();
        indexingSessionLawBoxPage().openFullRenditionInEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();
        assertThatRenditionIsNotLockedByDsEditor();
        indexingPage().switchToOpenedWindowByTitle(PAGE_TITLE);
    }

    // ---------- Assistive Methods ----------

    private void assertThatRenditionIsNotLockedByDsEditor()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        lockUuid = SourceDatabaseUtils.getLockUuids(connection, UUID_NO_SUGGESTIONS);
        assertThat(lockUuid.isEmpty())
                .as("The rendition should not be locked as it opens in DS Editor in View mode")
                .isTrue();
        BaseDatabaseUtils.disconnect(connection);
    }
}
