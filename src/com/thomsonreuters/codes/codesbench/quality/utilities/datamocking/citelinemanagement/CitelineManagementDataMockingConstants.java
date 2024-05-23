package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.citelinemanagement;

public class CitelineManagementDataMockingConstants
{
    public static final String  INSERT_INTO_PUB_STABLE_TABLE = "insert into pub_stable_table(js_jurisdiction_id, vols_ct1_num, record_type, citation_text, is_versioned, uuid, update_num, Content_set_name) values (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String DELETE_FROM_PUB_STABLE_TABLE = "delete from pub_stable_table where vols_ct1_num=?";

    public static final String INSERT_INTO_PUB_LEGACY_NORM_TBL = "insert into pub_legacy_norm_tbl(uuid, position, citation_prefix,condensed_prefix, js_jurisdiction_id,content_set_name) values (?, ?, ?, ?, ?, ?)";

    public static final String DELETE_FROM_PUB_LEGACY_NORM_TBL = "delete from pub_legacy_norm_tbl where CITATION_PREFIX=? and CONTENT_SET_NAME=?";
}
