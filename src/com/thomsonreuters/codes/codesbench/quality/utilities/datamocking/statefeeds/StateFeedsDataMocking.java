package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.statefeeds;

import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.landingStrips.LandingStripsFTPClient;
import org.junit.jupiter.api.Assertions;

import java.io.File;

public class StateFeedsDataMocking
{
//    private static final String FTP_PATH_TO_STATE_FEED = "/apps/Codes/MP2/LandingStrips/Publishing/CustomPubExtract/StateFeed/SOS.IAT/";
    private static final String FTP_PATH_TO_STATE_FEED = "/apps/Codes/MP2/LandingStrips/Publishing/CustomPubExtract/StateFeed/SOS.SCT/";
    private static final String COMMON_FILES_PATH = "commonFiles\\TestFiles\\StateFeed\\";
    private static final String TITLE_4 = "TITLE 4 - ";
    private static final String TITLE_4_ALL_DATA = TITLE_4 +  "All Data.xml";
    private static final String TITLE_4_TEXT_CREDIT_HISTORICAL = TITLE_4 + "Text Credit Historical.xml";
    private static final String TITLE_4_TEXT_CREDIT_ONLY = TITLE_4 + "Text Credit Only.xml";

    public static void deleteGroup(String groupName)
    {
        LandingStripsFTPClient client = new LandingStripsFTPClient();
        client.connectToServer();
        String year = DateAndTimeUtils.getCurrentYearyyyy();

        client.connectToServer();
        client.deleteDirectory(FTP_PATH_TO_STATE_FEED + year + "/" + groupName);
        client.disconnectFromServer();
    }

    public static String insertTestFilesIntoStateFeed()
    {
        String groupName = "State Feed Extract " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        LandingStripsFTPClient client = new LandingStripsFTPClient();
        String year = DateAndTimeUtils.getCurrentYearyyyy();
        String path = FTP_PATH_TO_STATE_FEED + year;

        client.connectToServer();
        client.changeWorkingDirectory(FTP_PATH_TO_STATE_FEED + year);
        client.makeDirectory(groupName);
        client.changeWorkingDirectory(path + "/" + groupName);
        client.upload(COMMON_FILES_PATH + TITLE_4_ALL_DATA, TITLE_4_ALL_DATA);
        client.upload(COMMON_FILES_PATH + TITLE_4_TEXT_CREDIT_HISTORICAL, TITLE_4_TEXT_CREDIT_HISTORICAL);
        client.upload(COMMON_FILES_PATH + TITLE_4_TEXT_CREDIT_ONLY, TITLE_4_TEXT_CREDIT_ONLY);
        client.disconnectFromServer();
        return groupName;
    }
}
