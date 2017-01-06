package com.mrpowergamerbr.millennium.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;

import spark.Request;
import spark.Response;

public class GlobalHandler {

	public static Object render(Request req, Response res) {
		String path = req.pathInfo();
		Object render = null;

		render = HomeView.render(req, res);
		
		PebbleTemplate compiledTemplate;
		StringWriter writer;
		String output;

		if (path.equalsIgnoreCase("/")) {
			render = HomeView.render(req, res);
		} else if (path.startsWith("/admin")) {
			render = AdminPanelView.render(req, res);
		} else if (path.startsWith("/posts")) {
			render = PostView.render(req, res);
		}
		
		if(render instanceof RenderWrapper) {
			try {
				Map<String, Object> context1 = ((RenderWrapper)render).context;
				context1.put("websiteUrl", Millennium.websiteUrl);
				
				compiledTemplate = ((RenderWrapper)render).pebble;
				writer = new StringWriter();
				compiledTemplate.evaluate(writer, context1);
				output = writer.toString();
				return output;
			} catch (Exception var11) {
				StringWriter db1 = new StringWriter();
				PrintWriter coll1 = new PrintWriter(db1);
				var11.printStackTrace(coll1);
				return db1.toString();
			}
		} else if(render == null) {
			HashMap<String, Object> context = new HashMap<String, Object>();
			context.put("websiteUrl", "http://mrpowergamerbr.com/");

			compiledTemplate = null;

			try {
				// compiledTemplate = SparklySunnyFunny.engine.getTemplate("404.html");
				throw new PebbleException(null, "Whoops!");
			} catch (PebbleException var14) {
				var14.printStackTrace();
			}

			writer = new StringWriter();

			try {
				compiledTemplate.evaluate(writer, context);
			} catch (PebbleException var12) {
				var12.printStackTrace();
			} catch (IOException var13) {
				var13.printStackTrace();
			}

			output = writer.toString();
			res.body(output);
			return output;
		} else {
			return (String)render;
		}

	}

}
