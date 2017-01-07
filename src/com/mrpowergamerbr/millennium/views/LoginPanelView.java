package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.HashMap;

import org.bson.Document;
import org.jooby.Request;
import org.jooby.Response;
import org.jooby.Session;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Author;

public class LoginPanelView {
	public static Object render(Request req, Response res) {
		try {		    
			HashMap<String, Object> context = new HashMap<String, Object>();

			context.put("loginStatus", "WAITING");

			Session session = req.session();


			if (session.get("loggedInAs").isSet()) {
				context.put("loggedInAs", session.get("loggedInAs").value());
			} else {
				context.put("loggedInAs", null);
			}

			String password = null;
			String username = null;

			if (req.param("password").isSet()) {
				password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(req.param("password").value());  
			}
			if (req.param("login").isSet()) {
				username = req.param("login").value();
			}
			if (username != null && password != null) {
				if (username.equals("logout")) {
					session.set("loggedInAs", null);
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

						session.set("loggedInAs", author.authorName);
						session.set("loggedInId", author.id.toString());
						
						try {
							res.redirect(Millennium.websiteUrl + "admin");
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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