package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source;

import java.util.ArrayList;

/**
 * @deprecated Deprecated in favor of SourceDataMockingNew
 */
public interface SourceDataMockingInterface
{

    String insertReturnRenditionUuid(String environment);

    SourceDocument insertReturnRenditionObject(String environment);

    ArrayList<String> insertReturnRenditionUuidList(String environment, int numOfRenditions);

}
