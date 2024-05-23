package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PLACEHOLDER_BY_DISPLAY_NAME;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PLACEHOLDER_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.RUNNING_HEAD_BLOCK;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;

public class ViewVolumeInformationDSTests extends TestService
{
    private static final String UUID = "IF071CE60157811DA8AC5CD53670E6B4E";
    private static final String VOLUME_NAME_AND_BOOK_NUMBER = "Volume Name and Book Number";
    private static final String END_OF_VOLUME_TEXT_SYMBOL = "End of Volume Text/Symbol";
    private SoftAssertions softAssertions;

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    /**
     * STORY/BUG - HALCYONST-10680 <br>
     * SUMMARY - Dynamic scrolling: DS options in the View Volume Information (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void dynamicScrollingOptionsInTheViewVolumeInformationTargetLegalTest()
    {
        String phrase = format("TEST %s ", System.currentTimeMillis());
        softAssertions = new SoftAssertions();

        //Find node, open 'View Volume Information' window and assert that it is opened
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(UUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        softAssertions.assertThat(siblingMetadataContextMenu().viewVolumeInfo())
                .as("View Volume Information window should be opened")
                .isTrue();

        //Select 'Edit Content Dynamic Scrolling' under 'Beginning of Volume'
        String volumeNumber = volumeInformationPage().getSelectedVolumeNumber();
        editContentDynamicScrollingUnderBeginningOfVolume();

        //Assert that DS editor is appeared with the 'Beginning of Volume' content
        softAssertThatDsEditorIsAppearedWithSpecifiedContent("//bov");
        //Assert that the 'Volume Number Start' matches the volume number of the node
        softAssertThatVolumeNumberInElementMatchesVolumeNumberOfTheNode("Volume Number Start", volumeNumber);
        //Assert that the 'Content Set' section is appeared
        softAssertThatElementExists("Content Set");
        //Assert that the 'Volume Name and Book Number' is appeared
        softAssertThatElementExists(VOLUME_NAME_AND_BOOK_NUMBER);
        //Assert that the 'Running Head' information is appeared
        softAssertions.assertThat(editorTextPage().doesElementExist(RUNNING_HEAD_BLOCK))
                .as("The Running Head information should be appeared")
                .isTrue();
        softAssertions.assertAll();

        insertPhraseInElementTextAndCheckIn(phrase, VOLUME_NAME_AND_BOOK_NUMBER);

        //Reopen node
        volumeInformationPage().switchToVolumeInformationPage();
        editContentDynamicScrollingUnderBeginningOfVolume();

        assertThatPhraseIsPresentInElementText(phrase, VOLUME_NAME_AND_BOOK_NUMBER);

        removePhraseFromElementTextAndCheckIn(phrase, VOLUME_NAME_AND_BOOK_NUMBER);

        //Select 'Edit Content Dynamic Scrolling' under 'End of Volume'
        editContentDynamicScrollingUnderEndOfVolume();

        //Assert that DS editor is appeared with the 'End of Volume' content
        softAssertThatDsEditorIsAppearedWithSpecifiedContent("//eov");
        //Assert that 'Index Reference Line' section is appeared
        softAssertThatElementExists("Index Reference Line");
        //Assert that 'End of Volume Text/Symbol' section is appeared
        softAssertThatElementExists(END_OF_VOLUME_TEXT_SYMBOL);
        //Assert that the 'Volume Number End' matches the volume number of the node
        softAssertThatVolumeNumberInElementMatchesVolumeNumberOfTheNode("Volume Number End", volumeNumber);
        softAssertions.assertAll();

        insertPhraseInElementTextAndCheckIn(phrase, END_OF_VOLUME_TEXT_SYMBOL);

        //Reopen node
        editContentDynamicScrollingUnderEndOfVolume();

        assertThatPhraseIsPresentInElementText(phrase, END_OF_VOLUME_TEXT_SYMBOL);

        removePhraseFromElementTextAndCheckIn(phrase, END_OF_VOLUME_TEXT_SYMBOL);
    }

//  ------------- Assistive methods: -------------

    private void editContentDynamicScrollingUnderBeginningOfVolume()
    {
        volumeInformationPage().clickEditContentDynamicScrollingUnderBov();
        editorPage().switchDirectlyToTextFrame();
    }

    private void editContentDynamicScrollingUnderEndOfVolume()
    {
        volumeInformationPage().switchToVolumeInformationPage();
        volumeInformationPage().clickEditContentDynamicScrollingUnderEov();
        editorPage().switchDirectlyToTextFrame();
    }

    private void insertPhraseInElementTextAndCheckIn(String phrase, String elementEnglishLabel)
    {
        editorTextPage().insertPhraseUnderGivenLabel(phrase,
                format(PLACEHOLDER_BY_DISPLAY_NAME, elementEnglishLabel) + "/span");
        editorPage().closeAndCheckInChanges();
    }

    private void removePhraseFromElementTextAndCheckIn(String phrase, String elementEnglishLabel)
    {
        editorTextPage().click(format(PLACEHOLDER_BY_DISPLAY_NAME, elementEnglishLabel) + PLACEHOLDER_TEXT);
        editorTextPage().sendKeys(Keys.HOME);
        editorTextPage().highlightHelper(phrase.length());
        editorPage().sendKeys(Keys.DELETE);
        editorPage().closeAndCheckInChanges();
    }

    private void assertThatPhraseIsPresentInElementText(String phrase, String elementEnglishLabel)
    {
        Assertions.assertThat(editorTextPage()
                .getElementsText(format(PLACEHOLDER_BY_DISPLAY_NAME, elementEnglishLabel) + PLACEHOLDER_TEXT))
                .as(format("Phrase %s should be present in %s text", phrase, elementEnglishLabel))
                .contains(phrase);
    }

    private void softAssertThatElementExists(String elementEnglishLabel)
    {
        softAssertions.assertThat(editorTextPage().doesElementExist(format(PLACEHOLDER_BY_DISPLAY_NAME, elementEnglishLabel)))
                .as(format("The %s section should be appeared", elementEnglishLabel))
                .isTrue();
    }

    private void softAssertThatDsEditorIsAppearedWithSpecifiedContent(String xPath)
    {
        String englishLabel = xPath.equals("//bov") ? "Beginning of Volume" : "End of Volume";
        softAssertions.assertThat(editorTextPage().doesElementExist(xPath))
                .as(format("DS editor should be appeared with the %s content", englishLabel))
                .isTrue();
    }

    private void softAssertThatVolumeNumberInElementMatchesVolumeNumberOfTheNode(String elementEnglishLabel,
                                                                                 String volumeNumber)
    {
        softAssertions.assertThat(editorTextPage()
                .getElementsText(format(PLACEHOLDER_BY_DISPLAY_NAME, elementEnglishLabel) + PLACEHOLDER_TEXT))
                .as(format("The %s should match the Volume Number of the node", elementEnglishLabel))
                .contains(volumeNumber);
    }
}
