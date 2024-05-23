package com.thomsonreuters.codes.codesbench.quality.utilities.database.home;

public class HomeDatabaseConstants
{
    public static final String SET_DEFAULT_TARGET_CONTENT_SET_QUERY = "update user_properties set property_value = ? where property_type = '1' and user_id = ?";
}
