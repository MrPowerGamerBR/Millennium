package com.mrpowergamerbr.millennium.views;

import org.jooby.Request;
import org.jooby.Response;

public class PageView {
	public static Object render(Request req, Response res) {
		return PostView.render(req, res, true);
	}
}
