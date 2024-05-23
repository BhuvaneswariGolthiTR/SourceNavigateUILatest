package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GlossaryNodeTypeEditorValidationsTests extends TestService
{
    /**
     * STORY/BUG - JETS-19927 <br>
     * SUMMARY - A test that checks for errors in a glossary node.
     * USER - Risk <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void glossaryNodeTypeEditorValidationTest()
    {
        String contentSet = "FCA Handbook";
        String quickSearch = "facilities";

        // Change content set and go to Hierarchy Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        // Search node and edit
        hierarchySearchPage().quickSearch("=", quickSearch);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        // Click validate and check if the errors are within the text
        editorToolbarPage().clickValidate();
        boolean dataErrorId2004Appeared = editorMessagePage().checkMessagePaneForText("Data error id[2004]");
        boolean dataErrorId928Appeared = editorMessagePage().checkMessagePaneForText("Data error id[928]");
        boolean dataErrorId937Appeared = editorMessagePage().checkMessagePaneForText("Data error id[937]");

        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
        (
                () -> Assertions.assertFalse(dataErrorId2004Appeared, "The data error with id 2004 appeared in the message pane and it should not have."),
                () -> Assertions.assertFalse(dataErrorId928Appeared, "The data error with id 928 appeared in the message pane and it should not have."),
                () -> Assertions.assertFalse(dataErrorId937Appeared, "The data error with id 937 appeared in the message pane and it should not have.")
        );
    }
}