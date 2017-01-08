package com.mrpowergamerbr.millennium.utils;

import java.util.regex.Pattern;

public class StrUtils {
	public static final Pattern datePatt = Pattern.compile("([0-9]+)/([0-9]+)/([0-9]+)");
	/**
	 * Converte um IP para algo que possa ser salvado no MongoDB
	 * 
	 * Ou seja, só troca os "." por "_"
	 * 
	 * @param str
	 * @return
	 */
	public static String ip2mongo(String str) {
		return str.replace(".", "_");
	}
	
	/**
	 * Converte um IP do MongoDB para um IP padrão
	 * 
	 * Ou seja, só troca os "_" por "."
	 * 
	 * @param str
	 * @return
	 */
	public static String mongo2ip(String str) {
		return str.replace("_", ".");
	}
}
