package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import org.bson.Document;
import org.jooby.Request;
import org.jooby.Response;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Post;

public class PostView {
	public static Object render(Request req, Response res) {
		return render(req, res, false);
	}

	public static Object render(Request req, Response res, boolean isPage) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();

			String[] args = req.path().split("/");

			if (args.length >= 3) {
				String slug = args[2];

				Document doc = Millennium.client.getDatabase("millennium").getCollection(isPage ? "pages" : "posts").find(Filters.eq("slug", slug)).first();

				if (doc != null) {
					Post post = Millennium.datastore.get(Post.class, doc.get("_id"));

					context.put("post", Millennium.fillPost(post));

					post.addOneMoreView(req);
				} else {
					return Error404View.render(req, res);
				}

				PebbleTemplate template = Millennium.engine.getTemplate("post.html");

				return new RenderWrapper(template, context);
			} else {
				return Error404View.render(req, res);
			}
		} catch (PebbleException e) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return e.toString();
		}
	}
}