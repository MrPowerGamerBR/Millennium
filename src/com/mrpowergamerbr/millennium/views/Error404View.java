package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import org.jooby.Request;
import org.jooby.Response;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;

public class Error404View {
	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();
			
			PebbleTemplate template = Millennium.engine.getTemplate("404.html");

			return new RenderWrapper(template, context);
		} catch (PebbleException e) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return e.toString();
		}
	}
}
