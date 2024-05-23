package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.sourceobjects;

public class RenditionObject extends SourceObject
{

    private String renditionUUID;
    private long docNumber;

    public RenditionObject(String renditionUUID, long docNumber)
    {
        this.renditionUUID = renditionUUID;
        this.docNumber = docNumber;
    }

    public String getRenditionUUID()
    {
        return renditionUUID;
    }

    public long getDocNumber()
    {
        return docNumber;
    }

}
