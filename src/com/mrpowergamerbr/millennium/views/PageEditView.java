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
import com.mrpowergamerbr.millennium.utils.blog.Page;
import com.mrpowergamerbr.millennium.utils.blog.Post;

public class PageEditView {
	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();

			String[] args = req.path().split("/");
			// args[0] = ""
			// args[1] = "admin"
			// args[2] = "editpost"
			// args[3] = slug

			String slug = args[3];

			if (req.param("deletar").isSet() && req.param("deletar").value().equals("YES")) {
				Document doc = Millennium.client.getDatabase("millennium").getCollection("pages").find(Filters.eq("_id", new ObjectId(req.param("postId").value()))).first();

				if (doc != null) {
					Post post = Millennium.datastore.get(Post.class, doc.get("_id"));

					Millennium.datastore.delete(post);

					return AdminPanelView.render(req, res, "Post deletado com sucesso!");
				}
			}

			if (req.get("title") != null) {
				if (req.param("postContent") != null) {
					System.out.println("Getting from the database... " + req.param("postId").value());

					Document doc = Millennium.client.getDatabase("millennium").getCollection("pages").find(Filters.eq("_id", new ObjectId(req.param("postId").value()))).first();

					if (doc != null) {
						Post post = Millennium.datastore.get(Page.class, doc.get("_id"));

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

							return AdminPanelView.render(req, res, "Página atualizada!");
						}
					}
					return AdminPanelView.render(req, res, "Página não existe!");
				}
			}

			Document doc = Millennium.client.getDatabase("millennium").getCollection("pages").find(Filters.eq("slug", slug)).first();

			if (doc != null) {
				Post post = Millennium.datastore.get(Page.class, doc.get("_id"));

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
