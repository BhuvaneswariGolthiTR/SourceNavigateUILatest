package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;

import java.io.File;

public class PublicationFilesPageElements extends OCExtractBasePageElements
{
    public static final String TABLE_NAME = "Files";
    public static final String TAB_PANEL_NUMBER = "1";
    public static final String YEAR_FIELD_LABEL = TAB_PANEL_DIV + "//div[contains(@class, 'publication-detail-container')]//dt[text()='Year']";
    public static final String PUBLICATION_NAME_FIELD_LABEL = TAB_PANEL_DIV + "//div[contains(@class, 'publication-detail-container')]//dt[text()='Name']";
    public static final String PUBLICATION_DETAIL_FIELD = "/following-sibling::dd[1]";
    public static final String YEAR_FIELD = YEAR_FIELD_LABEL + PUBLICATION_DETAIL_FIELD;
    public static final String NAME_FIELD = PUBLICATION_NAME_FIELD_LABEL + PUBLICATION_DETAIL_FIELD;
    public static final String DOWNLOAD_DIR_PATH = FileUtils.DOWNLOAD_FOLDER_PATH + File.separator;
    public static final String PUB_FILE_PDF_REGEX = "[A-Z\\d]{33}\\.pdf";
    public static final String UPLOAD_PDF_POPUP = "//div[@id='cdk-overlay-1']//h1[text()='Upload PDF']";
}
