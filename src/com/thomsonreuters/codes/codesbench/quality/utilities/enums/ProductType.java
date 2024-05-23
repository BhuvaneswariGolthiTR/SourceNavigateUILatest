package com.thomsonreuters.codes.codesbench.quality.utilities.enums;

public enum ProductType {

    ALL_PRODUCTS("All Products"),
    ARI_RULEBOOK("ARI Rulebook"),
    WIC_ENFLEX("WIC/EnFlex"),
    PRACTICE_POINT_RULEBOOK("Practice Point Rulebook"),
    PRACTICE_POINT_RULEBOOK_ACT_SECTION("Practice Point Rulebook-Act Section"),
    VRB("VRB"),
    JURISDICTIONAL_IDENTIFICATION("Jurisdictional Identification"),
    COURT_RULE_ID("Court Rule ID"),
    STATUTE_ID("Statute ID"),
    CHECKPOINT("Checkpoint", "CP_A"),
    TAX_TYPE("Tax Type", "TT_B1"),
    ORDINANCE_ID("Ordinance ID", "OI_A1");

    public String getType() {
        return type;
    }

    public String getTagView() {
        return tagView;
    }

    private String type;
    private String tagView;

    ProductType(String productType) {
        this.type = productType;
    }

    ProductType(String productType, String productTagView) {
        this.type = productType;
        this.tagView = productTagView;
    }
}
