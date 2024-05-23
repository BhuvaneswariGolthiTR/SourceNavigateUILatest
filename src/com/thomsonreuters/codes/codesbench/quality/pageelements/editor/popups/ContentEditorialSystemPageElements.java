package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

public class ContentEditorialSystemPageElements
{
	public static final String PAGE_TITLE = "Content Editorial System";
	public static final String MNEMONIC_DROPDOWN_BUTTON = "//span[@id='mnemonicToggle']//button";
	public static final String MNEMONIC_HINT_LIST = "//div[@id='mnemonicContainer']//ul";
	public static final String MNEMONIC_HINT_EXPECTED = "//div[@id='mnemonicContainer']//li[text()='%s']";
	public static final String MNEMONIC_HIGHLIGHTED_HINT = "//div[@id='mnemonicContainer']//li[contains(@class, 'highlight') and text()='%s']";
	public static final String MNEMONIC_INPUT_SELECT = "//input[@id='mnemonicInput']";
	public static final String ENGLISH_LABEL = "//input[@id='pageForm:englishLabelValue']";
	public static final String STOCKNOTE_INPUT ="//input[@id='stocknoteInput']";

	public enum StockNote
	{
		INSERTED_MATERIAL("Inserted Material");

		private final String name;

		StockNote(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return name;
		}
	}
}
