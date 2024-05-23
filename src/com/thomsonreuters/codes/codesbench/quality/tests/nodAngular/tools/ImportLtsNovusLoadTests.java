package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.HomePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.ImportLtcNovusLoadFilePopupsElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.landingStrips.LandingStripsFTPClient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImportLtsNovusLoadTests   extends TestService
{

    /**
     * HALCYONST-8326
     * 1. Copy example file
     * 2. Open it and change value of field md.cmsid to something like "1234567"
     * 3. Open it and change value of field md.ccserial to someting like "1234567890"
     * 4. Open it and change value of field  md.jurisnum to 5490 (you can see courts-jurisdiction mapping in HALCYONST-8326 in the attached table)
     * 5. Save changes and copy that file to mag2ftp@C116KPCWB1.int.thomsonreuters.com/apps/Codes/MP2/LandingStrips/NOD/NovusCaseLoadCanada/
     * 6. Open NOD Home page for Canada Ontario content set
     * 7. Click on "Tools" - "Import LTS/Novus Load" entry
     * 8. VERIFY: "Import LTS/Novus Load" pop up opened
     * 9. Find the file in right pane (under "Canada"), select it and click Load Canada Files
     * 10. VERIFY: After a reasonable time the file is processeed a message about success is shown
     * 11. Open Subscribed Cases page for content set 3001 (Canada Federal)
     * 12. Filter data by CCDB column by value in step 2
     * 13. VERIFY: a case with CCDB from step 2 exists
     * 14. Open Subscribed Cases page for content set 3012 (Canada Ontario)
     * 15. VERIFY: a case with CCDB from step 2 exists
     */
    @Test
    @EDGE
    @CARSWELL
    @LOG
    public void courtsMappingTest() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String source = "commonFiles\\TestFiles\\NodAngular\\testTemplate.xml";
        String target = "/apps/Codes/MP2/LandingStrips/NOD/NovusCaseLoadsCanada/";
        String fileName = "courtsMappingTest.xml";
        String cmsidField = "md.cmsid";
        String newCmsid = Integer.toString((int)(Math.random()*10000000));
        String ccserialField = "md.ccserial";
        String newCcserial = (int) (Math.random() * 10000000) + Integer.toString((int)(Math.random()*1000));
        String expectedMessageText = "Canada process completed!";

        File file = new File(source);
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = dBuilder.parse(file);

        setValueInXml(xml, cmsidField, newCmsid);
        setValueInXml(xml,ccserialField, newCcserial);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File(source));
        Source input = new DOMSource(xml);
        transformer.transform(input,output);

        LandingStripsFTPClient lsFtpClient = new LandingStripsFTPClient();
        lsFtpClient.connectToServer();
        assertTrue(lsFtpClient.checkDirectoryExistence(target),"VERIFY: the output directory exists.");
        lsFtpClient.upload(source, target+fileName);
        lsFtpClient.disconnectFromServer();

        homePageAngular().openNodHomePage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        loginPage().logIn();
        homePageAngular().waitForElement(String.format(HomePageElementsAngular.CONTENT_SET_PLACEHOLDER, ContentSets.CANADA_ONTARIO_DEVELOPMENT.getName()));

        homePageAngular().clickImportLtcNovusLoad();
        assertTrue(importLtcNovusLoadFilePopupsAngular().doesElementExist(ImportLtcNovusLoadFilePopupsElementsAngular.PAGE_TITLE), "VERIFY: Import LTC/Novus Load pop-up is open");
        importLtcNovusLoadFilePopupsAngular().selectFileByName(fileName);
        importLtcNovusLoadFilePopupsAngular().clickLoadCanadaFiles();
        importLtcNovusLoadFilePopupsAngular().clickClose();

        homePageAngular().switchHomePageAngular();
        String actualMessageText = homePageAngular().getTextFromMessageAndCloseIt();

        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        assertTrue(subscribedCasesPageAngular().isPageOpened(), "VERIFY: Subscribed Cases page is open");
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.CCDB);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, newCmsid);
        subscribedCasesTablePage().waitForTableToReload();
        boolean isCaseExistCanadaOntario = !subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.CCDB).isEmpty();

        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.CANADA_FEDERAL_DEVELOPMENT);
        assertTrue(subscribedCasesPageAngular().isPageOpened(), "VERIFY: Subscribed Cases page is open");
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.CCDB);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, newCmsid);
        subscribedCasesTablePage().waitForTableToReload();
        boolean isCaseExistCanadaFederal = !subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.CCDB).isEmpty();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedMessageText, actualMessageText,String.format("VERIFY: the message text is '%s'.",expectedMessageText)),
                        () -> Assertions.assertTrue(isCaseExistCanadaOntario, "VERIFY: a case with CCDB %s exists for Canada Ontario (Development)"),
                        () -> Assertions.assertTrue(isCaseExistCanadaFederal, "VERIFY: a case with CCDB %s exists for Canada Federal (Development)")
                );
    }

    /**
     * HALCYONST-8381
     * 1. Copy example file
     * 2. Open it and change value of field md.cmsid to something like "1234567"
     * 3. Open it and change value of field md.ccserial to someting like "1234567890"
     * 4. Locate headnote/paratext/cite.query node and set it to "I61eb5c52f4db11d99f28ffa0ae8c2000"
     * 5. Remember the cite.query text
     * 6. Save changes
     * 7. Copy that file to mag2ftp@C116KPCWB1.int.thomsonreuters.com/apps/Codes/MP2/LandingStrips/NOD/NovusCaseLoadCanada/
     * 8. Open NOD Home page for Canada Ontario content set
     * 9. Click on "Tools" - "Import LTS/Novus Load" entry
     * 10. VERIFY: "Import LTS/Novus Load" pop up opened
     * 11. Find the file in right pane (under "Canada"), select it and click Load Canada Files
     * 12. VERIFY: After a reasonable time the file is processeed a message about success is shown
     * 13. Open Subscribed Cases page for content set 3001 (Canada Federal)
     * 14. Filter data by CCDB column by value in step 2
     * 15.VERIFY: a case with CCDB from step 2 exists
     * 16. Click on CCDB hyperlink
     * 16. VERIFY: Classify\headnotes page is opened
     * 17. VERIFY: 2 headnotes are present and classified an each has cite.query text from step 5 highlighted
     */
    @Test
    @EDGE
    @CARSWELL
    @LOG
    public void highlightingTest() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String source = "commonFiles\\TestFiles\\NodAngular\\testTemplate.xml";
        String target = "/apps/Codes/MP2/LandingStrips/NOD/NovusCaseLoadsCanada/";
        String fileName = "highlightingTest.xml";
        String cmsidField = "md.cmsid";
        String newCmsid = Integer.toString((int)(Math.random()*10000000));
        String ccserialField = "md.ccserial";
        String newCcserial = (int) (Math.random() * 10000000) + Integer.toString((int)(Math.random()*1000));
        String expectedMessageText = "Canada process completed!";
        String expectedHighlighting = "Insurance Act, R.S.O. 1990, c. I.8";

        File file = new File(source);
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = dBuilder.parse(file);

        setValueInXml(xml, cmsidField, newCmsid);
        setValueInXml(xml,ccserialField, newCcserial);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File(source));
        Source input = new DOMSource(xml);
        transformer.transform(input,output);

        LandingStripsFTPClient lsFtpClient = new LandingStripsFTPClient();
        lsFtpClient.connectToServer();
        assertTrue(lsFtpClient.checkDirectoryExistence(target),"VERIFY: the output directory exists.");
        lsFtpClient.upload(source, target+fileName);
        lsFtpClient.disconnectFromServer();

        homePageAngular().openNodHomePage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        loginPage().logIn();
        homePageAngular().waitForElement(String.format(HomePageElementsAngular.CONTENT_SET_PLACEHOLDER, ContentSets.CANADA_ONTARIO_DEVELOPMENT.getName()));

        homePageAngular().clickImportLtcNovusLoad();
        assertTrue(importLtcNovusLoadFilePopupsAngular().doesElementExist(ImportLtcNovusLoadFilePopupsElementsAngular.PAGE_TITLE), "VERIFY: Import LTC/Novus Load pop-up is open");
        importLtcNovusLoadFilePopupsAngular().selectFileByName(fileName);
        importLtcNovusLoadFilePopupsAngular().clickLoadCanadaFiles();
        importLtcNovusLoadFilePopupsAngular().clickClose();

        homePageAngular().switchHomePageAngular();
        String actualMessageText = homePageAngular().getTextFromMessageAndCloseIt();

        homePageAngular().clickSubscribedCases();
        subscribedCasesTablePage().openHeadnotesPageByCCDB(newCmsid);
        assertTrue(headnotesPageAngular().isPageOpened(), "VERIFY: Headnote page is open");

        String actualHighlighting = headnotesPageAngular().getHighlightingHeadnoteText();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedMessageText, actualMessageText,String.format("VERIFY: the message text is '%s'.",expectedMessageText)),
                        () -> Assertions.assertEquals(expectedHighlighting, actualHighlighting,"VERIFY: the cite query text is highlighted")
                );
    }

    /**
     * HALCYONST-10489
     * 1. Log onto Canada Ontario (Development) content set
     * 2. Go to NOD -> Subscribed Cases - angular, then click "Home" in the header on the top of the page, and then click "Import LTC/Novus Load" from the Tools section
     * 3. VERIFY: Import LTC/Novus Load window opened
     * 4.Popup has import options for Canada
     * 5. Collapsable lists under United States and Canada are displayed in alphabetical order
     * 6. List of XMLs from /apps/Codes/MP2/Landing Strip/NOD/NovusCaseLoadsCanada is displayed below it
     * 8. 'Load Canada files' button is disabled
     * 9. Select three random XML files from the list for Canada
     * 10. 'Load Canada files' button becomes active
     * 11. Click on the 'Load Canada files' button
     * 12. VERIFY: 'Canada Process completed!" message is shown
     * 13. Imported XML file is no longer displayed in the list of XMLs
     * 14. 'Load Canada files' button becomes disabled
     * 15. Close ‘NOD Novus Load File Import’ popup
     * 16. access NOD Cases page for a content set using direct link
     * 17. Select ‘All Cases’ Content Team
     * 18. Sort by Loaded Date in descending (highest first) order
     * 19. For today's date there are cases with the same properties as loaded XML files
     */
    @Test
    @EDGE
    @CARSWELL
    @LOG
    public void multipleFilesTest() throws ParserConfigurationException, TransformerException, IOException, SAXException {
        String source = "commonFiles\\TestFiles\\NodAngular\\testTemplate.xml";
        String target = "/apps/Codes/MP2/LandingStrips/NOD/NovusCaseLoadsCanada/";
        String fileName = "multipleFilesTest.xml";
        String cmsidField = "md.cmsid";
        String newCmsid = Integer.toString((int)(Math.random()*10000000));
        String ccserialField = "md.ccserial";
        String newCcserial = (int) (Math.random() * 10000000) + Integer.toString((int)(Math.random()*1000));
        String expectedMessageText = "Canada process completed!";

        File file = new File(source);
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = dBuilder.parse(file);

        setValueInXml(xml, cmsidField, newCmsid);
        setValueInXml(xml,ccserialField, newCcserial);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File(source));
        Source input = new DOMSource(xml);
        transformer.transform(input,output);

        LandingStripsFTPClient lsFtpClient = new LandingStripsFTPClient();
        lsFtpClient.connectToServer();
        assertTrue(lsFtpClient.checkDirectoryExistence(target),"VERIFY: the output directory exists.");
        lsFtpClient.upload(source, target+fileName);
        lsFtpClient.disconnectFromServer();

        homePageAngular().openNodHomePage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        loginPage().logIn();
        homePageAngular().waitForElement(String.format(HomePageElementsAngular.CONTENT_SET_PLACEHOLDER, ContentSets.CANADA_ONTARIO_DEVELOPMENT.getName()));

        homePageAngular().clickImportLtcNovusLoad();
        assertTrue(importLtcNovusLoadFilePopupsAngular().doesElementExist(ImportLtcNovusLoadFilePopupsElementsAngular.PAGE_TITLE), "VERIFY: Import LTC/Novus Load pop-up is open");
        importLtcNovusLoadFilePopupsAngular().selectFileByName(fileName);
        importLtcNovusLoadFilePopupsAngular().clickLoadCanadaFiles();
        importLtcNovusLoadFilePopupsAngular().clickClose();

        homePageAngular().switchHomePageAngular();
        String actualMessageText = homePageAngular().getTextFromMessageAndCloseIt();

        homePageAngular().clickCases();
        casesPageAngular().selectContentSetTeam("All Cases");



        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedMessageText, actualMessageText,String.format("VERIFY: the message text is '%s'.",expectedMessageText))
                );
    }

    
    private void setValueInXml(Document xml, String field, String value){
        Node cmsid= xml.getElementsByTagName(field).item(0);
        cmsid.setTextContent(value);
    }

}
