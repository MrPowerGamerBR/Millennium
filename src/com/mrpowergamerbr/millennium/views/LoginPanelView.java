package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.HashMap;

import org.bson.Document;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Author;

import spark.Request;
import spark.Response;
import spark.Session;

public class LoginPanelView {
	public static Object render(Request req, Response res) {
		try {		    
			HashMap<String, Object> context = new HashMap<String, Object>();

			context.put("loginStatus", "WAITING");

			Session session = req.session(true);


			if (session.attribute("loggedInAs") != null) {
				context.put("loggedInAs", session.attribute("loggedInAs"));
			} else {
				context.put("loggedInAs", null);
			}

			String password = null;
			String username = null;

			if (req.queryParams("password") != null) {
				password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(req.queryParams("password"));  
			}
			if (req.queryParams("login") != null) {
				username = req.queryParams("login");
			}
			if (username != null && password != null) {
				if (username.equals("logout")) {
					session.attribute("loggedInAs", null);
				} else {
					Document doc = Millennium.client
							.getDatabase("millennium")
							.getCollection("authors")
							.find(
									Filters
									.and(
											Filters.eq("authorName", username),
											Filters.eq("password", password)
											)
									).first();

					if (doc != null) {
						Author author = Millennium.datastore.get(Author.class, doc.get("_id"));

						context.put("loginStatus", "SUCCESS");

						session.attribute("loggedInAs", author.authorName);
						session.attribute("loggedInId", author.id);
						
						res.redirect(Millennium.websiteUrl + "admin");
					} else {
						context.put("loginStatus", "FAIL");
					}
				}
			}

			PebbleTemplate template = Millennium.engine.getTemplate("login.html");

			return new RenderWrapper(template, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return e.toString();
		}
	}
}