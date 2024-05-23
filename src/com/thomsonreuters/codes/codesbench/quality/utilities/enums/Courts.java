package com.thomsonreuters.codes.codesbench.quality.utilities.enums;

public enum Courts
{
    IOWA("IOWA", "IC33EE93014E411DA8874A85152525D58");

    private String name;
    private String courtUuid;

    Courts(String name, String courtUuid)
    {
        this.name = name;
        this.courtUuid = courtUuid;
    }

    public String getName()
    {
        return name;
    }

    public String getCourtUuid()
    {
        return courtUuid;
    }
}
