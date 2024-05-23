package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.sourceobjects;

public class DeltaObject extends SourceObject
{

    private String deltaUUID;

    public DeltaObject(String deltaUUID)
    {
        this.deltaUUID = deltaUUID;
    }

    public String getDeltaUUID()
    {
        return deltaUUID;
    }

}
