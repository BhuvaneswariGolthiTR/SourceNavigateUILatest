package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.sourceobjects;

public class SectionObject extends SourceObject
{

    private String sectionUUID;

    public SectionObject(String sectionUUID)
    {
        this.sectionUUID = sectionUUID;
    }

    public String getSectionUUID()
    {
        return sectionUUID;
    }

}
