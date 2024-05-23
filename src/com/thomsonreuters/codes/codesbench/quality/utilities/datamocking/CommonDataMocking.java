package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static com.thomsonreuters.codes.codesbench.quality.utilities.TestService.logger;

public abstract class CommonDataMocking
{
    // Method generates a unique identifier that can be used as node uuids, content uuids, etc.
    public static String generateUUID() {
        UUID randomUUUID = UUID.randomUUID();
        String middlePartialUUID = randomUUUID.toString().replace("-", "").toUpperCase().substring(11);
        String uuid = String.format("%s%s%s", "IABCDEF", middlePartialUUID, "A0001");
        Assertions.assertEquals(33, uuid.length(), "The UUID length is not 33.");
        return uuid;
    }

    public static Connection connectToDatabase(String environment)
    {
        Connection connection;

        switch (environment)
        {
            case "uat":
            case "UAT":
                connection = BaseDatabaseUtils.connectToDatabaseUAT();
                break;
            case "dev":
            case "DEV":
                connection = BaseDatabaseUtils.connectToDatabaseDEV();
                break;
            default:
                connection = null;
        }
        return connection;
    }

    public static Clob clobify(Connection connection, String text)
    {
        try
        {
            logger.information("Creating clob...");
            String partOfText = text.substring(0, 100);
            logger.information("Text (first 100 chars): " + partOfText);
            Clob clob = connection.createClob();
            clob.setString(1, text);
            return clob;
        }
        catch (SQLException e)
        {
            Assertions.fail("Failed to create Clob");
        }
        return null;
    }

}