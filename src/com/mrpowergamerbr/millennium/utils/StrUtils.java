package com.mrpowergamerbr.millennium.utils;

public class StrUtils {
	/**
	 * Converte um IP para algo que possa ser salvado no MongoDB
	 * 
	 * Ou seja, s� troca os "." por "_"
	 * 
	 * @param str
	 * @return
	 */
	public static String ip2mongo(String str) {
		return str.replace(".", "_");
	}
	
	/**
	 * Converte um IP do MongoDB para um IP padr�o
	 * 
	 * Ou seja, s� troca os "_" por "."
	 * 
	 * @param str
	 * @return
	 */
	public static String mongo2ip(String str) {
		return str.replace("_", ".");
	}
}
