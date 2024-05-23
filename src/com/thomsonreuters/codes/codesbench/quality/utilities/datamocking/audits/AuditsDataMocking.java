package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits.AuditsDataMockingConstants.*;

/**
 * This class will be a general tools class for mocking up different nodes to be used in an audit
 * It will include all different types of mockup using HierarchyDatabaseUtils to complete
 * The following is a key for the hierarchy function table
 * 1	Repeal
 * 2	Transfer
 * 3	Amend
 * 4	Insert
 * 5	Update
 * 6	Forced
 * 7	Delete
 * 8	Undelete
 * 9	Reserve
 * 10	Move
 * 11	Clone
 * 12	Deep Clone
 * 13	Add To Range
 * 14	Remove From Range
 * 15	Reorder Children
 * 16	Add Child
 * 17	Move Child
 * 18	Remove Child
 * 19	Copy
 * 20	Reuse
 *
 * This class will do many different mockups as reflected in the manual testing document. Currently the only one is the add markup mockup method
 */
public class AuditsDataMocking
{

    public static HierarchyDatapodObject datapodObject;
//----------------------------------------------------------------------------------------------------------------//
//-------------------------------------------------MOCKUP-METHODS-------------------------------------------------//
//----------------------------------------------------------------------------------------------------------------//


    public static HierarchyDatapodObject mockUpForHeaderAndBookmarkTests(Connection connection, String userName)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion  = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(connection, contentUUID) + 1;
        HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion,userName, textForHeaderTests, newHeadText,
                106, connection);
        long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

        HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUUID, DateAndTimeUtils.getCurrentDateddMMMYYYY(), connection);
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,1,1,0,0, iowaCode, connection);

        auditId++;
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,0,1,0,0, iowaCode, connection);
        return datapodObject;
    }

    public static HierarchyDatapodObject mockUpForContentCompareTests(Connection connection, String userName)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion  = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(connection, contentUUID) + 1;
        HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion,userName, textForContentCompareTests, newHeadText,
                106, connection);
        long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

        HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUUID, DateAndTimeUtils.getCurrentDateddMMMYYYY(), connection);
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,1,1,0,0, iowaCode, connection);

        auditId++;
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,0,1,0,0, iowaCode, connection);
        return datapodObject;
    }

    public static HierarchyDatapodObject mockUpForContentCompareMiddleColumnTest(Connection connection, String userName)
    {
        datapodObject = TargetDataMockingNew.Iowa.Medium.insert();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion  = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(connection, contentUUID) + 1;
        HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion,userName, textForContentCompareMiddleColumnTest, newHeadText,
                106, connection);
        long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

        HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUUID, DateAndTimeUtils.getCurrentDateddMMMYYYY(), connection);
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,1,1,0,0, iowaCode, connection);

        auditId++;
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,0,1,0,0, iowaCode, connection);
        return datapodObject;
    }

    public static HierarchyDatapodObject mockUpForValidationFlagTests(Connection connection, String userName, String validationFlagNeeded)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(connection, contentUUID) + 1;
        if (validationFlagNeeded.equals("DTD Validation Error"))
        {
            HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion, userName, AuditsDataMockingConstants.textForDTDDataErrors, newHeadText,
                    106, connection);
        }
        else
        {
            HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion, userName, textForHeaderTests, newHeadText,
                    106, connection);
        }
        long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

        HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUUID, DateAndTimeUtils.getCurrentDateddMMMYYYY(), connection);
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName, 5, 0, 0, 0,
                0, 1, 1, 0, 0, iowaCode, connection);

        auditId++;
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName, 5, 0, 0, 0,
                0, 0, 1, 0, 0, iowaCode, connection);
        return datapodObject;
    }

    public static HierarchyDatapodObject mockUpForFootnoteDeleteTests(Connection connection, String userName)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion  = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(connection, contentUUID) + 1;
        HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion,userName, textForFootnoteDeleteTest, newHeadText,
                106, connection);
        newWipVersion++;
        HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion,userName, textForHeaderTests, newHeadText,
                106, connection);
        long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

        HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUUID, DateAndTimeUtils.getCurrentDateddMMMYYYY(), connection);
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,1,1,0,0, iowaCode, connection);

        auditId++;
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,0,1,0,0, iowaCode, connection);
        return datapodObject;
    }

    public static HierarchyDatapodObject mockUpForMarkupsTest(Connection connection, String userName)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion  = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(connection, contentUUID) + 1;
        HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion,userName, textForMarkupsTest, newHeadText,
                106, connection);
        long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

        HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUUID, DateAndTimeUtils.getCurrentDateddMMMYYYY(), connection);
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,1,1,0,0, iowaCode, connection);

        auditId++;
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,0,1,0,0, iowaCode, connection);
        return datapodObject;
    }

    public static HierarchyDatapodObject mockUpForCTabTest(Connection connection, String userName)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion  = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(connection, contentUUID) + 1;
        HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion,userName, textForCtabTest, newHeadText,
                106, connection);
        long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

        HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUUID, DateAndTimeUtils.getCurrentDateddMMMYYYY(), connection);
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName, 5, 0, 0, 0,
                0, 1, 1, 0, 0, iowaCode, connection);

        auditId++;
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName, 5, 0, 0, 0,
                0, 0, 1, 0, 0, iowaCode, connection);
        return datapodObject;
    }

    public static HierarchyDatapodObject mockUpForNODReportTests(Connection connection, String userName)
    {
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        String contentUUID = datapodObject.getSections().get(0).getContentUUID();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion  = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(connection, contentUUID) + 1;
        HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion,userName, textForHeaderTests, newHeadText,
                106, connection);
        long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

        HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUUID, DateAndTimeUtils.getCurrentDateddMMMYYYY(), connection);
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,1,1,0,0, iowaCode, connection);

        auditId++;
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName,5,0,0,0,
                0,0,1,0,0, iowaCode, connection);
        return datapodObject;
    }

    public static HierarchyDatapodObject mockUpForTaxTypeReportTests(Connection connection, String userName, String taxType)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        int iowaCode = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
        String materialUuid = CommonDataMocking.generateUUID();
        String jobId = CommonDataMocking.generateUUID();
        int newWipVersion  = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(connection, contentUUID) + 1;
        HierarchyDatabaseUtils.insertNewWipVersionInTocContent(contentUUID, newWipVersion,userName, textForHeaderTests, newHeadText,
                106, connection);
        long auditId = HierarchyDatabaseUtils.getMaxRowInTocNodeAudit(connection) + 1;

        HierarchyDatabaseUtils.setLatestWipVersionToNotLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUUID, connection);
        HierarchyDatabaseUtils.updateModifiedDate(contentUUID, DateAndTimeUtils.getCurrentDateddMMMYYYY(), connection);
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName, 5, 0, 0, 0,
                 0, 1, 1, 0, 0, iowaCode, connection);

        auditId++;
        HierarchyDatabaseUtils.insertEntryIntoTocNodeAudit(auditId, nodeUUID, contentUUID,
                materialUuid, newWipVersion, jobId, userName, 5, 0, 0, 0,
                 0, 0, 1, 0, 0, iowaCode, connection);
        int scriptIDForTaxType = 11343998;
        int scriptIDForProductTagTEST = 11331998;
        Assertions.assertTrue(ScriptMaintenanceDatabaseUtils.doesScriptExist(connection, scriptIDForTaxType), "Script ID for tax Type does not exist, ID:" + scriptIDForTaxType);
        Assertions.assertTrue(ScriptMaintenanceDatabaseUtils.doesScriptExist(connection, scriptIDForProductTagTEST), "Script ID for Product Tag does not exist, ID:" + scriptIDForProductTagTEST);
        switch(taxType)
        {
            case "Tax Type and Product Tag":
                ScriptMaintenanceDatabaseUtils.insertEntryIntoTocNodeScriptAssignment(connection, nodeUUID, scriptIDForProductTagTEST, iowaCode);
                ScriptMaintenanceDatabaseUtils.insertEntryIntoTocNodeScriptAssignment(connection, nodeUUID, scriptIDForTaxType, iowaCode);
                break;
            case "Tax Type":
                ScriptMaintenanceDatabaseUtils.insertEntryIntoTocNodeScriptAssignment(connection, nodeUUID, scriptIDForTaxType, iowaCode);
                break;
            case "Product Tag":
                ScriptMaintenanceDatabaseUtils.insertEntryIntoTocNodeScriptAssignment(connection, nodeUUID, scriptIDForProductTagTEST, iowaCode);
                break;
        }
        return datapodObject;
    }


    /*
     TODO:
     - Build out functionality of this class thoroughly for data mockups
     - Cover all different types of mockups needed for an audit
     - Will need to use the XML not the UI to do this
     - Lots more methods to write
     */
//----------------------------------------------------------------------------------------------------------------//
//-------------------------------------------------HELPER-METHODS-------------------------------------------------//
//----------------------------------------------------------------------------------------------------------------//

    public static String getNodeUUID(int index)
    {
        return datapodObject.getSections().get(index-1).getNodeUUID();
    }




    //Call at the end of each test in aftereach
    public static void deleteDatapod(Connection connection)
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
        if (connection != null)
        {
            disconnect(connection);
        }
    }


}
