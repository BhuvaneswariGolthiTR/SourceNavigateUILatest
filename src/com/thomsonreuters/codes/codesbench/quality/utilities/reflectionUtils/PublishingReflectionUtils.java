package com.thomsonreuters.codes.codesbench.quality.utilities.reflectionUtils;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Method;
import java.sql.Connection;

public class PublishingReflectionUtils extends TestSetupEdge
{
    public static final String READY_METHOD = "setPublishingNodeToReady";
    public static final String NOT_PUBLISH_METHOD = "setPublishingNodeToNotPublish";
    public static final String CODESBENCH_FAILURE_METHOD = "setPublishingNodeToCodesbenchFailure";
    public static final String LTC_FAILURE_METHOD = "setPublishingNodeToLTCFailure";
    public static final String PUBLISHING_METHOD = "setPublishingNodeToPublishing";
    public static final String APPROVE_METHOD = "setPublishingNodeToApprove";
    public static final String PUBLISH_COMPLETE_METHOD = "setPublishingNodeToPublishComplete";
    public static final String WESTLAW_LOADED_METHOD = "setPublishingNodeToWestlawLoaded";

    //TODO go through each call to this method and change to use the methods directly in the PublishingDatabaseUtils.java class
    public static void setPublishingStatus(String method, String uuid, String contentSet, Connection connection)
    {
        try
        {
            PublishingDatabaseUtils publishingDatabaseUtils = new PublishingDatabaseUtils();
            Class<?> publishingDatabaseUtilsClass = PublishingDatabaseUtils.class;

            Method setPublishingStatus = publishingDatabaseUtilsClass.getDeclaredMethod(method, String.class, String.class, Connection.class);
            Method resetPublishingStatus = publishingDatabaseUtilsClass.getDeclaredMethod("resetPublishingNode", String.class, String.class, Connection.class);

            resetPublishingStatus.invoke(publishingDatabaseUtils, uuid, contentSet, connection);
            setPublishingStatus.invoke(publishingDatabaseUtils, uuid, contentSet, connection);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assertions.fail("Something went wrong when we tried querying to the database");
        }
    }
}