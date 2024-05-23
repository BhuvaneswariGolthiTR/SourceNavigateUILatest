package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.sourceobjects;

public class LineageObject extends SourceObject
{

    private String lineageUUID;
    private long docNumber;

    public LineageObject(String lineageUUID, long docNumber)
    {
        this.lineageUUID = lineageUUID;
        this.docNumber = docNumber;
    }

    public String getLineageUUID()
    {
        return lineageUUID;
    }

    public long getDocNumber()
    {
        return docNumber;
    }

}
