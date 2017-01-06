package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Post;

import spark.Request;
import spark.Response;

public class PostEditView {
	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();

			String[] args = req.pathInfo().split("/");
			// args[0] = ""
			// args[1] = "admin"
			// args[2] = "editpost"
			// args[3] = slug

			String slug = args[3];


			System.out.println("What?");

			if (req.queryParams("title") != null) {
				if (req.queryParams("postContent") != null) {
					System.out.println("Getting from the database... " + req.queryParams("postId"));

					Document doc = Millennium.client.getDatabase("millennium").getCollection("posts").find(Filters.eq("_id", new ObjectId(req.queryParams("postId")))).first();

					if (doc != null) {
						Post post = Millennium.datastore.get(Post.class, doc.get("_id"));

						if (post != null) {
							post.setContent(req.queryParams("postContent"));
							post.setTitle(req.queryParams("title"));

							Millennium.datastore.save(post);

							System.out.println("Post atualizado!");
						} else {
							System.out.println("null");
						}
					} else {
						System.out.println("Triste a vida... documento n�o existe");
					}
				}
			}

			Document doc = Millennium.client.getDatabase("millennium").getCollection("posts").find(Filters.eq("slug", slug)).first();

			if (doc != null) {
				Post post = Millennium.datastore.get(Post.class, doc.get("_id"));

				context.put("post", post);
			}

			context.put("tags", Millennium.getAllTags());
			
			PebbleTemplate template = Millennium.engine.getTemplate("postedit.html");

			return new RenderWrapper(template, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			e.printStackTrace();
			return e.toString();
		}
	}
}
