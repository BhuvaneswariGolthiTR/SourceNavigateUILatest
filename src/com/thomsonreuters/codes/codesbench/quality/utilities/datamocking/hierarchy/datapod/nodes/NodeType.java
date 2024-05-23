package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes;

public enum NodeType
{

    // NOTE TO PEOPLE IN THE FUTURE: Even though NodeType.GLOSSARY is deprecated, DO NOT DELETE IT. This could break multiple switch-statements involved with Hierarchy Datapods.
    TITLE, SUBTITLE, CHAPTER, SECTION, NOD_CONTAINER, XND, BL_ANALYSIS, BLUELINE, GRADE, RULE, PART, NDRS,
    ARL, SUBCHAPTER, ARTICLE, DIVISION, /** @deprecated Glossary nodes are of type "=" in the database which is a section type */ GLOSSARY;

}
