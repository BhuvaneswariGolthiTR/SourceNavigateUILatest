package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class SourceDataMockingTests extends TestService
{

    SourceDatapodObject datapodObject;

    @Test
    @CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE
    @CustomAnnotations.UserAnnotations.LEGAL
    @CustomAnnotations.LogAnnotations.LOG
    public void sourceTest()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();

        homePage().goToHomePage();
        loginPage().logIn();

        System.out.println(datapodObject.getRenditions().get(0).getDocNumber());

        DateAndTimeUtils.takeNap(100);
    }


    @Test
    @CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE
    @CustomAnnotations.UserAnnotations.LEGAL
    @CustomAnnotations.LogAnnotations.LOG
    public void sourceTestAPVRS()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APVRS.insert();

        homePage().goToHomePage();
        loginPage().logIn();

        System.out.println(datapodObject.getRenditions().get(0).getDocNumber());

        DateAndTimeUtils.takeNap(100);
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
