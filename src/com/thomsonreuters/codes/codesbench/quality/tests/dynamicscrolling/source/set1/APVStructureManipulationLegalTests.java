package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class APVStructureManipulationLegalTests extends TestService
{
        //private String uuid = "I7FCB2B907BFA11E595C2C2D1230E49DA"; // for dev
        private String uuid = "IC2E80C51B13211EAAA96BB9C7D19D48A";

        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void apvStructureManipulationSMPToSMPFLegalTest()
        {
                /*
                 * SMP to SMPF (Text Paragraph Flush, Suppress Subsection Identifier for Bound Volume Reference)
                 */
                String[] listForSMP = new String[]
                        {"clpp", "hg9", "sip", "snl", "mp21", "mp32", "smpf", "pb2", "pb4", "xcom", "mp322"};
                String englishLabelTextForSMPF =
                        "Text Paragraph Flush, Suppress Subsection Identifier for Bound Volume Reference";

                // open DS editor
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                //scroll to the 3rd chunk because that is where the data exists
                int chunkNumber = 3;
                editorPage().scrollToChunk(chunkNumber);
                String thirdChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1);
                String element = thirdChunk + EditorTextPageElements.TEXT_PARAGRAPH_PARA +
                                 EditorTextPageElements.mnemonics.SMP.xpath();
                editorPage().scrollToElement(element);
                String mnemonicId = editorPage().getElementsId(element);
                String mnemonicXpath = String.format(EditorTextPageElements.ID, mnemonicId);

                boolean contentEditorialSystemWindowAppearedForSMP = editorTextPage().rightClickMnemonic(mnemonicXpath);
                boolean changingSMPMnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForSMP);
                contentEditorialSystemPage().setMnemonic("smpf");
                boolean englishLabelForSMPF = contentEditorialSystemPage().englishLabelTextEquals(englishLabelTextForSMPF);
                contentEditorialSystemPage().clickSave();

                boolean mnemonicChangedFromSMPToSMPF = editorTextPage().getElementsText(mnemonicXpath).equals("SMPF");
                boolean paraNameChangedForSMPF = editorTextPage().getElementsAttribute
                        (mnemonicXpath + "/../..","full-display-name").equals(englishLabelTextForSMPF);

                // close and discard changes
                editorPage().closeDocumentAndDiscardChanges();
                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(contentEditorialSystemWindowAppearedForSMP,
                                                            "Content Editorial System Window should appear"),
                                () -> Assertions.assertTrue(changingSMPMnemonicAllowed,
                                                            "Changing SMP mnemonic should be allowed"),
                                () -> Assertions.assertTrue(englishLabelForSMPF,
                                                            "English label should be populated with correct text for SMPF"),
                                () -> Assertions.assertTrue(mnemonicChangedFromSMPToSMPF,
                                                            "Mnemonic should be changed from SMP to SMPF"),
                                () -> Assertions.assertTrue(paraNameChangedForSMPF,
                                                            "Para full-display-name should be changedfor SMPF")
                        );
        }

        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void apvStructureManipulationSMPFToSNLLegalTest()
        {
                /*
                 * SMPF to SNL (Section Nameline)
                 */
                String[] listForSMPF = new String[]{"snl"};
                String englishLabelTextForSNL = "Section Nameline";

                // open DS editor
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                //scroll to the 3rd chunk because that is where the data exists
                int chunkNumber = 3;
                editorPage().scrollToChunk(chunkNumber);
                String thirdChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1);
                String element = thirdChunk + EditorTextPageElements.TEXT_PARAGRAPH_PARA +
                                 EditorTextPageElements.mnemonics.SMP.xpath();
                editorPage().scrollToElement(element);
                String mnemonicId = editorPage().getElementsId(element);
                String mnemonicXpath = String.format(EditorTextPageElements.ID, mnemonicId);

                editorTextPage().rightClickMnemonic(mnemonicXpath);
                contentEditorialSystemPage().setMnemonic("smpf");
                contentEditorialSystemPage().clickSave();

                boolean contentEditorialSystemWindowAppeared = editorTextPage().rightClickMnemonic(mnemonicXpath);
                boolean changingSMPFMnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForSMPF);
                contentEditorialSystemPage().setMnemonic("snl");
                boolean englishLabelForSNL = contentEditorialSystemPage().englishLabelTextEquals(englishLabelTextForSNL);
                contentEditorialSystemPage().clickSave();

                boolean mnemonicChangedFromSMPFToSNL = editorTextPage().getElementsText(mnemonicXpath).equals("SNL");
                boolean spanTextChangedForSNL = editorTextPage().getElementsText(mnemonicXpath + "/../../span")
                        .contains(englishLabelTextForSNL);
                boolean paraNameChangedForSNL = editorTextPage().getElementsAttribute
                        (mnemonicXpath + "/../..", "full-display-name").equals(englishLabelTextForSNL);

                // close and discard changes
                editorPage().closeDocumentAndDiscardChanges();
                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(contentEditorialSystemWindowAppeared, "Content Editorial System Window should appear"),
                                () -> Assertions.assertTrue(changingSMPFMnemonicAllowed, "Changing SMPF mnemonic should be allowed"),
                                () -> Assertions.assertTrue(englishLabelForSNL, "English label should be populated with correct text for SNL"),
                                () -> Assertions.assertTrue(mnemonicChangedFromSMPFToSNL, "Mnemonic should be changed from SMPF to SNL"),
                                () -> Assertions.assertTrue(spanTextChangedForSNL, "Span should be changed from SMPF to SNL"),
                                () -> Assertions.assertTrue(paraNameChangedForSNL, "Para full-display-name should be changed for SNL")
                        );
        }

        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void apvStructureManipulationSNLToMP322LegalTest()
        {
                /*
                 * SNL to MP322 (Text Paragraph Indented 322)
                 */
                String[] listForSNL = new String[]{"mp322"};
                String englishLabelTextForMP322 = "Text Paragraph Indented 322";

                // open DS editor
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                //scroll to the 3rd chunk because that is where the data exists
                int chunkNumber = 3;
                editorPage().scrollToChunk(chunkNumber);
                String thirdChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1);
                String element = thirdChunk + EditorTextPageElements.TEXT_PARAGRAPH_PARA +
                                 EditorTextPageElements.mnemonics.SMP.xpath();
                editorPage().scrollToElement(element);
                String mnemonicId = editorPage().getElementsId(element);
                String mnemonicXpath = String.format(EditorTextPageElements.ID, mnemonicId);

                editorTextPage().rightClickMnemonic(mnemonicXpath);
                contentEditorialSystemPage().setMnemonic("snl");
                contentEditorialSystemPage().clickSave();

                boolean contentEditorialSystemWindowAppeared = editorTextPage().rightClickMnemonic(mnemonicXpath);
                boolean changingSNLMnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForSNL);
                contentEditorialSystemPage().setMnemonic("mp322");
                boolean englishLabelForMP322 = contentEditorialSystemPage().englishLabelTextEquals(englishLabelTextForMP322);
                contentEditorialSystemPage().clickSave();

                boolean mnemonicChangedFromSNLToMP322 = editorTextPage().getElementsText(mnemonicXpath).equals("MP322");
                boolean spanTextChangedforMP322 = editorTextPage().getElementsText(mnemonicXpath + "/../../span")
                        .contains("Text Paragraph");
                boolean paraNameChangedForMP322 = editorTextPage().getElementsAttribute
                        (mnemonicXpath + "/../..", "full-display-name").equals(englishLabelTextForMP322);

                // close and discard changes
                editorPage().closeDocumentAndDiscardChanges();
                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(contentEditorialSystemWindowAppeared, "Content Editorial System Window should appear"),
                                () -> Assertions.assertTrue(changingSNLMnemonicAllowed, "Changing SNL mnemonic should be allowed"),
                                () -> Assertions.assertTrue(englishLabelForMP322, "English label should be populated with correct text for MP322"),
                                () -> Assertions.assertTrue(mnemonicChangedFromSNLToMP322, "Mnemonic should be changed from SNL to MP322"),
                                () -> Assertions.assertTrue(spanTextChangedforMP322, "Span should be changed from SNL to MP322"),
                                () -> Assertions.assertTrue(paraNameChangedForMP322, "Para full-display-name should be changed for MP322")
                        );
        }

        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void apvStructureManipulationMP322ToMP21LegalTest()
        {
                /*
                 * MP322 to MP21 (Text Paragraph Indented 21)
                 */
                String englishLabelTextForMP21 = "Text Paragraph Indented 21";

                // open DS editor
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                //scroll to the 3rd chunk because that is where the data exists
                int chunkNumber = 3;
                editorPage().scrollToChunk(chunkNumber);
                String thirdChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1);
                String element = thirdChunk + EditorTextPageElements.TEXT_PARAGRAPH_PARA +
                                 EditorTextPageElements.mnemonics.SMP.xpath();
                editorPage().scrollToElement(element);
                String mnemonicId = editorPage().getElementsId(element);
                String mnemonicXpath = String.format(EditorTextPageElements.ID, mnemonicId);

                editorTextPage().rightClickMnemonic(mnemonicXpath);
                contentEditorialSystemPage().setMnemonic("mp322");
                contentEditorialSystemPage().clickSave();

                boolean contentEditorialSystemWindowAppeared = editorTextPage().rightClickMnemonic(mnemonicXpath);
                contentEditorialSystemPage().setMnemonic("mp21");
                boolean englishLabelForMP21 = contentEditorialSystemPage().englishLabelTextEquals(englishLabelTextForMP21);
                contentEditorialSystemPage().clickSave();

                boolean mnemonicChangedFromMP322ToMP21 = editorTextPage().getElementsText(mnemonicXpath).equals("MP21");
                boolean spanTextChangedForMP21 = editorTextPage().getElementsText(mnemonicXpath + "/../../span")
                        .contains("Text Paragraph");
                boolean paraNameChangedForMP21 = editorTextPage().getElementsAttribute
                        (mnemonicXpath + "/../..", "full-display-name").equals(englishLabelTextForMP21);

                // close and discard changes
                editorPage().closeDocumentAndDiscardChanges();
                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(contentEditorialSystemWindowAppeared, "Content Editorial System Window should appear"),
                                () -> Assertions.assertTrue(englishLabelForMP21, "English label should be populated with correct text for MP322"),
                                () -> Assertions.assertTrue(mnemonicChangedFromMP322ToMP21, "Mnemonic should be changed from MP322 to MP21"),
                                () -> Assertions.assertTrue(spanTextChangedForMP21, "Span should be changed from MP322 to MP21"),
                                () -> Assertions.assertTrue(paraNameChangedForMP21, "Para full-display-name should be changed for MP21")
                        );
        }

        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void apvStructureManipulationMP21ToHG9LegalTest()
        {
                /*
                 * MP21 to HG9 (Grade Heading Size 9)
                 */
                String[] listForMP21 = new String[]{"mp32"};
                String englishLabelTextForHG9 = "Grade Heading Size 9";

                // open DS editor
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                //scroll to the 3rd chunk because that is where the data exists
                int chunkNumber = 3;
                editorPage().scrollToChunk(chunkNumber);
                String thirdChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1);
                String element = thirdChunk + EditorTextPageElements.TEXT_PARAGRAPH_PARA +
                                 EditorTextPageElements.mnemonics.SMP.xpath();
                editorPage().scrollToElement(element);
                String mnemonicId = editorPage().getElementsId(element);
                String mnemonicXpath = String.format(EditorTextPageElements.ID, mnemonicId);

                editorTextPage().rightClickMnemonic(mnemonicXpath);
                contentEditorialSystemPage().setMnemonic("mp21");
                contentEditorialSystemPage().clickSave();

                boolean contentEditorialSystemWindowAppeared = editorTextPage().rightClickMnemonic(mnemonicXpath);
                boolean changingMP21MnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForMP21);
                contentEditorialSystemPage().setMnemonic("hg9");
                boolean englishLabelForMP21 = contentEditorialSystemPage().englishLabelTextEquals(englishLabelTextForHG9);
                contentEditorialSystemPage().clickSave();

                boolean mnemonicChangedFromMP21ToHG9 = editorTextPage().getElementsText(mnemonicXpath).equals("HG9");
                boolean spanTextChangedForHG9 = editorTextPage().getElementsText(mnemonicXpath + "/../../span")
                        .contains("Grade Heading");
                boolean paraNameChangedForHG9 = editorTextPage().getElementsAttribute
                        (mnemonicXpath + "/../..", "full-display-name").equals(englishLabelTextForHG9);

                // close and discard changes
                editorPage().closeDocumentAndDiscardChanges();
                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(contentEditorialSystemWindowAppeared, "Content Editorial System Window should appear"),
                                () -> Assertions.assertTrue(changingMP21MnemonicAllowed, "Changing MP21 mnemonic should be allowed"),
                                () -> Assertions.assertTrue(englishLabelForMP21, "English label should be populated with correct text for MP322"),
                                () -> Assertions.assertTrue(mnemonicChangedFromMP21ToHG9, "Mnemonic should be changed from MP21 to HG9"),
                                () -> Assertions.assertTrue(spanTextChangedForHG9, "Span should be changed from MP21 to HG9"),
                                () -> Assertions.assertTrue(paraNameChangedForHG9, "Para full-display-name should be changed for HG9")
                        );
        }

        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void apvStructureManipulationHG9LegalTest()
        {
                /*
                 * HG9
                 */
                String[] listForHG9 = new String[]{"smp", "clpp"};

                // open DS editor
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                //scroll to the 3rd chunk because that is where the data exists
                int chunkNumber = 3;
                editorPage().scrollToChunk(chunkNumber);
                String thirdChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1);
                String element = thirdChunk + EditorTextPageElements.TEXT_PARAGRAPH_PARA +
                                 EditorTextPageElements.mnemonics.SMP.xpath();
                editorPage().scrollToElement(element);
                String mnemonicId = editorPage().getElementsId(element);
                String mnemonicXpath = String.format(EditorTextPageElements.ID, mnemonicId);

                editorTextPage().rightClickMnemonic(mnemonicXpath);
                contentEditorialSystemPage().setMnemonic("hg9");
                contentEditorialSystemPage().clickSave();

                boolean contentEditorialSystemWindowAppeared = editorTextPage().rightClickMnemonic(mnemonicXpath);
                boolean changingHG9MnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForHG9);
                contentEditorialSystemPage().setMnemonic("hg9");
                contentEditorialSystemPage().clickCancel();

                // close and discard changes
                editorPage().closeDocumentAndDiscardChanges();
                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(contentEditorialSystemWindowAppeared, "Content Editorial System Window should appear"),
                                () -> Assertions.assertTrue(changingHG9MnemonicAllowed, "Changing HG9 mnemonic should be allowed")
                        );
        }

        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void apvStructureManipulationCPToCPMLegalTest()
        {
                /*
                 * CP to CPM (Table Topic Entry)
                 */
                String[] listForCPM = new String[]{"cp", "cpn"};
                String englishLabelTextForCPM = "Table Topic Entry";

                // open DS editor
                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                //scroll to the 3rd chunk because that is where the data exists
                int chunkNumber = 3;
                editorPage().scrollToChunk(chunkNumber);
                String thirdChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1);
                String element = thirdChunk + EditorTextPageElements.mnemonics.CP.xpath();
                editorPage().scrollToElement(element);
                String mnemonicId = editorPage().getElementsId(element);
                String mnemonicXpath = String.format(EditorTextPageElements.ID, mnemonicId);

                boolean contentEditorialSystemWindowAppeared = editorTextPage().rightClickMnemonic(mnemonicXpath);
                contentEditorialSystemPage().setMnemonic("cpm");
                boolean englishLabelForCPM = contentEditorialSystemPage().englishLabelTextEquals(englishLabelTextForCPM);
                contentEditorialSystemPage().clickSave();

                boolean mnemonicChangedFromCPToCPM = editorTextPage().getElementsText(mnemonicXpath).equals("CPM");
                boolean spanTextChangedForCPM = editorTextPage().getElementsText(mnemonicXpath + "/../../span")
                        .contains("Table Topic Entry");
                boolean cornerpieceNameChangedForCPM = editorTextPage().getElementsAttribute
                        (mnemonicXpath + "/../..", "full-display-name").equals(englishLabelTextForCPM);

                editorTextPage().rightClickMnemonic(mnemonicXpath);
                boolean changingCPMMnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForCPM);
                contentEditorialSystemPage().clickCancel();

                // close and discard changes
                editorPage().closeDocumentAndDiscardChanges();
                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(contentEditorialSystemWindowAppeared, "Content Editorial System Window should appear"),
                                () -> Assertions.assertTrue(englishLabelForCPM, "English label should be populated with correct text for CPM"),
                                () -> Assertions.assertTrue(mnemonicChangedFromCPToCPM, "Mnemonic should be changed from CP to CPM"),
                                () -> Assertions.assertTrue(spanTextChangedForCPM, "Span should be changed from Cornerpiece to Table Topic Entry"),
                                () -> Assertions.assertTrue(cornerpieceNameChangedForCPM, "Cornerpiece full-display-name should be changed for CPM"),
                                () -> Assertions.assertTrue(changingCPMMnemonicAllowed, "Changing CPM mnemonic should be allowed")
                        );
        }

        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void prepStructureManipulationNegativeLegalTest()
        {
                //String renditionUUID = "I7D285E801A4611E7A202E150F9096B50"; // for dev
                String renditionUUID = "IFE3CF0B0251F11E7A7A3AB3D10011DB1";

                sourcePage().goToSourcePageWithRenditionUuids(renditionUUID);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                int chunkNumber = 1;
                editorPage().scrollToChunk(chunkNumber);
                String firstChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1);

                String element = firstChunk + EditorTextPageElements.TEXT_PARAGRAPH_PARA +
                                 EditorTextPageElements.mnemonics.SMP.xpath();
                editorPage().scrollToElement(element);
                boolean contentEditorialSystemWindowAppeared = editorTextPage().rightClickMnemonic(element);
                String[] listForSMP = new String[]{"clpp", "hg9", "snl", "xcom"};
                boolean changingSMPMnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForSMP);
                contentEditorialSystemPage().clickCancel();

                element = firstChunk + EditorTextPageElements.mnemonics.SNL.xpath();
                editorPage().scrollToElement(element);
                editorTextPage().rightClickMnemonic(element);
                String[] listForSNL = new String[]{"mp322"};
                boolean changingSNLMnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForSNL);
                contentEditorialSystemPage().clickCancel();

                element = firstChunk + EditorTextPageElements.mnemonics.HG11.xpath();
                editorPage().scrollToElement(element);
                editorTextPage().rightClickMnemonic(element);
                String[] listForHG2 = new String[]{"smp", "clpp"};
                boolean changingHG9MnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForHG2);
                contentEditorialSystemPage().clickCancel();

                element = firstChunk + EditorTextPageElements.mnemonics.CPM.xpath();
                editorPage().scrollToElement(element);
                editorTextPage().rightClickMnemonic(element);
                String[] listForCPM = new String[]{"cp", "cpn"};
                boolean changingCPMMnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForCPM);
                contentEditorialSystemPage().clickCancel();

                element = firstChunk + EditorTextPageElements.mnemonics.SMPF.xpath();
                editorPage().scrollToElement(element);
                editorTextPage().rightClickMnemonic(element);
                String[] listForSMPF = new String[]{"snl"};
                boolean changingSMPFMnemonicAllowed = contentEditorialSystemPage().areMnemonicsInDropdown(listForSMPF);
                contentEditorialSystemPage().clickCancel();

                editorPage().closeDocumentWithNoChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(contentEditorialSystemWindowAppeared, "Content Editorial System Window should appear"),
                                () -> Assertions.assertFalse(changingSMPMnemonicAllowed, "Changing SMP mnemonic should not be allowed"),
                                () -> Assertions.assertFalse(changingSNLMnemonicAllowed, "Changing SNL mnemonic should not be allowed"),
                                () -> Assertions.assertFalse(changingHG9MnemonicAllowed, "Changing HG9 mnemonic should not be allowed"),
                                () -> Assertions.assertFalse(changingSMPFMnemonicAllowed, "Changing SMPF mnemonic should not be allowed"),
                                () -> Assertions.assertFalse(changingCPMMnemonicAllowed, "Changing CPM mnemonic should not be allowed")
                        );
        }

}
