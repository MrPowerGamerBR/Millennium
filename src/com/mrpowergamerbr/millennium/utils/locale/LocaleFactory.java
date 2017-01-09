package com.mrpowergamerbr.millennium.utils.locale;

public class LocaleFactory {
	public static DefaultLocale getLocale(String lang) {
		DefaultLocale locale = new DefaultLocale();
		if (lang.equalsIgnoreCase("Portugu�s")) {
			locale = new PortugueseLocale();
		}
		return locale;
	}
}
