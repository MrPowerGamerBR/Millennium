package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.jooby.Request;
import org.jooby.Response;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Post;

public class PostEditView {
	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();

			String[] args = req.path().split("/");
			// args[0] = ""
			// args[1] = "admin"
			// args[2] = "editpost"
			// args[3] = slug

			String slug = args[3];

			if (req.param("deletar").isSet() && req.param("deletar").equals("YES")) {
				Document doc = Millennium.client.getDatabase("millennium").getCollection("posts").find(Filters.eq("_id", new ObjectId(req.param("postId").value()))).first();

				if (doc != null) {
					Post post = Millennium.datastore.get(Post.class, doc.get("_id"));

					Millennium.datastore.delete(post);

					return AdminPanelView.render(req, res, "Post deletado com sucesso!");
				}
			}

			if (req.param("title").isSet()) {
				if (req.param("postContent").isSet()) {
					System.out.println("Getting from the database... " + req.param("postId"));

					Document doc = Millennium.client.getDatabase("millennium").getCollection("posts").find(Filters.eq("_id", new ObjectId(req.param("postId").value()))).first();

					if (doc != null) {
						Post post = Millennium.datastore.get(Post.class, doc.get("_id"));

						if (post != null) {
							post.setContent(req.param("postContent").value());
							post.setTitle(req.param("title").value());
							
							String strTags = req.param("tags").value();
							String[] split = strTags.split(", ");

							HashSet<String> tags = new HashSet<String>();

							for (String tag : split) {
								if (!tag.trim().isEmpty()) {
									tags.add(tag.trim());
								}
							}

							post.setTags(tags);
							
							Millennium.datastore.save(post);

							return AdminPanelView.render(req, res, "Post atualizado!");
						}
					}
					return AdminPanelView.render(req, res, "Post n�o existe!");
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
