package com.mrpowergamerbr.millennium.utils;

import java.nio.file.Paths;

import org.jooby.Jooby;

public class JoobyTest extends Jooby {

	{
		assets("/**", Paths.get("D:\\workspace\\Millennium\\Millennium\\static\\"));
		get("/**", () -> "Hey Jooby!");
		{
			port(4568);
		}
	}

	public static void main(final String[] args) {
		run(JoobyTest::new, args);
	}
}