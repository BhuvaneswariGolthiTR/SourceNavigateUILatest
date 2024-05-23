package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.Assertions;

public enum SrcContentType
{

    BILL, LAW, UNCHAPTERED, CRT;

    public static int enumToDatabaseId(SrcContentType contentType)
    {
        switch(contentType.ordinal())
        {
            case(0):
                return 20;
            case(1):
                return 6;
            case(2):
                return 22;
            case(3):
                return 9;
            default:
                TestService.logger.warning("Received default in SrcContentType.enumToDatabaseId");
                return 0;
        }
    }

    public static SrcContentType databaseIdToEnum(int contentTypeId)
    {
        switch(contentTypeId)
        {
            case(20):
                return BILL;
            case(6):
                return LAW;
            case(22):
                return UNCHAPTERED;
            case(0):
                return CRT;
            default:
                Assertions.fail("Attempted to use SrcContentType.databaseIdToEnum for a JS_CONTENT_TYPE_ID that is not known.");
                return null;
        }
    }

}
