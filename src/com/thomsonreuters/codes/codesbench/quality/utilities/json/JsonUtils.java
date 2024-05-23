package com.thomsonreuters.codes.codesbench.quality.utilities.json;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;

public class JsonUtils
{
    JSONParser parser = new JSONParser();
    Object obj;

    // Returns a value for a given key from the json file mentioned in the path; In this case an sql query
    public String getDataFromJsonConstants(String key)
    {
        try
        {
            obj = parser.parse(new FileReader(System.getProperty("user.dir") + ".\\src\\com\\thomsonreuters\\codes\\codesbench\\quality\\utilities\\json\\SourceDatabaseJsonConstants.json"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }

        return (String)((JSONObject) obj).get(key);
    }
}
