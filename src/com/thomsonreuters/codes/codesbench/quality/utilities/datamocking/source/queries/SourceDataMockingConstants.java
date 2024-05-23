package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.queries;

public class SourceDataMockingConstants
{

    public static final String INSERT_INTO_SRC_LINEAGE = "insert into cwb_src_lineage (cwb_src_lineage_uuid, js_jurisdiction_id, js_content_set_id, js_doc_type_id, doc_number, js_class_name_id, class_number, correlation_id) " +
            "values (?, 28, ?, 44, ?, 1, 2, ?)";

    public static final String INSERT_INTO_SRC_RENDITION = "insert into cwb_src_rendition (cwb_src_rendition_uuid, cwb_src_lineage_uuid, year, cwb_rendition_status_id, src_version_date, document_uuid, serial_number, bill_id, js_content_type_id, legislation_type_id, js_session_id, js_doc_type_id, doc_number) " +
            "values (?, ?, ?, ?, sysdate, ?, ?, ?, ?, 2, 4, 44, ?)";

    public static final String INSERT_INTO_SRC_REND_PROC_PROPS = "insert into cwb_src_rend_proc_props (cwb_src_rend_proc_props_uuid, cwb_src_rendition_uuid, delta_count, difficulty_level," +
            " duplicate_flag, multiple_flag, source_load_date, requires_verification_flag, verified_flag, deleted_flag, manual_load_flag, manual_load_by, has_error_flag, has_warning_flag, has_validation_flag, " +
            "source_file_size, contains_topical_heading, contains_legislative_highlight)" +
            " values (?, ?, 0, 0, 0, 0, sysdate, 0, 0, 0, 0, ?, 0, 0, 0, ?, 0, 0)";

    //This inserts a section
    public static final String INSERT_INTO_SRC_RENDITION_CHILD = "insert into cwb_src_rendition_child (cwb_src_rendition_childuuid, cwb_src_rendition_uuid, child_type, sibling_order, section_name, section_number, delta_count, generated_flag)" +
            "values (?, ?, 'SectionImpl', ?, ?, ?, 0, 0)";

    public static final String INSERT_INTO_SECTION_PROC_PROPS = "insert into cwb_section_proc_props (cwb_section_proc_props_uuid, cwb_section_uuid, delta_count)" +
            "values (?, ?, 0)";

    public static final String INSERT_INTO_SECTION_CHILD_PARAGRAPH = "insert into cwb_section_child (cwb_section_child_uuid, cwb_section_uuid, child_type, sibling_order, text, generated_flag)" +
            "values (?, ?, 'SectionParagraphImpl', ?, ?, 0)";
    //Inserts a delta
    public static final String INSERT_INTO_SECTION_CHILD_DELTA = "insert into cwb_section_child (cwb_section_child_uuid, cwb_section_uuid, child_type, sibling_order, delta_action_id, delta_level_id)" +
            "values (?, ?, 'DeltaImpl', ?, 2, 3)";

    public static final String INSERT_INTO_DELTA_PROC_PROPS = "insert into cwb_delta_proc_props (cwb_delta_proc_props_uuid, cwb_delta_uuid)" +
            "values (?, ?)";

    public static final String INSERT_INTO_DELTA_CHILD = "insert into cwb_delta_child (cwb_delta_child_uuid, cwb_delta_uuid, child_type, sibling_order, text, generated_flag)" +
            "values (?, ?, 'DeltaParagraphImpl', ?, ?, 0)";

    public static final String INSERT_INTO_TARGET_LOCATION = "insert into cwb_target_location (cwb_target_location_uuid, cwb_delta_uuid, target_code, target_section, text_merge_status_id, flag_to_display)" +
            "values (?, ?, '18 USCA', '18 &sect; 1', 13, 2)";

    public static final String INSERT_ORIGINAL_INTO_SRC_RENDITION_BASELINE = "insert into cwb_src_rendition_baseline (cwb_src_renditionbaseline_uuid, cwb_src_rendition_uuid, creation_date, created_by, text, sibling_order, creation_process, modified_date)" +
            "values (?, ?, sysdate, 'Source Input', ?, ?, 'Initial Load', current_timestamp)";

    public static final String INSERT_INTO_SRC_RENDITION_BASELINE = "insert into cwb_src_rendition_baseline (cwb_src_renditionbaseline_uuid, cwb_src_rendition_uuid, creation_date, created_by, description, text, sibling_order, creation_process, modified_date)" +
            "values (?, ?, sysdate, ?, 'Content check-in via Editor.', null, ?, 'editor', current_timestamp)";

    public static final String INSERT_INTO_REND_WARNING_FLAG = "insert into cwb_src_rend_warning_flag (cwb_src_rend_warning_flag_uuid, cwb_src_rendition_uuid, warning_flag_type)" +
            "values (?, ?, 2)";

    public static final String INSERT_INTO_REND_ERROR_FLAG = "insert into cwb_src_rend_error_flag (cwb_src_rend_error_flag_uuid, cwb_src_rendition_uuid, error_flag_type)" +
            "values (?, ?, 9)";

    public static final String INSERT_INTO_SOURCE_FRONT = "insert into cwb_front_end_para (cwb_front_end_para_uuid, cwb_source_rendition_uuid, paragraph_type, paragraph_location, sibling_order, metadata_block, content_date, js_doc_type_id, doc_number, west_id, js_session_id, text) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_REND_PROC_PROPS_FLAG_WARNING = "update cwb_src_rend_proc_props set flag_to_display = 2 where cwb_src_rendition_uuid = ?";

    public static final String UPDATE_REND_PROC_PROPS_FLAG_ERROR = "update cwb_src_rend_proc_props set flag_to_display = 1 where cwb_src_rendition_uuid = ?";

    public static final String UPDATE_TARGET_LOCATION_FOR_ERROR = "update cwb_target_location set target_code = '18 SCA', target_section = '18 &sct 1' where cwb_delta_uuid = ?";

    public static final String DELETE_FROM_SRC_RENDITION_BASELINE = "delete from cwb_src_rendition_baseline where cwb_src_rendition_uuid = ?";

    public static final String DELETE_FROM_SRC_LINEAGE = "delete from cwb_src_lineage where cwb_src_lineage_uuid = ?";

    public static final String SELECT_LINEAGE_WITH_UUID = "select * from cwb_src_lineage where cwb_src_lineage_uuid = ?";

    public static final String DELETE_FROM_SRC_MANUAL_LOAD_PROPS = "delete from cwb_src_manual_load_props where cwb_src_rendition_uuid = ?";

    public static final String DELETE_FROM_SRC_RENDITION = "delete from cwb_src_rendition where cwb_src_rendition_uuid = ?";

    public static final String DELETE_FROM_SRC_REND_PROC_PROPS = "delete from cwb_src_rend_proc_props where cwb_src_rendition_uuid = ?";

    public static final String DELETE_FROM_SECTION_PROC_PROPS = "delete from cwb_section_proc_props where cwb_section_uuid in (select cwb_src_rendition_childuuid from cwb_src_rendition_child where cwb_src_rendition_uuid = ?)";

    public static final String DELETE_FROM_SRC_RENDITION_CHILD = "delete from cwb_src_rendition_child where cwb_src_rendition_uuid = ?";

    public static final String DELETE_FROM_SECTION_WARN_FLAG = "delete from cwb_section_warn_flag where cwb_section_uuid in (select cwb_src_rendition_childuuid from cwb_src_rendition_child where cwb_src_rendition_uuid = ?)";

    public static final String DELETE_FROM_DELTA_PROC_PROPS = "delete from cwb_delta_proc_props where cwb_delta_uuid in (select cwb_section_child_uuid from cwb_section_child where cwb_section_uuid in (select cwb_src_rendition_childuuid from cwb_src_rendition_child where cwb_src_rendition_uuid = ?))";

    public static final String DELETE_FROM_SECTION_CHILD = "delete from cwb_section_child where cwb_section_uuid in (select cwb_src_rendition_childuuid from cwb_src_rendition_child where cwb_src_rendition_uuid = ?)";

    public static final String DELETE_FROM_TARGET_LOCATION = "delete from cwb_target_location where cwb_delta_uuid in (select cwb_section_child_uuid from cwb_section_child where cwb_section_uuid in (select cwb_src_rendition_childuuid from cwb_src_rendition_child where cwb_src_rendition_uuid = ?))";

    public static final String DELETE_FROM_TARGET_LOC_CITELOC_ERR = "delete from cwb_target_loc_citeloc_err where cwb_target_location_uuid in (select cwb_target_location_uuid from cwb_target_location where cwb_delta_uuid in (select cwb_section_child_uuid from cwb_section_child where cwb_section_uuid in (select cwb_src_rendition_childuuid from cwb_src_rendition_child where cwb_src_rendition_uuid = ?)))";

    public static final String DELETE_FROM_TARGET_LOC_WARN_FLAG = "delete from cwb_target_loc_warn_flag where cwb_target_location_uuid in (select cwb_target_location_uuid from cwb_target_location where cwb_delta_uuid in (select cwb_section_child_uuid from cwb_section_child where cwb_section_uuid in (select cwb_src_rendition_childuuid from cwb_src_rendition_child where cwb_src_rendition_uuid = ?)))";

    public static final String DELETE_FROM_TARGET_LOC_ERR_FLAG = "delete from cwb_target_loc_err_flag where cwb_target_loc_uuid in (select cwb_target_location_uuid from cwb_target_location where cwb_delta_uuid in (select cwb_section_child_uuid from cwb_section_child where cwb_section_uuid in (select cwb_src_rendition_childuuid from cwb_src_rendition_child where cwb_src_rendition_uuid = ?)))";

    public static final String DELETE_FROM_DELTA_CHILD = "delete from cwb_delta_child where cwb_delta_uuid in (select cwb_section_child_uuid from cwb_section_child where cwb_section_uuid in (select cwb_src_rendition_childuuid from cwb_src_rendition_child where cwb_src_rendition_uuid = ?))";

    public static final String DELETE_FROM_SRC_REND_ERROR_FLAG = "delete from cwb_src_rend_error_flag where cwb_src_rendition_uuid = ?";

    public static final String DELETE_FROM_SRC_REND_WARNING_FLAG = "delete from cwb_src_rend_warning_flag where cwb_src_rendition_uuid = ?";

    public static final String DELETE_FROM_CWB_FRONT_END_PARA = "delete from cwb_front_end_para where cwb_source_rendition_uuid = ?";

    public static final String UPDATE_DELTA_COUNT = "update $tableName set delta_count = ? where $uuid = ?";

    public static final String DELETE_FROM_AI_EFFECTIVE_DATE = "delete from cwb_ai_effective_date_output where apv_src_rendition_uuid = ? OR prep_src_rendition_uuid = ?";

}