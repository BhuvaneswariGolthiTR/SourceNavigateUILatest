package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SendToVendorForCaptureTests extends TestService
{
    SourceDatapodObject datapodObject ;

    /**
     * STORY: N/A <br>
     * SUMMARY: Tests that send to vendor for capture context menu is disabled for an apvrs document as a legal user<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sendToVendorForCaptureAPVRSLegalTest()
    {
        datapodObject = SourceDataMockingNew.USCA.Small.APVRS.insert();

        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePage();
        sourcePage().waitForGridRefresh();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(String.valueOf(datapodObject.getRenditions().get(0).getDocNumber()));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();

        boolean sendToVendorForCaptureDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewSendToVendorForCapture);
        Assertions.assertTrue(sendToVendorForCaptureDisabled, "Send to vendor for capture is disabled in the context menu.");
    }

    /**
     * STORY: NA <br>
     * SUMMARY: tests that send to vendor for capture is enabled for an APVRS document and the correct alert is given when opening.<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void sendToVendorForCaptureAPVRSRiskTest()
    {
        datapodObject = SourceDataMockingNew.USCA.Small.APVRS.insert();

        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePage();
        sourcePage().waitForGridRefresh();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(String.valueOf(datapodObject.getRenditions().get(0).getDocNumber()));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();

        boolean sendToVendorForCaptureEnabled = !renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewSendToVendorForCapture);
        boolean sendToVendorConfirmationAppeared = renditionContextMenu().openSendToVendorForCapture();
        boolean alertWasCorrect = sendToVendorPage().clickOk();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(sendToVendorForCaptureEnabled, "Send To Vendor for capture is enabled"),
            () -> Assertions.assertTrue(sendToVendorConfirmationAppeared, "Send to Vendor for Capture Confirmation appeared"),
            () -> Assertions.assertTrue(alertWasCorrect, "Send to Vendor for Capture confirmation had the correct text")
        );
    }

    /**
     * STORY: NA <br>
     * SUMMARY: tests that the view -> send to vendor for capture context menu is disabled for a risk user<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void sendToVendorForCaptureNonAPVRSRiskTest()
    {
        datapodObject = SourceDataMockingNew.USCA.Small.PREP.insert();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(String.valueOf(datapodObject.getRenditions().get(0).getDocNumber()));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();

        boolean sendToVendorForCaptureDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.viewSendToVendorForCapture);
        Assertions.assertTrue(sendToVendorForCaptureDisabled, "Send to vendor for capture is disabled in the context menu.");
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}

