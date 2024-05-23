package com.thomsonreuters.codes.codesbench.quality.utilities.enums;

public enum SpecialCharacters
{
        SECT("\u00A7", "&sect;", "sect"),       //§
        EMSP("\u0192", "&emsp;", "emsp"),       //ƒ
        BULL("\u2022", "&bull;", "bull"),       //•
        PARA("\u00B6", "&para;", "para"),       //¶
        NBSP("\u00A0", "&nbsp;", "nbsp"),       //
        NDASH("\u2013", "&ndash;", "ndash"),    //–
        KEY("", "&TLRKey;", "TLRKey"),
        ENSP("\u2002", "&ensp;", "ensp"),       // 
        MDASH("\u2014", "&mdash;", "mdash"),    //—
        THINSP("\u2009", "&thinsp;", "thinsp"), // 
        AMP("\u0026", "&amp;", "amp"),          //&
        HYPHEN("\u2010", "&hyphen;", "hyphen"); //-

        private final String character;
        private final String html;
        private final String entity;

        SpecialCharacters(String character, String html, String entity)
        {
                this.character = character;
                this.html = html;
                this.entity = entity;
        }

        public String getCharacter()
        {
                return character;
        }

        public String getHtml()
        {
                return html;
        }

        public String getEntity()
        {
                return entity;
        }

        public static String convertHtmlCodesToCharacters(String text)
        {
                for (SpecialCharacters character : SpecialCharacters.values())
                {
                        text = text.replace(character.getHtml(), character.getCharacter());
                }
                return text;
        }
}
