package com.thomsonreuters.codes.codesbench.quality.utilities.enums;

public enum ContentSets
{
    CALIFORNIA_DEVELOPMENT("California (Development)", "2"),
    POP_NAME_UPT("PopName (UPT)","77"),
    USCA_DEVELOPMENT("USCA(Development)","100"),
    NEW_YORK_DEVELOPMENT("New York (Development)","101"),
    TEXAS_DEVELOPMENT("Texas (Development)","102"),
    TEXAS_ANNOTATED_RULES_DEVELOPMENT("Texas Annotated Rules (Development)","103"),
    MAINE_DEVELOPMENT("Maine (Development)","104"),
    NEW_JERSEY_DEVELOPMENT("New Jersey (Development)","105"),
    IOWA_DEVELOPMENT("Iowa (Development)", "106"),
    CODE_OF_FED_REGS_DEVELOPMENT("Code of Fed Regs (Development)", "109"),
    US_FEDERAL_RULES_DEVELOPMENT("US Federal Rules (Development)","111"),
    FLORIDA_DEVELOPMENT("Florida (Development)", "113"),
    WASHINGTON_DEVELOPMENT("Washington (Development)","115"),
    ARIZONA_DEVELOPMENT("Arizona (Development)","116"),
    ALABAMA_DEVELOPMENT("Alabama (Development)", "121"),
    ARKANSAS_DEVELOPMENT("Arkansas (Development)", "124"),
    MINNESOTA_DEVELOPMENT("Minnesota (Development)","125"),
    OHIO_DEVELOPMENT("Ohio (Development)","128"),
    MISSISSIPPI_DEVELOPMENT("Mississippi (Development)","135"),
    COLORADO_DEVELOPMENT("Colorado (Development)","141"),
    SOUTH_DAKOTA_DEVELOPMENT("South Dakota (Development)","145"),
    IDAHO_DEVELOPMENT("Idaho (Development)", "148"),
    ALASKA_DEVELOPMENT("Alaska (Development)", "167"),
    ILLINOIS_ADMIN_CODE_DEVELOPMENT("Illinois Admin Code (Development)","334"),
    ARIZONA_ADMIN_CODE_DEVELOPMENT("Arizona Admin Code (Development)","338"),
    SWITZERLAND_FINMA("Switzerland FINMA","8114"),
    SWITZERLAND_SIX_SWISS_EXCHANGE("Switzerland Six Swiss Exchange","8115"),
    SWITZERLAND_LEGISLATIVE("Switzerland Legislative","8116"),
    REG_GUIDANCE_SUMMARY_UK("Reg Guidance Summary UK","8020"),
    REG_GUIDANCE_SUMMARY_CAN("Reg Guidance Summary CAN","8021"),
    REG_GUIDANCE_SUMMARY_FINRA("Reg Guidance Summary FINRA","8037"),
    REG_GUIDANCE_SUMMARY_US("Reg Guidance Summary US","8040"),
    REG_GUIDANCE_SUMMARY_NJ("Reg Guidance Summary NJ","8041"),
    REG_GUIDANCE_SUMMARY_NE("Reg Guidance Summary NE","8071"),
    FINRA("FINRA","8105"),
    HONG_KONG_LEG_REG("Hong Kong Leg-Reg","8106"),
    LIECHTENSTEIN_LEGISLATIVE("Liechtenstein Legislative","8112"),
    LIECHTENSTEIN_REGULATORY("Liechtenstein Regulatory","8113"),
    MEXICO_LEGISLATIVE("Mexico Legislative","8117"),
    GERMANY_EXCHANGES("Germany Exchanges","8118"),
    GERMANY_BA_FIN("Germany BaFin","8119"),
    GERMAN_LEGISLATIVE("German Legislative","8120"),
    NETHERLANDS_LEGISLATIVE("Netherlands Legislative","8121"),
    MEXICO_REGULATORY("Mexico Regulatory","8122"),
    LUXEMBOURG_LEGISLATIVE("Luxembourg Legislative","8123"),
    LUXEMBOURG_REGULATORY("Luxembourg Regulatory","8124"),
    FRANCE_REGULATORY("France Regulatory","8125"),
    EUROPEAN_MATERIALS("European Materials","8137"),
    CROWN_DEPENDENCIES("Crown Dependencies","8140"),
    NASDAQ_STOCK_MARKET_LLC("NASDAQ Stock Market LLC","8145"),
    BOX_OPTIONS_EXCHANGE_BOX("BOX Options Exchange (BOX)","8148"),
    NEW_YORK_STOCK_EXCHANGE_NYSE("New York Stock Exchange (NYSE)","8152"),
    FCA_HANDBOOK("FCA Handbook","8170"),
    CANADA_ONTARIO_DEVELOPMENT("Canada Ontario (Development)", "3012"),
    CANADA_FEDERAL_DEVELOPMENT("Canada Federal", "3001"),
    PENNSYLVANIA_ADMIN_CODE_DEVELOPMENT("Pennsylvania Admin Code (Development)", "215"),
    MONTANA_DEVELOPMENT("Montana (Development)", "171"),
    UNIFORM_LAWS_ANNOTATED("Uniform Laws Annotated (Development)", "138"),
    GEORGIA_DEVELOPMENT("Georgia (Development)", "120"),
    UK_LEGISLATIVE("UK Legislative","4000");

    private String name;
    private String code;

    ContentSets(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }
}
