package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod;

public class SourceDatapodConfiguration
{
    private static SourceDatapodConfiguration config;

    public static SourceDatapodConfiguration getConfig()
    {
        if(config == null)
        {
            config = new SourceDatapodConfiguration();
        }
        return config;
    }

    private SourceDatapodType type;
    private int year;
    private SrcContentType contentType;

    private SourceDatapodConfiguration()
    {
        this.type = SourceDatapodType.LEGAL;
        this.year = 2021;
        this.contentType = SrcContentType.LAW;
    }

    public void loadDefaultConfig()
    {
        this.type = SourceDatapodType.LEGAL;
        this.year = 2021;
        this.contentType = SrcContentType.LAW;
    }

    /**
     * @deprecated RISK is not functional yet
     */
    public void loadDefaultRiskConfig()
    {
        this.type = SourceDatapodType.RISK;
        this.year = 2021;
        this.contentType = SrcContentType.LAW;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getYear()
    {
        return year;
    }

    public void setSrcContentType(SrcContentType contentType)
    {
        this.contentType = contentType;
    }

    public SrcContentType getContentType()
    {
        return contentType;
    }

    public SourceDatapodType getType()
    {
        return this.type;
    }
}
