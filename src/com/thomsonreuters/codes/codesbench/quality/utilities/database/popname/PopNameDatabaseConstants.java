package com.thomsonreuters.codes.codesbench.quality.utilities.database.popname;

public class PopNameDatabaseConstants
{
	public static final String TOTAL_COUNT_OF_POPNAME_WITH_LETTER_AND_DATE_QUERY = "select count(*) from POP_NAME_AUTH  where " +
			"POP_NAME_AUTH.MODIFIED_BY_DATE <= sysdate and POP_NAME_AUTH.PRIM_NAME_NORM LIKE ? and PRIM_NAME_NODE_UUID = ?";
	/*"SELECT SUM(INDIVIDUAL_COUNTS) FROM "
			+ "(SELECT COUNT(*) AS INDIVIDUAL_COUNTS FROM POP_NAME_AUTH WHERE PRIM_NAME_NORM LIKE ? AND MODIFIED_BY_DATE > TO_DATE(?, 'mm/dd/yyyy hh24:mi:ss') UNION ALL SELECT COUNT(*) AS INDIVIDUAL_COUNTS FROM POP_NAME_AUTH WHERE POP_NAME_AUTH_UUID IN "
			+ "(SELECT POP_NAME_AUTH_UUID FROM POP_NAME_AUTH_ALT_NAME WHERE POP_NAME_NORM LIKE ? and PRIM_NAME_NODE_UUID= ?))";*/
	public static final String NUMBER_OF_DELETED_POPNAME_NODES_WITH_LETTER_QUERY = "SELECT DISTINCT IS_DELETED FROM POP_NAME_AUTH WHERE PRIM_NAME_NORM LIKE ?";

	public static final String CHECK_IF_NODE_IS_DELETED_QUERY = "SELECT IS_DELETED FROM POP_NAME_AUTH WHERE PRIM_NAME_DESC = ?";
	public static final String CHECK_IF_NODE_IS_DELETED_RELATIONSHIP_QUERY = "SELECT IS_DELETED FROM POP_NAME_RELATIONSHIP WHERE POP_NAME_AUTH_UUID IN(SELECT POP_NAME_AUTH_UUID FROM POP_NAME_AUTH WHERE PRIM_NAME_DESC = ?)";// Qualified

	public static final String GET_UUID_FROM_POPNAME_QUERY = "select relationship_uuid  from pop_name_relationship "
			+ "where (pop_name_auth_uuid in (select pop_name_auth_uuid from pop_name_auth where prim_name_desc = :popName) or "
			+ "pop_name_auth_uuid in (select pop_name_auth_uuid from pop_name_auth_alt_name where pop_name_desc = :popName)) and PUBLIC_LAW_SECTION = 'ROOT'";	
	public static final String GET_TOTAL_POPNAME_COUNT = "select count(*)  from POP_NAME_AUTH  auth, POP_NAME_RELATIONSHIP rel where auth.POP_NAME_AUTH_UUID = rel.POP_NAME_AUTH_UUID and rel.PUBLIC_LAW_SECTION = ? and auth.IS_DELETED = 0";
	public static final String GET_RELATIONSHIP_UUID_FROM_GIVEN_POPNAME = "select relationship_uuid  from pop_name_relationship "
			+ "where (pop_name_auth_uuid in (select pop_name_auth_uuid from pop_name_auth where prim_name_desc = ?) or "
			+ "pop_name_auth_uuid in (select pop_name_auth_uuid from pop_name_auth_alt_name where pop_name_desc = ?)) and PUBLIC_LAW_SECTION = ?";

	public static final String UPDATE_DELETE_FLAG_TO_BE_DELETED = "update toc_node set IS_DELETED = ? where NODE_UUID = ? and js_content_set_id = ?";
}
