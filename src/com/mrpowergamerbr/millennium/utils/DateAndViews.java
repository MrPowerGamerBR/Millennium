package com.mrpowergamerbr.millennium.utils;

import java.util.Calendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DateAndViews {
	public final long views;
	public final Calendar calendar;
	public String fancyDate;
}
