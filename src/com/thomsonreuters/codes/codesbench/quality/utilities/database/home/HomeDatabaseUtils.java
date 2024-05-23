package com.thomsonreuters.codes.codesbench.quality.utilities.database.home;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HomeDatabaseUtils extends BaseDatabaseUtils
{

    public static void setDefaultTargetContentSet(Connection connection, String contentSetValue, String username)
    {
        logger.information(String.format("Changing the default content set to %s as %s user", contentSetValue, username));

        try
        {
            PreparedStatement setContentSetPreparedStatement = connection.prepareStatement(HomeDatabaseConstants.SET_DEFAULT_TARGET_CONTENT_SET_QUERY);
            setContentSetPreparedStatement.setString(1, contentSetValue);
            setContentSetPreparedStatement.setString(2, username);

            setContentSetPreparedStatement.executeUpdate();
            setContentSetPreparedStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Changing the default content set to %s failed: %s", contentSetValue, e.toString()));
        }
    }

}
