package com.thomsonreuters.codes.codesbench.quality.utilities.database.tools;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaxTypeAssignmentDatabaseUtils extends BaseDatabaseUtils
{
    private static List<String> statementStrings = new ArrayList<>();

    // Don't heck up the order or you will crash the database
    static
    {
        statementStrings.add("UPDATE CWB_DELTA_PROC_PROPS SET TAX_TYPES=NULL WHERE CWB_DELTA_UUID IN (SELECT CWB_SECTION_CHILD_UUID FROM CWB_SECTION_CHILD WHERE CWB_SECTION_UUID in (SELECT CWB_SRC_RENDITION_CHILDUUID FROM CWB_SRC_RENDITION_CHILD WHERE CWB_SRC_RENDITION_UUID = ?) AND CHILD_TYPE = 'DeltaImpl')");
        statementStrings.add("UPDATE CWB_SECTION_PROC_PROPS SET TAX_TYPES = NULL WHERE CWB_SECTION_UUID in (SELECT CWB_SRC_RENDITION_CHILDUUID FROM CWB_SRC_RENDITION_CHILD WHERE CWB_SRC_RENDITION_UUID = ?)");
        statementStrings.add("UPDATE CWB_SRC_REND_PROC_PROPS SET TAX_TYPES = NULL WHERE CWB_SRC_RENDITION_UUID = ?");
    }

    public static void deleteTaxTypeAssignmentFromRenditionAndItsSectionsAndDeltas(Connection connection, String renditionUUID)
    {

        logger.information(String.format("Deleting Tax Type Assignment for Rendition with UUID %s and for all its Sections and Deltas", renditionUUID));
        for (String statement : statementStrings)
        {
            try
            {
                PreparedStatement deleteTaxTypeAssignmentStatement = connection.prepareStatement(statement);
                deleteTaxTypeAssignmentStatement.setString(1, renditionUUID);

                deleteTaxTypeAssignmentStatement.executeUpdate();
                deleteTaxTypeAssignmentStatement.close();

            } catch (SQLException e)
            {
                Assertions.fail(String.format("Deleting Tax Type Assignment for Rendition with UUID %s and for all its Sections and Deltas, failed: %s", renditionUUID, e.toString()));
            }
        }

    }
}
