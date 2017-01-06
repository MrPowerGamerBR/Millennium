package com.mrpowergamerbr.millennium.utils;

import java.util.Calendar;

public class DateUtils {
	public static String getFancyDay(int day) {
		switch (day) {
		case Calendar.MONDAY:
			return "Segunda-feira";
		case Calendar.TUESDAY:
			return "Terça-feira";
		case Calendar.WEDNESDAY:
			return "Quarta-feira";
		case Calendar.THURSDAY:
			return "Quinta-feira";
		case Calendar.FRIDAY:
			return "Sexta-feira";
		case Calendar.SATURDAY:
			return "Sábado";
		case Calendar.SUNDAY:
			return "Domingo";
		default:
			return "Deu ruim gente!";
		}
	}
	
	public static String addZeroIfNeeded(int i) {
		if (10 > i) {
			return "0" + i;
		}
		return String.valueOf(i);
	}
	
	public static String addZeroIfNeeded(String str) {
		if (str.length() == 1) {
			return "0" + str;
		}
		return str;
	}
}
