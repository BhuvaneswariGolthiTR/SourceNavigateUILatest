package com.thomsonreuters.codes.codesbench.quality.utilities.database.tools;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.mappings.ContentSetMappings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpellcheckManagerDatabaseUtils
{
    public static String getWordsGroupForWord(String word)
    {
        String query = String.format("SELECT WORDS_GROUP FROM CODES_UAT.spellcheck_words WHERE WORD = '%s'", word);
        BaseDatabaseUtils uatDB = new BaseDatabaseUtils();
        Connection uatConnection = uatDB.connectToDatabaseUAT();
        String wordGroup = null;
        ResultSet resultSet = uatDB.executeQuery(uatConnection, query);
        try
        {
            while (resultSet.next())
            {
                wordGroup = resultSet.getString(1);
                System.out.println(wordGroup);
            }
            resultSet.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        uatDB.disconnect(uatConnection);
        return wordGroup;
    }

    public static void deleteWordFromDatabase(String word, String contentSet)
    {
        String update = String.format("DELETE FROM CODES_UAT.spellcheck_words WHERE WORD = '%s' AND CONTENT_SET = '%s'", word, ContentSetMappings.getCurrentSetMapping(contentSet));
        BaseDatabaseUtils uatDB = new BaseDatabaseUtils();
        Connection uatConnection = uatDB.connectToDatabaseUAT();
        uatDB.executeUpdate(uatConnection, update);
        uatDB.disconnect(uatConnection);
    }
}
