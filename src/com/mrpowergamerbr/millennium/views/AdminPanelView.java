package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Post;

import spark.Request;
import spark.Response;
import spark.Session;

public class AdminPanelView {
	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();
			
			Session session = req.session(true);

			if (session.attribute("loggedInAs") != null) {
				context.put("loggedInAs", session.attribute("loggedInAs"));
			} else {
				return LoginPanelView.render(req, res);
			}
			
			if (req.pathInfo().startsWith("/admin/login")) { // Debug
				return LoginPanelView.render(req, res);
			} else if (req.pathInfo().startsWith("/admin/createpost")) {
				return PostCreateView.render(req, res);
			} else if (req.pathInfo().startsWith("/admin/editpost")) {
				return PostEditView.render(req, res);
			}
			
			long count = Millennium.client.getDatabase("millennium").getCollection("posts").count();
			
			context.put("postsPublicados", count);
			
			ArrayList<Post> posts = Millennium.getAllPosts();
			
			context.put("posts", posts);
			
			PebbleTemplate template = Millennium.engine.getTemplate("panel.html");

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
