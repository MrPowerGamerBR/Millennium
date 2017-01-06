package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Post;
import spark.Request;
import spark.Response;

public class PostView {

	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();
			
			String[] args = req.pathInfo().split("/");
			// args[0] = ""
			// args[1] = "posts"
			// args[2] = "slug"
			
			String slug = args[2];
			
			Document doc = Millennium.client.getDatabase("millennium").getCollection("posts").find(Filters.eq("slug", slug)).first();

			if (doc != null) {
				Post post = Millennium.datastore.get(Post.class, doc.get("_id"));
				
				context.put("post", Millennium.fillPost(post));
			} else {
				
			}
			
			PebbleTemplate template = Millennium.engine.getTemplate("post.html");

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
