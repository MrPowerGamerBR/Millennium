package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.StrUtils;
import com.mrpowergamerbr.millennium.utils.blog.Page;
import com.mrpowergamerbr.millennium.utils.blog.Post;
import spark.Request;
import spark.Response;

public class PageView {

	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();

			String[] args = req.pathInfo().split("/");
			// args[0] = ""
			// args[1] = "posts"
			// args[2] = "slug"

			String slug = args[2];

			Document doc = Millennium.client.getDatabase("millennium").getCollection("pages").find(Filters.eq("slug", slug)).first();

			if (doc != null) {
				Post post = Millennium.datastore.get(Page.class, doc.get("_id"));

				context.put("post", Millennium.fillPost(post));

				// Adicionar uma nova view somente se a última visualização foi a mais de 60m				
				if (TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - post.getViewCache().getOrDefault(StrUtils.ip2mongo(req.ip()), 0L)) > 60) {
					post.getViewCache().put(StrUtils.ip2mongo(req.ip()), System.currentTimeMillis());
					
					post.setViewCount(post.getViewCount() + 1);
					
					Millennium.datastore.save(post);
				}
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
