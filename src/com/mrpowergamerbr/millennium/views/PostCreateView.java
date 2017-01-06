package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Post;

import spark.Request;
import spark.Response;

public class PostCreateView {
	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();
			
			System.out.println("Título: " + req.queryParams("title"));
			System.out.println("Conteúdo: " + req.queryParams("postContent"));
			
			if (req.queryParams("title") != null) {
				if (req.queryParams("postContent") != null) {
					Post post = new Post();
					post.authorId = req.session().attribute("loggedInId");
					post.content = req.queryParams("postContent");
					post.slug = Millennium.slg.slugify(req.queryParams("title"));
					post.title = req.queryParams("title");
					
					Millennium.datastore.save(post);
					
					System.out.println("Post criado!");
				}
			}
			PebbleTemplate template = Millennium.engine.getTemplate("postcreate.html");

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
